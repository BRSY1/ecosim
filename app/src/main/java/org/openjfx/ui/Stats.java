package org.openjfx.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.openjfx.App;
import java.util.HashMap;
import java.util.Map;

public class Stats {
    private VBox statsBox; // Holds title + gridPane
    private Map<AnimalEnum, Integer> animalCounts; // Stores animal counts
    private Map<AnimalEnum, Label> animalLabels;   // Maps animals to their UI labels
    private Map<AnimalEnum, Color> animalColors;
    public int births;
    public int deaths;
    private App app;
    

    public Stats(App app) {
        // Main container (VBox)
        this.app = app;
        this.births = 0;
        this.deaths = 0;
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
            animalLabels.put(animal, label);
        }

        // Use a GridPane to arrange the labels: max 5 per column
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30); // Horizontal gap between columns
        gridPane.setVgap(5); // Vertical gap between labels
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setStyle(
            "-fx-control-inner-background: #303030; " +  // Dark inner background
            "-fx-text-fill: white; " +                   // White text color
            "-fx-highlight-fill: #444; " +               // Darker highlight for selection
            "-fx-highlight-text-fill: white; " +         // Text color when highlighted
            "-fx-background-color: #303030; " +          // Main background color
            "-fx-background-insets: 0, 0, 1, 2; " +      // Remove extra insets (fixes dots)
            "-fx-background-radius: 5px; " +             // Smooth rounded edges
            "-fx-border-radius: 5px; " +                 // Rounded border
            "-fx-border-color: #252525; " +          // Hide default border
            "-fx-border-width: 2px; " +                    // No visible border
            "-fx-padding: 10px; " +                      // Inner padding
            "-fx-font-size: 14px; " +                    // Readable font size
            "-fx-overflow-x: hidden; " +                 // Hide horizontal scroll
            "-fx-overflow-y: hidden; " +                 // Hide vertical scroll
            "-fx-cursor: text; " +                       // Proper cursor for text
            "-fx-focus-color: transparent; " +           // No blue outline when focused
            "-fx-faint-focus-color: transparent;"        // Remove faint glow effect
        );

        int i = 0;
        for (Label label : animalLabels.values()) {
            int row = i % 5;   // Each column has 5 rows max
            int col = i / 5;   // New column after 5 labels
            gridPane.add(label, col, row);
            i++;
        }

        // Add title and gridPane to the statsBox
        initialiseAnimalText();
        statsBox.getChildren().addAll(title, gridPane);
    }

    private void initialiseAnimalText() {
        animalColors = new HashMap<>();

        animalColors.put(AnimalEnum.SQUIRREL, Color.rgb(255, 128, 0));  
        animalColors.put(AnimalEnum.RABBIT, Color.rgb(255, 0, 127));    
        animalColors.put(AnimalEnum.ELEPHANT, Color.rgb(153, 204, 255)); 
        animalColors.put(AnimalEnum.FOX, Color.PURPLE);                    
        animalColors.put(AnimalEnum.WOLF, Color.rgb(255, 255, 255));    
        animalColors.put(AnimalEnum.JELLYFISH, Color.rgb(255, 153, 204)); 
        animalColors.put(AnimalEnum.SALMON, Color.rgb(0, 255, 0));       
        animalColors.put(AnimalEnum.CROCODILE, Color.RED);               
        animalColors.put(AnimalEnum.LION, Color.rgb(204, 204, 0));       
        animalColors.put(AnimalEnum.SHARK, Color.rgb(0, 0, 0));  
    }

    /**
     * Updates the count of a specific animal in the stats box.
     *
     * @param animal The animal type to update.
     * @param count  The increment to add to the current count.
     */
    public void updateStats(AnimalEnum animal, int birth, int death) {
        
        if (animalCounts.containsKey(animal)) {
            this.births += birth;
            this.deaths += death;
            app.updateBirthDeath();
            Color color = animalColors.get(animal);
            int oldValue = animalCounts.get(animal);
            int newValue = oldValue + birth - death;
            if (newValue < 0){ newValue = 0;}
            animalCounts.put(animal, newValue);
            
            if (newValue ==0 && oldValue>0){
                this.app.eventBox.addEvent(animal + " has gone extinct.");
                
            }
            Label label = animalLabels.get(animal);
            label.setTextFill(color);
            label.setText("● " + animal.name() + ": " + newValue);
            
        }
    }

    public VBox getStatsBox() {
        return statsBox;
    }
}
