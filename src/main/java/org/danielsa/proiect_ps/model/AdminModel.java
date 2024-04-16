package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.Main;

import java.util.ArrayList;

public class AdminModel implements AdminModelInterface{
    private final DatabaseService databaseService;

    public AdminModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    @Override
    public ArrayList<UserModel> getUsers() {
        return databaseService.getUsers();
    }

    @Override
    public UserModel updateUser(String username, String newUsername, String newPassword, String newUserType) {
        return databaseService.updateUser(username, newUsername, newPassword, newUserType);
    }

    @Override
    public boolean deleteUser(String username) {
        return databaseService.deleteUser(username);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return databaseService.getUserByUsername(username);
    }

    @Override
    public boolean register(String username, String password, String usertype) {
        return databaseService.register(username, password, usertype);
    }
}
