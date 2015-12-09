package com.mygdx.game.UserDetails;

public class Account {
    String name;
    int stardust;

    public Account(){

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setStardust(int stardust){
        this.stardust = stardust;
    }

    public int getStardust(){
        return stardust;
    }

}
