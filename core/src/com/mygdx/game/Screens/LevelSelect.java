package com.mygdx.game.Screens;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.Levels.LevelOne;
import com.mygdx.game.Levels.LevelSix;
import com.mygdx.game.Levels.LevelTwo;
import com.mygdx.game.MyGdxGame;


/**
 * Return back multiplexer quits menu as well
 * - UPDATE LEVEL SELECT MENU OVERLAY IN GAMESCREEN CLASS WHEN ADDING NEW LEVEL
 *
 * */
public class LevelSelect implements Screen, ApplicationListener {
    Menu menu;
    MyGdxGame game;
    GameScreen gameScreen;
    InputProcessor inputController;
    InputMultiplexer inputMultiplexer;

    private static final int MENU_SCALE = 2;
    private static final int ROW_TOP_PAD = 40;

    Stage stage;
    Label levelLabel;
    Label lvOne;
    Label lvTwo;
    Label lvThree;
    Label lvFour;
    Label lvFive;
    Label lvSix;

    public static final int LEVEL_ONE = 0, LEVEL_TWO = 1;

    public LevelSelect(MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        menu = new Menu(game);
        stage = new Stage();
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        levelLabel = new Label("Level Select", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lvOne = new Label("Level One", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lvTwo = new Label("Level Two", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lvThree = new Label("Level Three", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lvFour = new Label("Level Four", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lvFive = new Label("Level Five", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lvSix = new Label("Test Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        levelLabel.setFontScale(MENU_SCALE);
        lvOne.setFontScale(MENU_SCALE * 2);
        lvTwo.setFontScale(MENU_SCALE * 2);
        lvThree.setFontScale(MENU_SCALE * 2);
        lvFour.setFontScale(MENU_SCALE * 2);
        lvFive.setFontScale(MENU_SCALE * 2);
        lvSix.setFontScale(MENU_SCALE * 2);

        lvOne.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameScreen = new LevelOne(game);
                game.levelSel = LEVEL_ONE;
                game.setScreen(gameScreen);
                return true;
            }
        });


        lvTwo.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameScreen = new LevelTwo(game);
                game.levelSel = LEVEL_TWO;
                game.setScreen(gameScreen);
                return true;
            }
        });

        lvSix.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameScreen = new LevelSix(game);
                game.levelSel = LEVEL_ONE; // dummy code
                game.setScreen(gameScreen);
                return true;
            }
        });

        table.add(levelLabel).expandX().padTop(10).center();
        table.row().padTop(ROW_TOP_PAD);
        table.add(lvOne).expandX().left().padLeft(4);
        table.add(lvTwo).expandX().center();
        table.add(lvThree).expandX().right();
        table.row().padTop(ROW_TOP_PAD);
        table.add(lvFour).expandX().left().padLeft(4);
        table.add(lvFive).expandX().center();
        table.add(lvSix).expandX().right();
        table.row().padTop(ROW_TOP_PAD);

        stage.addActor(table);

        /** Multiplexer handles multiple Inputs  */
        Gdx.input.setCatchBackKey(true);
        inputController = new InputController(){
            @Override
            public boolean keyUp(int keycode) {
                if(keycode == Input.Keys.BACK){
                    game.setScreen(menu);
                }
                return true;
            }
        };
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputController);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

}
