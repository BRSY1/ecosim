package org.openjfx;
 
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.openjfx.ui.Header;
import org.openjfx.ui.Stats;
import org.openjfx.pages.SettingsPage; // Import the new SettingsPage class
import java.awt.Desktop;
import java.net.URI;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignG;
import org.kordamp.ikonli.materialdesign2.MaterialDesignIIkonHandler;
import org.openjfx.ui.AnimalEnum;
import org.openjfx.ui.EventBox;
import org.openjfx.ui.InfoBox;
import org.openjfx.pages.SettingsPage; // Import the new SettingsPage class


import org.openjfx.ui.EventBox;
import org.openjfx.grid.GridView;

import java.net.URI;
import java.util.*;
 
public class App extends Application {
    private Grid grid;
    private Stage stage;
    private GameMap gameMap;
    private GridView gridView;
    private EventBox eventBox;
    private InfoBox infoBox;
    private Stats stats = new Stats();
    private ArrayList<ArrayList<Terrain>> terrainArray;
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private SettingsPage settingsPage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.grid = new Grid(800, 800, 0.0055f);
        this.gameMap = new GameMap(grid);
        terrainArray = gameMap.getTerrainArray();

        // spaw animals
        this.spawn(0.001f);

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
        eventBoxContainer.setPrefHeight(200);

        // Spacer to push settings icon down
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        FontIcon githubIcon = new FontIcon(MaterialDesignG.GITHUB);
        githubIcon.setIconSize(24);
        githubIcon.setIconColor(Color.WHITE);
        githubIcon.setCursor(Cursor.HAND); // Change cursor to pointer on hover

        // SETTINGS ICON (Bottom-right)
        FontIcon settingsIcon = new FontIcon(MaterialDesignC.COG);
        settingsIcon.setIconSize(24);
        settingsIcon.setIconColor(Color.WHITE);
        settingsIcon.setCursor(Cursor.HAND); // Change cursor to pointer on hover
        
        // Create StackPane for Overlay
        StackPane overlay = new StackPane();
        settingsPage = new SettingsPage(overlay, this); // Initialize settings page

        // Make sure overlay takes full screen
        overlay.setPickOnBounds(false); // Allow clicking outside to close settings

        settingsIcon.setOnMouseClicked(e -> settingsPage.showSettings());
        githubIcon.setOnMouseClicked((MouseEvent event) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/BRSY1")); // Change to your GitHub link
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Create an HBox for right alignment
        HBox iconBox = new HBox();
        iconBox.setSpacing(5);
        iconBox.getChildren().addAll(githubIcon, settingsIcon);
        iconBox.setPadding(new Insets(10));
        iconBox.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT); // Align to bottom-right

        // ADD COMPONENTS TO RIGHT PANEL IN ORDER
        rightPanel.getChildren().addAll(header, statsBox, infoBoxContainer, eventBoxContainer, spacer, iconBox);
    
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
                int randomType = random.nextInt(10) + 1;
                if ((randomType < 6 || randomType == 9) && ((terrain.biome != 7) && (terrain.biome != 8) && (terrain.biome != 2) && (Math.random() < probSpawn))){
                    Animal animal = new Animal(gameMap,randomType, 0, 20, gameMap.terrainArray.get(j).get(i), random.nextInt(2) + 1);
                    animals.add(animal);
                    AnimalEnum animalEnum = AnimalEnum.values()[animal.foodChainLevel - 1];
                    stats.updateStats(animalEnum, 1);
                }
                if((randomType == 6) && ((terrain.biome == 7) && (Math.random() < probSpawn))){
                    Animal animal = new Animal(gameMap, randomType, 0, 20, gameMap.terrainArray.get(j).get(i), random.nextInt(2) + 1);
                    animals.add(animal);
                    AnimalEnum animalEnum = AnimalEnum.values()[animal.foodChainLevel - 1];
                    stats.updateStats(animalEnum, 1);
                }
                if(((randomType == 7) || (randomType == 10)) && ((terrain.biome == 7) && (terrain.biome == 8) && (Math.random() < probSpawn))){
                    Animal animal = new Animal(gameMap, randomType, 0, 20, gameMap.terrainArray.get(j).get(i), random.nextInt(2) + 1);
                    animals.add(animal);
                    AnimalEnum animalEnum = AnimalEnum.values()[animal.foodChainLevel - 1];
                    stats.updateStats(animalEnum, 1);
                }
                if(randomType == 8 && ((terrain.biome == 7) && (terrain.biome == 8) && (terrain.biome == 3) && (Math.random() < probSpawn))){
                    Animal animal = new Animal(gameMap, randomType, 0, 20, gameMap.terrainArray.get(j).get(i), random.nextInt(2) + 1);
                    animals.add(animal);
                    AnimalEnum animalEnum = AnimalEnum.values()[animal.foodChainLevel - 1];
                    stats.updateStats(animalEnum, 1);
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

    public void resetGame() {
        // Reset game state
        animals.clear();
        
        // Restart the game using stored stage reference
        try {
            start(this.stage);
            eventBox.addEvent("Game has been reset!");
        } catch (Exception e) {
            eventBox.addEvent("Failed to reset game: " + e.getMessage());
        }
    }

    public void updateAnimalPopulation(double targetPopulation) {
        int currentPopulation = animals.size();
        int targetCount = (int) targetPopulation;
        
        while (currentPopulation < targetCount) {
            // Add more animals
            spawn((targetCount - currentPopulation + 100) / (800.0f * 800.0f));
            currentPopulation = animals.size();
        } 
        while (currentPopulation > targetCount) {
            // Remove excess animals
            Animal animalToRemove = animals.get(currentPopulation - 1);
            
            // Clear the animal's position from the map
            Terrain currentTerrain = animalToRemove.getCurrentTerrain(); 
            
            currentTerrain.removeOccupier(animalToRemove);
            if (currentTerrain.framesToRegrow > 0 && (currentTerrain.biome == 1 || currentTerrain.biome == 5 || currentTerrain.biome == 6)) {
                currentTerrain.colour = 11;
            } else {
                currentTerrain.colour = currentTerrain.underlyingColour;
            }

            // Update stats
            AnimalEnum animalEnum = AnimalEnum.values()[animalToRemove.foodChainLevel - 1];
            stats.updateStats(animalEnum, -1);

            animals.remove(--currentPopulation);
        }

        // Force a map redraw to clear old animal positions
        gridView.drawMap(gameMap.getTerrainArray());
        eventBox.addEvent("Animal population updated to: " + animals.size());
    }

    public static void main(String[] args) {
        launch();
    }
}