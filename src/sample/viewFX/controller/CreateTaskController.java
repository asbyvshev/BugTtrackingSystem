package sample.viewFX.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.Project;
import sample.entity.Task;
import sample.entity.User;
import sample.entity.base.TaskType;

public class CreateTaskController {

    @FXML
    private Button createTaskCreateButton;

    @FXML
    private TextField createTaskTopicField;

    @FXML
    private ComboBox<Project> createTaskProjectComboBox;

    @FXML
    private ComboBox<User> createTaskExecutorComboBox;

    @FXML
    private ComboBox<TaskType> createTaskTypeComboBox;

    @FXML
    private TextArea createTaskDescriptionArea;

    @FXML
    private TextField createTaskPriorityField;

    private ObservableList<Project> projects = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<TaskType> types = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        initComboBoxes();
        createTaskCreateButton.setOnAction(event -> {
            DataBaseHandler handler = DataBaseHandler.getInstance();
            handler.checkAndConnect();

            Task task = new Task(
                    createTaskTopicField.getText(),
                    createTaskDescriptionArea.getText(),
                    createTaskPriorityField.getText(),
                    createTaskProjectComboBox.getSelectionModel().getSelectedItem(),
                    createTaskExecutorComboBox.getSelectionModel().getSelectedItem(),
                    createTaskTypeComboBox.getSelectionModel().getSelectedItem());

            if (task.getProject() != null && task.getExecutor() != null) {
                handler.createTask(task);
                Window window = createTaskCreateButton.getScene().getWindow();
                ControllerHelper.openNewScene(
                        ControllerHelper.HOME_VIEW_PATH, window, getClass());
                System.out.println("Created new task: " + task.getId());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Create task");
                alert.setContentText("Must be entered project and executor!");
                alert.setHeaderText(null);
                alert.showAndWait();
                System.out.println("Must be entered project and executor!");
            }
        });
    }

    private void initComboBoxes() {
        DataBaseHandler handler = DataBaseHandler.getInstance();
        handler.checkAndConnect();
        projects.addAll(handler.getAllProjects());
        users.addAll(handler.getAllUsers());
        types.addAll(TaskType.values());
        createTaskProjectComboBox.setItems(projects);
        createTaskExecutorComboBox.setItems(users);
        createTaskTypeComboBox.setItems(types);
    }
}
