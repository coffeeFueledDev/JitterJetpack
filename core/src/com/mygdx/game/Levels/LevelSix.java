package com.mygdx.game.Levels;

import com.mygdx.game.Collectables.Collectible;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PowerUps.PowerUpManager;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.WorldVars;

/**
 *
 */
public class LevelSix extends GameScreen{
    Space space;
    Collectible collectible;
    PowerUpManager powerUpManager;

    @Override
    public void updateRunning(float delta) {
        super.updateRunning(delta);
        space.update(delta, super.camera);

        /*if(changeLevelWon) {
            System.out.println("NEXT LEVEL NOT IMPLEMENTED YET");
            state = GAME_PAUSED;
        }*/

        collectible.collectibleManager();
        powerUpManager.powerUpManager(delta, super.counters);

    }

    public LevelSix(MyGdxGame game) {
        super(game);
        super.renderer = new WorldRenderer(WorldVars.LEVEL_SIX);
        super.renderer.buildMapCollision(super.world, WorldVars.OBJECT_LAYER);
        super.renderer.buildMapSafeLayer(super.world, WorldVars.SAFE_LAYER);

        collectible = new Collectible(super.renderer, player);
        collectible.setFuel(super.world);
        collectible.setShield(super.world);
        powerUpManager = new PowerUpManager(player);
    }


    @Override
    public void show() {
        space = new Space(super.world);
        super.show();

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
                break;
            case GAME_WON:
                break;
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

    @Override
    public void updateGameWon() {
        super.updateGameWon();
        if(changeLevelWon) {
            System.out.println("NEXT LEVEL NOT IMPLEMENTED YET");
            state = GAME_PAUSED;
        }
    }

    public void restartThisLevel(){
        if(super.restartGame){
            GameScreen restart = new LevelSix(game);
            game.setScreen(restart);
        }
    }


}
