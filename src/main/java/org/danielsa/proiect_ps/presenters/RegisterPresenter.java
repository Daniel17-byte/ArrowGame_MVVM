package org.danielsa.proiect_ps.presenters;

import org.danielsa.proiect_ps.models.RegisterModel;
import org.danielsa.proiect_ps.views.RegisterView;

public class RegisterPresenter {
    private final RegisterView view;
    private final RegisterModel model;

    public RegisterPresenter(RegisterView view, RegisterModel model) {
        this.view = view;
        this.model = model;
        initView();
    }

    private void initView() {
        view.setOnRegister(this::handleRegisterAttempt);
    }

    private void handleRegisterAttempt(String username, String password, String usertype) {
        boolean authenticated = model.register(username, password, usertype);
        view.showRegisterResult(authenticated);
    }
}
