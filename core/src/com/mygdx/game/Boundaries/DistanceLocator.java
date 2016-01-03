package com.mygdx.game.Boundaries;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

/**
 * NOT IN USE; MAY NEVER USE.
 *
 * This class is for sensing when an object below the sprite
 * is at a certain distance from the sprite
 *
 * - Collision Filtering
 */
public class DistanceLocator {
    Body sensor;
    World world;
    public int SENSOR_DISTANCE = 5;

    public DistanceLocator(World world){
        this.world = world;
    }

    public void updateSensor(Body player){

    }

    public Body objectDistanceSensor(Body player){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();


        bodyDef.type = BodyDef.BodyType.KinematicBody;
        //bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(player.getPosition().x, player.getPosition().y - SENSOR_DISTANCE);
        bodyDef.fixedRotation = true;

        ChainShape shape = new ChainShape();
        shape.createChain(new Vector2[]{new Vector2(0, 0), new Vector2(1, 0)});

        fixtureDef.shape = shape;

        sensor = world.createBody(bodyDef);
        sensor.createFixture(fixtureDef);

        /**Distance Joint with player*//*
        DistanceJointDef distanceJointDef = new DistanceJointDef();
        distanceJointDef.bodyA = player;
        distanceJointDef.bodyB = sensor;
        distanceJointDef.length = SENSOR_DISTANCE;
        world.createJoint(distanceJointDef);
        */
        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = sensor;
        ropeJointDef.bodyB = player;
        ropeJointDef.maxLength = 5;
        ropeJointDef.localAnchorA.set(0,0);
        ropeJointDef.localAnchorB.set(0,0);
        world.createJoint(ropeJointDef);

        shape.dispose();

        return sensor;

    }

}
