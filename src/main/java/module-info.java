module com.example.demo2 {

    requires javafx.fxml;


    requires org.kordamp.bootstrapfx.core;
    requires javafx.controls;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}