package com.mygdx.game.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.UserDetails.Account;


public class Hud {
    public Stage stage;
    private Viewport viewport;
    private int worldTimer;
    private float timeCount;
    private int score;
    Label countLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label playerLabel;

    public Counters counter;
    Account account;

    public Hud(SpriteBatch sb){
        counter = new Counters();
        worldTimer = 0;
        timeCount = 0;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.RED));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.RED));
        playerLabel = new Label("NAME", new Label.LabelStyle(new BitmapFont(), Color.RED));

        countLabel.setFontScale(2,2);
        scoreLabel.setFontScale(2,2);
        timeLabel.setFontScale(2,2);
        levelLabel.setFontScale(2,2);
        worldLabel.setFontScale(2,2);
        playerLabel.setFontScale(2, 2);

        table.add(playerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countLabel).expandX();

        stage.addActor(table);

        account = new Account();
        account.setName("Alex"); /** Dummy code */
        playerLabel.setText(account.getName());

    }

    public void update(float dt){
        counter.update(dt);
        countLabel.setText("" + counter.getTime());

    }

}
