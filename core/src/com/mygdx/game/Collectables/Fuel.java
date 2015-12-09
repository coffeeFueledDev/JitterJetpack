package com.mygdx.game.Collectables;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Not finished.
 *
 * Converts fuel to "background" tile once touched
 */
public class Fuel {
    World world;

    public Fuel(World world) {
        this.world = world;
    }

    public void changeTile(TiledMap map){
        //map.getTileSets().getTile(10) = map.getTileSets().getTile(29);
        map.getTileSets().getTile(45).setId(198);
        //System.out.println("ID: " +  id);
    }


}
