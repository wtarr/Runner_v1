package com.runner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by William on 06/02/2016.
 */
public class Player {

    //private float x = 30;
    //private float y = 10;

    private Vector2 position = new Vector2(30, 10);

    private float legAxa = position.x + 2;
    private float legBxa = position.x + 13;

    private float legAxb = legAxa + 2;
    private float legBxb = legBxa - 2;

    private float legA = legAxa;
    private float legB = legBxa;

    private boolean canJump = true;
    //private final float gravity = -300f;
    private Vector2 gravity = new Vector2(0, -300f);
    // private float yVelocity = 0;
    private Vector2 jumpVelocity = Vector2.Zero;

    private Rectangle collisionRectangle;

    private final float playerWidth = 20;
    private final float playerHeight = 50;

    private float timerElapse = 20;
    private float currentTimer = timerElapse;

    private float floor;

    public Player(float floor) {

        this.floor = floor + 1;
        collisionRectangle = new Rectangle(position.x, position.y, playerWidth, playerHeight + 20);
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void update(float delta)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            jump();


        jumpVelocity.mulAdd(gravity, delta);
        position.mulAdd(jumpVelocity, delta);


        //y += yVelocity * delta;
        //yVelocity += gravity * delta;

        collisionRectangle.setY(position.y);

        if (position.y <= floor)
        {
            jumpVelocity = Vector2.Zero;
            position.y = floor;
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
        shapeRenderer.rect(position.x, position.y + 50, playerWidth, 20);

        // torso
        shapeRenderer.rect(position.x, position.y + 20, playerWidth, playerHeight - 20);
        shapeRenderer.rect(position.x, position.y + 20, playerWidth, playerHeight - 20);

        // legs
        shapeRenderer.rect(legA, position.y, 5, 20); // leg a
        shapeRenderer.rect(legB, position.y, 5, 20); // leg b


        // collision rect
        //shapeRenderer.setColor(Color.YELLOW);
        //shapeRenderer.rect(x, y, playerWidth, playerHeight + 20);
    }

    public void jump()
    {
        // System.out.println("jump");
        if (canJump)
        {
            jumpVelocity = new Vector2(0, 200f);
            canJump = false;
        }


    }

}
