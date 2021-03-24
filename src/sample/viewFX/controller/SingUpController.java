package sample.viewFX.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.User;

import java.net.URL;
import java.util.ResourceBundle;

public class SingUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
            DataBaseHandler.checkAndConnect();
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

                DataBaseHandler.createUser(user);
                Window window = singUpCreateButton.getScene().getWindow();
                ControllerHelper.openNewScene(
                        ControllerHelper.HOME_VIEW_PATH, window, getClass());
            } else {
                System.out.println("Must be entered name, login and password!");
            }
        });
    }
}
