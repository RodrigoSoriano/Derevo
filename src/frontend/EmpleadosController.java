package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmpleadosController {

    public void abrirRegistrarEmpleado() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("regEmpleado.fxml"));
            Stage regStage = new Stage();
            regStage.setTitle("Registro de Empleado");
            regStage.setScene(new Scene(root, 375, 272));
            regStage.setResizable(false);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
