package org.danielsa.proiect_ps.presenters;

import org.danielsa.proiect_ps.models.LoginModel;
import org.danielsa.proiect_ps.views.LoginView;

public class LoginPresenter {
    private final LoginView view;
    private final LoginModel model;

    public LoginPresenter(LoginView view, LoginModel model) {
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
