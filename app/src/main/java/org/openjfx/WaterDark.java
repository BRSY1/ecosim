package org.openjfx;


public class WaterDark extends Terrain implements Food{

    public boolean isDead;
    public WaterDark(int x, int y){
        this.colour = 0;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 7;
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