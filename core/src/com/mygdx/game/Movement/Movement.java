package com.mygdx.game.Movement;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GenericControls.InputController;
import com.mygdx.game.Sprites.Player;

public class Movement {
    private Vector2 lastTouch = new Vector2();
    private Vector2 movement = new Vector2();
    Player player;
    public int BOOST_SPEED = 250*4, LEFT_SPEED = 350*4, RIGHT_SPEED = 350*4, RESET_VELOCITY = 0;

    public Movement(Player player){
        this.player = player;
    }

    public Vector2 getMovement(){
        return movement;
    }

    public void boost(){
        movement.y = BOOST_SPEED;
        player.setBoostSprite();
    }

    /** Handles velocity reset and horizontal movement */
    public InputProcessor spriteMovementListener(){
        InputProcessor inputProcessor = new InputController(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                movement.y = -BOOST_SPEED;
                movement.x = 0;
                player.setStationarySprite();
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector2 newTouch = new Vector2(screenX, screenY);
                Vector2 delta = newTouch.cpy().sub(lastTouch);
                if (delta.x > 0)
                    movement.x = RIGHT_SPEED;
                if (delta.x < 0)
                    movement.x = -LEFT_SPEED;
                lastTouch = newTouch;
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                lastTouch.set(screenX, screenY);
                return true;
            }
        };

        return inputProcessor;
    }

    public void crashed(){
        movement.x = RESET_VELOCITY;
        movement.y = RESET_VELOCITY;
    }


}
