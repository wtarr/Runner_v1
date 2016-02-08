package com.runner.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Cloud
{
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float x = 0;
    private float y = 0;

    private float yVelocity;

    public boolean isEnabled = false;

    public Cloud(float x) {

        this.x = x;
        yVelocity = MathUtils.random(100, 130) * -1;
    }

    public void update(float delta)
    {
        if (isEnabled) {
            x += yVelocity * delta;
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        if (isEnabled) {
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.circle(x, y, 30);
            shapeRenderer.circle(x + 20, y + 5, 20);
            shapeRenderer.circle(x - 20, y - 5, 20);
        }
    }

}
