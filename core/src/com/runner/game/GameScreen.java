package com.runner.game;

import com.badlogic.gdx.Game;

/**
 * Created by William on 06/02/2016.
 */
public class GameScreen extends Game {
    @Override
    public void create() {
        setScreen(new RunnerGame());
    }
}
