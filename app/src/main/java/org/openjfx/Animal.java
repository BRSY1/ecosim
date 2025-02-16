package org.openjfx;

import java.util.*;

public class Animal {
  private int foodChainLevel; // number between 1 and 10
  private List<List<Terrain>> view;
  private int naturalTerrain;
  private int viewRange;
  Animal(int foodChainLevel, int naturalTerrain, int viewRange) {
    this.foodChainLevel = foodChainLevel;
    this.naturalTerrain = naturalTerrain;
    this.viewRange = viewRange;
  }

  // getView(viewRange) method
}
