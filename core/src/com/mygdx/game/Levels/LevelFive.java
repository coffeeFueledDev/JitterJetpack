package com.mygdx.game.Levels;

import com.mygdx.game.Collectables.Collectible;
import com.mygdx.game.LevelVars;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PowerUps.PowerUpManager;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.WorldVars;

/**
 *
 */
public class LevelFive extends GameScreen{
    Collectible collectible;
    PowerUpManager powerUpManager;

    public LevelFive(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.renderer = new WorldRenderer(WorldVars.LEVEL_FIVE);
        super.show();

        super.renderer.buildMapCollision(super.world, WorldVars.OBJECT_LAYER);
        super.renderer.buildMapSafeLayer(super.world, WorldVars.SAFE_LAYER);

        collectible = new Collectible(super.renderer, player);
        collectible.setShield(super.world);
        powerUpManager = new PowerUpManager(player);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        super.debugRenderer.render(world, camera.combined);

        switch (super.state) {
            case GAME_READY:
                break;
            case GAME_OVER:
                break;
            case GAME_PAUSED:
                break;
            case GAME_RUNNING:
                //updateRunningLevel(delta);
                break;
            case GAME_WON:
                //updateGameWonLevel();
                break;
        }

    }

    @Override
    public void updateReady() {
        super.updateReady();
    }

    @Override
    public void updatePaused() {
        super.updatePaused();
        restartThisLevel();

    }

    @Override
    public void updateRunning(float delta) {
        super.updateRunning(delta);
        collectible.collectibleManager();
        powerUpManager.powerUpManager(delta, super.counters);
    }

    @Override
    public void updateGameWon() {
        super.updateGameWon();
        if(changeLevelWon){
            GameScreen gameScreen = new LevelSix(super.game);
            super.game.levelSel = LevelVars.LEVEL_SIX;
            super.game.setScreen(gameScreen);
        }
    }


    public void restartThisLevel(){
        if(super.restartGame){
            GameScreen restart = new LevelFive(game);
            game.setScreen(restart);
        }
    }

    @Override
    public void updateGameOver(float dt) {
        super.updateGameOver(dt);
        restartThisLevel();
    }


}
