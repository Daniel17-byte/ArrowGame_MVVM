package org.danielsa.proiect_ps.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.danielsa.proiect_ps.model.RegisterModel;
import org.danielsa.proiect_ps.model.RegisterModelInterface;
import org.danielsa.proiect_ps.viewmodel.commands.register.CommandShowRegisterResult;

@Getter
public class RegisterViewModel {
    private final RegisterModelInterface model;
    private final StringProperty resultLabelProperty = new SimpleStringProperty();
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final ObjectProperty<String> userTypeProperty = new SimpleObjectProperty<>();
    private final CommandShowRegisterResult commandShowRegisterResult;

    public RegisterViewModel() {
        this.model = new RegisterModel();
        this.commandShowRegisterResult = new CommandShowRegisterResult(this);
    }

    public void showRegisterResult() {
        commandShowRegisterResult.execute();
    }

}
