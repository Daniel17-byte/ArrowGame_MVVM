package org.danielsa.proiect_ps.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.danielsa.proiect_ps.model.User;
import org.danielsa.proiect_ps.viewmodel.AdminViewModel;

import java.util.Arrays;

public class AdminView extends Scene implements AdminViewInterface {
    private final AdminViewModel viewModel;

    public AdminView() {
        super(new VBox(), 500, 500);
        this.viewModel = new AdminViewModel();
        initComponents();
    }

    @Override
    public void initComponents() {
        TextField userNameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("ADMIN", "PLAYER");
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        TableView<User> userTableView = new TableView<>();

        TableColumn<User, String> userNameColumn = new TableColumn<>("Username");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<User, String> userTypeColumn = new TableColumn<>("User Type");
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        TableColumn<User, Integer> gamesWonColumn = new TableColumn<>("Games Won");
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        userTableView.getColumns().addAll(Arrays.asList(userNameColumn, userTypeColumn, gamesWonColumn));

        userTableView.getItems().addAll(viewModel.getUsers());

        addButton.setOnAction(event -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            String userType = userTypeComboBox.getValue();
            viewModel.addUser(username, password, userType);
            userTableView.getItems().addAll(viewModel.getUserByUsername(username));
        });

        updateButton.setOnAction(event -> {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                String newUsername = userNameField.getText();
                String newPassword = passwordField.getText();
                String newUserType = userTypeComboBox.getValue();
                viewModel.updateUser(selectedUser, newUsername, newPassword, newUserType);
                userTableView.getItems().clear();
                userTableView.getItems().addAll(viewModel.getUsers());
            }
        });

        deleteButton.setOnAction(event -> {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                viewModel.deleteUser(selectedUser);
                userTableView.getItems().remove(selectedUser);
            }
        });

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                new HBox(new Label("Username:"), userNameField),
                new HBox(new Label("Password:"), passwordField),
                new HBox(new Label("User Type:"), userTypeComboBox),
                new HBox(addButton, updateButton, deleteButton),
                userTableView
        );
        setRoot(root);
    }

}
