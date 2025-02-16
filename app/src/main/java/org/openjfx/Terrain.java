package org.openjfx;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    public int x;
    public int y;
    public int colour;
    public int underlyingColour;
    private List<Integer> rgb;
    private boolean occupied = false;
    private Animal occupiedBy;
    protected int biome;

    public void addOccupier(Animal Occupier){
        this.occupied = true;
        this.occupiedBy = Occupier;
    }

    public void removeOccupier(Animal Occupier) {
        this.occupied = false;
        this.occupiedBy = null; 
    }

    public boolean isOccupied(){
        return occupied;
    }

    public Animal getOccupied() {
        return occupiedBy;
    }

    public int getColour(){
        return colour;
    }

}