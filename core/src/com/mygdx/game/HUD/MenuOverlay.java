package com.mygdx.game.HUD;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuOverlay {
    public Stage stage;

    Label saveLabel;
    Label startAgainLabel;
    Label selectLevelLabel;
    Label returnToGameLabel;
    Label mainMenuLabel;

    int MENU_SCALE = 4;
    private static final int PAD_TOP = 10;
    public static final int RETURNED = 0, START_AGAIN = 1, SELECT_LEVEL = 2, MAIN_MENU = 3;
    public int menuOption = -1;

    public MenuOverlay(SpriteBatch sb) {
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);


        mainMenuLabel = new Label("Main Menu", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        saveLabel = new Label("Save Progress", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        startAgainLabel = new Label("Start Again", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        selectLevelLabel = new Label("Change Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        returnToGameLabel = new Label("Return", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        mainMenuLabel.setFontScale(MENU_SCALE);
        saveLabel.setFontScale(MENU_SCALE);
        startAgainLabel.setFontScale(MENU_SCALE);
        selectLevelLabel.setFontScale(MENU_SCALE);
        returnToGameLabel.setFontScale(MENU_SCALE);

        mainMenuLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.out.println("MAIN MENU");
                menuOption = MAIN_MENU;
                return true;
            }
        });

        returnToGameLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.out.println("RETURNED");
                menuOption = RETURNED;
                return true;
            }
        });

        startAgainLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.out.println("START AGAIN");
                menuOption = START_AGAIN;
                return true;
            }
        });

        selectLevelLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.out.println("SELECT LEVEL");
                menuOption = SELECT_LEVEL;
                return true;
            }
        });

        table.center();
        table.add(mainMenuLabel).expandX().padTop(PAD_TOP);
        table.row();
        table.add(saveLabel).expandX().padTop(PAD_TOP);
        table.row();
        table.add(startAgainLabel).expandX().padTop(PAD_TOP);
        table.row();
        table.add(selectLevelLabel).expandX().padTop(PAD_TOP);
        table.row();
        table.add(returnToGameLabel).expandX().padTop(PAD_TOP);
        table.row();

        stage.addActor(table);
    }



}
