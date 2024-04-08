package org.danielsa.proiect_ps.model;

import jakarta.inject.Inject;
import org.danielsa.proiect_ps.DatabaseService;

import java.util.ArrayList;

public class AdminModel implements AdminModelInterface{
    @Inject
    private final DatabaseService databaseService;

    public AdminModel(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public ArrayList<User> getUsers() {
        return databaseService.getUsers();
    }

    @Override
    public User updateUser(String username, String newUsername, String newPassword, String newUserType) {
        return databaseService.updateUser(username, newUsername, newPassword, newUserType);
    }

    @Override
    public boolean deleteUser(String username) {
        return databaseService.deleteUser(username);
    }

    @Override
    public User getUserByUsername(String username) {
        return databaseService.getUserByUsername(username);
    }

    @Override
    public boolean register(String username, String password, String usertype) {
        return databaseService.register(username, password, usertype);
    }
}
