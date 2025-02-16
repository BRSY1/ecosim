package org.openjfx;

import java.util.*;

public class Animal {
  private int foodChainLevel; // number between 1 and 10
  private int naturalTerrain;
  private int viewRange;
  private Terrain terrain;
  private GameMap gameMap; // reference to the main map

  Animal(GameMap gameMap, int foodChainLevel, int naturalTerrain, int viewRange, Terrain terrain) {
    this.gameMap = gameMap;
    this.foodChainLevel = foodChainLevel;
    this.naturalTerrain = naturalTerrain;
    this.viewRange = viewRange;
    this.terrain = terrain;
  }

  private enum Move {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1);

    private final int dx;
    private final int dy;
    
    Move(int dx, int dy) {
      this.dx = dx;
      this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
  }

  public void animalUpdate() {
    ArrayList<ArrayList<Terrain>> view = gameMap.getView(terrain, viewRange);
    Move move = move(view);
    
    int newx = terrain.x + move.getDx();
    int newy = terrain.y + move.getDy();
    // check on map
    if (newx < 0 || newy < 0 || newx >= 600 || newy >= 600) {
      return;
    }
    
    Terrain newTerrain = gameMap.terrainArray.get(newx).get(newy);

    // check not occupied
    if (newTerrain.isOccupied()) {
      return; 
    }

    terrain.removeOccupier(this);

    this.terrain = newTerrain;
    terrain.addOccupier(this);
    terrain.colour = 8;
  }

  private Move move(ArrayList<ArrayList<Terrain>> view) {
    Terrain closest = null;
    int distance = 0;

    // find closest occupied tile with an animal on
    int numRows = view.size();
    int numCols = view.get(0).size();

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        if (view.get(i).get(j).isOccupied() && Math.max(i, j) < distance) {
          closest = view.get(i).get(j);
          distance = Math.max(i, j);
        }
      }
    }

    if (closest != null) {
      // vector in direction of animal to this
      int dx = this.terrain.x - closest.x;
      int dy = this.terrain.y - closest.y;

      if (this.foodChainLevel <= closest.getOccupied().foodChainLevel) {
        if (dx >= 0) {
          if (dy >= 0) {
            return Move.UP_RIGHT;
          } else {
            return Move.DOWN_RIGHT;
          }
        } else {
          if (dy >= 0) {
            return Move.UP_LEFT;
          } else {
            return Move.DOWN_LEFT;
          }
        }
      } else {
        if (dx >= 0) {
          if (dy >= 0) {
            return Move.DOWN_LEFT;
          } else {
            return Move.UP_LEFT;
          }
        } else {
          if (dy >= 0) {
            return Move.DOWN_RIGHT;
          } else {
            return Move.UP_RIGHT;
          }
        }
      }

    }
    return Move.UP;
  }
}
