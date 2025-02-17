package org.openjfx.pages;

import io.github.palexdev.materialfx.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.openjfx.App;

public class SettingsPage {
    private final VBox settingsPanel;
    private final StackPane overlay;
    private final MFXSlider animalPopulationSlider;
    private final MFXToggleButton updateAnimalPopulationToggle;
    private final MFXSlider animalSpeedSlider;
    private final MFXToggleButton resetGameToggle;
    private final App app;

    public SettingsPage(StackPane rootPane, App app) {
        this.overlay = rootPane;
        this.app = app;
        
        this.animalPopulationSlider = new MFXSlider();
        this.animalPopulationSlider.setMin(0);
        this.animalPopulationSlider.setMax(1000);
        this.animalPopulationSlider.setValue(app.getAnimalSize());
        this.animalPopulationSlider.setPrefWidth(300);

        this.updateAnimalPopulationToggle = new MFXToggleButton("Update Animal Population");

        this.animalSpeedSlider = new MFXSlider();
        this.animalSpeedSlider.setMin(1);
        this.animalSpeedSlider.setMax(20);
        this.animalSpeedSlider.setValue(app.getMultiplier());
        this.animalSpeedSlider.setPrefWidth(200);

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
        panel.setStyle("-fx-background-color: #151515; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #151515; -fx-effect: dropshadow(gaussian,rgb(48, 48, 48), 70, 0.3, 0, 0);");
        panel.setMaxWidth(600);
        panel.setMaxHeight(400); // Increased height for new components
        panel.setAlignment(Pos.CENTER);

        // Title with white text
        Text title = new Text("Settings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        // Reset Game Checkbox
        updateAnimalPopulationToggle.setTextFill(Color.WHITE);
        resetGameToggle.setTextFill(Color.WHITE);

         // Add Animal Population Control
        Text animalPopulationText = new Text("Animal Population");
        animalPopulationText.setFill(Color.WHITE);

        Text animalSpeedText = new Text("Animal Speed");
        animalSpeedText.setFill(Color.WHITE);

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
            updateAnimalPopulationToggle,
            animalSpeedText,
            animalSpeedSlider,
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

        if (updateAnimalPopulationToggle.isSelected()) {
            app.updateAnimalPopulation(animalPopulationSlider.getValue());
            updateAnimalPopulationToggle.setSelected(false);
        }

        double newSpeed = animalSpeedSlider.getValue();
        app.updateGameSpeed(newSpeed);
        
        hideSettings();
    }

    public void setAnimalPopulationSlider(double value) {
        animalPopulationSlider.setValue(value);
    }

    public void showSettings() {
        overlay.getChildren().forEach(node -> node.setVisible(true));
        this.animalSpeedSlider.setValue(app.getMultiplier());
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