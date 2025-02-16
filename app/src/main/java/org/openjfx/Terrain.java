package org.openjfx;
import java.util.List;
abstract class Terrain {

    private int x-cord;
    private int y-cord;
    private List<Integer> rgb;
    private List<Animal> eaters;
    private boolean occupied;
    private Animal occupiedBy;
    private List<Animals> validOccupiers;

    Terrain(int x-cord, int y-cord, List<Animal> eaters, boolean occupied, String rgb, Animal occupiedBy){
        this.x-cord = x-cord;
        this.y-cord = y-cord;
        this.eaters = eaters;
        this.occupied = occupied;
        this.rgb = rgb;
        this.occupiedBy = occupiedBy;
        List<Animals> tempValidOccupiers = new ArrayList<>();
        this.validOccupiers = tempValidOccupiers;
    }

    public void addOccupier(boolean setOccupied, Animal Occupier){
        if ((this.isOccupied() == false) || (this.validOccupiers.contains(Occupier))){
            this.occupied = setOccupied;
            this.occupiedBy = Occupier;
        }
    }

    public void remove

    public boolean isOccupied(){
        return occupied;
    }

    public String getRgb() {
        return rgb;
    }

}