package org.openjfx;

import java.util.*;

public class Animal {
  private int foodChainLevel; // number between 1 and 10
  private List<List<Terrain>> view;
  private Terrain location;
  private int naturalTerrain;
  private int viewRange;
  private ArrayList<ArrayList<Terrain>> map;

  Animal(int foodChainLevel, int naturalTerrain, int viewRange) {
    this.foodChainLevel = foodChainLevel;
    this.naturalTerrain = naturalTerrain;
    this.viewRange = viewRange;
  }

  void updateAnimal() {
    map.getView(location, viewRange);
  }
}
