package org.openjfx.grid;

import org.openjfx.App;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GridView {
    private Canvas canvas;
    private int width;
    private int height;


    public GridView(int width, int height) {
        this.width = width;
        this.height = height;
        int canvasWidth = 500;
        int canvasHeight = 500;
        canvas = new Canvas(canvasWidth, canvasHeight);
        canvas.setTranslateX((this.width - canvasWidth) / 2.0);  // Center horizontally
        canvas.setTranslateY((this.height - canvasHeight) / 2.0);  // Center vertically
        drawMap();
    }

    private void drawMap() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        // Generate random terrain (replace with your logic)
        for (int y = 0; y < 500; y++) {
            for (int x = 0; x < 500; x++) {
                Color color = getTileColor(x, y);
                pw.setColor(x, y, color);
            }
        }
    }

    private Color getTileColor(int x, int y) {
        // Example: Simple terrain generation based on coordinates
        if (y < 250) {
            return Color.GREEN;  // Grass
        } else if (x < 250) {
            return Color.BLUE;   // Water
        } else {
            return Color.BROWN;  // Dirt
        }
    }

    public Pane getGridPane() {
        return new Pane(canvas);
    }
}
