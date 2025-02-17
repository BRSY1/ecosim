package org.openjfx;


public class WaterLight extends Terrain implements Food{

    boolean isDead = false;
    public WaterLight(int x, int y){
        this.colour = 1;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 8;
    }
    @Override
    public void getsEaten() {
        this.framesToRegrow = 5;
    }

    @Override
    public void update() {
        if(this.framesToRegrow<=0) {
        }
        else{this.framesToRegrow-=1;}

    }

    @Override
    public boolean getDeadStatus() {
        return this.isDead;
    }
}