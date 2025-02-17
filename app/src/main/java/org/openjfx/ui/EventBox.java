package org.openjfx.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import java.util.HashMap;
import org.openjfx.ui.AnimalEnum;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class EventBox {
    private VBox eventBox;
    private TextArea eventLog;
    private HashMap<AnimalEnum, Color> animalColors;

    public EventBox() {
        // EVENT LOG TITLE
        Label eventLogTitle = new Label("Events");
        eventLogTitle.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        eventLogTitle.setPadding(new Insets(5,0,0,0));
        // EVENT LOG TEXT AREA
        eventLog = new TextArea();
        eventLog.setEditable(false);
        eventLog.setWrapText(true);
        eventLog.setStyle(
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
            "-fx-padding: 0px; " +                      // Inner padding
            "-fx-font-size: 12px; " +                    // Readable font size
            "-fx-overflow-x: hidden; " +                 // Hide horizontal scroll
            "-fx-overflow-y: hidden; " +                 // Hide vertical scroll
            "-fx-cursor: text; " +                       // Proper cursor for text
            "-fx-focus-color: transparent; " +           // No blue outline when focused
            "-fx-faint-focus-color: transparent;"        // Remove faint glow effect
        );


        Platform.runLater(() -> {
            eventLog.lookup(".scroll-bar").setStyle("-fx-opacity: 0; -fx-min-width: 0; -fx-min-height: 0;");
        });

        eventLog.setPrefHeight(200);
        eventLog.setWrapText(true); // Enables text wrapping

        

        // EVENT LOG CONTAINER (Title + Log)
        eventBox = new VBox();
        eventBox.setPadding(new Insets(10, 10, 10, 10)); // Add padding inside
        eventBox.setAlignment(Pos.TOP_CENTER);

        eventBox.getChildren().add(eventLog);
        eventBox.setSpacing(5);
        eventBox.setPrefWidth(300);
        eventBox.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), null, Insets.EMPTY
        )));
        eventBox.setStyle("-fx-background-color: #202020; -fx-background-radius: 10px; -fx-border: 20px; -fx-border-color: black; -fx-border-radius: 10px;");
        initialiseAnimalText();
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

    public VBox getEventBox() {
        return eventBox;
    }

    public void addEvent(String event) {
        eventLog.appendText(event + "\n");

    }
}
