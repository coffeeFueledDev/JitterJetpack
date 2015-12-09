package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Level {
    private TiledMap map;

    public Level(String tilemapName){
        map = new TmxMapLoader().load(tilemapName);

    }

    public TiledMap getMap() {
        return map;
    }


}
