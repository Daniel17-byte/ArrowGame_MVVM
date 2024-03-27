module org.danielsa.proiect_ps {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jakarta.annotation;
    requires jakarta.inject;
    requires org.slf4j;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires annotations;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens org.danielsa.proiect_ps to javafx.fxml;
    opens org.danielsa.proiect_ps.views to javafx.fxml;
    exports org.danielsa.proiect_ps;
    exports org.danielsa.proiect_ps.views;
    exports org.danielsa.proiect_ps.models;
    exports org.danielsa.proiect_ps.presenters;
    opens org.danielsa.proiect_ps.models to javafx.fxml;
}