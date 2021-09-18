package code.produccion.regProduccion;

import code.ConeccionBD;
import code.General;
import code.produccion.ModeloTablaProduccion;
import code.produccion.Produccion;
import code.produccion.ProduccionHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegProduccionController implements Initializable {
    @FXML
    private Button salirBoton;

    @FXML
    private DatePicker fecha;

    @FXML
    private TextField id;

    @FXML
    private TextField empleado_id;

    @FXML
    private TextField empleado;

    @FXML
    private TextField producto_id;

    @FXML
    private TextField producto;

    @FXML
    private TextField cantidad;

    @FXML
    private TableView<ModeloTablaProduccion> tablaProduccion;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_id;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_producto;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_cantidad;

    private String oldValue_cantidad = "";
    private int caretPosition_cantidad = 0;

    private boolean edicion = false;
    private String ventana = "Producción";

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    public void formatoCantidad(KeyEvent event) {
        if (!cantidad.getText().matches("\\d{0,9}?")) {
            cantidad.setText(oldValue_cantidad);
            cantidad.positionCaret(caretPosition_cantidad);
        }else{
            oldValue_cantidad = cantidad.getText();
            caretPosition_cantidad = cantidad.getCaretPosition();
        }
    }

    public void formatoId_empleado(KeyEvent event) {

    }

    private boolean validaDatos(){
        if(     !empleado_id.getText().isBlank() &&
                !producto_id.getText().isBlank() &&
                !fecha.getValue().toString().isBlank() &&
                !cantidad.getText().isBlank()){
            return true;
        }else{
            return false;
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

    public void llenarEmpleado(KeyEvent event) throws SQLException {
       if(event.getCode() == KeyCode.ENTER) {
           empleado.setText(ConeccionBD.getInstancia().getEmpleadoById(empleado_id.getText()));
       }
    }

    private void regProduccion() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Produccion produccion = ProduccionHolder.getInstancia().getProduccion();
        fecha.setValue(java.time.LocalDate.now());
    }
}
