package com.mygdx.game.Sprites;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class BadGuy {
    World world;

    public BadGuy(World world){
        this.world = world;
    }

    public void ball(){
        /** BALL
         * Body definition */
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; /** move-able */
        bodyDef.position.set(0, 10); /** meters */

        /** ball shape */
        CircleShape shape = new CircleShape();
        shape.setRadius(1.0f); /** meters */

        /** fixture definition */
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f; /** kilograms */
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 3.0f; /** bounciness, percentage bounced up from height of fall */

        world.createBody(bodyDef).createFixture(fixtureDef);
        shape.dispose();

    }


}
