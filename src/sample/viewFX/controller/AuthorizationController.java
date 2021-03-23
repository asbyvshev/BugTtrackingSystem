package sample.viewFX.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private final String VIEW_PATH = "/sample/viewFX/view/singUp.fxml";

    @FXML
    void initialize() {
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
            authSingUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(VIEW_PATH));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

    private void loginUser(String loginText, String passwordText) {
    }
}


