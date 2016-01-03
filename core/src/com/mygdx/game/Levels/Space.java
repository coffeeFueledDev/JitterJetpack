package com.mygdx.game.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Asteroid;
import com.mygdx.game.WorldVars;

import java.util.Random;

public class Space {
    Asteroid asteroid[] = new Asteroid[3];
    World world;
    float time = 0;
    int asteroidCounter = 0;
    float randY;
    float randSide;
    Vector2 ranMovement;
    float ASTEROID_HORIZONTAL_SPEED = 10000;
    float ASTEROID_RADIUS = 1.0f;
    Texture texture;
    Sprite asteroidSprite;

    public Space(World world){
        this.world = world;
        initSprites();
    }

    /**
     * Left or right random (0 or 1)
     * y within camera view*/
    public void randomisePosition(Camera camera){
        Random random = new Random();

        /** LEFT */
        if(random.nextInt(2) < 1) {
            randSide = 0;
            ranMovement = new Vector2(ASTEROID_HORIZONTAL_SPEED, 0);
        }
        /** RIGHT */
        else {
            randSide = camera.viewportWidth;
            ranMovement = new Vector2(-ASTEROID_HORIZONTAL_SPEED, 0);
        }

        float minY = camera.position.y + camera.viewportHeight/2;
        float maxY = camera.position.y;
        randY = random.nextFloat() * ( maxY - minY) + minY;

    }


    public void update(float dt, Camera camera){
            time += dt;
            if(time >= 3.0f){
                if(asteroid[asteroidCounter]!=null)
                    asteroid[asteroidCounter].destroyAsteroid();
                randomisePosition(camera);
                asteroid[asteroidCounter] = new Asteroid(world, new Vector2(randSide, randY), ASTEROID_RADIUS);
                asteroid[asteroidCounter].moveAsteroid(ranMovement);
                asteroid[asteroidCounter].asteroid = setSprite(asteroid[asteroidCounter].asteroid); /** sets asteroid sprite data */
                asteroidCounter++;
                if(asteroidCounter > 2){
                    asteroidCounter = 0;
                }
                time = 0;
            }
        }

    public void initSprites(){
        texture = new Texture(Gdx.files.internal(WorldVars.ASTEROID_SPRITE));
        asteroidSprite = new Sprite(texture);
        asteroidSprite.setSize(2, 2);
        asteroidSprite.setOrigin(asteroidSprite.getWidth()/2,asteroidSprite.getHeight()/2);
    }

    public Body setSprite(Body body){
        body.setUserData(asteroidSprite);
        return body;
    }

}


