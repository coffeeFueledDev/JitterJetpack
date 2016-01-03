package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Player;

/**
 *
 */
public class AccountScreen implements Screen, InputProcessor {
    Menu menu;
    MyGdxGame game;
    private static final int MENU_SCALE = 2;
    Stage stage;
    Player player;
    World tmpWorld;

    Label accountLabel;
    Label fuelLabel;

    public AccountScreen(MyGdxGame game){
        this.game = game;

        /** temporary world to grab static variables from player */
        player = new Player(tmpWorld);
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

        accountLabel = new Label("Account", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        fuelLabel = new Label("Fuel", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        accountLabel.setFontScale(MENU_SCALE);
        fuelLabel.setFontScale(MENU_SCALE * 2);

        table.add(accountLabel).expandX().padTop(10);
        table.row();
        table.add(fuelLabel).expandX().padTop(10).left().padLeft(3);
        stage.addActor(table);

        System.out.println("HERE");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        fuelLabel.setText("Fuel Collected: " + player.getFuelNumber());

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
