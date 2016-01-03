package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.GenericControls.InputController;
import com.mygdx.game.LevelVars;
import com.mygdx.game.Levels.LevelOne;
import com.mygdx.game.MyGdxGame;

public class Menu implements Screen {
    MyGdxGame game;
    InputProcessor inputController;
    GameScreen gameScreen;
    LevelSelect levelSelect;
    Workshop workshopScreen;
    Purse purseScreen;
    AccountScreen accountScreen;
    public int MENU_SCALE = 4;

    private Stage stage;

    Label startGameLabel;
    Label workshopLabel;
    Label continueLabel;
    Label purseLabel;
    Label levelSelectLabel;
    Label accountLabel;


    public Menu(MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        gameScreen = new LevelOne(game);
        levelSelect = new LevelSelect(game);
        workshopScreen = new Workshop(game);
        purseScreen = new Purse(game);
        accountScreen = new AccountScreen(game);
        stage = new Stage();
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        startGameLabel = new Label("Stage Game", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelSelectLabel = new Label("Level Select", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        workshopLabel = new Label("Workshop", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        continueLabel = new Label("Continue", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        purseLabel = new Label("Purse", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        accountLabel = new Label("Account", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        startGameLabel.setFontScale(MENU_SCALE);
        levelSelectLabel.setFontScale(MENU_SCALE);
        workshopLabel.setFontScale(MENU_SCALE);
        continueLabel.setFontScale(MENU_SCALE);
        purseLabel.setFontScale(MENU_SCALE);
        accountLabel.setFontScale(MENU_SCALE);

        startGameLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.setScreen(gameScreen);
                return true;
            }
        });

        levelSelectLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.levelSel = LevelVars.LEVEL_ONE;
                game.setScreen(levelSelect);
                return true;
            }
        });

        workshopLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.setScreen(workshopScreen);
                return true;
            }
        });

        purseLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.setScreen(purseScreen);
                return true;
            }
        });

        accountLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.setScreen(accountScreen);
                return false;
            }
        });

        table.center();
        table.add(startGameLabel).expandX().padTop(10);
        table.row();
        table.add(levelSelectLabel).expandX().padTop(10);
        table.row();
        table.add(workshopLabel).expandX().padTop(10);
        table.row();
        table.add(continueLabel).expandX().padTop(10);
        table.row();
        table.add(purseLabel).expandX().padTop(10);
        table.row();
        table.add(accountLabel).expandX().padTop(10);


        stage.addActor(table);


        inputController = new InputController();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputController);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
}
