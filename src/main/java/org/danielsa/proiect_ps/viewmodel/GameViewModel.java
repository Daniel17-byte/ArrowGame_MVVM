package org.danielsa.proiect_ps.viewmodel;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.model.*;
import org.danielsa.proiect_ps.view.AdminView;
import org.danielsa.proiect_ps.view.AdminViewInterface;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class GameViewModel {
    private final GameModelInterface model;
    private GridPane board;
    private GridPane largeBoard;
    private GridPane smallBoard;
    private String selectedDirection;


    public GameViewModel() {
        model = new GameModel();
        model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
    }

    public void userRegisterMove(TextField gamesWonText, TextArea usersPane, int row, int column, BorderPane borderPane, ChoiceBox<String> levelSelectChoiceBox) {
        boolean valid = model.makeUserMove(new Move(row, column, new Arrow(model.getUserPlayer().getColor(), selectedDirection)));

        if(!valid) {
            signalInvalidMove("Invalid Move.");
            return;
        }

        if(selectedDirection == null){
            signalInvalidMove("Direction not selected.");
            return;
        }

        placeArrow(model.getUserPlayer().getColor(), selectedDirection, row, column);

        if(model.isEndgame()) {
            signalEndgame(gamesWonText, usersPane, "User", borderPane, levelSelectChoiceBox);
            return;
        }

        Move move = model.getSystemMove();
        if (move != null){
            placeArrow(model.getComputer().getColor(), move.getArrow().getDirection(), move.getX(), move.getY());
        }

        if(model.isEndgame()){
            signalEndgame(gamesWonText, usersPane, "Computer", borderPane, levelSelectChoiceBox);
        }
    }

    public void setPlayerColor(String color) {
        if(model.getUserPlayer().getColor().equals(color)) return;
        model.getComputer().setColor(model.getUserPlayer().getColor());
        model.getUserPlayer().setColor(color);
        model.changePlayerColor(model.getUserPlayer(), color);
    }

    public void changeLevel(int boardSize) {
        model.changeBoardSize(boardSize);
    }

    public void undoMove() {
        Move sysMove = model.undo();
        Move usrMove = model.undo();
        if(sysMove != null) removeArrow(sysMove.getX(), sysMove.getY());
        if(usrMove != null) removeArrow(usrMove.getX(), usrMove.getY());
    }

    public void loadUserList(TextArea usersPane) {
        ArrayList<User> users = model.getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        usersPane.setText(stringBuilder.toString());
    }

    public Background setBgImage(String name){
        BackgroundImage b = new BackgroundImage(new Image(new File(Main.path + "g" + name).toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        return new Background(b);
    }

    public void signalInvalidMove(String message) {
        final Stage dialog = new Stage();
        dialog.setTitle("Error.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text(message));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(mouseEvent -> dialog.close());
        dialog.setScene(dialogScene);
        dialog.show();
    }

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
                .ifPresent(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    public void clickedManageUsersButton() {
        AdminViewInterface view = new AdminView();
        Stage adminStage = new Stage();

        adminStage.setScene((AdminView) view);
        adminStage.setTitle("Admin Panel");
        adminStage.show();
    }

    public void clickedUndoButton() {
        undoMove();
    }

    public void signalEndgame(TextField gamesWonText, TextArea usersPane, String winner, BorderPane borderPane, ChoiceBox<String> levelSelectChoiceBox) {
        final Stage dialog = new Stage();
        dialog.setTitle("Game end.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        if(winner.equals("User")){
            winner = model.getUser().getUserName();
        }
        dialogVbox.getChildren().add(new Text(winner.toUpperCase() + " wins!"));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(e -> {
            dialog.close();
            clearBoard(borderPane, levelSelectChoiceBox, gamesWonText, usersPane);
        });
        dialog.setScene(dialogScene);
        dialog.show();
        if (!winner.equalsIgnoreCase("COMPUTER")){
            model.updateUserScore();
        }
        loadWonGames(gamesWonText, usersPane);
    }

    public void loadWonGames(TextField gamesWonText, TextArea usersPane) {
        User user = model.getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            gamesWonText.setText("Games won : " + gamesWon);
            usersPane.setVisible(false);
        }else {
            gamesWonText.setText("Games won : " + gamesWon);
        }
    }

    public void placeArrow(String color, String direction, int row, int column) {
        Image image = new Image(new File(Main.path + color + direction + ".png").toURI().toString());

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

    public void clickedStartGame(BorderPane borderPane, HashMap<String, Button> buttons, ChoiceBox<String> levelSelectChoiceBox, TextField gamesWonText, TextArea usersPane) {
        clearBoard(borderPane, levelSelectChoiceBox, gamesWonText, usersPane);
        setPlayerColor("g");

        String selectedBoard = levelSelectChoiceBox.getValue();
        if(selectedBoard.equals("4x4")) {
            board.setVisible(true);
            borderPane.setVisible(true);
            board = smallBoard;
            borderPane.setCenter(board);
            changeLevel(4);
            adjustInterButtons(buttons, false);
        } else {
            board.setVisible(true);
            borderPane.setVisible(true);
            board = largeBoard;
            borderPane.setCenter(board);
            changeLevel(8);
            adjustInterButtons(buttons, true);
        }
    }

    public void clickedBoard(MouseEvent mouseEvent, TextField gamesWonText, TextArea usersPane, BorderPane borderPane, ChoiceBox<String> levelSelectChoiceBox) {
        EventTarget source = mouseEvent.getTarget();
        if(!(source instanceof ImageView)){
            return;
        }
        ImageView selectedImage = (ImageView)source;
        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);
        userRegisterMove(gamesWonText, usersPane, row, col, borderPane, levelSelectChoiceBox);
    }

    public void clickedArrowButton(@NotNull ActionEvent mouseEvent) {
        Button button = (Button)mouseEvent.getSource();
        selectedDirection = button.getText();
    }

    public void doButtonEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);

        scaleTransition.play();
    }

    public void adjustInterButtons(HashMap<String, Button> buttons, boolean isVisible) {
        buttons.get("nE").setVisible(isVisible);
        buttons.get("nW").setVisible(isVisible);
        buttons.get("sE").setVisible(isVisible);
        buttons.get("sW").setVisible(isVisible);
    }

    public void clearBoard(BorderPane borderPane, ChoiceBox<String> levelSelectChoiceBox, TextField gamesWonText, TextArea usersPane){
        if(board != null){
            model.clearBoard();
            board.getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .map(node -> (ImageView) node)
                    .forEach(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
        }
        if(levelSelectChoiceBox.getValue().equals("4x4")){
            board = smallBoard;
            borderPane.setCenter(board);
        }else {
            board = largeBoard;
            borderPane.setCenter(board);
        }
        borderPane.getCenter().setStyle("-fx-background-color: #9db98a;");
    }

    public GridPane initBoard(String sizeS, TextField gamesWonText, TextArea usersPane, BorderPane borderPane, ChoiceBox<String> levelSelectChoiceBox) {
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
                ImageView imageView = new ImageView(new File(Main.path + "img.png").toURI().toString());
                imageView.setOnMouseClicked( e -> clickedBoard(e, gamesWonText, usersPane, borderPane, levelSelectChoiceBox));
                imageView.setFitWidth(41.0);
                imageView.setFitHeight(38.0);
                GridPane.setMargin(imageView, new Insets(2));
                gridPane.add(imageView, j, i);
            }
        }

        return gridPane;
    }

    public void initializeButton(Button button){
        button.setBackground(setBgImage(button.getText() + ".png"));
        button.setVisible(true);
        button.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        button.setLayoutX(113.0);
        button.setLayoutY(13.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(50.0);
        button.setPrefWidth(50.0);
        button.setPrefWidth(50.0);
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));

        button.setOnAction( e ->{
            doButtonEffect(button);
            clickedArrowButton(e);
        });
    }

    public User getUser() {
        return model.getUser();
    }
}
