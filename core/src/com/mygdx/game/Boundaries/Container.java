package com.mygdx.game.Boundaries;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Container {
    private World world;
    private BodyDef bodyDef;

    public Container(World world){
        this.world = world;

    }

    public void createContainer(float mapSize){
        container(0); /** Ground */
        container(mapSize); /** Roof */
    }

    public Vector2 getRoofDimensions(){
        return bodyDef.position;
    }

    public void container(float height){
        bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        /** WALL
         * Body definition*/
        bodyDef.type = BodyDef.BodyType.StaticBody; /** non-move-able */
        bodyDef.position.set(0, height);

        /** ground shape */
        ChainShape shape = new ChainShape(); /** ChainShape is a line */
        shape.createChain(new Vector2[] {new Vector2(0, 0), new Vector2(75, 0)}); /** chain between these two vector2's (-Xm -> Xm) */

        /** fixture definition */
        fixtureDef.shape = shape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);
        shape.dispose();
    }

    public boolean wraparoundEffect(Body box, Camera camera){
        if(box.getPosition().x > camera.viewportWidth) {
            box.setTransform(0, box.getPosition().y, box.getAngle());
            return true;
        }
        if(box.getPosition().x < 0) {
            box.setTransform(camera.viewportWidth, box.getPosition().y, box.getAngle());
            return true;
        }
        return false;
    }



}
