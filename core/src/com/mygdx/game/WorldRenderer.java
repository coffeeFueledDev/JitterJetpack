package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class WorldRenderer {
    private static final float UNIT_SCALE = 1/22f; /** Used to be 1/16 */
    private OrthogonalTiledMapRenderer renderer;
    public Level level;
    private Vector2 mapSize;

    public WorldRenderer(String MAPNAME) {
        System.out.println("MAP NAME: " + MAPNAME);
        level = new Level(MAPNAME);
        renderer = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_SCALE);

        /** Size of map */
        int mapWidth = level.getMap().getProperties().get("width", Integer.class);
        int mapHeight = level.getMap().getProperties().get("height", Integer.class);
        mapSize = new Vector2(mapWidth, mapHeight);

    }

    public void render (float delta, OrthographicCamera cam) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(cam);
        renderer.render();
    }

    /** Return width and height of map */
    public Vector2 getMapSize(){
        return mapSize;
    }


    /****************************/

    public void buildMapCollision(World world, String LAYER){
        System.out.println("BUILDING MAP COLLISION");
        buildShapes(level.getMap(), world, LAYER);
    }

    public static Array<Body> buildShapes(Map map, World world, String LAYER) {
        MapObjects objects = map.getLayers().get(LAYER).getObjects();

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            /** ONLY RECTANGLE LOOP ENTERED
             *  I don't know why yet.. */
            if (object instanceof RectangleMapObject) { /**Works*/
                //System.out.println("RECTANGLE");
                shape = getRectangle((RectangleMapObject)object);
            }
            else if (object instanceof PolygonMapObject) { /**Works*/
                //System.out.println("POLYGON");
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) { /**Works*/
                //System.out.println("POLYLINE");
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) { /** Fails; Only EllipseMapObject in Tiled, not CircleMapObject */
                System.out.println("CIRCLE");
                shape = getCircle((CircleMapObject)object);
            }
            else {
                continue;
            }

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1);

            bodies.add(body);

            shape.dispose();
        }
        return bodies;
    }


    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();

        /** Make collision tiles fit the screen */
        rectangle.x *= UNIT_SCALE;
        rectangle.y *= UNIT_SCALE;
        rectangle.width *= UNIT_SCALE/2;
        rectangle.height *= UNIT_SCALE/2;

        Vector2 size = new Vector2((rectangle.x + rectangle.width), (rectangle.y + rectangle.height));
        polygon.setAsBox(rectangle.width, rectangle.height, size, 0.0f);

        return polygon;
    }


    /** Probably need to change position
     * Has to be Ellipse, Circle does not exist in Tiled
     * @param circleObject*/
    private static Shape getCircle(CircleMapObject circleObject) {
        /*CircleShape circleShape = new CircleShape();
        Circle circle = new Circle();
        //circle.x *= UNIT_SCALE;
        //circle.y *= UNIT_SCALE;
        //circle.radius *= UNIT_SCALE;

        //circleShape.setRadius(ellipse.radius);
        //circleShape.setPosition(new Vector2(ellipse.x, ellipse.y));

        circleShape.setRadius(2);
        circleShape.setPosition(new Vector2(2,2));

        //circleShape.setRadius(circle.radius / ppt);
        //circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        */
        return new CircleShape();
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();


        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
        worldVertices[i] = vertices[i] * UNIT_SCALE;
    }

        polygon.set(worldVertices);
        return polygon;
    }

    /** Probably need to change position */
    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] * UNIT_SCALE;
            worldVertices[i].y = vertices[i * 2 + 1] * UNIT_SCALE;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

}
