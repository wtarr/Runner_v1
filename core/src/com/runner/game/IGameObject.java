package com.runner.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by William on 12/02/2016.
 */
public interface IGameObject {
    void update(float delta);
    void render(SpriteBatch batch);
    void renderDebug(ShapeRenderer shapeRenderer);
}
