package com.mygdx.game.Collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.WorldVars;


/**
 * Not finished.
 *
 * Converts fuel to "background" tile once touched
 */
public class Fuel {
    WorldRenderer renderer;
    public Sprite fuelSprite;
    public static Array<Body> fuelArray = new Array<Body>();

    public Fuel(WorldRenderer renderer) {
        this.renderer = renderer;

    }

    public boolean destroyFuelSprite(Vector2 vector){
        for(int i =0; i<fuelArray.size;i++) {
            if(fuelArray.get(i).getPosition() == vector) {
                if(fuelArray.get(i).getUserData().equals(Collectible.EXHAUSTED))
                    return false;
                fuelArray.get(i).getFixtureList().clear();
                fuelArray.get(i).setUserData(Collectible.EXHAUSTED);
                System.out.println("fuel array: " + i);
                return true;
            }
        }
        return false;
    }



    /** Probably need to make this much better looking/acting */
    public Array<Body> setFuelSprites(){
        Array<Body> bodies = new Array<Body>();
        Texture textureS = new Texture(Gdx.files.internal(WorldVars.FUEL_SPRITE));
        int i = 0;
        while(i < renderer.objectCount){
            fuelSprite = new Sprite(textureS);
            fuelSprite.setSize(1, 2);
            renderer.collectibleBody.get(i).setUserData(fuelSprite);
            bodies.insert(i, renderer.collectibleBody.get(i));
            i++;
        }

        fuelArray = bodies;
        return bodies;
    }

}
