package sample.viewFX.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.User;

public class SingUpController {

    @FXML
    private Button singUpCreateButton;

    @FXML
    private TextField singUpLoginField;

    @FXML
    private TextField singUpPasswordField;

    @FXML
    private TextField singUpNameField;

    @FXML
    void initialize() {
        singUpCreateButton.setOnAction(event -> {
            DataBaseHandler handler = DataBaseHandler.getInstance();
            handler.checkAndConnect();
            User user = new User(
                    singUpNameField.getText(),
                    singUpLoginField.getText(),
                    singUpPasswordField.getText());

            if (user.getLogin() != null &&
                    user.getPassword() != null &&
                    user.getName() != null &&
                    !user.getLogin().isEmpty() &&
                    !user.getPassword().isEmpty() &&
                    !user.getName().isEmpty()) {

                handler.createUser(user);
                Window window = singUpCreateButton.getScene().getWindow();
                ControllerHelper.openNewScene(
                        ControllerHelper.HOME_VIEW_PATH, window, getClass());
                System.out.println("Created new user: " + user.getName());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SingUp");
                alert.setContentText("Must be entered name, login and password!");
                alert.setHeaderText(null);
                alert.showAndWait();
                System.out.println("Must be entered name, login and password!");
            }
        });
    }
}
