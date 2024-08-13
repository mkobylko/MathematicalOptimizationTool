module com.project.mmdo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.mmdo to javafx.fxml;
    exports com.project.mmdo;
    exports com.project.mmdo.controllers;
    opens com.project.mmdo.controllers to javafx.fxml;
    exports com.project.mmdo.model;
    opens com.project.mmdo.model to javafx.fxml;
}