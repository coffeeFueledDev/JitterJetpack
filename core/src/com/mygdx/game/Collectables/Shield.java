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
 *
 */
public class Shield {
   WorldRenderer renderer;

   public Sprite shieldSprite;
   public static Array<Body> shieldArray = new Array<Body>();
   public static Vector2 SHIELD_SPRITE_SIZE = new Vector2(4,4);

   public Shield(WorldRenderer renderer) {
      this.renderer = renderer;

   }

   public boolean destroyShieldSprite(Vector2 vector){
      for(int i =0; i<shieldArray.size;i++) {
         if(shieldArray.get(i).getPosition() == vector) {
             if(shieldArray.get(i).getUserData().equals(Collectible.EXHAUSTED))
                 return false;
             shieldArray.get(i).getFixtureList().clear();
             shieldArray.get(i).setUserData(Collectible.EXHAUSTED);
            System.out.println("shield array: " + i);
             return true;
         }
      }
       return true;
   }



   /** Probably need to make this much better looking/acting */
   public Array<Body> setShieldSprites(){
      Array<Body> bodies = new Array<Body>();
      Texture textureS = new Texture(Gdx.files.internal(WorldVars.SHIELD_SPRITE));
      shieldSprite = new Sprite(textureS);
      int i = 0;
      while(i < renderer.objectCount){
         shieldSprite.setSize(SHIELD_SPRITE_SIZE.x, SHIELD_SPRITE_SIZE.y);
         renderer.collectibleBody.get(i).setUserData(shieldSprite);
         bodies.insert(i, renderer.collectibleBody.get(i));
         i++;
      }

      shieldArray = bodies;
      return bodies;
   }



}
