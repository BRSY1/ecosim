package org.openjfx.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import java.util.HashMap;
import java.util.Map;

public class Stats {
    private VBox statsBox; // Holds all labels
    private Map<AnimalEnum, Integer> animalCounts; // Stores animal counts
    private Map<AnimalEnum, Label> animalLabels; // Maps animals to their UI labels

    public Stats() {
        statsBox = new VBox(); // Vertical layout with spacing
        statsBox.setPadding(new Insets(10, 10, 10, 10)); // Add padding inside
        statsBox.setMinWidth(150); // Minimum width to make it look good
        statsBox.setMaxWidth(200); // Prevent excessive width

        // Set background (dark, transparent with rounded corners)
        statsBox.setBackground(new Background(new BackgroundFill(
            Color.rgb(0, 0, 0, 0.7), // Black with 70% opacity
            new CornerRadii(10), // Rounded corners
            Insets.EMPTY
        )));

        // Title label
        Label title = new Label("Stats");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setTextFill(Color.WHITE);

        // Initialize containers
        animalCounts = new HashMap<>();
        animalLabels = new HashMap<>();

        // Initialize with some default animals (add more as needed)
        for (AnimalEnum animal : AnimalEnum.values()) {
            animalCounts.put(animal, 0); // Default count 0
            Label label = new Label(animal.name() + ": 0");
            label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            animalLabels.put(animal, label);
        }

        // Add everything to statsBox
        statsBox.getChildren().add(title);
        statsBox.getChildren().addAll(animalLabels.values());
    }

    /**
     * Updates the count of a specific animal in the stats box.
     *
     * @param animal The animal type to update.
     * @param count  The new count value.
     */
    public void updateStats(AnimalEnum animal, int count) {
        if (animalCounts.containsKey(animal)) {
            animalCounts.put(animal, count);
            animalLabels.get(animal).setText(animal.name() + ": " + count);
        }
    }

    public VBox getStatsBox() {
        return statsBox;
    }
}
