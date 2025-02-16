package org.openjfx.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class EventBox {
    private VBox eventBox;
    private TextArea eventLog;

    public EventBox() {
        // EVENT LOG TITLE
        Label eventLogTitle = new Label("Events");
        eventLogTitle.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        // EVENT LOG TEXT AREA
        eventLog = new TextArea();
        eventLog.setEditable(false);
        eventLog.setWrapText(true);
        eventLog.setStyle(
            "-fx-control-inner-background: #202020; " +
            "-fx-text-fill: white; " +
            "-fx-background-insets: 0; " +
            "-fx-background-color: #202020; " +
            "-fx-border-width: 0;"
        );
        eventLog.setPrefHeight(120);
        

        // EVENT LOG CONTAINER (Title + Log)
        eventBox = new VBox();
        eventBox.setPadding(new Insets(10, 10, 10, 10)); // Add padding inside
        eventBox.setAlignment(Pos.TOP_CENTER);

        eventBox.getChildren().addAll(eventLogTitle, eventLog);
        eventBox.setSpacing(5);
        eventBox.setPrefWidth(300);
        eventBox.setBackground(new Background(new BackgroundFill(
            Color.web("#151515"), null, Insets.EMPTY
        )));
        eventBox.setStyle("-fx-background-color: #202020; -fx-background-radius: 10px; -fx-border: 20px; -fx-border-color: black; -fx-border-radius: 10px;");
    }

    public VBox getEventBox() {
        return eventBox;
    }

    public void addEvent(String event) {
        eventLog.appendText(event + "\n");
    }
}
