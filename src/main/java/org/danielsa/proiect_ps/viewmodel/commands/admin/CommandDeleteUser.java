package org.danielsa.proiect_ps.viewmodel.commands.admin;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.viewmodel.AdminViewModel;

public class CommandDeleteUser implements Command {
    private final AdminViewModel viewModel;

    public CommandDeleteUser(AdminViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        boolean success = viewModel.getModel().deleteUser(viewModel.getSelectedUserProperty().getValue().getUserName());

        if (!success) {
            System.out.println("User not deleted!");
            return;
        }

        viewModel.getUserTableViewProperty().getValue().getItems().remove(viewModel.getUserTableViewProperty().getValue().getSelectionModel().getSelectedItem());
    }
}
