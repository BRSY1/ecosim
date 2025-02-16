package org.openjfx;
import java.util.*;

public class Grid {
    int width;
    int height;
    double scale;
    double[][] grid;
    double[][] gradientsX;
    double[][] gradientsY;

    ArrayList<ArrayList<Terrain>> map;

    //Constructor to create a new map
    public Grid(int width, int height, double scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;

        //Initialises new 2D array of map size
        this.grid = new double[width][height];


        //Produces Gradients for each point in array
        this.gradientsX = new double[height + 1][width + 1];
        this.gradientsY = new double[height + 1][width + 1];
        generateGradients();

        //Fills grid with arbitrary values
        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                double sampleX = x*scale;
                double sampleY = y*scale;
                grid[x][y]= perlin(sampleX, sampleY);
            }
        }
    }

    //Returns array of random gradients
    private void generateGradients() {
        Random rand = new Random();
        for (int x = 0; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
                double angle = rand.nextDouble() * 2 * Math.PI;
                this.gradientsX[x][y] = Math.cos(angle);
                this.gradientsY[x][y] = Math.sin(angle);
            }
        }
    }

    //Used to smooth out difference in gradient
    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Linear interpolation
    private double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    private double dotGridGradient(int gridX, int gridY, double x, double y) {
        if (gridX < 0 || gridX >= gradientsX[0].length || gridY < 0 || gridY >= gradientsX.length) {
            return 0; // Return zero if out of bounds to prevent crashes
        }
        double dx = x - gridX;
        double dy = y - gridY;
        return dx * gradientsX[gridX][gridY] + dy * gradientsY[gridX][gridY];
    }


    // return the animal's view
    public ArrayList<ArrayList<Terrain>> getGridSubset(int centerX, int centerY, int offset) {
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

    // Implements Perlin noise
    public double perlin(double sampleX, double sampleY) {
        // Find the integer grid cell coordinates
        int x0 = (int) Math.floor(sampleX);
        int y0 = (int) Math.floor(sampleY);
        int x1 = x0 + 1;
        int y1 = y0 + 1;

        // Compute the fade curves for x and y
        double sx = fade(sampleX - x0);
        double sy = fade(sampleY - y0);

        // Compute the dot products at the four corners
        double n00 = dotGridGradient(x0, y0, sampleX, sampleY);
        double n10 = dotGridGradient(x1, y0, sampleX, sampleY);
        double n01 = dotGridGradient(x0, y1, sampleX, sampleY);
        double n11 = dotGridGradient(x1, y1, sampleX, sampleY);

        // Interpolate along x-axis
        double ix0 = lerp(n00, n10, sx);
        double ix1 = lerp(n01, n11, sx);

        // Interpolate along y-axis and return final noise value
        return lerp(ix0, ix1, sy);
    }

    // Display the generated map
    public void printGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.printf("%.2f ", grid[y][x]);
            }
            System.out.println();
        }
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}


