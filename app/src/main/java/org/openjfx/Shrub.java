package org.openjfx;
import java.util.List;
import java.util.ArrayList;


public class Shrub extends Terrain implements Food{
    boolean isDead;
    int framesToRegrow;

    public Shrub(int x, int y){
        this.colour = 5;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.isDead = false;
        this.framesToRegrow = 0;
        this.biome = 5;
    }

    @Override
        public void getsEaten() {
        this.isDead = true;
        this.framesToRegrow = 8;
        this.colour = 12;
    }

    @Override
    public void update() {
        if(this.framesToRegrow<=1){
            this.isDead = false;
            this.colour = 5;
        }
        else{this.framesToRegrow-=1;}

    }

    @Override
    public boolean getDeadStatus() {
        return this.isDead;
    }

}