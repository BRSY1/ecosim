package org.openjfx;

public class UneatableGrass extends Terrain {
    public UneatableGrass(int x, int y){    
        this.colour = 4;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 2;
    }
}
