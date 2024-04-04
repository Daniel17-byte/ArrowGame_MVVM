package org.danielsa.proiect_ps.views;

import javafx.animation.ScaleTransition;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.models.GameViewInterface;
import org.danielsa.proiect_ps.models.User;
import org.danielsa.proiect_ps.models.UserType;
import org.danielsa.proiect_ps.presenters.DatabaseService;
import org.danielsa.proiect_ps.presenters.GamePresenter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

@Getter
public class GameView extends Scene implements GameViewInterface {
    private final BorderPane borderPane = new BorderPane();
    private final ChoiceBox<String> levelSelectChoiceBox  = new ChoiceBox<>();
    @Setter
    private GridPane board = new GridPane();
    private GridPane largeBoard = new GridPane();
    private GridPane smallBoard = new GridPane();
    private VBox topPane;
    private VBox leftPane;
    private VBox rightPane;
    private VBox centerPane;
    private VBox bottomPane;
    private final Button startGameButton = new Button("Start Game");
    private final Button restartButton = new Button("Restart Game");
    private final Button undoButton = new Button("Undo");
    private final Button northButton = new Button();
    private final Button southButton = new Button();
    private final Button eastButton = new Button();
    private final Button westButton = new Button();
    private final Button northEastButton = new Button();
    private final Button southEastButton = new Button();
    private final Button southWestButton = new Button();
    private final Button northWestButton = new Button();
    protected ImageView selectedImage;
    private String selectedDirection;
    private final TextField gamesWonText = new TextField();
    private final TextArea usersPane = new TextArea();

    private final GamePresenter presenter;
    @Getter
    private final DatabaseService databaseService;

    public GameView(DatabaseService databaseService) {
        super(new VBox(), 900, 500);
        this.databaseService = databaseService;
        presenter = new GamePresenter(this);
        smallBoard = initBoard("small");
        largeBoard = initBoard("large");

        initComponents();
    }

    private void initComponents() {
        VBox root = (VBox) getRoot();

        topPane = createTopPanel();
        borderPane.setTop(topPane);

        leftPane = createLeftPane();
        borderPane.setLeft(leftPane);

        rightPane = createRightPane();
        borderPane.setRight(rightPane);

        centerPane = createCenterPane();

        bottomPane = createBottomPane();
        borderPane.setBottom(bottomPane);

        root.getChildren().add(borderPane);
    }

    private GridPane initBoard(String sizeS) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(500, 500);

        int size = 4;

        if (sizeS.equals("large")){
            size = 8;
        }

