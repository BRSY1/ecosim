package org.openjfx;

public class UneatableShrub extends Terrain {
    public UneatableShrub(int x, int y){    
        this.colour = 5;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 2;
    }
}
