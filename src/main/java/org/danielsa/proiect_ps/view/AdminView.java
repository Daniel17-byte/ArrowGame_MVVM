package org.danielsa.proiect_ps.view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.viewmodel.AdminViewModel;

import java.util.Arrays;

public class AdminView extends Scene {
    private final AdminViewModel viewModel;

    public AdminView() {
        super(new VBox(), 500, 500);
        this.viewModel = new AdminViewModel();
        initComponents();
    }

    public void initComponents() {
        TextField userNameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("ADMIN", "PLAYER");
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        TableView<UserModel> userTableView = new TableView<>();

        TableColumn<UserModel, String> userNameColumn = new TableColumn<>("Username");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<UserModel, String> userTypeColumn = new TableColumn<>("User Type");
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        TableColumn<UserModel, Integer> gamesWonColumn = new TableColumn<>("Games Won");
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        userTableView.getColumns().addAll(Arrays.asList(userNameColumn, userTypeColumn, gamesWonColumn));

        userTableView.getItems().addAll(viewModel.getUsers());

        viewModel.getUserTableViewProperty().set(userTableView);

        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> viewModel.getSelectedUserProperty().set(newVal));

        addButton.setOnAction(event -> viewModel.addUser());

        updateButton.setOnAction(event -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                viewModel.updateUser();
            }
        });

        deleteButton.setOnAction(event -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                viewModel.deleteUser();
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

        Bindings.bindBidirectional(userNameField.textProperty(), viewModel.getUsernameProperty());
        Bindings.bindBidirectional(passwordField.textProperty(), viewModel.getPasswordProperty());
        Bindings.bindBidirectional(userTypeComboBox.valueProperty(), viewModel.getUserTypeProperty());

        setRoot(root);
    }

}
