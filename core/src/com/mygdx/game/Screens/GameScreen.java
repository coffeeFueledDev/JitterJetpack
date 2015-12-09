package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Boundaries.CollisionDetection;
import com.mygdx.game.Boundaries.Container;
import com.mygdx.game.HUD.Counters;
import com.mygdx.game.HUD.Hud;
import com.mygdx.game.HUD.MenuOverlay;
import com.mygdx.game.GenericControls.InputController;
import com.mygdx.game.Levels.LevelOne;
import com.mygdx.game.Levels.LevelTwo;
import com.mygdx.game.Movement.Movement;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.WorldRenderer;

/** FIX/DO:
 *  - Add loading screen to map creation so back gdx color layer doesn't show for 2 frames
 *
 *  - Create multiple object layers in Tiled for different collision properties
 *
 *  - Fix circle objects; Tiled uses Ellipses, not Circles.
 *  - Moving tiles, or sprites..?
 *
 * - If hit-box collides -> crash player
 *   - Box drawing multiple times after crashScreen switch
 *
 * - Tip: Use AssetManager for handling lots of textures
 * - Remember to dispose in all classes
 * - Ways to lose brainstorm:
 *      - Score = zero -> mission failed; Start whole map again.
 *          - Score decrements with time
 *          - Lose score when hit objects
 *      - Hit bad object -> respawn at checkpoint
 *
 * */

public class GameScreen implements Screen{
    CrashScreen crashScreen;
    Counters counters;
    Player player;
    Container container;
    Movement spriteMovement;
    CollisionDetection collisionDetection;
    private Hud hud;
    private MenuOverlay menuOverlay;

    public float GRAVITY = -9.81f;
    private static final float TIMESTEP = 1 / 60f; /** 60 frames per second, so 1/60 */
    private static final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    float ZOOM = 25;

    private MyGdxGame game;
    public WorldRenderer renderer;
    public World world;
    private Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    private Body box;

    SpriteBatch batch;
    private Array<Body> tmpBodies = new Array<Body>();
    SpriteBatch hudBatch;
    SpriteBatch menuBatch;

    InputProcessor inputController;
    InputMultiplexer inputMultiplexer;
    boolean menuPressed = false;

    public static final int GAME_READY = 0, GAME_RUNNING = 1, GAME_PAUSED = 2, GAME_OVER = 3;
    public int state;


    public GameScreen(MyGdxGame game) {
        this.game = game;
        crashScreen = new CrashScreen(game);
        batch = new SpriteBatch();
        world = new World(new Vector2(0, GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        player = new Player(world);
        spriteMovement = new Movement(player);
        collisionDetection = new CollisionDetection(world, player);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        hud = new Hud(hudBatch);
        menuBatch = new SpriteBatch();
        menuOverlay = new MenuOverlay(menuBatch);

        /** Dynamic Player object */
        player.setHitBoxPosition(new Vector2(22, 15));
        if(box == null)
            box = player.createHitBox();
        player.setStationarySprite();
        player.crashed(false);

        renderer.buildMapCollision(world, "oj");/**should probably build map somewhere else. Layer names likely to change with different levels and maps*/
        collisionDetection.collisionDetector();


        /** Multiplexer
         * - Allows control over multiple key inputs */
        Gdx.input.setCatchBackKey(true);
        inputController = new InputController(){
            @Override
            public boolean keyUp(int keycode) {
                if(keycode == Input.Keys.BACK){
                    System.out.println("BACK");
                    menuPressed = true;
                }
                return true;
            }

        };
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(menuOverlay.stage);
        inputMultiplexer.addProcessor(inputController);
        inputMultiplexer.addProcessor(spriteMovement.spriteMovementListener());
        Gdx.input.setInputProcessor(inputMultiplexer);

        container = new Container(world);

        /** Roof; Not sure why 1.455f, but works dynamically with any map height
         *  - Roof is slightly off on some maps; Probably something to do with Tiled size vs box2d sizes
         *      - Minor Bug; fix prior to release
         * sets floor also at x=0 */
        container.createContainer(renderer.getMapSize().y * 1.4555f);

        counters = new Counters();

        state = GAME_READY;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(delta, camera); /** render map */
        /** debugRenderer Renders box2d;
         * Must have this set until sprites are added or there will be invisible boxes */
        debugRenderer.render(world, camera.combined);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        /** Draws the sprite body to the hit-box */
        world.getBodies(tmpBodies);
        for(Body box : tmpBodies){
            if(box.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite) box.getUserData();
                sprite.setPosition(box.getPosition().x - sprite.getWidth()/2, box.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(box.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        batch.end();

        /** HUD */
        hud.counter.setHeight((int) box.getPosition().y);
        hudBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        switch (state){
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(delta);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }



    }

    public void updateRunning(float delta){
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        /** Apply force to player hit-box */
        box.applyForceToCenter(spriteMovement.getMovement(), true);

        if(Gdx.input.justTouched())
            spriteMovement.boost();

        moveCameraWithSprite();

        hud.update(delta);

        if(menuPressed) {
            System.out.println("MENU_PRESSED");
            state = GAME_PAUSED;
        }

        container.wraparoundEffect(box, camera);
        camera.update();
    }

    public void updateReady(){
        state = GAME_RUNNING;
    }



    public void updatePaused(){
        menuBatch.setProjectionMatrix(menuOverlay.stage.getCamera().combined);
        menuOverlay.stage.draw();

        switch(menuOverlay.menuOption){
            case 0:/**returned*/
                handleMenuOverlay();
                state = GAME_RUNNING;
                break;
            case 1:/**start again*/
                handleMenuOverlay();
                restartGame();
                break;
            case 2:/**level select*/
                handleMenuOverlay();
                LevelSelect levelSelect = new LevelSelect(game);
                game.setScreen(levelSelect);
                break;
            case 3:/**main menu*/
                handleMenuOverlay();
                Menu menu = new Menu(game);
                game.setScreen(menu);
                break;
            default:
                break;
        }
        menuOverlay.menuOption = -1;
    }

    public void handleMenuOverlay(){
        menuOverlay.stage.unfocusAll();
        menuPressed = false;
    }

    public void restartGame(){
        GameScreen restart;
        switch(game.levelSel){
            case 0:
                restart = new LevelOne(game);
                break;
            case 1:
                restart = new LevelTwo(game);
                break;
            default:
                restart = new LevelOne(game);
                break;
        }
        game.setScreen(restart);
    }

    public void updateGameOver(){

    }


    /** Camera follows player and doesn't leave map */
    public boolean moveCameraWithSprite(){
        /** ROOF */
        if(box.getPosition().y >= container.getRoofDimensions().y - camera.viewportHeight/2){
            camera.position.y = container.getRoofDimensions().y - camera.viewportHeight/2;
            return true;
        }

        /** NORMAL */
        if(box.getPosition().y > camera.viewportHeight/2) {
            camera.position.y = box.getPosition().y;
            return true;
        }

        /** GROUND */
        camera.position.y = camera.viewportHeight/2;
        return true;
    }

    /** Called prior to show */
    @Override
    public void resize(int width, int height) {
        /** Move camera closer to ball */
        camera.viewportWidth = width / ZOOM;
        camera.viewportHeight = height / ZOOM;
        camera.position.set((width/ZOOM)/2, (height/ZOOM), 0);
        camera.position.x = camera.viewportWidth/2;

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();

    }




    public void endLife(){
        float delay = 3;
        spriteMovement.crashed();
        Gdx.input.setInputProcessor(null);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(crashScreen);
                hide();
            }
        }, delay, 0, 1);

    }

}
