package org.danielsa.proiect_ps.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.model.AdminModel;
import org.danielsa.proiect_ps.model.AdminModelInterface;
import org.danielsa.proiect_ps.model.User;

import java.util.ArrayList;

public class AdminViewModel {
    private final AdminModelInterface model;
    @Getter
    private final StringProperty usernameProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty passwordProperty = new SimpleStringProperty();
    @Getter
    private final ObjectProperty<String> userTypeProperty = new SimpleObjectProperty<>();
    @Getter @Setter
    private ObjectProperty<User> selectedUserProperty = new SimpleObjectProperty<>();

    public AdminViewModel() {
        this.model = new AdminModel();
    }

    public void addUser() {
        boolean success = model.register(usernameProperty.getValue(), passwordProperty.getValue(), userTypeProperty.getValue());
        if (!success) {
            System.out.println("User not added!");
        }
    }

    public void updateUser() {
        User updatedUser = model.updateUser(selectedUserProperty.getValue().getUserName(), usernameProperty.getValue(), passwordProperty.getValue(), userTypeProperty.getValue());
        if (updatedUser == null) {
            System.out.println("User not updated!");
        }
    }

    public void deleteUser() {
        boolean success = model.deleteUser(selectedUserProperty.getValue().getUserName());
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

    public User getUserByUsername() {
        User user = model.getUserByUsername(usernameProperty.getValue());

        if (user == null) {
            System.out.println("User not found");
        }

        return user;
    }
}
