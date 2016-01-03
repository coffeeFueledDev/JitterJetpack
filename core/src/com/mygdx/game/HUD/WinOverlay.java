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

/**
 *
 */
public class WinOverlay {
    public Stage stage;
    private SpriteBatch sb;

    Label gameWonLabel;
    Label nextLevelLabel;
    Label mainMenuLabel;

    int MENU_SCALE = 4;
    int PAD_TOP = 10;

    public static final int NEXT_LEVEL = 0, MAIN_MENU = 1;
    public int gameOverOption = -1;


    public WinOverlay(SpriteBatch sb){
        this.sb = sb;
    }

    public void setUp(){
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);


        gameWonLabel = new Label("CONGRATULATIONS!", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        nextLevelLabel = new Label("continue to next level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mainMenuLabel = new Label("Main Menu", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        gameWonLabel.setFontScale(MENU_SCALE * 1.5f);
        nextLevelLabel.setFontScale(MENU_SCALE);
        mainMenuLabel.setFontScale(MENU_SCALE);


        nextLevelLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameOverOption = NEXT_LEVEL;
                return true;
            }
        });

        mainMenuLabel.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.out.println("MAIN MENU");
                gameOverOption = MAIN_MENU;
                return true;
            }
        });


        table.center();
        table.add(gameWonLabel).expandX().padTop(PAD_TOP*2);
        table.row();
        table.add(nextLevelLabel).expandX().padTop(PAD_TOP);
        table.row();
        table.add(mainMenuLabel).expandX().padTop(PAD_TOP);
        table.row();

        stage.addActor(table);
    }


}
