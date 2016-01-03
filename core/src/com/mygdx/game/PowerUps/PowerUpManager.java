package com.mygdx.game.PowerUps;

import com.mygdx.game.Collectables.Collectible;
import com.mygdx.game.HUD.Counters;
import com.mygdx.game.Sprites.Player;

/**
 *
 */
public class PowerUpManager {
    Player player;
    ShieldPower shieldPower;
    private float timer;
    private static int SHIELD_ACTIVATION_TIME = 10;
    private boolean shieldActivated = false;

    public PowerUpManager(Player player){
        this.player = player;
        shieldPower = new ShieldPower(player);
    }

    public void powerUpManager(float dt, Counters counters){
        if(player.getObjectCollected() == null) {
            return;
        }

        if(player.getObjectCollected().equals(Collectible.FUEL_ITEM)){
            player.fuelCollected();
            player.setObjectCollected(Collectible.EXHAUSTED);
        }

        if(player.getObjectCollected().equals(Collectible.SHIELD_ITEM)) {
            shieldActivated = true;
        }

        if(shieldActivated){
            timer += dt;
            timer = shieldPower.updateShieldTimer(timer);
            counters.setShieldTime(shieldPower.getShieldTimer());
            if (shieldPower.getShieldTimer() < SHIELD_ACTIVATION_TIME) {
                shieldPower.activateShield();
            } else {
                player.setObjectCollected(Collectible.EXHAUSTED);
                shieldPower.deactivateShield();
                shieldPower.resetShieldTimer();
                shieldActivated = false;
            }

        }

    }

    public int getShieldActivationTime(){
        return shieldPower.getShieldTimer();
    }


}
