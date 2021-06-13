package frontend;

import backend.ConeccionBD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/frontend/acceder/acceder.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 355, 426));
        primaryStage.setResizable(false);
        primaryStage.show();

        try {
            ConeccionBD conectar = new ConeccionBD();
            Connection coneccion = conectar.getConnection();
            coneccion.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión");
            alert.setHeaderText("No se pudo conectar con la base de datos");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
            System.exit(8);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
