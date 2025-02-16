package org.openjfx.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class InfoBox {
    private VBox infoBox;

    public InfoBox() {
        // EVENT LOG TITLE
        Label infoBoxTitle = new Label("Information");
        infoBoxTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

        infoBox = new VBox();
        infoBox.getChildren().add(infoBoxTitle);
        infoBox.setAlignment(Pos.TOP_CENTER);
    }

    public VBox getInfoBox() {
        return infoBox;
    }
}
