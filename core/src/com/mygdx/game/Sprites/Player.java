package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Sprite {
    private TextureRegion jpStationary;
    private TextureRegion jpBoost;
    private boolean crashed;
    private Vector2 vectorHitBoxPosition;
    World world;
    Body box;
    private Sprite playerSprite;
    private Sprite boost;
    private Sprite stationary;

    public float HITBOX_Y_RESIZE = 0.75f - 0.25f;
    public final Vector2 HIT_BOX_SIZE = new Vector2(0.5f*2, 1f*2);
    public float PLAYER_RESTITUTION = 0.5f, PLAYER_DENSITY = 5, PLAYER_FRICTION = 0.75f*2;

    public Player(World world){
        this.world = world;
        init_sprites();
    }

    public void init_sprites(){
        Texture texture = new Texture(Gdx.files.internal("player/stationary.png"));
        stationary = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("player/boost.png"));
        boost = new Sprite(texture);
    }

    public void setHitBoxPosition(Vector2 vector){
        vectorHitBoxPosition = vector;
    }


    public void crashed(boolean truth){
        crashed = truth;
    }

    public Body setBoostSprite(){
        playerSprite = boost;
        playerSprite.setSize(HIT_BOX_SIZE.x * 2, HIT_BOX_SIZE.y * 2);
        box.setUserData(playerSprite);
        return box;
    }

    public Body setStationarySprite(){
        playerSprite = stationary;
        playerSprite.setSize(HIT_BOX_SIZE.x * 2, HIT_BOX_SIZE.y * 2);
        box.setUserData(playerSprite);
        return box;
    }

    public Body createHitBox(){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        /** BOX
         * Box definition*/
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(vectorHitBoxPosition.x, vectorHitBoxPosition.y);
        bodyDef.fixedRotation = true;
        /** box shape */
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(HIT_BOX_SIZE.x, HIT_BOX_SIZE.y*HITBOX_Y_RESIZE); /** y multiplication to compensate for sprite jetpack blast having no collision */
        /** fixture definition */
        fixtureDef.shape = boxShape;
        fixtureDef.friction = PLAYER_FRICTION;
        fixtureDef.restitution = PLAYER_RESTITUTION; /** Bounciness */
        fixtureDef.density = PLAYER_DENSITY;

        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef);

        box.applyAngularImpulse(50, true);

        boxShape.dispose();

        return box;
    }


}
