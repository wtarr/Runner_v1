package com.runner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class RunnerGame extends ScreenAdapter {
    SpriteBatch batch;
    Texture img;


    private Viewport viewport;
    private com.badlogic.gdx.graphics.Camera camera;
    private com.badlogic.gdx.graphics.Camera yFlippedCameraProjection;

    private boolean flipped;

    private ShapeRenderer shapeRenderer;

    private final float WORLD_WIDTH = 640;
    private final float WORLD_HEIGHT = 480;

    private Vector2 originalCamPos = new Vector2(WORLD_WIDTH/2, WORLD_HEIGHT/2);

    private final float floor = 50f;

    private Player player;
    private CloudManager cloudManager;

    private Array<Obstacle> obstacleArray = new Array<Obstacle>();
    private Array<PickUp> pickUpArray = new Array<PickUp>();

    // private final float MAX_OBSTACLES = 5;
    private final float MIN_DISTANCE_BETWEEN_OBSTACLES = 100; // reduce to increase difficulty
    private Vector2 obstacleVelocity = new Vector2(-100, 0); // scale to increase difficulty

    private GlyphLayout layout = new GlyphLayout();
    private BitmapFont bitmapFont;

    private float playTime;

    private DestructableManager destructableManager;

    private int obstacleCount;
    private int nextPickup;
    private int min = 5, max = 10;

    private enum State {
        Playing,
        GameOver
    }

    private State currentGameState = State.Playing;

    public RunnerGame() {
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(originalCamPos.x, originalCamPos.y, 0);


        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        player = new Player(floor);
        cloudManager = new CloudManager(WORLD_WIDTH, WORLD_HEIGHT);

        bitmapFont = new BitmapFont();

        //destructableComponent = new DestructableComponent();
        destructableManager = new DestructableManager(3);

        Obstacle obs = new Obstacle(WORLD_WIDTH, floor, obstacleVelocity);
        obs.commission();

        obstacleArray.add(obs);
        obstacleCount++;
        nextPickup += MathUtils.random(min, max);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {

        update(delta);

        Matrix4 m;
        if (!flipped) {
            m = camera.projection.cpy().mul(new Matrix4(new float[]{
                    1, 0 , 0, 0,
                    0, 1, 0, 0,
                    0, 0,  1, 0,
                    0, 0,  0, 1}));
        }
        else
        {
            m = camera.projection.cpy().mul(new Matrix4(new float[]{
                    -1, 0 , 0, 0,
                    0, 1, 0, 0,
                    0, 0,  1, 0,
                    0, 0,  0, 1}));
        }

        clearScreen();

        shapeRenderer.setProjectionMatrix(m);
        shapeRenderer.setTransformMatrix(camera.view);

        // do filled stuff
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);        // render the ground
        shapeRenderer.rect(0, 40, WORLD_WIDTH,  floor - 40); // ground

        cloudManager.renderDebug(shapeRenderer);
        for (Obstacle obs : obstacleArray) {
            obs.renderDebug(shapeRenderer);
        }

        destructableManager.renderDebug(shapeRenderer);

        for (PickUp p : pickUpArray)
        {
            p.renderDebug(shapeRenderer);
        }

        shapeRenderer.end();

        // do line stuff
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        player.renderDebug(shapeRenderer);
        shapeRenderer.end();

        batch.setProjectionMatrix(m);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        String text = "Score: " + Integer.toString((int) playTime);
        layout.setText(bitmapFont, text);
        bitmapFont.setColor(Color.CYAN);
        bitmapFont.draw(batch, text, WORLD_WIDTH / 2 - layout.width / 2, WORLD_HEIGHT / 2);

        if (currentGameState == State.GameOver) {
            String go = "Game Over";
            layout.setText(bitmapFont, text);
            bitmapFont.setColor(Color.RED);
            bitmapFont.draw(batch, go, WORLD_WIDTH / 2 - layout.width / 2, WORLD_HEIGHT / 2 - 30);
        }

        batch.end();

    }


    private void update(float delta) {
        if (currentGameState == State.Playing) {

            playTime += delta;
            player.update(delta);
            cloudManager.update(delta);

            for (Obstacle obs : obstacleArray) {
                obs.update(delta);
            }

            for (PickUp pickUp : pickUpArray)
            {
                pickUp.update(delta);
            }

            checkForObstacleCollision();
            checkIfNewObstacleCanBeAdded();
            checkIsObstacleGoneOffScreen();
            checkIfNewPickupCanBePlaced();
            checkForPickupCollision();

            //destructableComponent.update(delta);
            destructableManager.update(delta);
            destructableManager.checkIfOutOfScope();

            cameraShake(delta);

        } else if (currentGameState == State.GameOver) {

        }

        if (Gdx.input.isKeyPressed(Input.Keys.F))
        {
            if (!flipped)
                flipped = true;
            else
                flipped = false;
        }

    }


    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void checkIfNewObstacleCanBeAdded() {
        if (obstacleArray.get(obstacleArray.size - 1).getPosition().x < (WORLD_WIDTH - MIN_DISTANCE_BETWEEN_OBSTACLES)) {
            Obstacle obs = new Obstacle(WORLD_WIDTH, floor, obstacleVelocity);
            obs.commission();

            obstacleArray.add(obs);
            obstacleCount++;

        }
    }

    private void checkIfNewPickupCanBePlaced()
    {
        if (obstacleCount > nextPickup)
        {
            // todo place pickup
            // get last item in current obstacle array
            Obstacle o = obstacleArray.get(obstacleArray.size-1);

            PickUp p = new PickUp(new Vector2(o.getPosition().x + MIN_DISTANCE_BETWEEN_OBSTACLES/2 + o.getCollisionRectangle().width, o.getPosition().y), o.getVelocity());
            pickUpArray.add(p);
            nextPickup += MathUtils.random(min, max);
        }


    }

    private void checkIsObstacleGoneOffScreen() {
        for (Obstacle obs : obstacleArray) {
            if (obs.getPosition().x < 0) {
                obstacleArray.removeValue(obs, false);
            }
        }
    }

    private void checkForObstacleCollision() {
        for (Obstacle obstacle : obstacleArray) {
            if (player.getCollisionRectangle().overlaps(obstacle.getCollisionRectangle())) {
                destructableManager.commissionADestructable(obstacle.getCollisionRectangle());

                obstacleArray.removeValue(obstacle, false);

                shake = 10f;

                break;
            }
        }
    }

    private void checkForPickupCollision() {
        for (PickUp p : pickUpArray) {
            if (player.getCollisionRectangle().overlaps(p.getCollisionRectangle())) {

                pickUpArray.removeValue(p, false);

                if (flipped)
                    flipped = false;
                else
                    flipped = true;

                break;
            }
        }
    }

    private float shake = 0f;
    private float decreaseFactor = 9f;

    // https://gist.github.com/ftvs/5822103
    private void cameraShake(float delta) {
        if (shake == 0 ) return;
        if (shake > 0)
        {
            Vector2 newCamPos = originalCamPos.cpy().add(randomInsideUnitCircle().scl(shake));

            shake -= delta * decreaseFactor;

            camera.position.set(newCamPos.x, newCamPos.y, 0);
            camera.update();
        }
        else
        {
            shake = 0;
            camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
            camera.update();
        }
    }

    // http://stackoverflow.com/a/5838055
    private Vector2 randomInsideUnitCircle()
    {
        double t = 2 * 3.14 * MathUtils.random();
        //double u = MathUtils.random() + MathUtils.random();
        //double r =  u > 1 ? 2 - u : u;

        double r = Math.sqrt(MathUtils.random());
        return new Vector2( (float)(r * Math.cos(t)), (float)(r*Math.sin(t)));
    }
}
