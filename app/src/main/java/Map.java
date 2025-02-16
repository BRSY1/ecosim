import java.util.Random;

public class Map {
    int width;
    int height;
    float scale;
    float[][] grid;
    int gradientsX[][];
    int gradientsY[][];



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





}
