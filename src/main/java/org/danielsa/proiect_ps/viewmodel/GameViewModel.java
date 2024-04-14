package org.danielsa.proiect_ps.viewmodel;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.model.*;
import org.danielsa.proiect_ps.view.AdminView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GameViewModel {
    private final GameModelInterface model;
    @Getter
    private final StringProperty selectedDirectionProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty gameswonProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty usersPaneProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty levelSelectChoiceBoxProperty = new SimpleStringProperty("8x8");
    @Getter @Setter
    private ObjectProperty<GridPane> boardProperty = new SimpleObjectProperty<>();
    @Getter @Setter
    private ObjectProperty<GridPane> gridLargeBoardProperty = new SimpleObjectProperty<>();
    @Getter @Setter
    private ObjectProperty<GridPane> gridSmallBoardProperty = new SimpleObjectProperty<>();
    @Getter @Setter
    private ObjectProperty<HashMap<String, Button>> buttonsProperty = new SimpleObjectProperty<>();

    public GameViewModel() {
        model = new GameModel();
        model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
    }

    public void userRegisterMove(int row, int column) {
        boolean valid = model.makeUserMove(new Move(row, column, new Arrow(model.getUserPlayer().getColor(), selectedDirectionProperty.getValue())));

        if(!valid) {
            signalInvalidMove("Invalid Move.");
            return;
        }

        if(selectedDirectionProperty.getValue() == null){
            signalInvalidMove("Direction not selected.");
            return;
        }

        placeArrow(model.getUserPlayer().getColor(), selectedDirectionProperty.getValue(), row, column);

        if(model.isEndgame()) {
            signalEndgame("User");
            return;
        }

        Move move = model.getSystemMove();
        if (move != null){
            placeArrow(model.getComputer().getColor(), move.getArrow().getDirection(), move.getX(), move.getY());
        }

        if(model.isEndgame()){
            signalEndgame("Computer");
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

    public void loadUserList() {
        ArrayList<User> users = model.getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        usersPaneProperty.setValue(stringBuilder.toString());
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
        getBoardProperty().getValue().getChildren().stream()
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

    public void clickedManageUsersButton() {
        AdminView view = new AdminView();
        Stage adminStage = new Stage();

        adminStage.setScene(view);
        adminStage.setTitle("Admin Panel");
        adminStage.show();
    }

    public void clickedUndoButton() {
        undoMove();
    }

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
            winner = model.getUser().getUserName();
        }
        dialogVbox.getChildren().add(new Text(winner.toUpperCase() + " wins!"));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(e -> {
            dialog.close();
            clearBoard();
        });
        dialog.setScene(dialogScene);
        dialog.show();
        if (!winner.equalsIgnoreCase("COMPUTER")){
            model.updateUserScore();
        }
        loadWonGames();
    }

    public void loadWonGames() {
        User user = model.getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            gameswonProperty.setValue("Games won : " + gamesWon);
        }else {
            gameswonProperty.setValue("Games won : " + gamesWon);
        }
    }

    public void placeArrow(String color, String direction, int row, int column) {
        Image image = new Image(new File(Main.path + color + direction + ".png").toURI().toString());

        getBoardProperty().getValue().getChildren().stream()
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

    public void clickedStartGame() {
        clearBoard();
        setPlayerColor("g");

        String selectedBoard = levelSelectChoiceBoxProperty.getValue();
        if(selectedBoard.equals("4x4")) {
            getBoardProperty().setValue(gridSmallBoardProperty.getValue());
            changeLevel(4);
            adjustInterButtons(false);
        } else {
            getBoardProperty().setValue(gridLargeBoardProperty.getValue());
            changeLevel(8);
            adjustInterButtons(true);
        }
    }

    public void clickedBoard(MouseEvent mouseEvent) {
        EventTarget source = mouseEvent.getTarget();
        if(!(source instanceof ImageView)){
            return;
        }
        ImageView selectedImage = (ImageView)source;
        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);
        userRegisterMove(row, col);
    }

    public void clickedArrowButton(ActionEvent mouseEvent) {
        Button button = (Button)mouseEvent.getSource();
        selectedDirectionProperty.setValue(button.getText());
    }

    public void adjustInterButtons(boolean isVisible) {
        buttonsProperty.getValue().get("nE").setVisible(isVisible);
        buttonsProperty.getValue().get("nW").setVisible(isVisible);
        buttonsProperty.getValue().get("sE").setVisible(isVisible);
        buttonsProperty.getValue().get("sW").setVisible(isVisible);
    }

    public void clearBoard(){
        if(getBoardProperty().getValue() != null){
            model.clearBoard();
            getBoardProperty().getValue().getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .map(node -> (ImageView) node)
                    .forEach(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
        }
        if(levelSelectChoiceBoxProperty.getValue().equals("4x4")){
            getBoardProperty().setValue(gridSmallBoardProperty.getValue());
        }else {
            getBoardProperty().setValue(gridLargeBoardProperty.getValue());
        }
    }

    public GridPane initBoard(String sizeS) {
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
                imageView.setOnMouseClicked(this::clickedBoard);
                imageView.setFitWidth(41.0);
                imageView.setFitHeight(38.0);
                GridPane.setMargin(imageView, new Insets(2));
                gridPane.add(imageView, j, i);
            }
        }

        return gridPane;
    }

    public User getUser() {
        return model.getUser();
    }
}
