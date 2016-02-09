package com.runner.game;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractDestructable {

    protected Vector2 blockBottomLeftInitVelocity = new Vector2(-200, -100);
    protected Vector2 blockBottomRightInitVelocity = new Vector2(200, 100);
    protected Vector2 blockTopLeftInitVelocity = new Vector2(-300, -200);
    protected Vector2 blockTopRightInitVelocity = new Vector2(300, 200);
    protected Vector2 windRestFromRight = new Vector2(0, 20);
    protected Vector2 windRestFromLeft = new Vector2(0, 20);
    protected Vector2 gravity = new Vector2(0, -400);


}
