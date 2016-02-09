package com.runner.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by William on 09/02/2016.
 */
public class DestructableManager {
    public enum Location {
        BottomLeft,
        BottomRight,
        TopLeft,
        TopRight
    };

    public Array<DestructableCollisionBlock> destructableBlockPool;

    public DestructableManager(int poolSize) {
        destructableBlockPool = new Array<DestructableCollisionBlock>();

        for (int i = 0; i < poolSize; i++)
        {
            destructableBlockPool.add(new DestructableCollisionBlock());
        }

    }

    public void commissionADestructable(Rectangle collisionRect)
    {
        for (DestructableCollisionBlock block : destructableBlockPool)
        {
            if (!block.isCommissioned())
            {
                block.CommissionDestructable(collisionRect);
                break;
            }
        }
    }

    public void update(float delta)
    {
        for (DestructableCollisionBlock block : destructableBlockPool)
        {
            block.update(delta);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        for (DestructableCollisionBlock block : destructableBlockPool)
        {
            block.renderDebug(shapeRenderer);
        }
    }

    public void checkIfOutOfScope()
    {
        for (DestructableCollisionBlock block : destructableBlockPool)
        {
            block.CheckIfOutOfCommission();
            // todo

        }
    }
}
