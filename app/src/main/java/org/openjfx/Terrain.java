package org.openjfx;
import java.util.ArrayList;
import java.util.List;

class Terrain {
    private int xcord;
    private int ycord;
    private List<Integer> rgb;
    private boolean occupied;
    private Animal occupiedBy;
    private List<Animal> validOccupiers = new ArrayList<Animal>();

    Terrain(int xcord, int ycord, boolean occupied, List<Integer> rgb, Animal occupiedBy){
        this.xcord = xcord;
        this.ycord = ycord;
        this.occupied = occupied;
        this.rgb = rgb;
        this.occupiedBy = occupiedBy;
    }

    public void addOccupier(boolean setOccupied, Animal Occupier){
        if ((this.isOccupied() == false) || (this.validOccupiers.contains(Occupier))){
            this.occupied = setOccupied;
            this.occupiedBy = Occupier;
        }
    }

    public void remove () {

    }

    public boolean isOccupied(){
        return occupied;
    }

    public List<Integer> getRgb() {
        return this.rgb;
    }

}