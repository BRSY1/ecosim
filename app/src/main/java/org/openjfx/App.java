package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.openjfx.ui.Header;
import org.openjfx.ui.Stats;
import org.openjfx.ui.EventBox; // Import new EventBox
import org.openjfx.grid.GridView;

import java.util.*;

public class App extends Application {
    private Grid grid;
    private GameMap gameMap;
    private GridView gridView;
    private EventBox eventBox;

    @Override
    public void start(Stage stage) {
        this.grid = new Grid(1000, 1000, 0.01f);
        this.gameMap = new GameMap(grid);
        ArrayList<ArrayList<Terrain>> terrainArray = gameMap.getTerrainArray();
    
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
    
        // GRID (Map) CONTAINER - Takes full space on the left
        StackPane mapContainer = new StackPane();
        this.gridView = new GridView(1000, 1000);
        mapContainer.getChildren().add(gridView.getGridPane());
        this.gridView.drawMap(terrainArray);
        mapContainer.setClip(new Rectangle(1000, 1000));
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
        statsBox.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), CornerRadii.EMPTY, Insets.EMPTY
        )));
    
        // EVENT BOX (Below Stats)
        this.eventBox = new EventBox();
        VBox eventBoxContainer = eventBox.getEventBox();
        VBox.setVgrow(eventBoxContainer, Priority.ALWAYS); // Allow event log to expand
    
        // ADD STATS + EVENT LOG TO RIGHT PANEL
        rightPanel.getChildren().addAll(statsBox, eventBoxContainer);
    
        // ADD COMPONENTS TO MAIN CONTENT (Map on Left, Right Panel on Right)
        mainContent.getChildren().addAll(mapContainer, rightPanel);
    
        // ADD HEADER & MAIN CONTENT TO ROOT
        root.getChildren().addAll(header, mainContent);
    
        // SCENE SETUP
        Scene scene = new Scene(root, 1400, 1080);
        stage.setScene(scene);
        stage.setTitle("Ecosim");
        stage.setResizable(true);
        stage.show();
    
        // Start game loop
        startGameLoop();
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
        // Step 1: Update the game logic (e.g., terrain changes, animals moving, etc.)
        //gameMap.update();  // Assuming GameMap has an update method
        //grid.update();     // Assuming Grid has an update method

        // Step 2: Get the latest terrain array
        ArrayList<ArrayList<Terrain>> terrainArray = gameMap.getTerrainArray();

        // Step 3: Redraw the map with updated terrain
        gridView.drawMap(terrainArray);

        // Step 4: Log an event (optional)
        eventBox.addEvent("Game tick updated.");
    }

    public static void main(String[] args) {
        launch();
    }
}
