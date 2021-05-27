package frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegEmpleadoController {

    @FXML
    private Button salirBoton;

    public void salirBotonOnAction(ActionEvent event) {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

}
