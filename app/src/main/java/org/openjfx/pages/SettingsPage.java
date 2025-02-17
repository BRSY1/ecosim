package org.openjfx.pages;

import io.github.palexdev.materialfx.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Node;
import org.openjfx.App;

public class SettingsPage {
    private final VBox settingsPanel;
    private final StackPane overlay;
    private final MFXSlider animalPopulationSlider;
    private final MFXToggleButton resetGameToggle;
    private final App app;

    public SettingsPage(StackPane rootPane, App app) {
        this.overlay = rootPane;
        this.app = app;
        
        this.animalPopulationSlider = new MFXSlider();
        this.animalPopulationSlider.setMin(0);
        this.animalPopulationSlider.setMax(1000);
        this.animalPopulationSlider.setValue(100);
        this.animalPopulationSlider.setPrefWidth(300);

        this.resetGameToggle = new MFXToggleButton("Reset Game");
        
        settingsPanel = createSettingsPanel();
        
        Region overlayBackground = new Region();
        overlayBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        overlayBackground.setVisible(false);
        
        overlay.getChildren().addAll(overlayBackground, settingsPanel);
        settingsPanel.setVisible(false);
        
        overlayBackground.setOnMouseClicked(e -> hideSettings());
    }

    private VBox createSettingsPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: #151515; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #151515;");
        panel.setPrefWidth(400);
        panel.setMaxHeight(600); // Increased height for new components
        panel.setAlignment(Pos.CENTER);

        // Title with white text
        Text title = new Text("Settings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        // Reset Game Checkbox
        resetGameToggle.setTextFill(Color.WHITE);

         // Add Animal Population Control
        Text animalPopulationText = new Text("Animal Population");
        animalPopulationText.setFill(Color.WHITE);

        // Buttons container
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        MFXButton saveButton = new MFXButton("Save");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> saveSettings());

        MFXButton closeButton = new MFXButton("Close");
        closeButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white;");
        closeButton.setOnAction(e -> hideSettings());

        buttonContainer.getChildren().addAll(saveButton, closeButton);

        panel.getChildren().addAll(
            title,
            animalPopulationText,
            animalPopulationSlider,
            resetGameToggle,
            buttonContainer
        );
        
        return panel;
    }

    private void saveSettings() {
        // Handle reset game if checkbox is selected
        if (resetGameToggle.isSelected()) {
            app.resetGame();
            resetGameToggle.setSelected(false);
        }

        double newPopulation = animalPopulationSlider.getValue();
        app.updateAnimalPopulation(newPopulation);
        
        hideSettings();
    }

    public void showSettings() {
        overlay.getChildren().forEach(node -> node.setVisible(true));
        settingsPanel.setVisible(true);
        settingsPanel.setManaged(true);
        StackPane.setAlignment(settingsPanel, Pos.CENTER);
    }

    public void hideSettings() {
        overlay.getChildren().forEach(node -> node.setVisible(false));
        settingsPanel.setVisible(false);
        settingsPanel.setManaged(false);
    }
}