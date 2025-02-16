package org.openjfx;
import java.util.ArrayList;
import java.util.List;

abstract class Terrain {
    public int xcord;
    public int ycord;
    private List<Integer> rgb;
    private boolean occupied;
    private Animal occupiedBy;
    private List<Animal> validOccupiers = new ArrayList<Animal>();

    Terrain(int xcord, int ycord, boolean occupied, List<Integer> rgb, Animal occupiedBy){
        this.xcord = xcord;
        this.ycord = ycord;
        this.eaters = eaters;
        this.occupied = occupied;
        this.rgb = rgb;
        this.occupiedBy = null;
    }

    public void addOccupier(boolean setOccupied, Animal Occupier){
        if ((this.isOccupied() == false) || (this.validOccupiers.contains(Occupier))){
            this.occupied = setOccupied;
            this.occupiedBy = Occupier;
        }
    }

    public void removeOccupier(Animal Occupier) {
        if (this.occupiedBy == Occupier){this.occupiedBy = null;}
    }

    public boolean isOccupied(){
        return occupied;
    }

    public List<Integer> getRgb() {
        return this.rgb;
    }

}