package org.danielsa.proiect_ps.presenters;

import org.danielsa.proiect_ps.models.LoginModelInterface;
import org.danielsa.proiect_ps.models.LoginViewInterface;

public class LoginPresenter {
    private final LoginViewInterface view;
    private final LoginModelInterface model;

    public LoginPresenter(LoginViewInterface view, LoginModelInterface model) {
        this.view = view;
        this.model = model;
        initView();
    }

    private void initView() {
        view.setOnLoginAttempt(this::handleLoginAttempt);
    }

    private void handleLoginAttempt(String username, String password) {
        boolean authenticated = model.authenticate(username, password);
        view.showLoginResult(authenticated);
    }

}
