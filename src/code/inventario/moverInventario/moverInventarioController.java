package code.inventario.moverInventario;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class moverInventarioController {

    @FXML
    private Button salirBoton;

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

}
