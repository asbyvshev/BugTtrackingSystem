package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.connectionDB.DataBaseHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataBaseHandler dbHandler = DataBaseHandler.getInstance();
        dbHandler.connect();
        dbHandler.createDB();
        Parent root = FXMLLoader.load(getClass().getResource("viewFX/view/authorization.fxml"));
        primaryStage.setTitle("BugTracing");
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 700, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
