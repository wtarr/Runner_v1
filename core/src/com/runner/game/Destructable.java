package com.runner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by William on 08/02/2016.
 */


/*
* _  _
*|_||_|
*
*
*
*
 */

public class Destructable {

    // public Vector2

    private Vector2 blPos;
    //private Vector2 brPos;
    //private Vector2 tlPos;
    //private Vector2 trPos;

    private Rectangle bottomleft;
    //private Rectangle bottomright;
    //private Rectangle topleft;
    //private Rectangle topright;

    private Vector2 velocity = new Vector2(100, 100);
    private Vector2 windRest = new Vector2(0, 50);
    private Vector2 gravity = new Vector2(0, -500);


    private float speed = 0;

    public Destructable() {

        blPos = new Vector2(50, 50);
        bottomleft = new Rectangle(0, 0, 15, 15);
        bottomleft.setCenter(blPos);
    }

    // vel += gravity * delta
    // pos += velocity * delta
    //
    //
    //
    //
    //


    public void reset()
    {
        blPos = new Vector2(50, 50);
        bottomleft.setCenter(blPos);
        velocity = new Vector2(100, 200);
    }



    public void update(float delta)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.J))
            reset();

        //Vector2 velocity = new Vector2(0, 0);
        // Vector2 vel = new Vector2(50, 50);

        //velocity =  //direction.scl((speed * delta));
        velocity.mulAdd(gravity, delta);
        velocity.mulAdd(windRest, delta);

        blPos.mulAdd(velocity, delta);

        bottomleft.setCenter(blPos);
    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.rect(bottomleft.x, bottomleft.y, bottomleft.width, bottomleft.height);


    }



}
