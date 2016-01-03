package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
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
import com.mygdx.game.Collectables.Fuel;

/**
 * When applying sprites to box2d look at Fuel.class getRectangleFuelbox method!
 * */
public class WorldRenderer {
    public static final float UNIT_SCALE = 1/22f; /** Used to be 1/16 */
    private OrthogonalTiledMapRenderer renderer;
    public Level level;
    private Vector2 mapSize;

    public static int objectCount = 0;
    public static Vector2 box2dPosition;
    public Array<Body> collectibleBody;
    Fuel fuel;

    public WorldRenderer(String MAPNAME) {
        System.out.println("MAP NAME: " + MAPNAME);
        level = new Level(MAPNAME);
        renderer = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_SCALE);

        /** Size of map */
        int mapWidth = level.getMap().getProperties().get("width", Integer.class);
        int mapHeight = level.getMap().getProperties().get("height", Integer.class);
        mapSize = new Vector2(mapWidth, mapHeight);

        fuel = new Fuel(this);
        boxSize = new Vector2();
    }

    public void render (OrthographicCamera cam) {
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
        System.out.println("BUILDING MAP COLLISION LAYER: " + LAYER);
        buildShapes(level.getMap(), world, LAYER, B2DVars.COLLISION_DATA);
    }

    public void buildMapSafeLayer(World world, String LAYER){
        System.out.println("BUILD MAP SAFE LAYER: " + LAYER);
        buildShapes(level.getMap(), world, LAYER, B2DVars.SAFE_DATA);
    }

    /**
     * FIX ALL METHODS RELATED
     *      - Badly coded in Fuel.class and update methods like done for polygon shape method
     */
    public void buildMapCollectibleLayer(World world, String LAYER){
        System.out.println("BUILD MAP COLLECTIBLE LAYER: " + LAYER);
        objectCount = 0;
        collectibleBody = buildShapes(level.getMap(), world, LAYER, B2DVars.COLLECTIBLE_DATA);

    }


    public static Array<Body> buildShapes(Map map, World world, String LAYER, String USER_DATA) {
        MapObjects objects = map.getLayers().get(LAYER).getObjects();
        box2dPosition = new Vector2();
        boolean polyline;

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects){
            polyline = false;
            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;
            if (object instanceof RectangleMapObject) { /**Works */
                //System.out.println("RECTANGLE");
                shape = getRectangle((RectangleMapObject)object);
            }
            else if (object instanceof PolygonMapObject) { /**Works */
                //System.out.println("POLYGON");
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) { /**Works */
                //System.out.println("POLYLINE");
                polyline = true;
                shape = getPolyline((PolylineMapObject)object);
            }
            else if(object instanceof EllipseMapObject) { /**Works */
                System.out.println("CIRCLE");
                shape = getCircle((EllipseMapObject) object);
                System.out.println("Circle: " + shape.getRadius());
            }
            else {
                continue;
            }

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;

            /** Likely need to update boxSize for other shapes like done for polygon */
            if(!polyline) {
                bd.position.x = boxSize.x;
                bd.position.y = boxSize.y;
            }

            Body body = world.createBody(bd);

            /**bd.position.x = box2dPosition.x;
            bd.position.y = box2dPosition.y;
            Body body = world.createBody(bd);*/
            /** For collectibles to be sensors */
            if(!USER_DATA.equals(B2DVars.COLLECTIBLE_DATA)) {
                body.createFixture(shape, 1).setUserData(USER_DATA);
            } else {
                body.createFixture(shape,1).setSensor(true);
            }


            bodies.add(body);

            shape.dispose();
        }
        return bodies;
    }

    private static Vector2 boxSize;
    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();

        /** Make collision tiles fit the screen */
        rectangle.x *= UNIT_SCALE;
        rectangle.y *= UNIT_SCALE;
        rectangle.width *= UNIT_SCALE/2;
        rectangle.height *= UNIT_SCALE/2;

        Vector2 size = new Vector2((rectangle.x + rectangle.width), (rectangle.y + rectangle.height));
        /**polygon.setAsBox(rectangle.width, rectangle.height, size, 0.0f); MOVED TO bodyDef */

        WorldRenderer.box2dPosition = new Vector2(rectangle.x, rectangle.y);
        boxSize = size;

        polygon.setAsBox(rectangle.width,rectangle.height);

        objectCount++;
        return polygon;
    }


    /** Probably need to change position
     * Has to be Ellipse, Circle does not exist in Tiled*/
    private static CircleShape getCircle(EllipseMapObject circleMapObject) {
        boxSize.x = circleMapObject.getEllipse().x *= UNIT_SCALE;
        boxSize.y = circleMapObject.getEllipse().y *= UNIT_SCALE;
        CircleShape circle = new CircleShape();
        circle.setRadius(circleMapObject.getEllipse().width *= UNIT_SCALE);
        return circle;
    }



    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();


        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            /**System.out.println(vertices[i]);*/
        worldVertices[i] = vertices[i] * UNIT_SCALE;
    }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        System.out.println("new polyline");
        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] * UNIT_SCALE;
            worldVertices[i].y = vertices[i * 2 + 1] * UNIT_SCALE;
            /**System.out.println(worldVertices[i]);*/
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

}
