package com.runner.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by William on 06/02/2016.
 */
public class CloudManager {



    private float worldWidth;
    private float worldHeight;

    private float maxNumberOnScreen = 3;
    private float onScreen = 0;

    private Array<Cloud> cloudPool = new Array<Cloud>();


    public CloudManager(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        for (int i = 0; i < maxNumberOnScreen; i++)
        {
            Cloud c = new Cloud(worldWidth + 50);
            c.isEnabled = false;

            cloudPool.add(c);
        }
    }

    public void update(float delta)
    {
        for (Cloud cloud : cloudPool)
        {
            cloud.update(delta);
        }

        canAddCloudToScene();
        checkIfStillOnScreen();
    }

    public void renderDebug(ShapeRenderer shapeRenderer)
    {
        for (Cloud cloud : cloudPool)
        {
            cloud.renderDebug(shapeRenderer);
        }
    }

    private void canAddCloudToScene()
    {
        if (onScreen < maxNumberOnScreen)
        {
            for (Cloud cloud : cloudPool) {
                if (cloud.isEnabled == false)
                {
                    // recommission it
                    cloud.setX(worldWidth + MathUtils.random(100, 500));
                    cloud.setY(MathUtils.random(200, worldHeight));
                    cloud.isEnabled = true;
                    onScreen++;
                }
            }
        }
    }

    private void checkIfStillOnScreen()
    {
        for (Cloud cloud : cloudPool) {
            if (cloud.getX() < 0)
            {
                cloud.isEnabled = false;
                onScreen--;
            }
        }
    }
}

