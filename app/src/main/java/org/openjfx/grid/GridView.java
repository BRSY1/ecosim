package org.openjfx.grid;

import java.util.ArrayList;
import java.util.Arrays;
import org.openjfx.Terrain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;

public class GridView {
    private Canvas canvas;
    private int width;
    private int height;
    private double zoomLevel = 1.0;
    private final double minZoomLevel = 1.0;
    private final double maxZoomLevel = 4.0;
    private double dragStartX, dragStartY;
    private double translateX = 0, translateY = 0;
    private Pane container;
    private ArrayList<Color> colorList = new ArrayList<>(Arrays.asList(
            Color.DARKBLUE,   // Dark Blue
            Color.BLUE,       // Blue
            Color.rgb(247, 239, 83),
            Color.rgb(61, 148, 21),
            Color.GREEN,      // Green
            Color.DARKGREEN,
            Color.FORESTGREEN,
            Color.DARKGRAY,
            Color.RED,
            Color.rgb(0,80,0),
            Color.DARKGRAY
        ));

    public GridView(int width, int height) {
        this.width = width;
        this.height = height;

        // Make canvas match the defined width and height
        canvas = new Canvas(width, height);
        container = new Pane(canvas);

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Zoom handler
        canvas.setOnScroll(this::handleScroll);

        // Drag handler for panning
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleMousePressed(MouseEvent e) {
        dragStartX = e.getX() - translateX;
        dragStartY = e.getY() - translateY;
    }

    private void handleMouseDragged(MouseEvent e) {
        double newTranslateX = e.getX() - dragStartX;
        double newTranslateY = e.getY() - dragStartY;

        // Limit panning within valid bounds
        translateX = clampTranslateX(newTranslateX);
        translateY = clampTranslateY(newTranslateY);

        // Apply panning
        canvas.setTranslateX(translateX);
        canvas.setTranslateY(translateY);
    }

    private void handleScroll(ScrollEvent event) {
        double zoomFactor = 1.1;
        double oldZoom = zoomLevel;
        double deltaY = event.getDeltaY();
    
        if (deltaY < 0) {
            zoomLevel /= zoomFactor;
        } else {
            zoomLevel *= zoomFactor;
        }
    
        // Clamp zoom level
        zoomLevel = Math.min(Math.max(zoomLevel, minZoomLevel), maxZoomLevel);
    
        // Get mouse position relative to the canvas BEFORE zooming
        double mouseX = event.getX() - 400;
        double mouseY = event.getY() - 400;
        System.out.println("" + mouseX + "" +  mouseY);
    
        // Convert mouse position to world coordinates before zooming
        double worldX = (mouseX - translateX) / oldZoom;
        double worldY = (mouseY - translateY) / oldZoom;
    
        // Apply zoom scaling
        canvas.setScaleX(zoomLevel);
        canvas.setScaleY(zoomLevel);
    
        // Convert world coordinates back to new screen coordinates
        double newTranslateX = mouseX - (worldX * zoomLevel); 
        double newTranslateY = mouseY - (worldY * zoomLevel);
    
        // Apply new translations
        translateX = clampTranslateX(newTranslateX);
        translateY = clampTranslateY(newTranslateY);
        canvas.setTranslateX(translateX);
        canvas.setTranslateY(translateY);
    
        event.consume();
    }
    
    
       

    private double clampTranslateX(double proposedTranslateX) {
        double scaledWidth = width * zoomLevel;
        double viewWidth = canvas.getWidth();
    
        // **Corrected clamping**
        double minTranslateX = viewWidth - scaledWidth;  // Allow negative values
        double maxTranslateX = 0;  // Prevent moving too far right
    
        return Math.max(minTranslateX, Math.min(proposedTranslateX, maxTranslateX));
    }
    
    private double clampTranslateY(double proposedTranslateY) {
        double scaledHeight = height * zoomLevel;
        double viewHeight = canvas.getHeight();
    
        // **Corrected clamping**
        double minTranslateY = viewHeight - scaledHeight;  // Allow negative values
        double maxTranslateY = 0;  // Prevent moving too far down
    
        return Math.max(minTranslateY, Math.min(proposedTranslateY, maxTranslateY));
    }
    
       

    public void drawMap(ArrayList<ArrayList<Terrain>> terrainArray) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        // Example terrain: green for top, blue for left, brown for bottom-right
        int y = 0;
        for (ArrayList<Terrain> oneDterrains : terrainArray){
            int x = 0;
            for (Terrain temp : oneDterrains){
                int index = temp.colour;
                Color colour = colorList.get(index);
                pw.setColor(x,y,colour);
                x++;
            }
            y++;
        }
    }

    public Pane getGridPane() {
        return container;
    }
}
