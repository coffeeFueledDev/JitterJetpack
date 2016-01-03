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
public class LevelThree extends GameScreen {
    Collectible collectible;
    PowerUpManager powerUpManager;
    Space space;

    @Override
    public void updateRunning(float delta) {
        space.update(delta, super.camera);
        super.updateRunning(delta);
    }

    public LevelThree(MyGdxGame game) {
        super(game);
        super.renderer = new WorldRenderer(WorldVars.LEVEL_THREE);
        /**super.renderer.buildMapCollision(super.world, WorldVars.OBJECT_LAYER);
        super.renderer.buildMapCollision(super.world, "oj2");
        super.renderer.buildMapSafeLayer(super.world, WorldVars.SAFE_LAYER);
        collectible = new Collectible(super.renderer, player);
        collectible.setShield(super.world);
        powerUpManager = new PowerUpManager(player);*/

    }


    @Override
    public void show() {
        space = new Space(super.world);
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
                break;
            case GAME_WON:
                break;
        }

    }

    @Override
    public void updateGameWon() {
        super.updateGameWon();
        if(changeLevelWon){
            GameScreen gameScreen = new LevelFour(super.game);
            super.game.levelSel = LevelVars.LEVEL_FOUR;
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
            GameScreen restart = new LevelThree(game);
            game.setScreen(restart);
        }
    }


}
