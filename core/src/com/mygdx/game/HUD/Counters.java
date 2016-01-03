package com.mygdx.game.HUD;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Counters displayed in Hud
 * Calculates level completion, current time passed, .., anything counter related.
 * */
public class Counters {
    private float timeCount;
    private int worldTimer;
    private static int START_SCORE = 100; /** malleable */
    private int score;
    private int height;
    private int countScore;
    private static int shieldTimer = 10;

    public Counters(){
        worldTimer = 0;
        score = START_SCORE;
        countScore = 0;
        shieldTimer = 0;
    }

    public int getTime(){
        return worldTimer;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }


    /** Fix score start when height is low */
    public void update(float dt,Body player){
        timeCount += dt;
        if(timeCount >= 1.0f){
            worldTimer++;
            timeCount = 0;
        }
        updateHeight(player);
    }

    public void scoreChanger(){
        if(score > 0) {
            countScore--;
            score = height + countScore;
        }
    }

    public int getHeight(){
        return height;
    }

    public void updateHeight(Body player){
        this.height = (int)player.getPosition().y;
    }

    public void setShieldTime(int shieldTime){
        this.shieldTimer = 10 - shieldTime;
        //System.out.println(this.shieldTimer);
    }

    public int getShieldTime(){
        if(shieldTimer == 10){
            return 0;
        }
        return shieldTimer;
    }

}
