package org.openjfx;


public class Rock extends Terrain{

    public Rock(int x, int y){
        this.colour = 7;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 2;

    }
}