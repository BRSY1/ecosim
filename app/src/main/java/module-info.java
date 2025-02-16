module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires javafx.graphics;
    
    exports org.openjfx;
    opens org.openjfx to javafx.fxml;
}