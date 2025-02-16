package org.openjfx.pages;

import io.github.palexdev.materialfx.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Node;

public class SettingsPage {
    private final VBox settingsPanel;
    private final StackPane overlay;
    private final MFXTextField playerNameField;
    private final MFXToggleButton darkModeToggle;
    private final MFXCheckbox enableSounds;
    private final MFXSlider volumeSlider;

    public SettingsPage(StackPane rootPane) {
        this.overlay = rootPane;
        
        this.playerNameField = new MFXTextField();
        this.darkModeToggle = new MFXToggleButton();
        this.enableSounds = new MFXCheckbox("Enable Sounds");
        this.volumeSlider = new MFXSlider();
        
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
        panel.setMaxHeight(450);
        panel.setAlignment(Pos.CENTER);

        // Title with white text
        Text title = new Text("Settings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        // Configure components with white text
        playerNameField.setPromptText("Enter your name");
        playerNameField.setPrefWidth(300);
        playerNameField.setStyle("-fx-text-fill: #151515; -fx-prompt-text-fill: #151515;");

        darkModeToggle.setText("Dark Mode");
        darkModeToggle.setTextFill(Color.WHITE);
        
        enableSounds.setTextFill(Color.WHITE);
        
        Text volumeText = new Text("Volume");
        volumeText.setFill(Color.WHITE);
        
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(50);
        volumeSlider.setPrefWidth(300);

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
            playerNameField,
            darkModeToggle,
            enableSounds,
            volumeText,
            volumeSlider,
            buttonContainer
        );
        
        return panel;
    }

    private void saveSettings() {
        String playerName = playerNameField.getText();
        boolean isDarkMode = darkModeToggle.isSelected();
        boolean soundsEnabled = enableSounds.isSelected();
        double volume = volumeSlider.getValue();
        
        System.out.println("Settings saved:");
        System.out.println("Player Name: " + playerName);
        System.out.println("Dark Mode: " + isDarkMode);
        System.out.println("Sounds Enabled: " + soundsEnabled);
        System.out.println("Volume: " + volume);
        
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