package org.openjfx;
import java.util.*;

public class Map {
    int width;
    int height;
    float scale;
    float[][] grid;
    int gradientsX[][];
    int gradientsY[][];

    ArrayList<ArrayList<Terrain>> map;

    //Constructor to create a new map
    public Map(int width, int height, float scale) {

        //Initialises new 2D array of map size
        this.grid = new float[width][height];


        //Produces Gradients for each point in array
        gradientsX = new int[height + 1][width + 1];
        gradientsY = new int[height + 1][width + 1];
        generateGradients();

        //Fills grid with arbitrary values
        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                float sampleX = x*scale;
                float sampleY = y*scale;
                grid[x][y]= perlin(sampleX, sampleY);
            }
        }
    }

    //Returns array of random gradients
    private void generateGradients() {
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                double angle = Random.nextDouble() * 2 * Math.PI;
                gradientsX[y][x] = (int) Math.cos(angle);
                gradientsY[y][x] = (int) Math.sin(angle);
            }
        }
    }

    //Used to smooth out difference in gradient
    private float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Linear interpolation
    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    // Compute the dot product between the gradient vector and the distance vector
    private float dotGridGradient(int gridX, int gridY, float x, float y) {
        float dx = x - gridX;
        float dy = y - gridY;
        return dx * gradientsX[gridY][gridX] + dy * gradientsY[gridY][gridX];
    }

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
    public <Terrain> ArrayList<ArrayList<Terrain>> getGridSubset(int centerX, int centerY, int offset) {
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
