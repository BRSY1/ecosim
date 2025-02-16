package org.openjfx.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Header {
    public static VBox createHeader() {
        VBox header = new VBox();
        header.setPrefHeight(80);
        header.setAlignment(Pos.CENTER);

        Label title = new Label("Ecosim");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);

        header.getChildren().add(title);
        return header;
    }
}
