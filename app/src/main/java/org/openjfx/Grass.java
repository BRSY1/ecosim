package org.openjfx;


public class Grass extends Terrain implements Food{

    public boolean isDead;

    public Grass(int x, int y){
        this.colour = 4;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.framesToRegrow = 0;
        this.biome = 1;
    }

    @Override
    public void getsEaten() {
        this.framesToRegrow = 3;
        this.colour = 11;
    }

    @Override
    public void update() {
        if(this.framesToRegrow<=0) {
            this.colour = 4;
        }
        else{this.framesToRegrow-=1;}

    }

    @Override
    public boolean getDeadStatus() {
        return this.isDead;
    }

}