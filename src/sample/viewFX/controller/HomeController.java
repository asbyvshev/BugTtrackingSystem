package sample.viewFX.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.Project;
import sample.entity.Task;
import sample.entity.User;
import sample.entity.base.AdditionalTable;
import sample.entity.base.BaseEntity;
import sample.entity.base.Const;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem homeMenuItemUsers;

    @FXML
    private MenuItem homeMenuItemProject;

    @FXML
    private MenuItem homeMenuItemTasks;

    @FXML
    private MenuItem homeMenuItemClose;

    @FXML
    private MenuItem createTask;

    @FXML
    private MenuItem createUser;

    @FXML
    private MenuItem createProject;

    @FXML
    private MenuItem delSelected;

    @FXML
    private TableView<Task> homeViewTaskTable;

    @FXML
    private TableColumn<Task, String> homeTableProjectCol;

    @FXML
    private TableColumn<Task, String> homeTableExecutorCol;

    @FXML
    private TableColumn<Task, String> homeTableTypeCol;

    @FXML
    private TableColumn<Task, Integer> homeTablePriorityCol;

    @FXML
    private TableColumn<Task, String> homeTableTopicCol;

    @FXML
    private TableColumn<Task, String> homeTableDescriptionCol;

    @FXML
    private TableView<AdditionalTable> homeViewAdditionalTable;

    @FXML
    private TableColumn<AdditionalTable, String> nameCol;

    @FXML
    private Button showTasksFilteredByButton;

    private FilterType filterType;

    private ObservableList<AdditionalTable> additionalObservableList = FXCollections.observableArrayList();
    private ObservableList<Task> homeObservableList = FXCollections.observableArrayList();

    @FXML
    void addProject(ActionEvent event) {
        Window window = showTasksFilteredByButton.getScene().getWindow();
        ControllerHelper.openNewScene(ControllerHelper.CREATE_PROJECT_VIEW_PATH, window, getClass());
    }

    @FXML
    void addTask(ActionEvent event) {
        Window window = showTasksFilteredByButton.getScene().getWindow();
        ControllerHelper.openNewScene(ControllerHelper.CREATE_TASK_VIEW_PATH, window, getClass());
    }

    @FXML
    void addUser(ActionEvent event) {
        Window window = showTasksFilteredByButton.getScene().getWindow();
        ControllerHelper.openNewScene(ControllerHelper.SING_UP_VIEW_PATH, window, getClass());
    }

    @FXML
    void removeSelected(ActionEvent event) {
        String table = null;
        Integer id = null;
        TableView tableView = null;
        ObservableList observableList = null;

        if (filterType.equals(FilterType.USER) || filterType.equals(FilterType.PROJECT)) {
            table = Const.USERS_TABLE;
            AdditionalTable selectedItem = homeViewAdditionalTable.getSelectionModel().getSelectedItem();
            id = selectedItem != null ? selectedItem.getId() : null;
            tableView = homeViewAdditionalTable;
            observableList = additionalObservableList;
            observableList.remove(selectedItem);
        }
        if (filterType.equals(FilterType.TASK)) {
            table = Const.TASKS_TABLE;
            Task item = homeViewTaskTable.getSelectionModel().getSelectedItem();
            id = item != null ? item.getId() : null;
            tableView = homeViewTaskTable;
            observableList = homeObservableList;
            observableList.remove(item);
        }
        if (id != null) {
            DataBaseHandler.remove(table, id);
            tableView.setItems(observableList);
            tableView.refresh();
        } else {
            System.out.println("Select the item to delete!");
        }
    }

    @FXML
    void showProjects(ActionEvent event) {
        showTasksFilteredByButton.setText(ControllerHelper.SHOW_ALL_TASKS_BY_PROJECT);
        filterType = FilterType.PROJECT;
        additionalObservableList.clear();
        additionalObservableList.addAll(DataBaseHandler.getAllProjects());
        initAdditional();
    }

    @FXML
    void showTasks(ActionEvent event) {
        homeObservableList.clear();
        homeObservableList.addAll(DataBaseHandler.getAllTasks());
        init();
    }

    @FXML
    void showUsers(ActionEvent event) {
        showTasksFilteredByButton.setText(ControllerHelper.SHOW_ALL_TASKS_OF_USER);
        filterType = FilterType.USER;
        additionalObservableList.clear();
        additionalObservableList.addAll(DataBaseHandler.getAllUsers());
        initAdditional();
    }

    @FXML
    void CloseApp(ActionEvent event) {
        DataBaseHandler.disconnect();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Const.NAME));
        homeTableProjectCol.setCellValueFactory(new PropertyValueFactory<>(Const.PROJECT));
        homeTableExecutorCol.setCellValueFactory(new PropertyValueFactory<>(Const.EXECUTOR));
        homeTableTypeCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_TYPE));
        homeTablePriorityCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_PRIORITY));
        homeTableTopicCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_TOPIC));
        homeTableDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_DESCRIPTION));
        homeObservableList.addAll(DataBaseHandler.getAllTasks());

        init();
        showTasksFilteredByButton.setOnAction(event -> {
            homeObservableList.clear();
            AdditionalTable selectItem = homeViewAdditionalTable.getSelectionModel().getSelectedItem();
            if (selectItem != null) {
                switch (filterType) {
                    case PROJECT:
                        Project project = (Project) selectItem;
                        homeObservableList.addAll(DataBaseHandler.getTasksByProject(project));
                        break;
                    case USER:
                        User user = (User) selectItem;
                        homeObservableList.addAll(DataBaseHandler.getTasksByUser(user));
                        break;
                }
            } else {
                homeObservableList.addAll(DataBaseHandler.getAllTasks());
            }
            homeViewTaskTable.setItems(homeObservableList);
            init();
        });
    }

    private void init() {
        filterType = FilterType.TASK;
        homeViewTaskTable.setItems(homeObservableList);
        homeViewAdditionalTable.setVisible(false);
        showTasksFilteredByButton.setVisible(false);
        homeViewTaskTable.setVisible(true);
        homeViewTaskTable.refresh();
    }

    private void initAdditional() {
        homeViewTaskTable.setVisible(false);
        homeViewAdditionalTable.setItems(additionalObservableList);
        homeViewAdditionalTable.setVisible(true);
        showTasksFilteredByButton.setVisible(true);
        homeViewAdditionalTable.refresh();
    }
}
