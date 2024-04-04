package org.danielsa.proiect_ps.presenters;

import lombok.Getter;
import org.danielsa.proiect_ps.models.RegisterModel;
import org.danielsa.proiect_ps.models.RegisterModelInterface;
import org.danielsa.proiect_ps.views.RegisterViewInterface;
import org.danielsa.proiect_ps.views.RegisterView;

@Getter
public class RegisterPresenter {
    private final RegisterViewInterface view;
    private final RegisterModelInterface model;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
        this.model = new RegisterModel(view.getDatabaseService());
    }

}
