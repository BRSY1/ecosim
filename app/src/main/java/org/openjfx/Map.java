package org.openjfx;
import java.utils.*;

public class Map {
    ArrayList<ArrayList<Terrain>> map;


    // add terrain objects to the map
    private void createMap() {
        for (int i = 0; i < 500; i++) {
            ArrayList<Terrain> tmpList = new ArrayList<Terrain>();
            ArrayList<Integer> rgb = new ArrayList<>();
            rgb.add(0); rgb.add(0); rgb.add(0);
            for (int j = 0; j < 500; j++) {
                tmpList.add(new Terrain(i, j, false, rgb, null));
            }
            map.add(tmpList);
        }
    }

    // return the animal's view
    public <Terrain> ArrayList<ArrayList<Terrain>> getView(Terrain terrain, int offset) {
        int centerX = terrain.x;
        int centerY = terrain.y;


        ArrayList<ArrayList<Terrain>> subset = new ArrayList<>();

        int numRows = map.size();
        if (numRows == 0) return subset;
        int numCols = map.get(0).size();

        for (int i = centerX - offset; i <= centerX + offset; i++) {
            ArrayList<Terrain> rowSubset = new ArrayList<>();
            for (int j = centerY - offset; j <= centerY + offset; j++) {
                if (i >= 0 && i < numRows && j >= 0 && j < numCols) {
                    rowSubset.add((Terrain) map.get(i).get(j));
                }
            }
            if (!rowSubset.isEmpty()) {
                subset.add(rowSubset);
            }
        }

        return subset;
    }

}