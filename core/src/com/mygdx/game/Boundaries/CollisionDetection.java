package com.mygdx.game.Boundaries;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Player;

public class CollisionDetection {
    World world;
    Player player;


    public CollisionDetection(World world, Player player){
        this.world = world;
        this.player = player;
    }


    public void collisionDetector(){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                /** Change to true prior to release */
                player.crashed(true);

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }



}
