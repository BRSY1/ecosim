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

  private enum Move {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1);
    
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
    List<List<Terrain>> view = map.getView(terrain, viewRange);
    Move move = move(view);
    
    int newx = move.getDx();
    int newy = move.getDy();
    // check on map
    if (newx < 0 || newy < 0 || newx >= 500 || newy >= 500) {
      return;
    }
    
    Terrain newTerrain = map.get(newx).get(newy);

    // check not occupied
    if (newTerrain.isOccupied()) {
      return;
    }

    terrain.removeOccupier(this);

    terrain = newTerrain;
    terrain.addOccupier(this);

  }

  private int move(List<List<Terrain>> view) {
    Terrain closest = null;
    int distance = 0;

    // find closest occupied tile with an animal on
    for (int i = 0; i < 2 * viewRange + 1; i++) {
      for (int j = 0; j < 2 * viewRange + 1; j++) {
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

      if (this.foodChainLevel <= closest.foodChainLevel) {
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

    
    return 0;
  }

  void updateAnimal() {
    map.getView(location, viewRange);
  }
}
