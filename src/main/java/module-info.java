module com.example.myfxapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.myfxapp to javafx.fxml;
    exports com.example.myfxapp;
}