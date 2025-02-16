package org.openjfx;
import java.util.List;
import java.util.ArrayList;


public class WaterLight extends Terrain{

    public WaterLight(int x, int y){
        this.colour = 1;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
    }
}