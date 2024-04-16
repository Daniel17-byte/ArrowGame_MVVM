package org.danielsa.proiect_ps.viewmodel.commands.admin;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.viewmodel.AdminViewModel;

public class CommandUpdateUser implements Command {
    private final AdminViewModel viewModel;

    public CommandUpdateUser(AdminViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        UserModel updatedUser = viewModel.getModel().updateUser(viewModel.getSelectedUserProperty().getValue().getUserName(), viewModel.getUsernameProperty().getValue(), viewModel.getPasswordProperty().getValue(), viewModel.getUserTypeProperty().getValue());

        if (updatedUser == null) {
            System.out.println("User not updated!");
            return;
        }

        viewModel.getUserTableViewProperty().getValue().getItems().clear();
        viewModel.getUserTableViewProperty().getValue().getItems().addAll(viewModel.getModel().getUsers());
    }
}
