package org.openjfx;
 
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.openjfx.ui.Header;
import org.openjfx.ui.Stats;
import org.openjfx.pages.SettingsPage; // Import the new SettingsPage class

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import org.openjfx.ui.EventBox;
import org.openjfx.ui.InfoBox;
import org.openjfx.pages.SettingsPage; // Import the new SettingsPage class

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import org.openjfx.ui.EventBox;
import org.openjfx.grid.GridView;
 
import java.util.*;
 
public class App extends Application {
    private Grid grid;
    private GameMap gameMap;
    private GridView gridView;
    private EventBox eventBox;
    private InfoBox infoBox;
    private ArrayList<ArrayList<Terrain>> terrainArray;
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private SettingsPage settingsPage;

    @Override
    public void start(Stage stage) {
        this.grid = new Grid(800, 800, 0.0055f);
        this.gameMap = new GameMap(grid);
        terrainArray = gameMap.getTerrainArray();

        // spaw animals
        this.spawn(0.0002f);

        // Create main VBox layout (Header at top, Content below)
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #202020;");
 
        // HEADER (Stays at the top)
        var header = Header.createHeader();
        header.setStyle("-fx-background-color: #151515; -fx-text-fill: white;");
 
        // MAIN CONTENT (HBox: Left - Map, Right - Stats/Event Log)
        HBox mainContent = new HBox();
        mainContent.setPrefWidth(400);
        VBox.setVgrow(mainContent, Priority.ALWAYS); // Allow main content to expand

        // infobox
        this.infoBox = new InfoBox();
        VBox infoBoxContainer = infoBox.getInfoBox();
        VBox.setMargin(infoBoxContainer, new Insets(10,10,10,10));
        infoBoxContainer.setPrefHeight(400);
 
        // GRID (Map) CONTAINER - Takes full space on the left
        StackPane mapContainer = new StackPane();
        this.gridView = new GridView(800, 800, infoBox);
        mapContainer.getChildren().add(gridView.getGridPane());
        this.gridView.drawMap(terrainArray);
        mapContainer.setClip(new Rectangle(800, 800));
        HBox.setHgrow(mapContainer, Priority.ALWAYS); // Let it expand
 
        // RIGHT PANEL (Stats + Event Log)
        VBox rightPanel = new VBox();
        rightPanel.setPrefWidth(400); // Fixed width for right panel
        rightPanel.setPadding(new Insets(10));
        rightPanel.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), CornerRadii.EMPTY, Insets.EMPTY
        )));
 
        // STATS PANEL (Top of right panel)
        Stats stats = new Stats();
        VBox statsBox = stats.getStatsBox();
        statsBox.setPrefHeight(150);
        VBox.setMargin(statsBox, new Insets(10,10,10,10));
        statsBox.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), CornerRadii.EMPTY, Insets.EMPTY
        )));
 
        // EVENT BOX (Below Stats)
        this.eventBox = new EventBox();
        VBox eventBoxContainer = eventBox.getEventBox();
        VBox.setMargin(eventBoxContainer, new Insets(10,10,10,10));
        eventBoxContainer.setPrefHeight(250);

        // Spacer to push settings icon down
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // SETTINGS ICON (Bottom-right)
        FontIcon settingsIcon = new FontIcon(MaterialDesignC.COG);
        settingsIcon.setIconSize(24);
        settingsIcon.setIconColor(Color.WHITE);
        settingsIcon.setCursor(Cursor.HAND); // Change cursor to pointer on hover
        
        // Create StackPane for Overlay
        StackPane overlay = new StackPane();
        settingsPage = new SettingsPage(overlay); // Initialize settings page

        // Make sure overlay takes full screen
        overlay.setPickOnBounds(false); // Allow clicking outside to close settings

        settingsIcon.setOnMouseClicked(e -> settingsPage.showSettings());

        // Create an HBox for right alignment
        HBox settingsBox = new HBox(settingsIcon);
        settingsBox.setPadding(new Insets(10));
        settingsBox.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT); // Align to bottom-right

        // ADD COMPONENTS TO RIGHT PANEL IN ORDER
        rightPanel.getChildren().addAll(header, statsBox, infoBoxContainer, eventBoxContainer, spacer, settingsBox);
    
        // ADD COMPONENTS TO MAIN CONTENT (Map on Left, Right Panel on Right)
        mainContent.getChildren().addAll(mapContainer, rightPanel);
 
        // ADD MAIN CONTENT TO ROOT
        root.getChildren().addAll(mainContent);
    
        // Wrap everything in a StackPane to allow overlay
        StackPane rootPane = new StackPane(root, overlay);
        Scene scene = new Scene(rootPane, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Ecosim");
        stage.setResizable(true);
        stage.show();
 
        // Start game loop
        startGameLoop();
    }

    private void spawn(float probSpawn) {
        Random random = new Random();
        for (int j = 0; j < 800; j++) {
            for (int i = 0; i < 800; i++) {
                Terrain terrain = this.gameMap.terrainArray.get(j).get(i);
                if (terrain.biome != 7 && terrain.biome != 8 && Math.random() < probSpawn) {
                    animals.add(new Animal(gameMap, random.nextInt(10) + 1, 0, 20, gameMap.terrainArray.get(j).get(i), random.nextInt(2) + 1));
                }
            }
        }
    }
    
    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
 
            @Override
            public void handle(long now) {
                if (lastUpdate == 0 || now - lastUpdate >= 1_000_000_000) { // ~60 FPS (16.67ms per frame)
                    updateGame();
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        gameMap.update();  // Assuming GameMap has an update method

        for (Animal animal : animals) {
            animal.animalUpdate();
        }
 
        // Step 2: Get the latest terrain array
        ArrayList<ArrayList<Terrain>> terrainArray = gameMap.getTerrainArray();

        // Step 3: Redraw the map with updated terrain
        gridView.drawMap(terrainArray);

        // Log an event (optional)
        eventBox.addEvent("Game tick updated.");



    }

    public static void main(String[] args) {
        launch();
    }
}