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
    private ArrayList<ArrayList<Terrain>> terrainArray;

    private ArrayList<Animal> animals = new ArrayList<Animal>();

    @Override
    public void start(Stage stage) {
        this.grid = new Grid(600,600, 0.01f);
        this.gameMap = new GameMap(grid);
        terrainArray = gameMap.getTerrainArray();
        animals.add(new Animal(gameMap, 0, 0, 2, gameMap.terrainArray.get(300).get(300)));

        // Create main VBox layout
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #202020;");
        root.setSpacing(0); // No extra space between elements

        // HEADER
        var header = Header.createHeader();
        header.setPrefHeight(50);
        header.setStyle("-fx-background-color: #151515; -fx-text-fill: white;");

        // GRID (Map) CONTAINER
        StackPane mapContainer = new StackPane();
        mapContainer.setPrefHeight(300); // Adjustable height for map
        this.gridView = new GridView(600, 600);
        mapContainer.getChildren().add(gridView.getGridPane());
        this.gridView.drawMap(terrainArray);
        VBox.setVgrow(mapContainer, Priority.ALWAYS);
        // ** FIX: Prevent grid from overlapping the header when zooming **
        mapContainer.setClip(new Rectangle(600, 600));

        // STATS PANEL
        Stats stats = new Stats();
        VBox statsBox = stats.getStatsBox();
        statsBox.setPrefHeight(150);
        statsBox.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), CornerRadii.EMPTY, Insets.EMPTY
        )));

        // EVENT BOX
        this.eventBox = new EventBox();
        VBox eventBoxContainer = eventBox.getEventBox();

        // BOTTOM CONTAINER (Stats + Event Log)
        HBox bottomContainer = new HBox();
        bottomContainer.setSpacing(10);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), CornerRadii.EMPTY, Insets.EMPTY
        )));
        bottomContainer.getChildren().addAll(statsBox, eventBoxContainer);
        HBox.setHgrow(statsBox, Priority.ALWAYS);
        HBox.setHgrow(eventBoxContainer, Priority.ALWAYS);

        // ADD COMPONENTS TO ROOT
        root.getChildren().addAll(header, mapContainer, bottomContainer);

        // SCENE SETUP
        Scene scene = new Scene(root, 600, 800);
        stage.setScene(scene);
        stage.setTitle("Ecosim");
        stage.setResizable(true);
        stage.show();

        // Example of adding an event
        this.eventBox.addEvent("Simulation started...");

        // Start the game loop
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
        for (Animal animal : animals) {
            animal.animalUpdate();
        }

        // Redraw the map with updated terrain
        gridView.drawMap(terrainArray);

        // Log an event (optional)
        eventBox.addEvent("Game tick updated.");
    }

    public static void main(String[] args) {
        launch();
    }
}
