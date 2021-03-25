package sample.viewFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.Project;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateProjectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createProjectCreateButton;

    @FXML
    private TextField createProjectNameField;

    @FXML
    void initialize() {
        createProjectCreateButton.setOnAction(event -> {
            DataBaseHandler.checkAndConnect();
            Project project = new Project(createProjectNameField.getText());
            if (project.getName() != null && !project.getName().isEmpty()) {
                DataBaseHandler.createProject(project);
                Window window = createProjectCreateButton.getScene().getWindow();
                ControllerHelper.openNewScene(
                        ControllerHelper.HOME_VIEW_PATH, window, getClass());
            } else {
                System.out.println("Must be entered name!");
            }
        });
    }
}

