package com.mygdx.game.PowerUps;

import com.mygdx.game.Sprites.Player;

/**
 *
 */
public class ShieldPower {
    Player player;
    public int shieldTimer;

    public ShieldPower(Player player){
        this.player = player;
    }

    public void activateShield(){
        player.setShieldActivated(true);
    }

    public void deactivateShield(){
        player.setShieldActivated(false);
    }

    public float updateShieldTimer(float timer){
        if(timer >= 1) {
            shieldTimer++;
            return 0;
        }
        return timer;
    }

    public void resetShieldTimer(){
        shieldTimer = 0;
    }

    public int getShieldTimer(){
        return shieldTimer;
    }

}
