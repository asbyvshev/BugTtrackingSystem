package sample.viewFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.Project;

public class CreateProjectController {

    @FXML
    private Button createProjectCreateButton;

    @FXML
    private TextField createProjectNameField;

    @FXML
    void initialize() {
        createProjectCreateButton.setOnAction(event -> {
            DataBaseHandler handler = DataBaseHandler.getInstance();
            handler.checkAndConnect();
            Project project = new Project(createProjectNameField.getText());
            if (project.getName() != null && !project.getName().isEmpty()) {
                handler.createProject(project);
                Window window = createProjectCreateButton.getScene().getWindow();
                ControllerHelper.openNewScene(
                        ControllerHelper.HOME_VIEW_PATH, window, getClass());
            } else {
                System.out.println("Must be entered name!");
            }
        });
    }
}

