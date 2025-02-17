package org.openjfx;

import java.util.*;

public class Animal {
  public int foodChainLevel; // number between 1 and 10
  private int naturalTerrain;
  private int viewRange;
  private Terrain terrain;
  private GameMap gameMap; // reference to the main map
  private int update = 0;
  private int updateRate;
  private int foodLevel; // ranges between 1 and 5
  private int foodLevelDecreaseRate = 20;
  private List<Integer> invalidMoves;

  Animal(GameMap gameMap, int foodChainLevel, int naturalTerrain, int viewRange, Terrain terrain, int updateRate) {
    this.gameMap = gameMap;
    this.foodChainLevel = foodChainLevel;
    this.naturalTerrain = naturalTerrain;
    this.viewRange = viewRange;
    this.terrain = terrain;
    this.updateRate = updateRate;
    ArrayList<Integer> invalidMoves = new ArrayList<Integer>();
    if ((foodChainLevel < 6) || (foodChainLevel == 10)){
      invalidMoves.add(0);
      invalidMoves.add(1);
      invalidMoves.add(7);
    }
    if(foodChainLevel == 6){
      invalidMoves.add(1);
    
    }
    if((foodChainLevel == 7) || (foodChainLevel == 10)){invalidMoves.add(2);}
    if(foodChainLevel == 8){invalidMoves.add(3);}
    this.invalidMoves = invalidMoves;
  }

  private enum Move {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1);

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

  public Terrain getCurrentTerrain() {
    return this.terrain;
  }

  public void animalUpdate() {
    ArrayList<ArrayList<Terrain>> view = gameMap.getView(terrain, viewRange);

    if (update >= updateRate - 1) {
      Move move = move(view);
    
      int newx = terrain.x + move.getDx();
      int newy = terrain.y + move.getDy();
  
      if (newx < 0 || newy < 0 || newx >= 800 || newy >= 800) {
        return;
      }
      
      Terrain newTerrain = gameMap.terrainArray.get(newy).get(newx);
  
      // check not occupied
      if (newTerrain.isOccupied()) {
        return;
      }
      
      if(invalidMoves.contains(newTerrain)){
        return;
      }

      terrain.removeOccupier(this);
      if (terrain.framesToRegrow > 0 && (terrain.biome == 1 || terrain.biome == 5 || terrain.biome == 6)) {
        terrain.colour = 11;
      } else {
        terrain.colour = terrain.underlyingColour;
      }
  
      this.terrain = newTerrain;


      update = 0;
    } else {
      update++;
    }
  
    if (foodLevel < 5 && (terrain.biome == 6 || terrain.biome == 1 || terrain.biome == 5)) {
      terrain.getsEaten();
      foodLevel++;
    }

    if (foodLevelDecreaseRate < 20) {
      foodLevelDecreaseRate++;
    } else {
      foodLevelDecreaseRate = 0;
    }

    terrain.colour = 10 + this.foodChainLevel;
  }

  private Move move(ArrayList<ArrayList<Terrain>> view) {
    Terrain closest = null;
    int distance = 100;

    // find closest occupied tile with an animal on
    int numRows = view.size();
    int numCols = view.get(0).size();

    // j is inverse y co-ord, i is the x co-ord
    for (int j = 0; j < numRows; j++) {
      for (int i = 0; i < numCols; i++) {
        if (true && Math.max(Math.abs(j - (int) numRows / 2), Math.abs(i - (int) numCols / 2)) < distance && !this.terrain.equals(view.get(j).get(i))) {
          if (view.get(j).get(i).isOccupied()) {
            closest = view.get(j).get(i);
            distance = Math.max(Math.abs(j - (int) numRows / 2), Math.abs(i - (int) numCols / 2));
          }
        }
      }
    }

    if (closest != null) {
      // vector in direction of this to animal
      int dx = this.terrain.x - closest.x;
      int dy = this.terrain.y - closest.y;

      // if this is the prey
      if (this.foodChainLevel <= closest.getOccupied().foodChainLevel) {
        if (dx >= 0) {
          if (dy <= 0) {
            return Move.UP_RIGHT;
          } else {
            return Move.DOWN_RIGHT;
          }
        } else {
          if (dy <= 0) {
            return Move.UP_LEFT;
          } else {
            return Move.DOWN_LEFT;
          }
        }
      // if this is the predator
      } else {
        if (dx >= 0) {
          if (dy <= 0) {
            return Move.DOWN_LEFT;
          } else {
            return Move.UP_LEFT;
          }
        } else {
          if (dy <= 0) {
            return Move.DOWN_RIGHT;
          } else {
            return Move.UP_RIGHT;
          }
        }
      }

    }

    double num = Math.random();
    if (num < 0.25) {
      return Move.UP;
    } else if (num < 0.5) {
      return Move.RIGHT;
    } else if (num < 0.75) {
      return Move.DOWN;
    } else {
      return Move.LEFT;
    }
  }
}
