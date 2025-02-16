package org.openjfx.grid;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class GridView {
    private GridPane gridPane;

    public GridView() {
        gridPane = new GridPane();
        initializeGrid();
    }

    private void initializeGrid() {
        int rows = 5;
        int cols = 5;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button cell = new Button("R" + row + "C" + col);
                cell.setMinSize(50, 50);
                gridPane.add(cell, col, row);
            }
        }

        gridPane.setHgap(5); // Set spacing
        gridPane.setVgap(5);
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}