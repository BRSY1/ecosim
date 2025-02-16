package org.openjfx;

import java.util.*;

public class Animal {
  private int foodChainLevel; // number between 1 and 10
  private List<List<Terrain>> view;
  Animal(int foodChainLevel) {
    this.foodChainLevel = foodChainLevel;
  }

}
