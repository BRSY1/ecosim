package org.openjfx;

public class Terrain {
    public int x;
    public int y;
    public int colour;
    public int underlyingColour;
    private boolean occupied = false;
    public Animal occupiedBy;
    protected int biome;
    public int framesToRegrow = 1; // so the class is not considered consumable by default

    public void getsEaten() {

    }

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