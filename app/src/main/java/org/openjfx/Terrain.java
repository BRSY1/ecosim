package org.openjfx;
import java.util.ArrayList;
import java.util.List;

class Terrain {
    public int x;
    public int y;
    public int colour;
    private List<Integer> rgb;
    private boolean occupied;
    private Animal occupiedBy;
    private List<Animal> validOccupiers = new ArrayList<Animal>();

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

    public Animal getOccupied() {
        return occupiedBy;
    }

    public int getColour(){
        return colour;
    }

}