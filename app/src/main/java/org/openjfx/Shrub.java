package org.openjfx;
import java.util.List;
import java.util.ArrayList;


public class Shrub extends Terrain{

    public Shrub(int x, int y){
        this.colour = 5;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 5;
    }
}