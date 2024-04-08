package org.danielsa.proiect_ps.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.model.UserType;
import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.util.HashMap;

@Getter
public class GameView extends Scene implements GameViewInterface {
    private final BorderPane borderPane = new BorderPane();
    private final ChoiceBox<String> levelSelectChoiceBox  = new ChoiceBox<>();
    @Setter
    private GridPane board = new GridPane();
    @Setter
    private GridPane largeBoard = new GridPane();
    @Setter
    private GridPane smallBoard = new GridPane();
    private final Button northButton = new Button();
    private final Button southButton = new Button();
    private final Button eastButton = new Button();
    private final Button westButton = new Button();
    private final Button northEastButton = new Button();
    private final Button southEastButton = new Button();
    private final Button southWestButton = new Button();
    private final Button northWestButton = new Button();
    @Setter
    private String selectedDirection;
    private final TextField gamesWonText = new TextField();
    private final TextArea usersPane = new TextArea();

    private final GameViewModel presenter;
    @Getter
    private final DatabaseService databaseService;

    public GameView(DatabaseService databaseService) {
        super(new VBox(), 900, 500);
        this.databaseService = databaseService;
        presenter = new GameViewModel(this);
        initComponents();
    }

    private void initComponents() {
        VBox topPane;
        VBox leftPane;
        VBox rightPane;
        VBox bottomPane;

        setSmallBoard(presenter.initBoard("small", gamesWonText, usersPane, borderPane));
        setLargeBoard(presenter.initBoard("large", gamesWonText, usersPane, borderPane));

        VBox root = (VBox) getRoot();

        topPane = createTopPanel();
        borderPane.setTop(topPane);

        leftPane = createLeftPane();
        borderPane.setLeft(leftPane);

        rightPane = createRightPane();
        borderPane.setRight(rightPane);

        createCenterPane();

        bottomPane = createBottomPane();
        borderPane.setBottom(bottomPane);

        root.getChildren().add(borderPane);
    }

    private VBox createTopPanel() {
        VBox vBox = new VBox();
        Label greetingLabel = new Label("Hi " + getDatabaseService().getUser().getUserName().toUpperCase());
        vBox.setStyle(("-fx-background-color: #60c760;"));

        vBox.getChildren().add(greetingLabel);

        return vBox;
    }

    private VBox createLeftPane() {
        Button startGameButton = new Button("Start Game");
        Button restartButton = new Button("Restart Game");
        Button undoButton = new Button("Undo");

        VBox leftPanel = new VBox();

        AnchorPane leftPane = new AnchorPane();
        leftPane.setPrefSize(200, 500);
        leftPane.setStyle("-fx-background-color: grey;");

        HashMap<String, Button> buttons = new HashMap<>();
        buttons.put("northEastButton", northEastButton);
        buttons.put("northWestButton", northWestButton);
        buttons.put("southEastButton", southEastButton);
        buttons.put("southWestButton", southWestButton);

        startGameButton.setOnAction(e -> presenter.clickedStartGame(borderPane, buttons));
        AnchorPane.setTopAnchor(startGameButton, 113.0);
        AnchorPane.setLeftAnchor(startGameButton, 22.0);

        restartButton.setOnAction(e -> presenter.clearBoard(borderPane));
        AnchorPane.setTopAnchor(restartButton, 230.0);
        AnchorPane.setLeftAnchor(restartButton, 22.0);

        getLevelSelectChoiceBox().getItems().addAll("4x4", "8x8");
        getLevelSelectChoiceBox().setValue("8x8");
        AnchorPane.setTopAnchor(getLevelSelectChoiceBox(), 151.0);
        AnchorPane.setLeftAnchor(getLevelSelectChoiceBox(), 68.0);

        Label boardLabel = new Label("Board:");
        AnchorPane.setTopAnchor(boardLabel, 153.0);
        AnchorPane.setLeftAnchor(boardLabel, 22.0);

        undoButton.setOnAction(e -> presenter.clickedUndoButton());
        AnchorPane.setTopAnchor(undoButton, 191.0);
        AnchorPane.setLeftAnchor(undoButton, 22.0);

        leftPane.getChildren().addAll(startGameButton, restartButton, boardLabel, getLevelSelectChoiceBox(), undoButton);

        BorderPane.setAlignment(leftPane, Pos.CENTER_LEFT);
        leftPanel.getChildren().add(leftPane);

        return leftPanel;
    }

    private VBox createRightPane() {
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

        presenter.loadWonGames(gamesWonText, usersPane);

        if (databaseService.getUser().getUserType().equals(UserType.ADMIN)) {
            usersPane.setEditable(false);
            usersPane.setPrefSize(180, 300);
            BorderPane.setMargin(usersPane, new Insets(10, 0, 0, 10));
            rightPane.setCenter(usersPane);
            presenter.loadUserList(usersPane);

            manageUsersButton.setOnAction(event -> presenter.clickedManageUsersButton());
            rightPane.setBottom(manageUsersButton);
            BorderPane.setMargin(manageUsersButton, new Insets(10, 0, 0, 10));
        }

        rightPanel.getChildren().add(rightPane);
        VBox.setMargin(rightPane, new Insets(0, 0, 0, 0));
        BorderPane.setAlignment(rightPane, Pos.CENTER_RIGHT);

        return rightPanel;
    }

    private VBox createBottomPane() {
        VBox bottomPanel = new VBox();

        presenter.initializeButton(getNorthButton(), "N", "N.png");
        presenter.initializeButton(getSouthButton(), "S", "S.png");
        presenter.initializeButton(getEastButton(), "E", "E.png");
        presenter.initializeButton(getWestButton(), "W", "W.png");
        presenter.initializeButton(getNorthEastButton(), "NE", "NE.png");
        presenter.initializeButton(getNorthWestButton(), "NW", "NW.png");
        presenter.initializeButton(getSouthEastButton(), "SE", "SE.png");
        presenter.initializeButton(getSouthWestButton(), "SW", "SW.png");

        HBox buttonRow = new HBox();
        buttonRow.setSpacing(2);
        buttonRow.getChildren().addAll(getNorthButton(), getNorthEastButton(), getEastButton(), getSouthEastButton(), getSouthButton(), getSouthWestButton(), getWestButton(), getNorthWestButton());

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

    private void createCenterPane() {
        VBox centerPanel = new VBox();
        AnchorPane centerPane = new AnchorPane();
        centerPane.setPrefSize(500, 500);
        centerPanel.getChildren().add(centerPane);

        getBoard().setVisible(true);
        setBoard(largeBoard);
        borderPane.setCenter(getBoard());
        borderPane.getCenter().setStyle("-fx-background-color: #9db98a;");
        borderPane.setVisible(true);
    }

}
