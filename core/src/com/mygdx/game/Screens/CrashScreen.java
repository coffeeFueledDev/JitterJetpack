package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.MyGdxGame;

/**
 * - Not in use.
 *
 * */
public class CrashScreen implements Screen {
    MyGdxGame game;

    public CrashScreen(MyGdxGame game){
        this.game = game;

    }



    @Override
    public void show() {

    }

    public void changeScreen(){
        if(Gdx.input.justTouched()) {
            //game.setScreen(game.gameScreen);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        changeScreen();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    // Probably won't use
    /*
    public void endLife(){
        float delay = 3;
        spriteMovement.crashed();
        Gdx.input.setInputProcessor(null);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(crashScreen);
                hide();
            }
        }, delay, 0, 1);
    }*/

}
