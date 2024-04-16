package org.danielsa.proiect_ps.viewmodel.commands.admin;

import org.danielsa.proiect_ps.model.User;
import org.danielsa.proiect_ps.viewmodel.AdminViewModel;

import java.util.ArrayList;

public class CommandGetUsers {
    private final AdminViewModel viewModel;

    public CommandGetUsers(AdminViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public ArrayList<User> execute() {
        ArrayList<User> users = viewModel.getModel().getUsers();

        if (users.isEmpty()){
            System.out.println("Couldn't load users!");
            return null;
        }

        return users;
    }
}
