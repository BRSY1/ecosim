package org.openjfx.grid;

import java.util.ArrayList;
import java.util.Arrays;
import org.openjfx.ui.AnimalInfo;
import org.openjfx.Terrain;
import org.openjfx.ui.InfoBox;
import org.openjfx.ui.BiomeInfo;
import javafx.scene.shape.Rectangle;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Duration;

public class GridView {
    private Canvas canvas;
    private int width;
    private int height;
    private double zoomLevel = 1.0;
    private final double maxZoomLevel = 15.0;
    private double translateX = 0, translateY = 0;
    private Pane container;        // Whether the user is currently highlighting
    private InfoBox infoBox;
    private final Map<Color, BiomeInfo> biomeMap = new HashMap<>();
    private final Map<Color, AnimalInfo> animalMap = new HashMap<>();
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
        Color.rgb(70, 42, 1), 
        Color.rgb(255, 128, 0),       
        Color.rgb(255, 0, 127),
        Color.rgb(153,204,255),
        Color.PURPLE,
        Color.rgb(255,255,255),
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
        container.setMaxSize(width, height);


        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(container.widthProperty());
        clipRect.heightProperty().bind(container.heightProperty());
        container.setClip(clipRect);
    
        // Ensure the canvas matches the container size
        canvas.widthProperty().bind(container.widthProperty());
        canvas.heightProperty().bind(container.heightProperty());
    
        initialiseBiomeData();
        initialiseAnimalData();
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

    private void initialiseAnimalData() {
        animalMap.put(Color.rgb(255, 128, 0), new AnimalInfo("Squirrel", "A nimble and quick rodent.\n Prey to Fox and Wolf.",
            getClass().getResource("/org/openjfx/ui/assets/squirrel.jpg").toExternalForm()));
    
        animalMap.put(Color.rgb(255, 0, 127), new AnimalInfo("Rabbit", "A fast and cute animal.\n Prey to Fox and Wolf.",
            getClass().getResource("/org/openjfx/ui/assets/rabbit.jpg").toExternalForm()));
    
        animalMap.put(Color.rgb(153, 204, 255), new AnimalInfo("Elephant", "A large, majestic, and intelligent animal.\n Prey to Wolf and Lion.",
            getClass().getResource("/org/openjfx/ui/assets/elephant.jpg").toExternalForm()));
    
        animalMap.put(Color.PURPLE, new AnimalInfo("Fox", "A cunning and agile predator.\n Prey to Wolf and Lion.",
            getClass().getResource("/org/openjfx/ui/assets/fox.jpg").toExternalForm()));
    
        animalMap.put(Color.rgb(255, 255, 255), new AnimalInfo("Wolf", "A strong pack animal with a keen sense of loyalty.\n Prey to Lion.",
            getClass().getResource("/org/openjfx/ui/assets/wolf.jpg").toExternalForm()));
    
        animalMap.put(Color.rgb(255, 153, 204), new AnimalInfo("Jellyfish", "A mesmerizing sea creature with a delicate form.\n Prey to Salmon and Crocodile.",
            getClass().getResource("/org/openjfx/ui/assets/jellyfish.jpg").toExternalForm()));
    
        animalMap.put(Color.rgb(0, 255, 0), new AnimalInfo("Salmon", "A fish known for its upstream journey.\n Prey to Crocodile and Shark.",
            getClass().getResource("/org/openjfx/ui/assets/salmon.jpg").toExternalForm()));
    
        animalMap.put(Color.RED, new AnimalInfo("Crocodile", "A powerful reptile with an impressive bite.\n Prey to Shark.",
            getClass().getResource("/org/openjfx/ui/assets/crocodile.jpg").toExternalForm()));
    
        animalMap.put(Color.rgb(204, 204, 0), new AnimalInfo("Lion", "The king of the jungle, majestic and powerful.\n Apex Predator (on land).",
            getClass().getResource("/org/openjfx/ui/assets/lion.jpg").toExternalForm()));
    
        animalMap.put(Color.rgb(0, 0, 0), new AnimalInfo("Shark", "A fearsome predator ruling the seas.\n Apex Predator (on water).",
            getClass().getResource("/org/openjfx/ui/assets/shark.jpg").toExternalForm()));
    }


    private void setupEventHandlers() {
        // Zoom handler
        container.setOnScroll(this::handleScroll);

        canvas.setOnMousePressed(this::handleMousePressed);
        
    }

    private void handleMousePressed(MouseEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        
        Color clickedColor = getPixelColor(x,y);

        if (biomeMap.containsKey(clickedColor)) {
            BiomeInfo biome = biomeMap.get(clickedColor);
            this.infoBox.displayBiome(biome);
        }
        if (animalMap.containsKey(clickedColor)) {
            AnimalInfo animal = animalMap.get(clickedColor);
            this.infoBox.displayAnimal(animal);
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

        // After computing newTranslateX and newTranslateY:
        if (zoomLevel == 1.0) {
            // Instead of snapping back immediately, animate the translation back to 0
            TranslateTransition transition = new TranslateTransition(Duration.millis(20), container);
            transition.setToX(0);
            transition.setToY(0);
            transition.play();
            
            // Also update our stored translation values
            translateX = 0;
            translateY = 0;
        } else {
            // Apply new translations immediately if not resetting to default zoom
            translateX = clampTranslateX(newTranslateX);
            translateY = clampTranslateY(newTranslateY);
            container.setTranslateX(translateX);
            container.setTranslateY(translateY);
        }

        event.consume();
    }

    private Color getPixelColor(int x, int y) {
        return canvas.snapshot(null, null).getPixelReader().getColor(x, y);
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
