package com.mygdx.game.Levels;


import com.mygdx.game.Collectables.Collectible;
import com.mygdx.game.LevelVars;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PowerUps.PowerUpManager;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.WorldVars;

public class LevelTwo extends GameScreen {
    Collectible collectible;
    PowerUpManager powerUpManager;


    public LevelTwo(MyGdxGame game) {
        super(game);
        super.renderer = new WorldRenderer(WorldVars.LEVEL_TWO);
        super.renderer.buildMapCollision(super.world, WorldVars.OBJECT_LAYER);
        super.renderer.buildMapSafeLayer(super.world, WorldVars.SAFE_LAYER);

        collectible = new Collectible(super.renderer, player);
        collectible.setShield(super.world);
        powerUpManager = new PowerUpManager(player);
    }


    @Override
    public void show() {
        super.show();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        /**super.debugRenderer.render(world, camera.combined);*/

        switch (super.state) {
            case GAME_READY:
                break;
            case GAME_OVER:
                break;
            case GAME_PAUSED:
                break;
            case GAME_RUNNING:
                //updateRunningLevelTwo(delta);
                break;
            case GAME_WON:
                //updateGameWonLevelTwo();
                break;
        }

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
            GameScreen gameScreen = new LevelThree(super.game);
            super.game.levelSel = LevelVars.LEVEL_THREE;
            super.game.setScreen(gameScreen);
        }
    }

    @Override
    public void updatePaused() {
        super.updatePaused();
        restartThisLevel();
    }

    @Override
    public void updateGameOver(float dt) {
        super.updateGameOver(dt);
        restartThisLevel();
    }

    public void restartThisLevel(){
        if(super.restartGame){
            GameScreen restart = new LevelTwo(game);
            game.setScreen(restart);
        }
    }






}
