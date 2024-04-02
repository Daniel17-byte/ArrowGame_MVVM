package org.danielsa.proiect_ps.presenters;

import org.danielsa.proiect_ps.models.RegisterModelInterface;
import org.danielsa.proiect_ps.models.RegisterViewInterface;

public class RegisterPresenter {
    private final RegisterViewInterface view;
    private final RegisterModelInterface model;

    public RegisterPresenter(RegisterViewInterface view, RegisterModelInterface model) {
        this.view = view;
        this.model = model;
        initView();
    }

    private void initView() {
        view.setOnRegisterAttempt(this::handleRegisterAttempt);
    }

    private void handleRegisterAttempt(String username, String password, String usertype) {
        boolean authenticated = model.register(username, password, usertype);
        view.showRegisterResult(authenticated);
    }
}
