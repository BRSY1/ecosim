package org.openjfx;

import java.util.*;

public class Animal {
  private int foodChainLevel; // number between 1 and 10
  private int naturalTerrain;
  private int viewRange;
  private Terrain terrain;
  private ArrayList<ArrayList<Terrain>> map; // reference to the main map

  Animal(ArrayList<ArrayList<Terrain>> map, int foodChainLevel, int naturalTerrain, int viewRange, Terrain terrain) {
    this.map = map;
    this.foodChainLevel = foodChainLevel;
    this.naturalTerrain = naturalTerrain;
    this.viewRange = viewRange;
    this.terrain = terrain;
  }


  public void animalUpdate() {
    List<List<Terrain>> view = map.getView(terrain, viewRange);
    int move = move(view);
  }

  private int move(List<List<Terrain>> view) {
    return 0;
  }

  // getView(viewRange) method
}
