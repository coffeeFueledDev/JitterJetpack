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

public class CrashOverlay {
    public Stage stage;
    private SpriteBatch sb;
    MenuOverlay menuOverlay;

    Label gameOverLabel;
    Label startAgainLabel;
    Label selectLevelLabel;

    int MENU_SCALE = 4;
    int PAD_TOP = 10;

    public CrashOverlay(MenuOverlay menuOverlay, SpriteBatch sb){
        this.menuOverlay = menuOverlay;
        this.sb = sb;
    }

    public void setUp(){
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);


        gameOverLabel = new Label("GAME OVER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        startAgainLabel = new Label("Start Again", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        selectLevelLabel = new Label("Select Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        gameOverLabel.setFontScale(MENU_SCALE*2);
        startAgainLabel.setFontScale(MENU_SCALE);
        selectLevelLabel.setFontScale(MENU_SCALE);


        startAgainLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.out.println("START AGAIN");
                menuOverlay.menuOption = menuOverlay.START_AGAIN;
                return true;
            }
        });

        selectLevelLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.out.println("START AGAIN");
                menuOverlay.menuOption = menuOverlay.SELECT_LEVEL;
                return true;
            }
        });


        table.center();
        table.add(gameOverLabel).expandX().padTop(PAD_TOP*2);
        table.row();
        table.add(startAgainLabel).expandX().padTop(PAD_TOP);
        table.row();
        table.add(selectLevelLabel).expandX().padTop(PAD_TOP);
        table.row();

        stage.addActor(table);
    }



}
