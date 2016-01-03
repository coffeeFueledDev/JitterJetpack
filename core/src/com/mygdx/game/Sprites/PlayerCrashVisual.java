package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;


/**
 *
 */
public class PlayerCrashVisual {
    public Animation animation;
    TextureRegion currentFrame;
    TextureAtlas expl;
    public static float EXPLOSION_SIZE = 6;

    private float state_time = 0;

    public PlayerCrashVisual(){
        expl = new TextureAtlas(Gdx.files.internal("cool effects/explosion.atlas"));
        animation = new Animation(1/30f, expl.getRegions());
    }



    public void update(float dt, SpriteBatch batch, Body box){
        state_time += dt;
        currentFrame = animation.getKeyFrame(state_time, false);
        batch.draw(currentFrame, box.getPosition().x - EXPLOSION_SIZE/2, box.getPosition().y - EXPLOSION_SIZE/2, EXPLOSION_SIZE, EXPLOSION_SIZE);

    }




}
