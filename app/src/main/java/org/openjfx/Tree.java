package org.openjfx;
import java.util.ArrayList;
import java.util.List;

public class Tree extends Terrain implements Food{
    int framesToRegrow;
    boolean isDead;

    public Tree(int x, int y){
        this.x = x;
        this.y = y;
        this.colour = 6;
        this.isDead = false;
    }

    @Override
    public void getsEaten() {
        this.isDead = true;
        this.framesToRegrow = 10;
        this.colour = 8;
    }

    @Override
    public void update() {
        if(this.framesToRegrow<=1){
            this.isDead = false;
            this.colour = 6;
        }
        else{this.framesToRegrow-=1;}

    }

    @Override
    public boolean getDeadStatus() {
        return this.isDead;
    }
}