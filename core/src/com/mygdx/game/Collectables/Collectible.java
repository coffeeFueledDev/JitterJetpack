package com.mygdx.game.Collectables;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.WorldVars;

/**
 * Sensor still operating once object is collected
 *  - I have created a workaround but not fixed the root cause
 *  - Repeat bug by picking up an item and then moving back to the position the item 'used' to be in
 *      - System will print "Found sensor / this is a bug and should be fixed" inside collectibleManager method
 */
public class Collectible {
    WorldRenderer renderer;
    Fuel fuel;
    com.mygdx.game.Collectables.Shield shield;
    Player player;
    private Vector2 RESET = new Vector2(0,0);
    private boolean fuelSet = false;
    private boolean shieldSet = false;
    public static String EXHAUSTED = "EXHAUSTED";
    public static String FUEL_ITEM = "FUEL";
    public static String SHIELD_ITEM = "SHIELD";

    public Collectible(WorldRenderer renderer, Player player){
        this.renderer = renderer;
        this.player = player;
    }

    public void collectibleManager(){
        if(player.hasCollected()) {
            System.out.println("Found sensor / this is a bug and should be fixed");
            destroySprite();
            player.setCollected(false);
        }
    }

    public void destroySprite(){
        if(fuelSet) {
            if(fuel.destroyFuelSprite(player.collectionPosition)) {
                player.setObjectCollected(FUEL_ITEM);
                return;
            }
            System.out.println("Object collected: " + player.getObjectCollected());
        }
        if(shieldSet) {
            if (shield.destroyShieldSprite(player.collectionPosition)) {
                player.setObjectCollected(SHIELD_ITEM);
                return;
            }
            System.out.println("Object collected: " + player.getObjectCollected());
        }
        System.out.println("Object collected: " + player.getObjectCollected());
        player.collectionPosition = RESET;
    }

    public void setFuel(World world){
        renderer.buildMapCollectibleLayer(world, WorldVars.FUEL_LAYER);
        fuel = new Fuel(renderer);
        fuel.setFuelSprites();
        fuelSet = true;
    }

    public void setShield(World world){
        renderer.buildMapCollectibleLayer(world, WorldVars.SHIELD_LAYER);
        shield = new com.mygdx.game.Collectables.Shield(renderer);
        shield.setShieldSprites();
        shieldSet = true;
    }


}
