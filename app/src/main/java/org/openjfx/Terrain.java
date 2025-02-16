package org.openjfx;
import java.util.*;

class Terrain {
  private int xcord;
  private int ycord;
  private List<Integer> rgb;
  private List<Animal> eaters;
  private boolean occupied;
  Terrain(int xcord, int ycord, List<Animal> eaters, boolean occupied){
    this.xcord = xcord;
    this.ycord = ycord;
    this.eaters = eaters;

  }
}