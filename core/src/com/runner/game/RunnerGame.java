package com.runner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RunnerGame extends ScreenAdapter {
	SpriteBatch batch;
	Texture img;


	private Viewport viewport;
	private com.badlogic.gdx.graphics.Camera camera;
	private ShapeRenderer shapeRenderer;

	private final float WORLD_WIDTH = 640;
	private final float WORLD_HEIGHT = 480;

	private final float floor = 10f;

	private Player player;
	private CloudManager cloudManager;

	private Array<Obstacle> obstacleArray = new Array<Obstacle>();

    private final float MAX_OBSTACLES = 4;
	private final float MIN_DISTANCE_BETWEEN_OBSTACLES = 200;

    private GlyphLayout layout = new GlyphLayout();
    private BitmapFont bitmapFont;

    private float playTime;

	private enum State {
        Playing,
        GameOver
    }

    private State currentGameState = State.Playing;

	public RunnerGame() {
	}

	@Override
	public void show () {
		camera = new OrthographicCamera();
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		camera.update();
		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

		shapeRenderer = new ShapeRenderer();

		batch = new SpriteBatch();

		player = new Player(floor);
		cloudManager = new CloudManager(WORLD_WIDTH, WORLD_HEIGHT);

        bitmapFont = new BitmapFont();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render (float delta) {

		update(delta);

		clearScreen();

		shapeRenderer.setProjectionMatrix(camera.projection);
		shapeRenderer.setTransformMatrix(camera.view);

		// do filled stuff
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.GREEN);		// render the ground
		shapeRenderer.rect(0, 0, WORLD_WIDTH, floor); // ground
        cloudManager.renderDebug(shapeRenderer);
        for (Obstacle obs : obstacleArray)
        {
            obs.renderDebug(shapeRenderer);
        }

		shapeRenderer.end();

		// do line stuff
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		player.renderDebug(shapeRenderer);
		shapeRenderer.end();

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        String text = "Score: " + Integer.toString((int)playTime);
        layout.setText(bitmapFont, text);
        bitmapFont.setColor(Color.CYAN);
        bitmapFont.draw(batch, text, WORLD_WIDTH / 2 - layout.width /2, WORLD_HEIGHT /2);

        if (currentGameState == State.GameOver) {
            String go = "Game Over";
            layout.setText(bitmapFont, text);
            bitmapFont.setColor(Color.RED);
            bitmapFont.draw(batch, go, WORLD_WIDTH / 2 - layout.width / 2, WORLD_HEIGHT / 2 - 30);
        }

        batch.end();

	}

	private void update(float delta)
	{
        if (currentGameState == State.Playing) {

            playTime += delta;
            player.update(delta);
            cloudManager.update(delta);

            for (Obstacle obs : obstacleArray) {
                obs.update(delta);
            }

            checkIfNewObstacleCanBeAdded();
            checkIsObstacleGoneOffScreen();
            checkForObstacleCollision();
        }
        else if (currentGameState == State.GameOver)
        {

        }

	}


	private void clearScreen() {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void checkIfNewObstacleCanBeAdded()
	{
        // add initial one
        if (obstacleArray.size == 0)
        {
            Obstacle obs = new Obstacle(WORLD_WIDTH);
            obs.commission();

            obstacleArray.add(obs);
        }


        if (obstacleArray.size < MAX_OBSTACLES)
        {
            boolean okToAdd = false;

            for (Obstacle obstacle : obstacleArray)
            {
                if (obstacle.getX() < (WORLD_WIDTH - MIN_DISTANCE_BETWEEN_OBSTACLES))
                {
                    okToAdd = true;
                }
                else
                {
                    okToAdd = false;
                }
            }

            if (okToAdd)
            {
                Obstacle obs = new Obstacle(WORLD_WIDTH);
                obs.commission();

                obstacleArray.add(obs);
            }
        }



	}

	private void checkIsObstacleGoneOffScreen()
	{
		for (Obstacle obs : obstacleArray)
		{
            if (obs.getX() < 0)
            {
                obstacleArray.removeValue(obs, true);
            }
		}
	}

	private void checkForObstacleCollision()
	{
        for (Obstacle obstacle : obstacleArray)
        {
            if (player.getCollisionRectangle().overlaps(obstacle.getCollisionRectangle()))
            {
                // System.out.println("Hit");
                // todo introduce lives
                currentGameState = State.GameOver;
            }
        }
	}
}
