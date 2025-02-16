package org.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.openjfx.grid.GridView;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
    var javaVersion = SystemInfo.javaVersion();
    var javafxVersion = SystemInfo.javafxVersion();

    // Create main layout
    BorderPane root = new BorderPane();

    var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    // root.setTop(label);

    // Create GridView
    GridView gridView = new GridView();

    // Wrap grid in StackPane to center it
    StackPane centerPane = new StackPane(gridView.getGridPane());
    root.setCenter(centerPane);  // Center the StackPane inside BorderPane

    var scene = new Scene(root, 1000, 1000);
    stage.setScene(scene);
    stage.show();
}


    public static void main(String[] args) {
        launch();
    }

}