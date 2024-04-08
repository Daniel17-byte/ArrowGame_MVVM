package org.danielsa.proiect_ps;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.view.LoginViewInterface;
import org.danielsa.proiect_ps.view.LoginView;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Getter
public class Main extends Application {
    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    @Override
    public void start(Stage primaryStage) {
        LoginViewInterface view = new LoginView();

        primaryStage.setScene((LoginView) view);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
