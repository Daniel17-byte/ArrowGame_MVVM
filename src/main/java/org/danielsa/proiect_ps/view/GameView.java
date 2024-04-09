package org.danielsa.proiect_ps.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.danielsa.proiect_ps.model.UserType;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class GameView extends Scene implements GameViewInterface {
    private final GameViewModel viewModel;

    public GameView() {
        super(new VBox(), 900, 500);
        viewModel = new GameViewModel();
        initComponents();
    }

    private void initComponents() {
        VBox topPane;
        VBox leftPane;
        VBox rightPane;
        VBox bottomPane;

        HashMap<String, Button> buttons = new LinkedHashMap<>();
        TextField gamesWonText = new TextField();
        ChoiceBox<String> levelSelectChoiceBox  = new ChoiceBox<>();
        TextArea usersPane = new TextArea();
        BorderPane borderPane = new BorderPane();

        buttons.put("n", new Button("N"));
        buttons.put("nE", new Button("NE"));
        buttons.put("e", new Button("E"));
        buttons.put("sE", new Button("SE"));
        buttons.put("s", new Button("S"));
        buttons.put("sW", new Button("SW"));
        buttons.put("w", new Button("W"));
        buttons.put("nW", new Button("NW"));

        viewModel.setBoard(new GridPane());
        viewModel.setSmallBoard(viewModel.initBoard("small", gamesWonText, usersPane, borderPane, levelSelectChoiceBox));
        viewModel.setLargeBoard(viewModel.initBoard("large", gamesWonText, usersPane, borderPane, levelSelectChoiceBox));

        VBox root = (VBox) getRoot();

        topPane = createTopPanel();
        borderPane.setTop(topPane);

        leftPane = createLeftPane(buttons, levelSelectChoiceBox, borderPane, gamesWonText, usersPane);
        borderPane.setLeft(leftPane);

        rightPane = createRightPane(gamesWonText, usersPane);
        borderPane.setRight(rightPane);

        createCenterPane(borderPane);

        bottomPane = createBottomPane(buttons);
        borderPane.setBottom(bottomPane);

        root.getChildren().add(borderPane);
    }

    private VBox createTopPanel() {
        VBox vBox = new VBox();
        Label greetingLabel = new Label("Hi " + viewModel.getUser().getUserName().toUpperCase());
        vBox.setStyle(("-fx-background-color: #60c760;"));

        vBox.getChildren().add(greetingLabel);

        return vBox;
    }

    private VBox createLeftPane(HashMap<String, Button> buttons, ChoiceBox<String> levelSelectChoiceBox, BorderPane borderPane, TextField gamesWonText, TextArea usersPane) {
        Button startGameButton = new Button("Start Game");
        Button restartButton = new Button("Restart Game");
        Button undoButton = new Button("Undo");

        VBox leftPanel = new VBox();

        AnchorPane leftPane = new AnchorPane();
        leftPane.setPrefSize(200, 500);
        leftPane.setStyle("-fx-background-color: grey;");

        startGameButton.setOnAction(e -> viewModel.clickedStartGame(borderPane, buttons, levelSelectChoiceBox, gamesWonText, usersPane));
        AnchorPane.setTopAnchor(startGameButton, 113.0);
        AnchorPane.setLeftAnchor(startGameButton, 22.0);

        restartButton.setOnAction(e -> viewModel.clearBoard(borderPane, levelSelectChoiceBox, gamesWonText, usersPane));
        AnchorPane.setTopAnchor(restartButton, 230.0);
        AnchorPane.setLeftAnchor(restartButton, 22.0);

        levelSelectChoiceBox.getItems().addAll("4x4", "8x8");
        levelSelectChoiceBox.setValue("8x8");
        AnchorPane.setTopAnchor(levelSelectChoiceBox, 151.0);
        AnchorPane.setLeftAnchor(levelSelectChoiceBox, 68.0);

        Label boardLabel = new Label("Board:");
        AnchorPane.setTopAnchor(boardLabel, 153.0);
        AnchorPane.setLeftAnchor(boardLabel, 22.0);

        undoButton.setOnAction(e -> viewModel.clickedUndoButton());
        AnchorPane.setTopAnchor(undoButton, 191.0);
        AnchorPane.setLeftAnchor(undoButton, 22.0);

        leftPane.getChildren().addAll(startGameButton, restartButton, boardLabel, levelSelectChoiceBox, undoButton);

        BorderPane.setAlignment(leftPane, Pos.CENTER_LEFT);
        leftPanel.getChildren().add(leftPane);

        return leftPanel;
    }

    private VBox createRightPane(TextField gamesWonText, TextArea usersPane) {
        Button manageUsersButton = new Button("Manage Users");

        VBox rightPanel = new VBox();

        BorderPane rightPane = new BorderPane();
        rightPane.setPrefSize(200, 500);
        rightPane.setStyle("-fx-background-color: grey;");

        gamesWonText.setText("Games won : ");
        gamesWonText.setEditable(false);
        gamesWonText.setPrefSize(180, 30);
        BorderPane.setMargin(gamesWonText, new Insets(20, 0, 0, 10));
        rightPane.setTop(gamesWonText);

        viewModel.loadWonGames(gamesWonText, usersPane);

        if (viewModel.getUser().getUserType().equals(UserType.ADMIN)) {
            usersPane.setEditable(false);
            usersPane.setPrefSize(180, 300);
            BorderPane.setMargin(usersPane, new Insets(10, 0, 0, 10));
            rightPane.setCenter(usersPane);
            viewModel.loadUserList(usersPane);

            manageUsersButton.setOnAction(event -> viewModel.clickedManageUsersButton());
            rightPane.setBottom(manageUsersButton);
            BorderPane.setMargin(manageUsersButton, new Insets(10, 0, 0, 10));
        }

        rightPanel.getChildren().add(rightPane);
        VBox.setMargin(rightPane, new Insets(0, 0, 0, 0));
        BorderPane.setAlignment(rightPane, Pos.CENTER_RIGHT);

        return rightPanel;
    }

    private VBox createBottomPane(HashMap<String, Button> buttons) {
        VBox bottomPanel = new VBox();

        HBox buttonRow = new HBox();
        buttonRow.setSpacing(2);

        buttons.forEach((key, value) -> {
            viewModel.initializeButton(value);
            buttonRow.getChildren().add(value);
        });

        AnchorPane bottomPane = new AnchorPane();
        bottomPane.setStyle("-fx-background-color: grey;");
        bottomPane.getChildren().add(buttonRow);
        AnchorPane.setBottomAnchor(buttonRow, 0.0);
        AnchorPane.setLeftAnchor(buttonRow, 250.0);
        AnchorPane.setRightAnchor(buttonRow, 100.0);

        bottomPanel.getChildren().add(bottomPane);

        bottomPanel.setAlignment(Pos.CENTER);

        return bottomPanel;
    }

    private void createCenterPane(BorderPane borderPane) {
        VBox centerPanel = new VBox();
        AnchorPane centerPane = new AnchorPane();
        centerPane.setPrefSize(500, 500);
        centerPanel.getChildren().add(centerPane);

        viewModel.getBoard().setVisible(true);
        viewModel.setBoard(viewModel.getLargeBoard());
        borderPane.setCenter(viewModel.getBoard());
        borderPane.getCenter().setStyle("-fx-background-color: #9db98a;");
        borderPane.setVisible(true);
    }

}
