package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2DVars;

public class Player extends Sprite {
    public boolean crashed = false;
    private boolean win = false;
    private boolean collected = false;
    private Vector2 vectorHitBoxPosition;
    World world;
    Body box;
    private Sprite playerSprite;
    private Sprite boost;
    private Sprite stationary;
    public Vector2 collectionPosition;
    protected boolean shield = false;

    public float HITBOX_Y_RESIZE = 0.75f - 0.25f;
    public final Vector2 HIT_BOX_SIZE = new Vector2(0.5f*2, 1f*2);
    public float PLAYER_RESTITUTION = 0.2f, PLAYER_DENSITY = 5, PLAYER_FRICTION = 0.75f*10;

    B2DVars B2DVars;
    String objectCollected;
    private int health;
    private static int PLAYER_START_HEALTH = 100;
    private static int fuelNumber;

    public Player(World world){
        this.world = world;
        init_sprites();
        B2DVars = new B2DVars();
        collectionPosition = new Vector2();
        health = PLAYER_START_HEALTH;
    }

    public void init_sprites(){
        Texture texture = new Texture(Gdx.files.internal("player/stationary.png"));
        stationary = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("player/boost.png"));
        boost = new Sprite(texture);
    }

    public void fuelCollected(){
        fuelNumber++;
    }

    public int getFuelNumber(){
        return fuelNumber;
    }

    public void deteriorateHealth(int damage){
        health -= damage;
        Gdx.input.vibrate(100);
    }

    public int getCurrentHealth(){
        return health;
    }


    public void setShieldActivated(Boolean shield){
        this.shield = shield;
    }

    public boolean getShieldActivated(){
        return shield;
    }

    public void setHitBoxPosition(Vector2 vector){
        vectorHitBoxPosition = vector;
    }

    public void setCollected(boolean truth){
        this.collected = truth;
    }

    public boolean hasCollected(){
        return collected;
    }

    public void setObjectCollected(String object){
        objectCollected = object;
        System.out.println("Collected: "  + objectCollected);
    }

    public String getObjectCollected(){
        return objectCollected;
    }

    public void crashed(boolean truth){
        crashed = truth;
    }

    public void win(boolean truth){
        win = truth;
    }

    public boolean hasWon(){
        return win;
    }


    /** Changes boost and stationary sprite to shield colour */
    public void setShieldActivatedSprite(){
        System.out.println("SET SHIELD ACTIVATED SPRITE");

    }

    public void resetShieldDeactivatedSprite(){
        System.out.println("RESET SHIELD DE-ACTIVATED SPRITE");
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
        box.createFixture(fixtureDef).setUserData(com.mygdx.game.B2DVars.PLAYER_DATA);

        /** Bottom sensor so player can land on harsh objects and not crash
        EdgeShape bottom = new EdgeShape();
        bottom.set(new Vector2(-2,-1), new Vector2(2,-1));
        fixtureDef.shape = bottom;
        fixtureDef.isSensor = true;
        box.createFixture(fixtureDef).setUserData("BOTTOM");*/

        box.applyAngularImpulse(50, true);

        boxShape.dispose();

        return box;
    }


}
