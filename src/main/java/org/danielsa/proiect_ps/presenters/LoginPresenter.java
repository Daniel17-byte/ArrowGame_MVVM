package org.danielsa.proiect_ps.presenters;

import lombok.Getter;
import org.danielsa.proiect_ps.models.LoginModel;
import org.danielsa.proiect_ps.models.LoginModelInterface;
import org.danielsa.proiect_ps.models.LoginViewInterface;
import org.danielsa.proiect_ps.views.LoginView;

@Getter
public class LoginPresenter {
    private final LoginViewInterface view;
    private final LoginModelInterface model;

    public LoginPresenter(LoginView view) {
        this.view = view;
        model = new LoginModel(view.getDatabaseService());
    }
}
