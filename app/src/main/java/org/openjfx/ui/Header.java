package org.openjfx.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class Header {
    public static VBox createHeader() {
      
        VBox header = new VBox();
        header.setPrefHeight(80);
        header.setAlignment(Pos.CENTER);

        // Create a gradient fill for the text
        Paint gradient = new LinearGradient(
            0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#66ff66")),  // Bright green
            new Stop(0.5, Color.web("#2E7D32")), // Medium dark green
            new Stop(1, Color.web("#004d00"))   // Deep dark green
        );

        Label title = new Label("🌿 Ecosim 🌿");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(gradient);

        Label subTitle = new Label("made by TBD4");
        subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        subTitle.setTextFill(Color.WHITE);

        header.getChildren().addAll(title, subTitle);
        return header;
    }
}
