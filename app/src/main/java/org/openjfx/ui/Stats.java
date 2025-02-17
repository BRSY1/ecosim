package org.openjfx.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.openjfx.ui.AnimalEnum; // Adjust import if needed

import java.util.HashMap;
import java.util.Map;

public class Stats {
    private VBox statsBox; // Holds title + gridPane
    private Map<AnimalEnum, Integer> animalCounts; // Stores animal counts
    private Map<AnimalEnum, Label> animalLabels;   // Maps animals to their UI labels

    public Stats() {
        // Main container (VBox)
        statsBox = new VBox();
        statsBox.setPadding(new Insets(10));
        statsBox.setMinWidth(150);
        statsBox.setMaxWidth(400);
        statsBox.setStyle("-fx-background-color: #202020; -fx-background-radius: 10px; " +
                "-fx-border: 20px; -fx-border-color: black; -fx-border-radius: 10px;");
        statsBox.setAlignment(Pos.TOP_CENTER);

        // Semi-transparent black background
        statsBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 0, 0, 0.7),
                new CornerRadii(10),
                Insets.EMPTY
        )));

        // Title label
        Label title = new Label("Stats");
        title.setPadding(new Insets(0,0,5,0));
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Initialize containers
        animalCounts = new HashMap<>();
        animalLabels = new HashMap<>();

        // Create labels for each AnimalEnum
        for (AnimalEnum animal : AnimalEnum.values()) {
            animalCounts.put(animal, 0); // Default count is 0
            Label label = new Label(animal.name() + ": 0");
            label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            animalLabels.put(animal, label);
        }

        // Use a GridPane to arrange the labels: max 5 per column
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Horizontal gap between columns
        gridPane.setVgap(5); // Vertical gap between labels
        gridPane.setAlignment(Pos.TOP_CENTER);

        int i = 0;
        for (Label label : animalLabels.values()) {
            int row = i % 5;   // Each column has 5 rows max
            int col = i / 5;   // New column after 5 labels
            gridPane.add(label, col, row);
            i++;
        }

        // Add title and gridPane to the statsBox
        statsBox.getChildren().addAll(title, gridPane);
    }

    /**
     * Updates the count of a specific animal in the stats box.
     *
     * @param animal The animal type to update.
     * @param count  The increment to add to the current count.
     */
    public void updateStats(AnimalEnum animal, int count) {
        if (animalCounts.containsKey(animal)) {
            int oldValue = animalCounts.get(animal);
            int newValue = oldValue + count;
            animalCounts.put(animal, newValue);

            Label label = animalLabels.get(animal);
            label.setText(animal.name() + ": " + newValue);
        }
    }

    public VBox getStatsBox() {
        return statsBox;
    }
}
