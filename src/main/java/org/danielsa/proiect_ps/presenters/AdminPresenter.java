package org.danielsa.proiect_ps.presenters;

import lombok.Getter;
import org.danielsa.proiect_ps.models.AdminModel;
import org.danielsa.proiect_ps.models.AdminModelInterface;
import org.danielsa.proiect_ps.views.AdminView;
import org.danielsa.proiect_ps.views.AdminViewInterface;

@Getter
public class AdminPresenter {
    private final AdminViewInterface view;
    private final AdminModelInterface model;

    public AdminPresenter(AdminView view) {
        this.view = view;
        this.model = new AdminModel(view.getDatabaseService());
    }
}
