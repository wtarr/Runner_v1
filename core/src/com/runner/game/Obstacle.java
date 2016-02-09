package com.runner.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by William on 06/02/2016.
 */
public class Obstacle {

    // private float x = 0;
    // private float y = 10;

    public Vector2 getPosition() {
        return position;
    }

    private Vector2 position = new Vector2(0, 10);

    private float width = 30;
    private float height = 30;

    private float floorHeight;

    private Rectangle collisionRectangle;

    // private float xVelocity =  -100;
    private Vector2 velocity = new Vector2(-100, 0);

    private float worldWidth;

    public Obstacle(float worldWidth, float floor) {
        this.worldWidth = worldWidth;
        this.floorHeight = floor;
        height = MathUtils.random(30, 60);
        commission();
    }

    //public float getX() {
    //    return x;
    //}
    public Rectangle getCollisionRectangle() { return collisionRectangle; }

    public void update(float delta)
    {
        //if (isEnabled) {
            position.mulAdd(velocity.cpy(), delta);
            //x += xVelocity * delta;
            collisionRectangle.setX(position.x);
        //}
    }

    public void render()
    {

    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        //if (isEnabled) {
            shapeRenderer.setColor(Color.GOLD);
            shapeRenderer.rect(position.x, position.y, width, height);
        //}
    }

    public void commission()
    {
        position = new Vector2(worldWidth + MathUtils.random(30, 200), floorHeight);
        collisionRectangle = new Rectangle(position.x, position.y, width, height);
        // isEnabled = true;
    }
}
