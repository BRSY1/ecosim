package org.openjfx.ui;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Arrays;

import org.openjfx.Animal;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class InfoBox {
    private VBox infoBox;
    private Label infoBoxLabel;
    private Label infoBoxDescription;
    private Label infoBoxPrey;
    private ImageView infoBoxImage;

    public InfoBox() {
        // EVENT LOG TITLE
        Label infoBoxTitle = new Label("Information");
        infoBoxTitle.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        infoBoxTitle.setPadding(new Insets(10,0,10,0));

        Rectangle clip = new Rectangle(300, 200);
        clip.setArcWidth(20);  // Controls corner roundness
        clip.setArcHeight(20);

        // Create a drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(40);        // Larger blur radius
        dropShadow.setSpread(0.1);       // Slight spread for density
        dropShadow.setOffsetX(10);       // Move shadow horizontally
        dropShadow.setOffsetY(10);       // Move shadow vertically
        dropShadow.setColor(Color.rgb(0, 0, 0, 1)); // Semi-transparent black
                
        infoBoxImage = new ImageView(new Image(getClass().getResource("/org/openjfx/ui/assets/blank.png").toExternalForm()));
        infoBoxImage.setFitWidth(300); // Set a fixed width
        infoBoxImage.setFitHeight(200); // Set a fixed height
        infoBoxImage.setPreserveRatio(false); // Allow full stretching
        infoBoxImage.setSmooth(true); // Enable smoothing
        infoBoxImage.setClip(clip);    
        infoBoxImage.setEffect(dropShadow);  
  

        infoBoxLabel = new Label("Click anywhere!");
        infoBoxLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        infoBoxLabel.setPadding(new Insets(10,0,0,0));

        infoBoxDescription = new Label("Information about biome/animal will appear here!");
        infoBoxDescription.setStyle("-fx-font-size: 14px; -fx-text-fill: #bbbbbb; -fx-font-weight: bold;");
        infoBoxDescription.setPadding(new Insets(0,10,0,10));
        infoBoxDescription.setTextAlignment(TextAlignment.CENTER);
        infoBoxDescription.setWrapText(true);
        
        infoBoxPrey = new Label("");
        infoBoxPrey.setStyle("-fx-font-size: 12px; -fx-text-fill: #bbbbbb; -fx-font-weight: bold;");
        infoBoxPrey.setPadding(new Insets(0,10,0,10));
        infoBoxPrey.setTextAlignment(TextAlignment.CENTER);
        infoBoxPrey.setWrapText(true);

        infoBox = new VBox();
        infoBox.getChildren().addAll(infoBoxTitle, infoBoxImage, infoBoxLabel, infoBoxDescription);
        infoBox.setAlignment(Pos.TOP_CENTER);
        infoBox.setStyle("-fx-background-color: #202020; -fx-background-radius: 10px; -fx-border: 20px; -fx-border-color: black; -fx-border-radius: 10px;");
    }
    public VBox getInfoBox() {
        return infoBox;
    }

    public void displayBiome(BiomeInfo biome) {
      Platform.runLater(() -> {
          infoBoxLabel.setText(biome.getName());
          infoBoxDescription.setText(biome.getDescription());
  
          if (biome.getImage() != null) {
              infoBoxImage.setImage(biome.getImage());
          } else {
              System.out.println("Warning: Biome image is null!");
          }
      });
    }

      public void displayAnimal(AnimalInfo animal) {
        Platform.runLater(() -> {
            infoBoxLabel.setText(animal.getName());
            infoBoxDescription.setText(animal.getDescription());
            
            if (animal.getImage() != null) {
                infoBoxImage.setImage(animal.getImage());
            } else {
                System.out.println("Warning: animal image is null!");
            }
        });
      }
  }
  




