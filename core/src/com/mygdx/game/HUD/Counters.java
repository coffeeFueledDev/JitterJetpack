package com.mygdx.game.HUD;

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

    public Counters(){
        worldTimer = 0;
        score = START_SCORE;
        countScore = 0;
        height = 0;
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
    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1.0f){
            worldTimer++;
            timeCount = 0;
        }
    }

    public void scoreChanger(){
        if(score > 0) {
            countScore--;
            score = height + countScore;
        }
    }

    public void setHeight(int height){
        if(height >= 0)
            this.height = height;
    }

    public int getHeight(){
        return height;
    }

}
