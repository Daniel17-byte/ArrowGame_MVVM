package org.danielsa.proiect_ps.viewmodel.commands.game;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.io.File;

public class CommandInitializeButton {
    private final GameViewModel viewModel;

    public CommandInitializeButton(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void execute(Button button) {
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

    private void clickedArrowButton(ActionEvent actionEvent){
        Button button = (Button)actionEvent.getSource();
        viewModel.getSelectedDirectionProperty().setValue(button.getText());
    }

    private Background setBgImage(String name){
        BackgroundImage b = new BackgroundImage(new Image(new File(Main.path + "g" + name).toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        return new Background(b);
    }

    private void doButtonEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);

        scaleTransition.play();
    }
}
