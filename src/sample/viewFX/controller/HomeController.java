package sample.viewFX.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.Project;
import sample.entity.Task;
import sample.entity.User;
import sample.entity.base.AdditionalTable;
import sample.entity.base.Const;

public class HomeController {

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

    private final ObservableList<AdditionalTable> additionalObservableList = FXCollections.observableArrayList();

    private final ObservableList<Task> homeObservableList = FXCollections.observableArrayList();

    private DataBaseHandler handler;

    @FXML
    void initialize() {
        handler = DataBaseHandler.getInstance();
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Const.NAME));
        homeTableProjectCol.setCellValueFactory(new PropertyValueFactory<>(Const.PROJECT));
        homeTableExecutorCol.setCellValueFactory(new PropertyValueFactory<>(Const.EXECUTOR));
        homeTableTypeCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_TYPE));
        homeTablePriorityCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_PRIORITY));
        homeTableTopicCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_TOPIC));
        homeTableDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(Const.TASKS_DESCRIPTION));
        homeObservableList.addAll(handler.getAllTasks());

        init();
        showTasksFilteredByButton.setOnAction(event -> {
            homeObservableList.clear();
            AdditionalTable selectItem = homeViewAdditionalTable.getSelectionModel().getSelectedItem();
            if (selectItem != null) {
                switch (filterType) {
                    case PROJECT:
                        Project project = (Project) selectItem;
                        homeObservableList.addAll(handler.getTasksByProject(project));
                        break;
                    case USER:
                        User user = (User) selectItem;
                        homeObservableList.addAll(handler.getTasksByUser(user));
                        break;
                }
            } else {
                homeObservableList.addAll(handler.getAllTasks());
            }
            homeViewTaskTable.setItems(homeObservableList);
            init();
        });
    }

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
        String table;
        Integer id;
        TableView tableView;
        ObservableList observableList;

        if (filterType != null && filterType.equals(FilterType.TASK)) {
            table = Const.TASKS_TABLE;
            Task item = homeViewTaskTable.getSelectionModel().getSelectedItem();
            id = item != null ? item.getId() : null;
            tableView = homeViewTaskTable;
            observableList = homeObservableList;
            observableList.remove(item);
        } else {
            AdditionalTable selectedItem = homeViewAdditionalTable.getSelectionModel().getSelectedItem();
            id = selectedItem != null ? selectedItem.getId() : null;
            tableView = homeViewAdditionalTable;
            observableList = additionalObservableList;
            observableList.remove(selectedItem);
            table = filterType.equals(FilterType.USER) ? Const.USERS_TABLE : Const.PROJECTS_TABLE;
        }
        if (id != null) {
            handler.remove(table, id);
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
        additionalObservableList.addAll(handler.getAllProjects());
        initAdditional();
    }

    @FXML
    void showTasks(ActionEvent event) {
        homeObservableList.clear();
        homeObservableList.addAll(handler.getAllTasks());
        init();
    }

    @FXML
    void showUsers(ActionEvent event) {
        showTasksFilteredByButton.setText(ControllerHelper.SHOW_ALL_TASKS_OF_USER);
        filterType = FilterType.USER;
        additionalObservableList.clear();
        additionalObservableList.addAll(handler.getAllUsers());
        initAdditional();
    }

    @FXML
    void CloseApp(ActionEvent event) {
        handler.disconnect();
        Platform.exit();
        System.exit(0);
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