        for (int i = 0; i < size; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / size);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < size; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / size);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageView imageView = new ImageView(new File("/Users/daniellungu/Desktop/PROIECT_PS/src/main/resources/images/img.png").toURI().toString());
                imageView.setOnMouseClicked(this::clickedBoard);
                imageView.setFitWidth(41.0);
                imageView.setFitHeight(38.0);
                GridPane.setMargin(imageView, new Insets(2));
                gridPane.add(imageView, j, i);
            }
        }

        return gridPane;
    }

    private VBox createTopPanel() {
        return new VBox();
    }

    private VBox createLeftPane() {
        VBox leftPanel = new VBox();

        AnchorPane leftPane = new AnchorPane();
        leftPane.setPrefSize(200, 500);
        leftPane.setStyle("-fx-background-color: grey;");

        startGameButton.setOnMouseClicked(this::clickedStartGame);
        AnchorPane.setTopAnchor(startGameButton, 113.0);
        AnchorPane.setLeftAnchor(startGameButton, 22.0);

        restartButton.setOnMouseClicked(this::clickedRestartButton);
        AnchorPane.setTopAnchor(restartButton, 230.0);
        AnchorPane.setLeftAnchor(restartButton, 22.0);

        levelSelectChoiceBox.getItems().addAll("4x4", "8x8");
        levelSelectChoiceBox.setValue("8x8");
        AnchorPane.setTopAnchor(levelSelectChoiceBox, 151.0);
        AnchorPane.setLeftAnchor(levelSelectChoiceBox, 68.0);

        Label boardLabel = new Label("Board:");
        AnchorPane.setTopAnchor(boardLabel, 153.0);
        AnchorPane.setLeftAnchor(boardLabel, 22.0);

        undoButton.setOnMouseClicked(this::clickedUndoButton);
        AnchorPane.setTopAnchor(undoButton, 191.0);
        AnchorPane.setLeftAnchor(undoButton, 22.0);

        leftPane.getChildren().addAll(startGameButton, restartButton, levelSelectChoiceBox, boardLabel, undoButton);

        BorderPane.setAlignment(leftPane, Pos.CENTER_LEFT);
        leftPanel.getChildren().add(leftPane);

        return leftPanel;
    }

    private VBox createRightPane() {
        VBox rightPanel = new VBox();

        AnchorPane rightPane = new AnchorPane();
        rightPane.setPrefSize(200, 500);
        rightPane.setStyle("-fx-background-color: grey;");

        gamesWonText.setText("Games won : ");
        gamesWonText.setEditable(false);
        gamesWonText.setPrefSize(180, 30);
        AnchorPane.setTopAnchor(gamesWonText, 20.0);
        AnchorPane.setLeftAnchor(gamesWonText, 10.0);

        rightPane.getChildren().addAll(gamesWonText);

        loadWonGames();

        if (databaseService.getUser().getUserType().equals(UserType.ADMIN)) {
            usersPane.setEditable(false);
            usersPane.setPrefSize(180, 300);
            AnchorPane.setTopAnchor(usersPane, 60.0);
            AnchorPane.setLeftAnchor(usersPane, 10.0);
            rightPane.getChildren().addAll(usersPane);
            loadUserList();
        }

        rightPanel.getChildren().add(rightPane);
        VBox.setMargin(rightPane, new Insets(0, 0, 0, 10));
        BorderPane.setAlignment(rightPane, Pos.CENTER_RIGHT);

        return rightPanel;
    }

    private VBox createBottomPane() {
        VBox bottomPanel = new VBox();

        initializeButton(getNorthButton(), "N", "N.png");
        initializeButton(getSouthButton(), "S", "S.png");
        initializeButton(getEastButton(), "E", "E.png");
        initializeButton(getWestButton(), "W", "W.png");
        initializeButton(getNorthEastButton(), "NE", "NE.png");
        initializeButton(getNorthWestButton(), "NW", "NW.png");
        initializeButton(getSouthEastButton(), "SE", "SE.png");
        initializeButton(getSouthWestButton(), "SW", "SW.png");

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

    private VBox createCenterPane() {
        VBox centerPanel = new VBox();
        AnchorPane centerPane = new AnchorPane();
        centerPane.setPrefSize(500, 500);
        centerPanel.getChildren().add(centerPane);

        getBoard().setVisible(true);
        setBoard(largeBoard);
        borderPane.setCenter(board);
        borderPane.setVisible(true);

        return centerPanel;
    }

    void initializeButton(Button button, String text, String photo){
        button.setText(text);
        button.setBackground(setBgImage(photo));
        button.setVisible(true);
        button.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        button.setLayoutX(113.0);
        button.setLayoutY(13.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(50.0);
        button.setPrefWidth(50.0);
        button.setPrefWidth(50.0);
        button.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null, null)));

        button.setOnMouseClicked(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0.8);
            scaleTransition.setToY(0.8);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(2);

            clickedArrowButton(event);

            scaleTransition.play();
        });
    }

    public void loadWonGames() {
        User user = databaseService.getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            System.out.println(gamesWon);
            gamesWonText.setText("Games won : " + gamesWon);
            usersPane.setVisible(false);
        }else {
            gamesWonText.setText("Games won : " + gamesWon);
        }
    }

    public void clickedBoard(MouseEvent mouseEvent) {
        EventTarget source = mouseEvent.getTarget();
        if(!(source instanceof ImageView)){
          return;
        }
        selectedImage = (ImageView)source;
        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);
        presenter.userRegisterMove(selectedDirection, row, col);
    }

    public void clickedArrowButton(@NotNull MouseEvent mouseEvent) {
        Button button = (Button)mouseEvent.getSource();
        selectedDirection = button.getText();
    }

    @Override
    public void clickedStartGame(MouseEvent mouseEvent) {
        clearBoard();
        presenter.setPlayerColor("g");

        String selectedBoard = levelSelectChoiceBox.getValue();
        if(selectedBoard.equals("4x4")) {
            getBoard().setVisible(true);
            borderPane.setVisible(true);
            setBoard(smallBoard);
            borderPane.setCenter(board);
            presenter.changeLevel(4);
            adjustInterButtons(false);
        } else {
            getBoard().setVisible(true);
            borderPane.setVisible(true);
            setBoard(largeBoard);
            borderPane.setCenter(board);
            borderPane.setVisible(true);
            presenter.changeLevel(8);
            adjustInterButtons(true);
        }

    }

    private void adjustInterButtons(boolean isVisible) {
        northEastButton.setVisible(isVisible);
        northWestButton.setVisible(isVisible);
        southEastButton.setVisible(isVisible);
        southWestButton.setVisible(isVisible);
    }

    @Override
    public void clickedRestartButton(MouseEvent mouseEvent) {
        clearBoard();
    }

    @Override
    public void placeArrow(String color, String direction, int row, int column) {
        String imageName = "/Users/daniellungu/Desktop/PROIECT_PS/src/main/resources/images/" + color + direction + ".png";
        File img = new File(imageName);
        Image image = new Image(img.toURI().toString());

        board.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(image));
    }

    @Override
    public void removeArrow(int row, int column) {
        board.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(new Image(new File("/Users/daniellungu/Desktop/PROIECT_PS/src/main/resources/images/img.png").toURI().toString())));
    }

    @Override
    public void signalEndgame(String winner) {
        final Stage dialog = new Stage();
        dialog.setTitle("Game end.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        if(winner.equals("User")){
            winner = databaseService.getUser().getUserName();
        }
        dialogVbox.getChildren().add(new Text(winner.toUpperCase() + " wins!"));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(mouseEvent -> {
            dialog.close(); clearBoard();
        });
        dialog.setScene(dialogScene);
        dialog.show();
        databaseService.updateUserScore();
        loadWonGames();
    }

    public void signalInvalidMove() {
        final Stage dialog = new Stage();
        dialog.setTitle("Error.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Invalid move!"));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(mouseEvent -> dialog.close());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void clearBoard(){
        if(board != null){
            presenter.clearBoard();
            board.getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .map(node -> (ImageView) node)
                    .forEach(imageView -> imageView.setImage(new Image(new File("/Users/daniellungu/Desktop/PROIECT_PS/src/main/resources/images/img.png").toURI().toString())));
        }
        if(levelSelectChoiceBox.getValue().equals("4x4")){
            setBoard(smallBoard);
            borderPane.setCenter(board);
        }else {
            setBoard(largeBoard);
            borderPane.setCenter(board);
        }
    }


    @Override
    public void clickedUndoButton(MouseEvent mouseEvent) {
        presenter.undoMove();
    }

    private Background setBgImage(String name){
        BackgroundImage b = new BackgroundImage(new Image(new File("/Users/daniellungu/Desktop/PROIECT_PS/src/main/resources/images/" + "g" + name).toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        return new Background(b);
    }

    @Override
    public void loadUserList() {
        ArrayList<User> users = databaseService.getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        usersPane.setText(stringBuilder.toString());
    }

}
