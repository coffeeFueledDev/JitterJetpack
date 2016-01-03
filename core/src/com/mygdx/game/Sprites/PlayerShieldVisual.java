package com.mygdx.game.Sprites;

import com.badlogic.gdx.physics.box2d.World;

/**
 *
 */
public class PlayerShieldVisual extends Player{

    public PlayerShieldVisual(World world) {
        super(world);
    }

    public void setShieldVisual(){
        if(super.shield)
            System.out.println("shield current");
        else System.out.println("no shield");
    }

}
