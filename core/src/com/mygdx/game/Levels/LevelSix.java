package com.mygdx.game.Levels;

import com.mygdx.game.Collectables.Fuel;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.WorldRenderer;

/**
 * TEST LEVEL
 */
public class LevelSix extends GameScreen{
    Fuel fuel;

    public LevelSix(MyGdxGame game) {
        super(game);
        fuel = new Fuel(world);
    }

    @Override
    public void show() {
        super.renderer = new WorldRenderer("maps/map_two_wfuel_test.tmx");
        super.show();
        fuel.changeTile(super.renderer.level.getMap());
    }



}
