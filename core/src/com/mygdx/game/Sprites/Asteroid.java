package com.mygdx.game.Sprites;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2DVars;

/**
 * Generates one asteroid randomly left or right inside top half of camera viewport
 *  - Called multiple times in other class
 *
 * */
public class Asteroid {
    World world;
    public Body asteroid;
    public FixtureDef fixtureDef;
    float DENSITY = 3.0f*2, FRICTION = 0.4f, RESTITUTION = 0.4f*2;


    public Asteroid(World world, Vector2 pos, float rad){
        this.world = world;
        generateAsteroid(pos, rad);

    }

    public void destroyAsteroid(){
        world.destroyBody(asteroid);
    }


    public void generateAsteroid(Vector2 pos, float rad){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x, pos.y);
        asteroid = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(rad);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = DENSITY;
        fixtureDef.friction = FRICTION;
        fixtureDef.restitution = RESTITUTION;
        asteroid.createFixture(fixtureDef).setUserData(B2DVars.COLLISION_DATA);
        circle.dispose();

        asteroid.setGravityScale(0);
    }

    public void moveAsteroid(Vector2 vector){
        asteroid.applyAngularImpulse(70, true);
        asteroid.applyForceToCenter(new Vector2(vector), true);
    }



}
