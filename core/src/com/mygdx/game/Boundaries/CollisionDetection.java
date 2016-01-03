package com.mygdx.game.Boundaries;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2DVars;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Sprites.Player;

public class CollisionDetection {
    World world;
    Player player;
    GameScreen gameScreen;



    public CollisionDetection(World world, Player player, GameScreen gameScreen){
        this.world = world;
        this.player = player;
        this.gameScreen = gameScreen;
    }

    /** Inside world step */
    public void collisionDetector(){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(contact.getFixtureA().getUserData() == null || contact.getFixtureB().getUserData() == null){
                    if(contact.getFixtureA().isSensor() && contact.getFixtureB().getUserData().equals(B2DVars.PLAYER_DATA)){
                        player.setCollected(true);
                        player.collectionPosition = contact.getFixtureA().getBody().getPosition();
                        return;
                    } else
                    if(contact.getFixtureB().isSensor()&& contact.getFixtureA().getUserData().equals(B2DVars.PLAYER_DATA)){
                        player.setCollected(true);
                        player.collectionPosition = contact.getFixtureB().getBody().getPosition();
                        return;
                    }
                    System.out.println("FIXTURE USER_DATA NOT SET FOR COLLISION DETECTOR: (NULL)!");
                    return;
                }


                String contactA = contact.getFixtureA().getUserData().toString();
                String contactB = contact.getFixtureB().getUserData().toString();

                if(player.getShieldActivated()) {
                    System.out.println("Player has a shield!");
                    if(contactA.equals(B2DVars.WIN_DATA) ||
                            contactB.equals(B2DVars.WIN_DATA)){
                        player.crashed(false);
                        player.win(true);
                    }

                    return;
                }

                player.crashed(true);

                if(contactA.equals(B2DVars.SAFE_DATA) ||
                        contactB.equals(B2DVars.SAFE_DATA)){
                    player.crashed(false);
                }


                if(contactA.equals(B2DVars.COLLISION_DATA) &&
                        contactB.equals(B2DVars.COLLISION_DATA)) {
                    player.crashed(false);
                }

                if(contactA.equals(B2DVars.WIN_DATA) ||
                        contactB.equals(B2DVars.WIN_DATA)){
                    player.crashed(false);
                    player.win(true);
                }


            }


            @Override
            public void endContact(Contact contact) {
                player.crashed(false);
                player.setCollected(false);
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
