package com.mygdx.game.Levels;


import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.WorldRenderer;

public class LevelTwo extends GameScreen {
    Space space;

    @Override
    public void updateRunning(float delta) {
        super.updateRunning(delta);
        space.update(delta, super.camera);
    }

    public LevelTwo(MyGdxGame game) {
        super(game);
        super.renderer = new WorldRenderer("maps/level_two.tmx");
    }

    @Override
    public void show() {
        space = new Space(super.world);
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        space.update(delta, super.camera);
    }
}
