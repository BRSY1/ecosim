package org.openjfx;


public class SandLight extends Terrain{

    public SandLight(int x, int y){
        this.colour = 3;
        this.underlyingColour = 3;
        this.x = x;
        this.y = y;
        this.biome = 4;
    }
}