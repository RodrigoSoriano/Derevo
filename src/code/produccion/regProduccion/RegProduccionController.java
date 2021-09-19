package code.produccion.regProduccion;

import code.ConeccionBD;
import code.General;
import code.produccion.Produccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
    private TextField nota;

    @FXML
    private TableView tablaProduccion;

    @FXML
    private TableColumn colum_id;

    @FXML
    private TableColumn colum_producto;

    @FXML
    private TableColumn colum_cantidad;

    @FXML
    private Button aperturar;

    private boolean edicion = false;
    private String ventana = "Producción";
    private ObservableList<String> produccionTabla = FXCollections.observableArrayList();

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    private boolean validaDatos(){
        if(     !empleado_id.getText().isBlank() &&
                !fecha.getValue().toString().isBlank() &&
                !cantidad.getText().isBlank()){
            return true;
        }else{
            return false;
        }
    }

    public void aperturarProduccionBoton() {
        if(validaDatos()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registro de producción");
            alert.setHeaderText("Se procedera a registrar la producción");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                aperturarProduccion();
            }
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Datos incompletos");
        }
    }

    private void aperturarProduccion() {

    }

    public void llenarEmpleado(KeyEvent event) throws SQLException {
        empleado.setText("");
        if(event.getCode() == KeyCode.ENTER) {
            empleado.setText(ConeccionBD.getInstancia().getEmpleadoById(empleado_id.getText()));
        }
    }

    public void llenarProducto(KeyEvent event) throws SQLException {
        producto.setText("");
        if(event.getCode() == KeyCode.ENTER) {
            producto.setText(ConeccionBD.getInstancia().getProductoById(producto_id.getText()));
        }
    }

    public void agregarProduccion() throws SQLException {
        ConeccionBD.getInstancia().agregarProduccion(id.getText(), producto_id.getText(), cantidad.getText());
        actualizarTabla();
    }

    public void removerProduccion() {
        ConeccionBD.getInstancia().removerProduccion(id.getText(), tablaProduccion.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
    }

    private void clear() {
        fecha.setValue(java.time.LocalDate.now());
    }

    private void actualizarTabla() throws SQLException {
        General.llenarTabla(tablaProduccion, "SubProduccion", id.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Produccion produccion = Produccion.getProduccion();
        if (produccion.getId_produccion() != null){
            edicion = true;
            aperturar.setDisable(true);
            id.setText(produccion.getId_produccion());
            nota.setText(produccion.getNota());
            empleado_id.setText(produccion.getId_empleado());
            fecha.setValue(produccion.getFecha());
            try {
                empleado.setText(ConeccionBD.getInstancia().getEmpleadoById(empleado_id.getText()));
                actualizarTabla();
            } catch (Exception e) {
                ConeccionBD.getInstancia().error(e);
            }
        }else{
            clear();
        }
        empleado_id.setTextFormatter(General.soloNumero());
        producto_id.setTextFormatter(General.soloNumero());
        cantidad.setTextFormatter(General.soloNumero());
    }
}
