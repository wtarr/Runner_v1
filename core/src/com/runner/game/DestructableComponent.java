package com.runner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class DestructableComponent extends AbstractDestructable implements IGameObject{

    private Vector2 position; // always bottom left of rectangle

    private Rectangle boundingRectangle;

    private Vector2 velocity ;
    private Vector2 windRest;
    private Vector2 g;

    private boolean inUse;

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public DestructableComponent() {
        position = Vector2.Zero;
    }

    public void CommissionDestructableComponent(DestructableManager.Location location, Rectangle originalCollidedObject) {

        velocity = Vector2.Zero;

        float rectW = originalCollidedObject.width/2;
        float rectH = originalCollidedObject.height/2;

        switch (location)
        {
            case BottomLeft:
                position = new Vector2(originalCollidedObject.x, originalCollidedObject.y);
                boundingRectangle = new Rectangle(position.x, position.y, rectW, rectH);
                velocity = blockBottomLeftInitVelocity.cpy();
                windRest = windRestFromRight.cpy();
                inUse = true;
                break;
            case BottomRight:
                position = new Vector2(originalCollidedObject.x + originalCollidedObject.width/2, originalCollidedObject.y);
                boundingRectangle = new Rectangle(position.x, position.y, rectW, rectH);
                velocity = blockBottomRightInitVelocity.cpy();
                windRest = windRestFromLeft.cpy();
                inUse = true;
                break;
            case TopLeft:
                position = new Vector2(originalCollidedObject.x, originalCollidedObject.y + originalCollidedObject.height/2);
                boundingRectangle = new Rectangle(position.x, position.y, rectW, rectH);
                velocity = blockTopLeftInitVelocity.cpy();
                windRest = windRestFromRight.cpy();
                inUse = true;
                break;
            case TopRight:
                position = new Vector2(originalCollidedObject.x + originalCollidedObject.width/2, originalCollidedObject.y + originalCollidedObject.height/2);
                boundingRectangle = new Rectangle(position.x, position.y, rectW, rectH);
                velocity = blockTopRightInitVelocity.cpy();
                windRest = windRestFromLeft.cpy();
                inUse = true;
                break;
            default:
                break;
        }

        g = gravity.cpy();

        //position = new Vector2(50, 50);
        //boundingRectangle = new Rectangle(0, 0, 15, 15);
        //boundingRectangle.setCenter(position);
    }

    public void update(float delta)
    {
        if (inUse) {
            velocity.mulAdd(g, delta);
            velocity.mulAdd(windRest, delta);

            position.mulAdd(velocity, delta);

            boundingRectangle.setCenter(position);
        }
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        if (inUse)
            shapeRenderer.rect(boundingRectangle.x, boundingRectangle.y, boundingRectangle.width, boundingRectangle.height);
    }

    public boolean OutOfScope()
    {
        if (position.y < -10)
        {
            inUse = false;
            return true;
        }

        return false;
    }



}
