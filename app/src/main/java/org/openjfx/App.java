package org.openjfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import org.openjfx.ui.Header;
import org.openjfx.ui.Stats;
import org.openjfx.grid.GridView;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create main VBox layout (stacks items vertically)
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #202020;");
        root.setSpacing(0); // No extra space between elements

        // Create the header
        var header = Header.createHeader();
        header.setPrefHeight(50);
        header.setStyle("-fx-background-color: #151515; -fx-text-fill: white;"); // Dark header

        // Create the map (GridView) inside a StackPane
        StackPane mapContainer = new StackPane();
        mapContainer.setPrefHeight(300); // Adjustable height for map
        GridView gridView = new GridView(600, 600);
        mapContainer.getChildren().add(gridView.getGridPane());

        VBox.setVgrow(mapContainer, Priority.ALWAYS);


        // Create the stats panel
        Stats stats = new Stats();
        VBox statsBox = stats.getStatsBox();
        statsBox.setPrefHeight(150); // Larger stats section
        statsBox.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), // Dark background
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        // Add everything to the VBox
        root.getChildren().addAll(header, mapContainer, statsBox);

        // Set up the scene
        Scene scene = new Scene(root, 600, 800);
        stage.setScene(scene);
        stage.setTitle("Ecosim");
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
