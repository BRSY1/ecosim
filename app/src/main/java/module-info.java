module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    
    exports org.openjfx;
    opens org.openjfx to javafx.fxml;
}