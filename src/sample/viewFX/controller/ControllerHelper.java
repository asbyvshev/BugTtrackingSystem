package sample.viewFX.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ControllerHelper {

    public static final String SING_UP_VIEW_PATH = "/sample/viewFX/view/singUp.fxml";
    public static final String HOME_VIEW_PATH = "/sample/viewFX/view/home.fxml";
    public static final String CREATE_PROJECT_VIEW_PATH = "/sample/viewFX/view/createProject.fxml";
    public static final String CREATE_TASK_VIEW_PATH = "/sample/viewFX/view/createTask.fxml";
    public static final String SHOW_ALL_TASKS_BY_PROJECT = "Show all tasks by project";
    public static final String SHOW_ALL_TASKS_OF_USER = "Show all tasks of user";

    public static void openNewScene(String view, Window window, Class<?> clazz) {
        window.hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(clazz.getResource(view));

        try {
            loader.load();
        } catch (IOException e) {
            showError(e);
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private static void showError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Open new window");
        alert.setHeaderText(e.getMessage());

        VBox dialogPaneContent = new VBox();
        Label label = new Label("Stack Trace:");

        String stackTrace = getStackTrace(e);
        TextArea textArea = new TextArea();
        textArea.setText(stackTrace);

        dialogPaneContent.getChildren().addAll(label, textArea);

        // Set content for Dialog Pane
        alert.getDialogPane().setContent(dialogPaneContent);

        alert.showAndWait();
    }

    private static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String s = sw.toString();
        return s;
    }
}
