package org.openjfx.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class InfoBox {
    private VBox infoBox;
    private Label infoBoxLabel;
    private Label infoBoxDescription;
    private ImageView infoBoxImage;

    public InfoBox() {
        // EVENT LOG TITLE
        Label infoBoxTitle = new Label("Information");
        infoBoxTitle.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        infoBoxTitle.setPadding(new Insets(10,0,0,0));

        // infoBoxImage = new ImageView("");

        infoBoxLabel = new Label();
        infoBoxLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        infoBoxLabel.setPadding(new Insets(10,0,0,0));

        infoBoxDescription = new Label();
        infoBoxLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");


        infoBox = new VBox();
        infoBox.getChildren().add(infoBoxTitle);
        infoBox.setAlignment(Pos.TOP_CENTER);
        infoBox.setStyle("-fx-background-color: #202020; -fx-background-radius: 10px; -fx-border: 20px; -fx-border-color: black; -fx-border-radius: 10px;");
    }
    public VBox getInfoBox() {
        return infoBox;
    }

    public void displayBiome(BiomeInfo biome) {
      infoBoxLabel.setText(biome.getName());
      infoBoxDescription.setText(biome.getDescription());
      // infoBoxImage.setImage(biome.getImage());
    }



}
