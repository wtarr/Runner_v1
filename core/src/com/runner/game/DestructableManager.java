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

    public Array<IGameObject> destructableBlockPool;

    public DestructableManager(int poolSize) {
        destructableBlockPool = new Array<IGameObject>();

        for (int i = 0; i < poolSize; i++)
        {
            destructableBlockPool.add(new DestructableCollisionBlock());
        }

    }

    public void commissionADestructable(Rectangle collisionRect)
    {
        for (IGameObject block : destructableBlockPool)
        {
            DestructableCollisionBlock item = (DestructableCollisionBlock)block;

            if (!item.isCommissioned())
            {
                item.CommissionDestructable(collisionRect);
                break;
            }
        }
    }

    public void update(float delta)
    {
        for (IGameObject block : destructableBlockPool)
        {
            block.update(delta);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        for (IGameObject block : destructableBlockPool)
        {
            block.renderDebug(shapeRenderer);
        }
    }

    public void checkIfOutOfScope()
    {
        for (IGameObject block : destructableBlockPool)
        {
            DestructableCollisionBlock item = (DestructableCollisionBlock)block;

            item.CheckIfOutOfCommission();
            // todo

        }
    }
}
