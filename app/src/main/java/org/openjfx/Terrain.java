package org.openjfx;
import java.util.ArrayList;
import java.util.List;

class Terrain {
    public int x;
    public int y;
    private List<Integer> rgb;
    private boolean occupied;
    private Animal occupiedBy;
    private List<Animal> validOccupiers = new ArrayList<Animal>();

    Terrain(int x, int y, boolean occupied, List<Integer> rgb, Animal occupiedBy){
        this.x = x;
        this.y = y;
        this.occupied = occupied;
        this.rgb = rgb;
        this.occupiedBy = null;
    }

    public void addOccupier(Animal Occupier){
        if ((this.isOccupied() == false) && (this.validOccupiers.contains(Occupier))){
            this.occupied = true;
            this.occupiedBy = Occupier;
        }
    }

    public void removeOccupier(Animal Occupier) {
        if (this.occupiedBy == Occupier){this.occupiedBy = null;}
    }

    public boolean isOccupied(){
        return occupied;
    }

    public int getColour(){
        return colour;
    }

}