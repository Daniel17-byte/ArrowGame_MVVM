module org.danielsa.proiect_ps {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jakarta.annotation;
    requires jakarta.inject;
    requires jakarta.cdi;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires static lombok;
    requires annotations;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens org.danielsa.proiect_ps to javafx.fxml;
    opens org.danielsa.proiect_ps.view to javafx.fxml;
    exports org.danielsa.proiect_ps;
    exports org.danielsa.proiect_ps.view;
    exports org.danielsa.proiect_ps.model;
    exports org.danielsa.proiect_ps.viewmodel;
    opens org.danielsa.proiect_ps.model to javafx.fxml;
    exports org.danielsa.proiect_ps.viewmodel.commands.admin;
}