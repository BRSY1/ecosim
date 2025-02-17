package org.openjfx;

public class UneatableTree extends Terrain {
    public UneatableTree(int x, int y){    
        this.colour = 6;
        this.underlyingColour = this.colour;
        this.x = x;
        this.y = y;
        this.biome = 6;
    }
}

