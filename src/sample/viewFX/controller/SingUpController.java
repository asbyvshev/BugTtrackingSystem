package sample.viewFX.controller;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.connectionDB.DataBaseHandler;
import sample.entity.User;

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
            try {
                DataBaseHandler.connect();
                User user = new User(
                        singUpLoginField.getText(),
                        singUpPasswordField.getText(),
                        singUpNameField.getText());
                DataBaseHandler.createUser(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}
