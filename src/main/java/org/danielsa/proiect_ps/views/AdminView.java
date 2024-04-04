package org.danielsa.proiect_ps.views;

import jakarta.inject.Inject;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.presenters.AdminPresenter;

@Getter
public class AdminView extends Scene implements AdminViewInterface {
    private final AdminPresenter presenter;
    @Inject
    private final DatabaseService databaseService;

    public AdminView(DatabaseService databaseService) {
        super(new VBox(), 500, 500);
        this.databaseService = databaseService;
        this.presenter = new AdminPresenter(this);
        initComponents();
    }

    private void initComponents() {
    }
}
