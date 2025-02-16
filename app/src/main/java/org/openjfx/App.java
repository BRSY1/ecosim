package org.openjfx;

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

    @Override
    public void start(Stage stage) {
        Grid grid = new Grid(1000,1000, 0.0075f);
        GameMap gameMap = new GameMap(grid);
        ArrayList<ArrayList<Terrain>> terrainArray = gameMap.getTerrainArray();
        
        
        //for(int x=0; x<grid.getWidth();x++){
        //    for (int y=0; y<grid.getHeight(); y++){
        //        System.out.println(grid.grid[x][y]);
        //    }
        //}
        
        
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
        GridView gridView = new GridView(1000, 1000);
        mapContainer.getChildren().add(gridView.getGridPane());
        gridView.drawMap(terrainArray);
        VBox.setVgrow(mapContainer, Priority.ALWAYS);
        // ** FIX: Prevent grid from overlapping the header when zooming **
        mapContainer.setClip(new Rectangle(1000, 1000));

        // STATS PANEL
        Stats stats = new Stats();
        VBox statsBox = stats.getStatsBox();
        statsBox.setPrefHeight(150);
        statsBox.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), CornerRadii.EMPTY, Insets.EMPTY
        )));

        // EVENT BOX
        EventBox eventBox = new EventBox();
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
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("Ecosim");
        stage.setResizable(true);
        stage.show();

        // Example of adding an event
        eventBox.addEvent("Simulation started...");
    }

    public static void main(String[] args) {
        launch();
    }
}
