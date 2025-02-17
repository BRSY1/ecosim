package org.openjfx.grid;

import java.util.ArrayList;
import java.util.Arrays;
import org.openjfx.Terrain;
import org.openjfx.ui.InfoBox;
import org.openjfx.ui.BiomeInfo;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GridView {
    private Canvas canvas;
    private int width;
    private int height;
    private double zoomLevel = 1.0;
    private final double minZoomLevel = 1.0;
    private final double maxZoomLevel = 4.0;
    private double translateX = 0, translateY = 0;
    private Pane container;        // Whether the user is currently highlighting
    private InfoBox infoBox;
    private final Map<Color, BiomeInfo> biomeMap = new HashMap<>();
    private ArrayList<Color> colorList = new ArrayList<>(Arrays.asList(
        Color.DARKBLUE,   // Dark Blue
        Color.BLUE,       // Blue
        Color.rgb(247, 239, 83),
        Color.rgb(61, 148, 21),
        Color.GREEN,      // Green
        Color.DARKGREEN,
        Color.rgb(5, 72, 5),
        Color.DARKGRAY,
        Color.RED,
        Color.rgb(0,80,0),
        Color.DARKGRAY,
        Color.rgb(255, 128, 0),
        Color.rgb(70, 42, 1),        
        Color.rgb(255, 0, 127),
        Color.rgb(153,204,255),
        Color.BLUE,
        Color.rgb(64,64,64),
        Color.rgb(255,153,204),
        Color.rgb(0,255,0),
        Color.RED,
        Color.rgb(204,204,0),
        Color.rgb(0,0,0)
    ));

    public GridView(int width, int height, InfoBox infobox) {
        this.width = width;
        this.height = height;
        this.infoBox = infobox;
    
        // Make canvas match the defined width and height
        canvas = new Canvas(width, height);
        // Create a container and ensure it starts and stays at the correct size
        container = new Pane(canvas);
        container.setPrefSize(width, height);
        container.setMinSize(width, height); // Prevents zooming out
    
        // Ensure the canvas matches the container size
        canvas.widthProperty().bind(container.widthProperty());
        canvas.heightProperty().bind(container.heightProperty());
    
        initialiseBiomeData();
        setupEventHandlers();
    }
        

    private void initialiseBiomeData() {
    biomeMap.put(Color.DARKGRAY, new BiomeInfo("Rocky Mountains", "A high-altitude region with rocky peaks.",
        getClass().getResource("/org/openjfx/ui/assets/rocky.jpg").toExternalForm()));

    biomeMap.put(Color.DARKBLUE, new BiomeInfo("Oasis", "A fertile area in a desert, providing water.",
        getClass().getResource("/org/openjfx/ui/assets/ocean.jpg").toExternalForm()));

    biomeMap.put(Color.BLUE, new BiomeInfo("Oasis", "A fertile area in a desert, providing water.",
        getClass().getResource("/org/openjfx/ui/assets/ocean.jpg").toExternalForm()));

    biomeMap.put(Color.rgb(247, 239, 83), new BiomeInfo("Sandy Desert", "An arid biome with vast sand dunes.",
        getClass().getResource("/org/openjfx/ui/assets/desert.jpg").toExternalForm()));

    biomeMap.put(Color.rgb(61, 148, 21), new BiomeInfo("Grasslands", "A vast area of open fields and grass.",
        getClass().getResource("/org/openjfx/ui/assets/grass.jpg").toExternalForm()));

    biomeMap.put(Color.GREEN, new BiomeInfo("Grasslands", "A vast area of open fields and grass.",
        getClass().getResource("/org/openjfx/ui/assets/grass.jpg").toExternalForm()));

    biomeMap.put(Color.DARKGREEN, new BiomeInfo("Shrubbery", "Densely packed low vegetation.",
        getClass().getResource("/org/openjfx/ui/assets/shrub.jpg").toExternalForm()));

    biomeMap.put(Color.rgb(5, 72, 5), new BiomeInfo("Forests", "A biome covered with tall trees and rich wildlife.",
        getClass().getResource("/org/openjfx/ui/assets/forest.jpg").toExternalForm()));
}


    private void setupEventHandlers() {
        // Zoom handler
        container.setOnScroll(this::handleScroll);

        canvas.setOnMousePressed(this::handleMousePressed);
        
    }

    private void handleMousePressed(MouseEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        System.out.println("X: " + x + "Y: " + y);

        
        Color clickedColor = getPixelColor(x,y);

        if (biomeMap.containsKey(clickedColor)) {
            BiomeInfo biome = biomeMap.get(clickedColor);
            this.infoBox.displayBiome(biome);
        }
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
        zoomLevel = Math.min(Math.max(zoomLevel, 1), maxZoomLevel);
    
        // Get mouse position relative to the canvas BEFORE zooming
        double mouseX = event.getX() - 400;
        double mouseY = event.getY() - 400;
    
        // Convert mouse position to world coordinates before zooming
        double worldX = (mouseX - translateX) / oldZoom;
        double worldY = (mouseY - translateY) / oldZoom;
        
        // Apply zoom scaling
        container.setScaleX(zoomLevel);
        container.setScaleY(zoomLevel);
    
        // Convert world coordinates back to new screen coordinates
        double newTranslateX = mouseX - (worldX * zoomLevel); 
        double newTranslateY = mouseY - (worldY * zoomLevel);
    
        // Apply new translations
        translateX = clampTranslateX(newTranslateX);
        translateY = clampTranslateY(newTranslateY);
        container.setTranslateX(translateX);
        container.setTranslateY(translateY);

        event.consume();
    }

    private Color getPixelColor(int x, int y) {
        return canvas.snapshot(null, null).getPixelReader().getColor(x, y);
    }

    private void resetView() {
        zoomLevel = 1.0; // Reset zoom
        translateX = 0;   // Reset X translation
        translateY = 0;   // Reset Y translation
    
        container.setScaleX(zoomLevel);
        container.setScaleY(zoomLevel);
        container.setTranslateX(translateX);
        container.setTranslateY(translateY);
    }
       

    private double clampTranslateX(double proposedTranslateX) {
        double scaledWidth = width * zoomLevel;
        double viewWidth = container.getWidth();
    
        // **Corrected clamping**
        double minTranslateX = viewWidth - scaledWidth;  // Allow negative values
        double maxTranslateX = viewWidth + scaledWidth;  // Prevent moving too far right
    
        return Math.max(minTranslateX, Math.min(proposedTranslateX, maxTranslateX));
    }
    
    private double clampTranslateY(double proposedTranslateY) {
        double scaledHeight = height * zoomLevel;
        double viewHeight = container.getHeight();
    
        // **Corrected clamping**
        double minTranslateY = viewHeight - scaledHeight;  // Allow negative values
        double maxTranslateY = viewHeight + scaledHeight;  // Prevent moving too far down
    
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
