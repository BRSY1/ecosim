package org.openjfx;

public class Tree extends Terrain implements Food{
    boolean isDead;

    public Tree(int x, int y){
        this.x = x;
        this.y = y;
        this.colour = 6;
        this.isDead = false;
        this.underlyingColour = this.colour;
        this.biome = 6;
    }

    @Override
    public void getsEaten() {
        this.isDead = true;
        this.framesToRegrow = 6;
        this.colour = 11;
    }

    @Override
    public void update() {
        if(this.framesToRegrow<=0){
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