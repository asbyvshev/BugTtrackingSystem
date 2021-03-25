package sample.viewFX.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSingInButton;

    @FXML
    private TextField authLoginField;

    @FXML
    private PasswordField authPasswordField;

    @FXML
    private Button authSingUpButton;

    @FXML
    void initialize() {
        DataBaseHandler.checkAndConnect();
        authSingInButton.setOnAction(event -> {
            String loginText = authLoginField.getText().trim();
            String passwordText = authPasswordField.getText().trim();
            
            if (loginText != null && passwordText != null &&
                    !loginText.isEmpty() && !passwordText.isEmpty()) {
                loginUser(loginText, passwordText);
            } else {
                System.out.println("Login or password is empty");
            }
        });

        authSingUpButton.setOnAction(event -> {
            Window window = authSingUpButton.getScene().getWindow();
            ControllerHelper.openNewScene(ControllerHelper.SING_UP_VIEW_PATH, window, getClass());
        });
    }

    private void loginUser(String loginText, String passwordText) {
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(passwordText);
        ResultSet result = DataBaseHandler.getUserAuth(user);

        int counter = 0;

        try {
            while (result.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (counter > 0) {
            Window window = authSingInButton.getScene().getWindow();
            ControllerHelper.openNewScene(ControllerHelper.HOME_VIEW_PATH, window, getClass());
            System.out.println("Success!");
        } else {
            System.out.println("User not found!");
        }
    }
}


