package com.runner.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by William on 12/02/2016.
 */
public class PickUp implements IGameObject{



    private Rectangle collisionRectangle;
    private Vector2 position;
    private Vector2 velocity;
    private final float width = 30;
    private final float height = 30;

    public PickUp(Vector2 position, Vector2 velocity) {

        this.position = position;
        this.velocity = velocity;
        collisionRectangle = new Rectangle(position.x, position.y, width, height);
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
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

    }

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(collisionRectangle.getX(), collisionRectangle.getY(), width, height);
    }
}
