package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.tween.SpriteAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;


/**
 * Old and won't likely use this splash
 *  */

public class SplashScreen implements Screen {
    MyGdxGame game;
    private SpriteBatch batch;
    Animation animation;
    private float timePassed = 0;
    private TextureAtlas jetpackerGirl;
    private static Texture backgroundTexture;

    private Sprite splash;
    private TweenManager tweenManager;

    public SplashScreen(MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
    }

    public void initJetpackerGirl(){
        jetpackerGirl = new TextureAtlas(Gdx.files.internal("jetpacker_girl_atlas.pack"));
        animation = new Animation(1/30f, jetpackerGirl.getRegions());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void show() {
        initJetpackerGirl();

        backgroundTexture = new Texture(Gdx.files.internal("splash_background.png"));
        splash = new Sprite(backgroundTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());


        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(0).delay(2).start(tweenManager);

        screenDelay();
        changeScreenJustTouched();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        showBackground();
        timePassed += Gdx.graphics.getDeltaTime();
        showJetpackerGirl();
        changeScreenJustTouched();
        batch.end();
    }

    public void changeScreenJustTouched(){
        if(Gdx.input.justTouched()) {
            //game.setScreen(game.menuScreen);
            hide();
        }
    }

    public void screenDelay(){
        float delay = 3;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                //game.setScreen(game.menuScreen);
                hide();
            }
        }, delay, 0,
                1); /** Used once then stopped */
    }

    /** Shows the floating sprite on open */
    public void showJetpackerGirl(){
        batch.draw(animation.getKeyFrame(timePassed, true), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 300, 300);
    }

    public void showBackground(){
        splash.draw(batch);

    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        jetpackerGirl.dispose();
        splash.getTexture().dispose();
        //game.splashScreen.dispose();
    }
}
