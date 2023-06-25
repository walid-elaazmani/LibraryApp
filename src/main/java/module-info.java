module be.intecbrussel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    exports be.intecbrussel.controllers;
    opens be.intecbrussel.controllers to javafx.fxml;
    opens be.intecbrussel.model;
    exports be.intecbrussel.Exceptions;
    opens be.intecbrussel.Exceptions to javafx.fxml;
}