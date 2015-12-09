package com.mygdx.game.Levels;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.WorldRenderer;

public class LevelOne extends GameScreen {
    public LevelOne(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.renderer = new WorldRenderer("maps/test_map_04.tmx");
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
