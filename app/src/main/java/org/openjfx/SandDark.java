package org.openjfx;
import java.util.List;
import java.util.ArrayList;


public class SandDark extends Terrain{

    public SandDark(int x, int y){
        this.colour = 2;
        this.underlyingColour = 2;
        this.x = x;
        this.y = y;
        this.biome = 3;
    }
}