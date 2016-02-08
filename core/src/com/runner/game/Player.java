package com.runner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by William on 06/02/2016.
 */
public class Player {

    private float x = 30;
    private float y = 10;

    private float legAxa = x + 2;
    private float legBxa = x + 13;

    private float legAxb = legAxa + 2;
    private float legBxb = legBxa - 2;

    private float legA = legAxa;
    private float legB = legBxa;

    private boolean canJump = true;
    private final float gravity = -300f;
    private float yVelocity = 0;

    private Rectangle collisionRectangle;

    private final float playerWidth = 20;
    private final float playerHeight = 50;

    private float timerElapse = 20;
    private float currentTimer = timerElapse;

    private float floor;

    public Player(float floor) {

        this.floor = floor + 1;
        collisionRectangle = new Rectangle(x, y, playerWidth, playerHeight + 20);
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void update(float delta)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            jump();

        y += yVelocity * delta;
        yVelocity += gravity * delta;

        collisionRectangle.setY(y);

        if (y <= floor)
        {
            yVelocity = 0;
            y = floor;
            canJump = true;
        }

        currentTimer--;

        if (currentTimer <= 0)
        {
            if (canJump) {
                if (legA == legAxa)
                    legA = legAxb;
                else
                    legA = legAxa;


                if (legB == legBxa)
                    legB = legBxb;
                else
                    legB = legBxa;

                currentTimer = timerElapse;
            }
        }

    }

    public void render()
    {

    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(Color.CYAN);
        // head
        shapeRenderer.rect(x, y + 50, playerWidth, 20);

        // torso
        shapeRenderer.rect(x, y + 20, playerWidth, playerHeight - 20);
        shapeRenderer.rect(x, y + 20, playerWidth, playerHeight - 20);

        // legs
        shapeRenderer.rect(legA, y, 5, 20); // leg a
        shapeRenderer.rect(legB, y, 5, 20); // leg b


        // collision rect
        //shapeRenderer.setColor(Color.YELLOW);
        //shapeRenderer.rect(x, y, playerWidth, playerHeight + 20);
    }

    public void jump()
    {
        // System.out.println("jump");
        if (canJump)
        {
            yVelocity = 200f;
            canJump = false;
        }


    }

}
