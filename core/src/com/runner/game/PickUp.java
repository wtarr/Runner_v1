package com.runner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by William on 12/02/2016.
 */
public class PickUp implements IGameObject{
    public enum Contents { Life, Flip };

    private Contents content;

    public Contents getContent() {
        return content;
    }

    private final int FRAME_COLS = 2;
    private final int FRAME_ROWS = 1;

    private Animation flashingAnimation;
    private Texture spriteSheet;
    private TextureRegion[] spriteFrames;
    private TextureRegion currentFrame;

    private float stateTime;

    private Rectangle collisionRectangle;
    private Vector2 position;
    private Vector2 velocity;
    private final float width = 30;
    private final float height = 30;

    public PickUp(Vector2 position, Vector2 velocity, Contents content) {

        this.position = position;
        this.velocity = velocity;
        collisionRectangle = new Rectangle(position.x, position.y, width, height);
        this.content = content;
        create();
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }


    public void create() {
        spriteSheet = new Texture(Gdx.files.internal("pickup.png"));
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/FRAME_COLS, spriteSheet.getHeight()/FRAME_ROWS);
        spriteFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++){
            for (int j = 0; j < FRAME_COLS; j++) {
                spriteFrames[index++] = tmp[i][j];
            }
        }

        flashingAnimation = new Animation(0.500f, spriteFrames);
        stateTime = 0;
    }

    @Override
    public void update(float delta) {
        //if (isEnabled) {
        position.mulAdd(velocity.cpy(), delta);
        //x += xVelocity * delta;
        collisionRectangle.setX(position.x);


    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame  = flashingAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, collisionRectangle.getX(), collisionRectangle.getY());
    }

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        //shapeRenderer.setColor(Color.BROWN);
        //shapeRenderer.rect(collisionRectangle.getX(), collisionRectangle.getY(), width, height);
    }
}
