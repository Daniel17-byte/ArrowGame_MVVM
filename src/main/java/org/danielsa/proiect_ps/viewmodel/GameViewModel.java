package org.danielsa.proiect_ps.viewmodel;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.model.*;
import org.danielsa.proiect_ps.viewmodel.commands.game.*;

import java.util.HashMap;

@Getter
public class GameViewModel {
    private final GameModelInterface model;
    private final StringProperty selectedDirectionProperty = new SimpleStringProperty();
    private final StringProperty gameswonProperty = new SimpleStringProperty();
    private final StringProperty usersPaneProperty = new SimpleStringProperty();
    private final StringProperty levelSelectChoiceBoxProperty = new SimpleStringProperty("8x8");
    @Setter
    private ObjectProperty<GridPane> boardProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<GridPane> gridLargeBoardProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<GridPane> gridSmallBoardProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<HashMap<String, Button>> buttonsProperty = new SimpleObjectProperty<>();
    private final CommandClearBoard commandClearBoard;
    private final CommandGetUser commandGetUser;
    private final CommandLoadUsers commandLoadUsers;
    private final CommandManageUsersButton commandManageUsersButton;
    private final CommandStartGame commandStartGame;
    private final CommandUndoMove commandUndoMove;
    private final CommandUserRegisterMove commandUserRegisterMove;
    private final CommandLoadWonGames commandLoadWonGames;
    private final CommandInitializeButton commandInitializeButton;
    private final CommandInitBoard commandInitBoard;

    public GameViewModel() {
        model = new GameModel();
        model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
        this.commandStartGame = new CommandStartGame(this);
        this.commandGetUser = new CommandGetUser(this);
        this.commandLoadUsers = new CommandLoadUsers(this);
        this.commandClearBoard = new CommandClearBoard(this);
        this.commandManageUsersButton = new CommandManageUsersButton();
        this.commandUndoMove = new CommandUndoMove(this);
        this.commandUserRegisterMove = new CommandUserRegisterMove(this);
        this.commandLoadWonGames = new CommandLoadWonGames(this);
        this.commandInitializeButton = new CommandInitializeButton(this);
        this.commandInitBoard = new CommandInitBoard(this);
    }

    public void userRegisterMove(int row, int column) {
        commandUserRegisterMove.execute(row, column);
    }

    public void undoMove() {
        commandUndoMove.execute();
    }

    public void loadUserList() {
        commandLoadUsers.execute();
    }

    public void clickedManageUsersButton() {
        commandManageUsersButton.execute();
    }

    public void loadWonGames() {
        commandLoadWonGames.execute();
    }

    public void clickedStartGame() {
        commandStartGame.execute();
    }

    public void clearBoard(){
        commandClearBoard.execute();
    }

    public User getUser() {
        return commandGetUser.execute();
    }

    public void initializeButton(Button button){
        commandInitializeButton.execute(button);
    }

    public GridPane initBoard(String size) {
        return commandInitBoard.execute(size);
    }
}
