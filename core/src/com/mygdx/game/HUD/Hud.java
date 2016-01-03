package com.mygdx.game.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Sprites.Player;
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
    static Vector2 FONT_SCALE = new Vector2(4,4);

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
        worldLabel = new Label("HEIGHT", new Label.LabelStyle(new BitmapFont(), Color.RED));
        playerLabel = new Label("NAME", new Label.LabelStyle(new BitmapFont(), Color.RED));

        countLabel.setFontScale(FONT_SCALE.x, FONT_SCALE.y);
        scoreLabel.setFontScale(FONT_SCALE.x, FONT_SCALE.y);
        timeLabel.setFontScale(FONT_SCALE.x/2, FONT_SCALE.y/2);
        levelLabel.setFontScale(FONT_SCALE.x, FONT_SCALE.y);
        worldLabel.setFontScale(FONT_SCALE.x/2, FONT_SCALE.y/2);
        playerLabel.setFontScale(FONT_SCALE.x/2, FONT_SCALE.y/2);


        table.add(playerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countLabel).expandX();

        stage.addActor(table);

        account = new Account();
        account.setName("SHIELD"); /** Dummy code */
        playerLabel.setText(account.getName());

    }

    public void update(float dt, Body player, Player playerDetails){
        counter.update(dt, player);
        countLabel.setText("" + counter.getTime());
        if((counter.getHeight()%5) == 0)
            levelLabel.setText(""+ counter.getHeight());
        /** Just using score label as a placeholder for the shield counter..
        scoreLabel.setText("" + counter.getShieldTime());*/

        scoreLabel.setText("" + playerDetails.getCurrentHealth());

    }

}
