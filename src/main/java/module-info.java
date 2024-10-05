module org.example.managementandstorebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires java.sql;

    opens org.example.managementandstorebook to javafx.fxml;
    opens org.example.managementandstorebook.controllers to javafx.fxml;
    opens org.example.managementandstorebook.models to javafx.base;

    exports org.example.managementandstorebook;
    exports org.example.managementandstorebook.controllers;
}
