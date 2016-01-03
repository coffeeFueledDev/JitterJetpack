package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.MyGdxGame;

public class Loading implements Screen {
    MyGdxGame game;
    private SpriteBatch batch;

    private TextureAtlas textureAtlas;
    private Animation animation;
    private Sprite splash;
    private float timePassed = 0;

    public Loading(MyGdxGame game){
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas(Gdx.files.internal("loading screen/loading.atlas"));
        animation = new Animation(1/30f, textureAtlas.getRegions());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        timePassed += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(timePassed, true), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 300, 300);
        batch.end();

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
        batch.dispose();
        textureAtlas.dispose();

    }
}
