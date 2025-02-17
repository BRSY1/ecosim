package org.openjfx;

import org.openjfx.ui.AnimalEnum;
import org.openjfx.ui.EventBox;
import org.openjfx.ui.Stats;
import java.util.*;

public class Animal {
  public int foodChainLevel; // number between 1 and 10
  private int naturalTerrain;
  private int viewRange;
  private Terrain terrain;
  public boolean dead;
  private GameMap gameMap; // reference to the main map
  private int update = 0;
  private int updateRate;
  private int foodLevel = 100; 
  private boolean hasBred;
  private int foodLevelDecreaseRate = 1;
  private EventBox eventBox;
  private Stats stats;
  private List<Integer> invalidMoves;

  Animal(GameMap gameMap, int foodChainLevel, int naturalTerrain, int viewRange, Terrain terrain, int updateRate) {
    this.gameMap = gameMap;
    this.dead = false;
    this.foodChainLevel = foodChainLevel;
    this.naturalTerrain = naturalTerrain;
    this.viewRange = viewRange;
    this.terrain = terrain;
    this.updateRate = updateRate;
    ArrayList<Integer> invalidMoves = new ArrayList<Integer>();
    if ((foodChainLevel < 6) || (foodChainLevel == 9)){
      invalidMoves.add(0);
      invalidMoves.add(1);
      invalidMoves.add(7);
    }
    if(foodChainLevel == 6){invalidMoves.add(1);}
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

  public void setEventBoxAndStats(EventBox eventBox, Stats stats) {
    this.eventBox = eventBox;
    this.stats = stats;
  }

  public Terrain getCurrentTerrain() {
    return this.terrain;
  }

  public void setCurrentTerrain(Terrain terrain) {
    this.terrain = terrain;
  }

  public void animalUpdate() {
    ArrayList<ArrayList<Terrain>> view = gameMap.getView(terrain, viewRange);

    if (update >= updateRate - 1) {
      Move move = moveMCTS(view);
    
      int newx = terrain.x + move.getDx();
      int newy = terrain.y + move.getDy();
  
      if (newx < 0 || newy < 0 || newx >= 800 || newy >= 800) {
        return;
      }
      
      Terrain newTerrain = gameMap.terrainArray.get(newy).get(newx);
  
      // check not occupied
      if (newTerrain.isOccupied()) {
        Animal current = this;
        Animal occupier = newTerrain.getOccupied();
        Animal killer;
        Animal killed;
        if (current.foodChainLevel < occupier.foodChainLevel) {
          killed = current;
          killer = occupier;
          killer.foodLevel += 40;
          current.dead = true;
          AnimalEnum killerAnimalEnum = AnimalEnum.values()[killer.foodChainLevel - 1];
          AnimalEnum killedAnimalEnum = AnimalEnum.values()[killed.foodChainLevel - 1];
          
          eventBox.addEvent("A " + killerAnimalEnum + " eats a " + killedAnimalEnum);
          stats.updateStats(killedAnimalEnum, 0,1);
        } else if (current.foodChainLevel == occupier.foodChainLevel) { // reproduction logic
          if (current.hasBred == false && occupier.hasBred == false) {
            AnimalEnum animalEnum = AnimalEnum.values()[current.foodChainLevel - 1];
            stats.updateStats(animalEnum, 1,0);
            eventBox.addEvent("A baby " + animalEnum + " was made with ❤️");
            current.hasBred = true;
            occupier.hasBred = true;
          } 
        } else {
          killed = occupier;
          killer = current;
          killer.foodLevel += 40;
          occupier.dead = true;
          AnimalEnum killerAnimalEnum = AnimalEnum.values()[killer.foodChainLevel - 1];
          AnimalEnum killedAnimalEnum = AnimalEnum.values()[killed.foodChainLevel - 1];
          
          eventBox.addEvent("A " + killerAnimalEnum + " eats a " + killedAnimalEnum);
          stats.updateStats(killedAnimalEnum, 0,1);
        }
        
        newTerrain.colour = newTerrain.underlyingColour;
        
      }
      
      if(invalidMoves.contains(newTerrain.colour)){
        return;
      }

      terrain.removeOccupier(this);
      if (terrain.framesToRegrow > 0 && (terrain.biome == 1 || terrain.biome == 5 || terrain.biome == 6)) {
        terrain.colour = 11;
      } else {
        terrain.colour = terrain.underlyingColour;
      }
  
      this.terrain = newTerrain;
      terrain.addOccupier(this);

      update = 0;
    } else {
      update++;
    }
  
    if (foodLevel < 70 && (terrain.biome == 6 || terrain.biome == 1 || terrain.biome == 5 || terrain.biome == 7 || terrain.biome == 8)) {
      terrain.getsEaten();
      foodLevel += 20;
    }

    if (foodLevel > 0) {foodLevel-= foodLevelDecreaseRate; }

    if (foodLevel<=0){
      this.getCurrentTerrain().colour = this.terrain.underlyingColour;
      AnimalEnum killedAnimalEnum = AnimalEnum.values()[this.foodChainLevel - 1];
      eventBox.addEvent("A " + killedAnimalEnum + " died of hunger.");
      this.dead = true;
      stats.updateStats(killedAnimalEnum, 0,1);
    }

    else{
      terrain.colour = 11 + this.foodChainLevel;
    }
  }


  private ArrayList<Animal> getEntities(int numRows, int numCols, ArrayList<ArrayList<Terrain>> view) {
    ArrayList<Animal> animals = new ArrayList<Animal>();

    for (int j = 0; j < numRows; j++) {
      for (int i = 0; i < numCols; i++) {
        if (view.get(j).get(i).isOccupied() && !this.terrain.equals(view.get(j).get(i))) {
          animals.add(view.get(j).get(i).getOccupied());
        }
      }
    }
    return animals;
  }

  // get a list of the grid locations of the animals within view
  private ArrayList<ArrayList<Integer>> getLocations(int numRows, int numCols, ArrayList<ArrayList<Terrain>> view) {
    ArrayList<ArrayList<Integer>> arrayList = new ArrayList<ArrayList<Integer>>();

    for (int j = 0; j < numRows; j++) {
      for (int i = 0; i < numCols; i++) {
        if (view.get(j).get(i).isOccupied() && !this.terrain.equals(view.get(j).get(i))) {
          ArrayList<Integer> tmp = new ArrayList<Integer>();
          tmp.add(view.get(j).get(i).x);
          tmp.add(view.get(j).get(i).y);
          arrayList.add(tmp);
        }
      }
    }
    return arrayList;
  }

  private static final int foodMul = 4;

  // error to be fixed - check for illegal moves
  private int reward(ArrayList<Animal> animals, Animal currAnimal, ArrayList<ArrayList<Integer>> locs, ArrayList<Integer> thisLoc) {
    int x = thisLoc.get(0);
    int y = thisLoc.get(1);
    int reward = 0;
    for (int i = 0; i < locs.size(); i++) {
      Animal animal = animals.get(i);
      int dist = (int) Math.sqrt(Math.pow(Math.abs(locs.get(i).get(0) - x), 2) + Math.pow(Math.abs(locs.get(i).get(1) - y), 2));
      if (dist == 0) dist = 1;
      if (currAnimal.foodChainLevel < animal.foodChainLevel) {
        reward -= (int) this.viewRange / dist * 2;
      } else {
        reward += (int) this.viewRange / dist * 2;
      }
    }

    // get change in food level
    if (gameMap.terrainArray.get(y).get(x).framesToRegrow <= 0) {
      reward += (foodLevel + 4) / foodMul;
    } else {
      reward += foodLevel / foodMul;
    }
    return reward;
  }

  void randomMove(ArrayList<Integer> location) {
    Random random = new Random();
    Move move = Move.values()[random.nextInt(8)];
    location.set(0, Math.max(0, Math.min(599, location.get(0) + move.dx)));
    location.set(1, Math.max(0, Math.min(599, location.get(1) + move.dy)));
  }

  // rl parameters
  private static final int numRandomWalks = 5;
  private static final int walkDepth = 2;

  private Move moveMCTS(ArrayList<ArrayList<Terrain>> view) {
    int numRows = view.size();
    int numCols = view.get(0).size();

    // get arrays (of references) of the animal objects and their locations
    ArrayList<Animal> animals = getEntities(numRows, numCols, view);
    ArrayList<ArrayList<Integer>> initLocs = getLocations(numRows, numCols, view);
    
    // get this animal's location
    ArrayList<Integer> initLoc = new ArrayList<Integer>();
    initLoc.add(terrain.x);
    initLoc.add(terrain.y);

    // initialise parameters to detect and store the maximum value
    int maxReward = Integer.MIN_VALUE;
    Move bestMove = null;

    // create a shuffled list of possible moves
    List<Move> movesList = Arrays.asList(Move.values());
    Collections.shuffle(movesList);

    for (Move move : movesList) {
      // in lieu of a efficient way to simulate without calling the main game loop, only the
      // key features of the game will be simulated (e.g. position)

      int avgReward = 0;

      // do 5 random runs
      for (int i = 0; i < numRandomWalks; i++) {
        boolean exit = false;

        // create new copies of locations
        ArrayList<Integer> thisLoc = new ArrayList<Integer>(initLoc);
        thisLoc.set(0, thisLoc.get(0) + move.getDx());
        thisLoc.set(1, thisLoc.get(1) + move.getDy());

        ArrayList<ArrayList<Integer>> locs = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> loc : initLocs) { // iterate to create deep copies
            locs.add(new ArrayList<>(loc));
        }

        // runs of depth 2
        for (int j = 0; j < walkDepth; j++) {
          if (exit) { break; }

          // move this animal
          randomMove(thisLoc);

          // move the other animals
          for (ArrayList<Integer> loc : locs) {
            if (loc.equals(thisLoc)) {
              exit = true;
              break;
            }
            randomMove(loc);
            if (loc.equals(thisLoc)) {
              exit = true;
              break;
            }
          } 
        } 
        avgReward += reward(animals, this, locs, thisLoc) / numRandomWalks;
      }

      // avgReward = reward(animals, this, initLocs, thisLoc);

      // pick the move with the highest return
      if (avgReward > maxReward) {
        maxReward = avgReward;
        bestMove = move;
      }
    }
    return bestMove;
  }
}
