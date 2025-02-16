package org.openjfx;
import java.util.*;

public class GameMap {
   Grid myGrid;
   ArrayList<ArrayList<Terrain>> terrainArray;

   public GameMap(Grid myGrid) {
       this.myGrid = myGrid;
       this.terrainArray = new ArrayList<>();

       for (int y = 0; y < myGrid.getHeight(); y++) {  // Loop through height (rows)
           ArrayList<Terrain> innerList = new ArrayList<>();
           for (int x = 0; x < myGrid.getWidth(); x++) { // Loop through width (columns)
               double val = myGrid.grid[y][x];  // Ensure correct indexing

               if (val <= 1.0 && val > 0.8) { innerList.add(new Rock(x, y)); }
               else if (val <= 0.8 && val > 0.6) { innerList.add(new Tree(x, y)); }
               else if (val <= 0.6 && val > 0.2) { innerList.add(new Shrub(x, y)); }
               else if (val <= 0.2 && val > -0.2) { innerList.add(new Grass(x, y)); }
               else if (val <= -0.2 && val > -0.4) { innerList.add(new SandLight(x, y)); }
               else if (val <= -0.4 && val > -0.6) { innerList.add(new SandDark(x, y)); }
               else if (val <= -0.6 && val > -0.8) { innerList.add(new WaterLight(x, y)); }
               else if (val <= -0.8 && val > -1.0) { innerList.add(new WaterDark(x, y)); }
           }
           terrainArray.add(innerList);
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
}
