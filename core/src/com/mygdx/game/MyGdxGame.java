package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.Menu;

/** */


public class MyGdxGame extends Game {

    public Menu menu;
    Viewport viewport;
    public int levelSel;

    @Override
	public void create () {
        Camera camera = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, camera);

        menu = new Menu(this);
        setScreen(menu);

	}

}
