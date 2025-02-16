package org.openjfx;
import java.util.*;

public class GameMap {
   Grid myGrid;
   List<List<Terrain>> terrainArray;

   public GameMap(Grid myGrid){
       this.myGrid = myGrid;
       this.terrainArray = new ArrayList<>();
       for (int y=0; y<myGrid.getHeight(); y++){
            List<Terrain> innerList = new ArrayList<>();
            for (int x=0; x<myGrid.getWidth(); x++){
                if ((myGrid.grid[y][x] <= 1.0) && (myGrid.grid[y])[x] > 0.8){innerList.add(new Rock(x, y));}
                if ((myGrid.grid[y][x] <= 0.8) && (myGrid.grid[y])[x] > 0.6){innerList.add(new Tree(x,y));}
                if ((myGrid.grid[y][x] <= 0.6) && (myGrid.grid[y])[x] > 0.2){innerList.add(new Shrub(x, y));}
                if ((myGrid.grid[y][x] <= 0.2) && (myGrid.grid[y])[x] > -0.2){innerList.add(new Grass(x, y));}
                if ((myGrid.grid[y][x] <= -0.2) && (myGrid.grid[y])[x] > -0.4){innerList.add(new SandLight(x, y));}
                if ((myGrid.grid[y][x] <= -0.4) && (myGrid.grid[y])[x] > -0.6){innerList.add(new SandDark(x, y));}
                if ((myGrid.grid[y][x] <= -0.6) && (myGrid.grid[y])[x] > -0.8){innerList.add(new WaterLight(x, y));}
                if ((myGrid.grid[y][x] <= -0.8) && (myGrid.grid[y])[x] > -1.0){innerList.add(new WaterDark(x, y));}
            }
            terrainArray.add(innerList);
        }

   }

    // return the animal's view
    public ArrayList<ArrayList<Terrain>> getView(Terrain terrain, int offset) {
        int centerX = terrain.x;
        int centerY = terrain.y;


        ArrayList<ArrayList<Terrain>> subset = new ArrayList<>();

        int numRows = terrainArray.size();
        if (numRows == 0) return subset;
        int numCols = terrainArray.get(0).size();

        for (int i = centerX - offset; i <= centerX + offset; i++) {
            ArrayList<Terrain> rowSubset = new ArrayList<>();
            for (int j = centerY - offset; j <= centerY + offset; j++) {
                if (i >= 0 && i < numRows && j >= 0 && j < numCols) {
                   rowSubset.add((Terrain) terrainArray.get(i).get(j));
                }
            }
            if (!rowSubset.isEmpty()) {
                subset.add(rowSubset);
            }
        }

        return subset;
    }
}