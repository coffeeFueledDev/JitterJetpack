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
import com.mygdx.game.Boundaries.CollisionDetection;
import com.mygdx.game.Boundaries.Container;
import com.mygdx.game.GenericControls.InputController;
import com.mygdx.game.HUD.Counters;
import com.mygdx.game.HUD.CrashOverlay;
import com.mygdx.game.HUD.Hud;
import com.mygdx.game.HUD.MenuOverlay;
import com.mygdx.game.HUD.WinOverlay;
import com.mygdx.game.Movement.Movement;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.PlayerCrashVisual;
import com.mygdx.game.WorldRenderer;

import java.util.ArrayList;

/** FIX/DO:
 *
 * - Create Tutorial map
 *  - Make shield visual
 *  - Refactor shield for re-activation on acquire
 *
 * - Change sprite colour when shield is applied
 * - Add damage visual to sprite
 *
 *  - LevelThree Tiled map lagged to shit when there are lots of corners of box2d (Honestly it's likely just a box2d set-back)
 *      - Best scenario is to create Tiled maps that do not warrant as many corners
 *
 *  - PowerUpOverlay not setting correctly in updateRunning
 *  - Inside Counters.class shieldTimer is static because I am creating multiple Counters classes accidentally
 *      - Inside LevelTwo.class I have grabbed the super.counters from GameScreen.class and this is what leads to the
 *        objects not connecting
 *          - It might be a good idea to refactor all of the PowerUpManager counting methods to the counter class!
 *
 *  - Greater than three sided objects seem to be rendered as triangles instead
 *  - Create cool shield timer as an overlay (like Hud)
 *
 *                                           Refactor, Refactor, Refactor
 * - I think levelSel is obsolete now
 *
 * - Shield timer does not reset once a new shield is collected (i.e if the shield is on 7 seconds and another is collected, it does not reset to 10 seconds)
 *      - 70% sure a fix requires refactoring
 *
 *  - Add power-ups!
 *      - Collectible or use-on-acquire?
 *      - Shield (almost complete backend)
 *      - Teleportation? (Probably tunnels of some sort)
 *
 *
 * - Implement AssetManager for handling textures
 *   - Add loading screen to map creation so gdx color layer doesn't show for 2 frames
 *   - Remember to dispose in all classes
 *
 *   - Asteroids can finish the game on LevelThree, and most likely other levels - bug
 *
 * */

public class GameScreen implements Screen{
    CrashScreen crashScreen;
    protected Counters counters;
    protected Player player;
    Container container;
    Movement spriteMovement;
    CollisionDetection collisionDetection;

    private Hud hud;
    private MenuOverlay menuOverlay;
    private CrashOverlay crashOverlay;
    protected WinOverlay winOverlay;
    PlayerCrashVisual crashVisual;

    public float GRAVITY = -9.81f;
    private static final float TIMESTEP = 1 / 60f; /** 60 frames per second, so 1/60 */
    private static final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    public static float ZOOM = 25;
    public static final int GAME_READY = 0, GAME_RUNNING = 1, GAME_PAUSED = 2, GAME_OVER = 3, GAME_WON = 4;
    public static int SPRITE_POSITION_MIDDLE_OFFSET = 10;

    protected MyGdxGame game;
    public WorldRenderer renderer;
    public World world;
    protected Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    protected Body box;

    SpriteBatch batch;
    private Array<Body> tmpBodies = new Array<Body>();
    SpriteBatch hudBatch;
    protected SpriteBatch menuBatch;

    InputProcessor inputController;
    InputMultiplexer inputMultiplexer;
    boolean menuPressed = false;

    public int state;
    public ArrayList<Body> spriteDeletionList;


