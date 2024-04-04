package org.danielsa.proiect_ps.views;

import jakarta.inject.Inject;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.models.User;
import org.danielsa.proiect_ps.presenters.AdminPresenter;

import java.util.Arrays;

@Getter
public class AdminView extends Scene implements AdminViewInterface {
    private final AdminPresenter presenter;
    @Inject
    private final DatabaseService databaseService;

    public AdminView(DatabaseService databaseService) {
        super(new VBox(), 500, 500);
        this.databaseService = databaseService;
        this.presenter = new AdminPresenter(this);
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

        userTableView.getItems().addAll(presenter.getUsers());

        addButton.setOnAction(event -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            String userType = userTypeComboBox.getValue();
            presenter.addUser(username, password, userType);
            userTableView.getItems().addAll(presenter.getUserByUsername(username));
        });

        updateButton.setOnAction(event -> {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                String newUsername = userNameField.getText();
                String newPassword = passwordField.getText();
                String newUserType = userTypeComboBox.getValue();
                presenter.updateUser(selectedUser, newUsername, newPassword, newUserType);
                userTableView.getItems().clear();
                userTableView.getItems().addAll(presenter.getUsers());
            }
        });

        deleteButton.setOnAction(event -> {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                presenter.deleteUser(selectedUser);
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
