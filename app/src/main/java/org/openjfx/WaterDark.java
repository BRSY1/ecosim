package org.openjfx;


public class WaterDark extends Terrain{

    public WaterDark(int x, int y){
        this.colour = 0;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 7;
    }
}