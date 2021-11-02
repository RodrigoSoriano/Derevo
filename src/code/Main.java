package code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        ConexionBD.getInstancia().testConection();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/code/acceder/acceder.fxml"));
        } catch (IOException e) {
            ConexionBD.getInstancia().error(e);
        }
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 355, 426));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
