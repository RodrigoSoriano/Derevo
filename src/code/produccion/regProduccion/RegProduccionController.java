package code.produccion.regProduccion;

import code.ConeccionBD;
import code.General;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class RegProduccionController {
    @FXML
    private Button salirBoton;

    @FXML
    private DatePicker fecha;

    @FXML
    private TextField id;

    @FXML
    private ComboBox empleado;

    @FXML
    private ComboBox producto;

    @FXML
    private TextField cantidad;

    private String oldValue_cantidad = "";
    private int caretPosition_cantidad = 0;

    private boolean edicion = false;
    private String ventana = "Producción";

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    public void formatoCantidad() {
        if (!cantidad.getText().matches("\\d{0,9}?")) {
            cantidad.setText(oldValue_cantidad);
            cantidad.positionCaret(caretPosition_cantidad);
        }else{
            oldValue_cantidad = cantidad.getText();
            caretPosition_cantidad = cantidad.getCaretPosition();
        }
    }

    private boolean validaDatos(){
        if(     !empleado.getSelectionModel().isEmpty() &&
                !producto.getSelectionModel().isEmpty() &&
                !fecha.getValue().toString().isBlank() &&
                !cantidad.getText().isBlank()){
            return false;
        }else{
            return true;
        }
    }

    public void regProduccionButton() {
        if(validaDatos()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registro de producción");
            alert.setHeaderText("Se procedera a registrar la producción");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                regProduccion();
            }
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Datos incompletos");
        }
    }

    private void regProduccion() {
        if (ConeccionBD.getInstancia().regProduccion(
                edicion,
                id.getText(),
                fecha.getValue().toString(),
                empleado.getSelectionModel().toString(),
                producto.getSelectionModel().toString(),
                cantidad.toString())){
            General.mensaje(Alert.AlertType.INFORMATION, ventana, "Los datos han sido guardados satisfactoriamente");
            if(!edicion){
                clear();
            }else{
                salirBotonOnAction();
            }
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Los datos no fueron registrados");
        }
    }
}
