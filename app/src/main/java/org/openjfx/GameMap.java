package org.openjfx;
import java.util.*;

public class GameMap {
   Grid myGrid;
   ArrayList<ArrayList<Terrain>> terrainArray;
   ArrayList<Food> foodStuffs;


   public GameMap(Grid myGrid) {
       this.myGrid = myGrid;
       this.foodStuffs = new ArrayList<>();
       this.terrainArray = new ArrayList<>();
       
       for (int y=0; y<myGrid.getHeight(); y++){
            ArrayList<Terrain> innerList = new ArrayList<>();
            for (int x=0; x<myGrid.getWidth(); x++){
                if ((myGrid.grid[y][x] <= 1.0) && (myGrid.grid[y])[x] > 0.4){innerList.add(new Rock(x, y));}

                if ((myGrid.grid[y][x] <= 0.4) && (myGrid.grid[y])[x] > 0.385){innerList.add(new UneatableTree(x,y));}
                if ((myGrid.grid[y][x] <= 0.385) && (myGrid.grid[y])[x] > 0.32){innerList.add(new Tree(x,y));}
                if ((myGrid.grid[y][x] <= 0.32) && (myGrid.grid[y])[x] > 0.3){innerList.add(new UneatableTree(x,y));}

                if ((myGrid.grid[y][x] <= 0.3) && (myGrid.grid[y])[x] > 0.285){innerList.add(new UneatableShrub(x, y));}
                if ((myGrid.grid[y][x] <= 0.285) && (myGrid.grid[y])[x] > 0.22){innerList.add(new Shrub(x, y));}
                if ((myGrid.grid[y][x] <= 0.22) && (myGrid.grid[y])[x] > 0.2){innerList.add(new UneatableShrub(x, y));}

                if ((myGrid.grid[y][x] <= 0.2) && (myGrid.grid[y])[x] > 0.125){innerList.add(new Grass(x, y));}
                if ((myGrid.grid[y][x] <= 0.125) && (myGrid.grid[y])[x] > 0.075){innerList.add(new Grass(x, y));}
                if ((myGrid.grid[y][x] <= 0.075) && (myGrid.grid[y])[x] > 0.0){innerList.add(new Grass(x, y));}

                if ((myGrid.grid[y][x] <= 0.0) && (myGrid.grid[y])[x] > -0.15){innerList.add(new SandLight(x, y));}
                if ((myGrid.grid[y][x] <= -0.15) && (myGrid.grid[y])[x] > -0.25){innerList.add(new SandDark(x, y));}
                if ((myGrid.grid[y][x] <= -0.25) && (myGrid.grid[y])[x] > -0.4){innerList.add(new WaterLight(x, y));}
                if ((myGrid.grid[y][x] <= -0.4) && (myGrid.grid[y])[x] > -1.0){innerList.add(new WaterDark(x, y));}
            }
            terrainArray.add(innerList);}
        
       for (int y=0; y<myGrid.getHeight(); y++){
            for (int x=0; x<myGrid.getWidth(); x++){
                
                if(terrainArray.get(y).get(x) instanceof Tree | terrainArray.get(y).get(x) instanceof Shrub | terrainArray.get(y).get(x) instanceof Grass | terrainArray.get(y).get(x) instanceof WaterDark){
                    foodStuffs.add((Food)terrainArray.get(y).get(x));
                }
            }
        }
    }

   
   public ArrayList<ArrayList<Terrain>> getTerrainArray() {
        return terrainArray;
    }

    // return the animal's view
    public ArrayList<ArrayList<Terrain>> getView(Terrain terrain, int offset) {
        int centerX = terrain.x;
        int centerY = terrain.y;

        ArrayList<ArrayList<Terrain>> subset = new ArrayList<>();

        int numRows = terrainArray.size();
        if (numRows == 0) return subset;
        int numCols = terrainArray.get(0).size();

        for (int j = centerY - offset; j <= centerY + offset; j++) {  // Loop rows first
            ArrayList<Terrain> rowSubset = new ArrayList<>();
            for (int i = centerX - offset; i <= centerX + offset; i++) {  // Loop cols
                if (j >= 0 && j < numRows && i >= 0 && i < numCols) {
                   rowSubset.add(terrainArray.get(j).get(i)); // Ensure proper indexing
                }
            }
            if (!rowSubset.isEmpty()) {
                subset.add(rowSubset);
            }
        }

        return subset;
    }


    public Grid getGrid(){
        return myGrid;
    }

    public void update(){
       for(Food food : foodStuffs){
        food.update();
       } 
    }
}
