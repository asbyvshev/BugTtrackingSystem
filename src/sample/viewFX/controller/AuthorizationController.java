package sample.viewFX.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    }
}


