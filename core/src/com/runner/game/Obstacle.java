package com.runner.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by William on 06/02/2016.
 */
public class Obstacle {

    private float x = 0;
    private float y = 10;

    private float width = 30;
    private float height = 30;

    private Rectangle collisionRectangle;

    private float xVelocity =  -100;

    private float worldWidth;

    private boolean isEnabled = false;

    public Obstacle(float worldWidth) {
        this.worldWidth = worldWidth;
        height = MathUtils.random(30, 60);
        commission();
    }

    public float getX() {
        return x;
    }
    public Rectangle getCollisionRectangle() { return collisionRectangle; }

    public void update(float delta)
    {
        if (isEnabled) {
            x += xVelocity * delta;
            collisionRectangle.setX(x);
        }
    }

    public void render()
    {

    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        if (isEnabled) {
            shapeRenderer.setColor(Color.GOLD);
            shapeRenderer.rect(x, y, width, height);
        }
    }

    public void commission()
    {
        x = worldWidth + MathUtils.random(30, 200);
        collisionRectangle = new Rectangle(x, y, width, height);
        isEnabled = true;
    }
}
