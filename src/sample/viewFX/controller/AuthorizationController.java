package sample.viewFX.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import sample.connectionDB.DataBaseHandler;
import sample.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationController {

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
        authSingInButton.setOnAction(event -> {
            String loginText = authLoginField.getText().trim();
            String passwordText = authPasswordField.getText().trim();
            
            if (loginText != null && passwordText != null &&
                    !loginText.isEmpty() && !passwordText.isEmpty()) {
                loginUser(loginText, passwordText);
            } else {
                authAlert("Login or password is empty");
//                System.out.println("Login or password is empty");
            }
        });

        authSingUpButton.setOnAction(event -> {
            Window window = authSingUpButton.getScene().getWindow();
            ControllerHelper.openNewScene(ControllerHelper.SING_UP_VIEW_PATH, window, getClass());
        });
    }

    private void authAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Authorization");
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void loginUser(String loginText, String passwordText) {
        DataBaseHandler handler = DataBaseHandler.getInstance();
        handler.checkAndConnect();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(passwordText);
        ResultSet result = handler.getUserAuth(user);

        int counter = 0;

        try {
            while (result.next()) {
                counter++;
            }
        } catch (SQLException e) {
            handler.showDBError(e);
            e.printStackTrace();
        }

        if (counter > 0) {
            Window window = authSingInButton.getScene().getWindow();
            ControllerHelper.openNewScene(ControllerHelper.HOME_VIEW_PATH, window, getClass());
            System.out.println("Success!");
        } else {
            authAlert("User not found!");
            System.out.println("User not found!");
        }
    }
}