    public GameScreen(MyGdxGame game) {
        this.game = game;
        crashScreen = new CrashScreen(game);
        batch = new SpriteBatch();
        world = new World(new Vector2(0, GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        player = new Player(world);
        spriteMovement = new Movement(player);
        collisionDetection = new CollisionDetection(world, player, this);
        spriteDeletionList = new ArrayList<Body>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        hud = new Hud(hudBatch);
        menuBatch = new SpriteBatch();
        menuOverlay = new MenuOverlay(menuBatch);
        crashOverlay = new CrashOverlay(menuOverlay, batch);
        crashOverlay.setUp();
        winOverlay = new WinOverlay(batch);
        winOverlay.setUp();
        crashVisual = new PlayerCrashVisual();

        /** Dynamic Player object */
        player.setHitBoxPosition(new Vector2(22, 30));
        box = player.createHitBox();

        player.setStationarySprite();
        player.crashed(false);
        player.win(false);

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
        inputMultiplexer.addProcessor(crashOverlay.stage);
        inputMultiplexer.addProcessor(winOverlay.stage);
        inputMultiplexer.addProcessor(inputController);
        inputMultiplexer.addProcessor(spriteMovement.spriteMovementListener());
        Gdx.input.setInputProcessor(inputMultiplexer);

        container = new Container(world);

        /** Roof; Not sure why 1.455f, but works more-or-less dynamically with any map height
         *  - Roof is slightly off on some maps; Probably something to do with Tiled size vs box2d sizes
         *      - Minor Bug; fix prior to release. Has simple workarounds
         * sets floor also at x=0 */
        container.createContainer(renderer.getMapSize().y * 1.4555f);

        counters = new Counters();

        state = GAME_READY;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(camera); /** render map */
        /** debugRenderer Renders box2d;
         * Must have this set until sprites are added or there will be invisible boxes */
        //debugRenderer.render(world, camera.combined);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        /** Draws the sprite body to the hit-box */
        world.getBodies(tmpBodies);
        for(Body box : tmpBodies){
            if(box.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite) box.getUserData();
                sprite.setPosition(box.getPosition().x - sprite.getWidth() / 2, box.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(box.getAngle() * MathUtils.radiansToDegrees);
                if(box.getPosition().x != 0 && box.getPosition().y != 0) /** To test if fuel position passes over/probably don't need */
                    sprite.draw(batch);

            }
        }
        batch.end();

        /** HUD */
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
                updateGameOver(delta);
                break;
            case GAME_WON:
                updateGameWon();
                break;
        }


    }

    public void updateRunning(float delta){
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        /** Apply force to player hit-box */
        box.applyForceToCenter(spriteMovement.getMovement(), true);

        if(Gdx.input.justTouched())
            spriteMovement.boost();
        spriteMovement.accelerometer();
        spriteMovement.velocityLimitations(box);

        moveCameraWithSprite();

        hud.update(delta, box, player);

        if(menuPressed) {
            System.out.println("MENU_PRESSED");
            state = GAME_PAUSED;
        }

        if(player.crashed) {
            player.deteriorateHealth(20);
            if(player.getCurrentHealth() <= 0)
                state = GAME_OVER;
        }

        if(player.hasWon()) {
            state = GAME_WON;
            Gdx.input.vibrate(50);
            System.out.println("WON GAME");
        }

        container.wraparoundEffect(box, camera);
        camera.update();
    }

    public void updateReady(){
        state = GAME_RUNNING;
    }

    protected boolean changeLevelWon = false;
    public void updateGameWon() {
        menuBatch.setProjectionMatrix(winOverlay.stage.getCamera().combined);
        winOverlay.stage.draw();

        switch(winOverlay.gameOverOption){
            case 0:
                changeLevelWon = true;
                break;
            case 1:
                Menu menu = new Menu(game);
                game.setScreen(menu);
                break;
            default:
                break;
        }

    }

    protected boolean restartGame = false;
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
                /**restartGame();*/
                restartGame = true;
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


    public void updateGameOver(float dt){
        hud.update(0, box, player); /** delta time set to 0 for hud counters to not count */

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        crashVisual.update(dt, batch, box);
        batch.end();

        menuBatch.setProjectionMatrix(crashOverlay.stage.getCamera().combined);
        crashOverlay.stage.draw();


        switch(menuOverlay.menuOption){
            case 1:/**start again*/
                handleMenuOverlay();
                /**restartGame();*/
                restartGame = true;
                break;
            case 2:/**level select*/
                handleMenuOverlay();
                LevelSelect levelSelect = new LevelSelect(game);
                game.setScreen(levelSelect);
                break;
            default:
                break;
        }
        menuOverlay.menuOption = -1;

    }


    /** Camera follows player and doesn't leave map
     * box.getPosition().y is sometimes +10 to set the sprite lower on the screen instead of
     * in the middle of the viewport y-axis */
    public boolean moveCameraWithSprite(){
        /** ROOF */
        if(box.getPosition().y + SPRITE_POSITION_MIDDLE_OFFSET >= container.getRoofDimensions().y - camera.viewportHeight/2){
            camera.position.y = container.getRoofDimensions().y - camera.viewportHeight/2;
            return true;
        }

        /** NORMAL */
        if(box.getPosition().y + SPRITE_POSITION_MIDDLE_OFFSET > camera.viewportHeight/2) {
            camera.position.y = box.getPosition().y + SPRITE_POSITION_MIDDLE_OFFSET;
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
        /**powerUpOverlay.disposalService();*/

    }


}
