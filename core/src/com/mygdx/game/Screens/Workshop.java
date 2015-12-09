package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGdxGame;


/**
 * - Player outfits
 * - Customizations
 * - Can buy outfits
 * - Can buy power-ups (MAYBE)
 * */
public class Workshop implements Screen, ApplicationListener, InputProcessor {
    private static final int MENU_SCALE = 2;
    Stage stage;
    Menu menu;
    Label workshopLabel;
    MyGdxGame game;


    public Workshop(MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        menu = new Menu(game);
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        stage = new Stage();
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        workshopLabel = new Label("Workshop", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        workshopLabel.setFontScale(MENU_SCALE);

        table.add(workshopLabel).expandX().padTop(10);
        table.row();
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

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

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.setScreen(menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
