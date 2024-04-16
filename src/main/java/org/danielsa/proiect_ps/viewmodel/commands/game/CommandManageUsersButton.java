package org.danielsa.proiect_ps.viewmodel.commands.game;

import eu.hansolo.tilesfx.Command;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.view.AdminView;

public class CommandManageUsersButton implements Command {

    public CommandManageUsersButton() {
    }

    @Override
    public void execute() {
        AdminView view = new AdminView();
        Stage adminStage = new Stage();

        adminStage.setScene(view);
        adminStage.setTitle("Admin Panel");
        adminStage.show();
    }
}
