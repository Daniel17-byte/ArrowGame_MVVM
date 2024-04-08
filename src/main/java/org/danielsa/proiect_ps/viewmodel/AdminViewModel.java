package org.danielsa.proiect_ps.viewmodel;

import lombok.Getter;
import org.danielsa.proiect_ps.model.AdminModel;
import org.danielsa.proiect_ps.model.AdminModelInterface;
import org.danielsa.proiect_ps.model.User;

import java.util.ArrayList;

@Getter
public class AdminViewModel {
    private final AdminModelInterface model;

    public AdminViewModel() {
        this.model = new AdminModel();
    }

    public void addUser(String username, String password, String userType) {
        boolean success = model.register(username, password, userType);
        if (!success) {
            System.out.println("User not added!");
        }
    }

    public void updateUser(User selectedUser, String newUsername, String newPassword, String newUserType) {
        User updatedUser = model.updateUser(selectedUser.getUserName(), newUsername, newPassword, newUserType);
        if (updatedUser == null) {
            System.out.println("User not updated!");
        }
    }

    public void deleteUser(User selectedUser) {
        boolean success = model.deleteUser(selectedUser.getUserName());
        if (!success) {
            System.out.println("User not deleted!");
        }
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = model.getUsers();

        if (users.isEmpty()){
            System.out.println("Couldn't load users!");
            return null;
        }

        return users;
    }

    public User getUserByUsername(String username) {
        User user = model.getUserByUsername(username);

        if (user == null) {
            System.out.println("User not found");
        }

        return user;
    }
}
