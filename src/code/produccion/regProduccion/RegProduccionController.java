package code.produccion.regProduccion;

import code.ConeccionBD;
import code.General;
import code.produccion.Produccion;
import code.produccion.ProduccionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class RegProduccionController implements Initializable {
    //region Variables FXML
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
    private Button aperturar;

    @FXML
    private Button buscarEmpleado;
    //endregion

    private boolean edicion = false;
    private String ventana = "Producción";
    private ObservableList<String> produccionTabla = FXCollections.observableArrayList();

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    private boolean validaDatos(){
        if(     !empleado_id.getText().isBlank() &&
                !fecha.getValue().toString().isBlank()){
            return true;
        }else{
            return false;
        }
    }

    public void aperturarProduccionBoton() throws SQLException {
        if(validaDatos()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Apertura de producción");
            alert.setHeaderText("Se procedera a aprturar la producción");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                aperturarProduccion();
            }
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Datos incompletos");
        }
    }

    private void aperturarProduccion() throws SQLException {
        ConeccionBD.getInstancia().aperturarProduccion(empleado_id.getText(), fecha.getValue().toString(), nota.getText());
        setDatos();
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
            if (!producto.getText().equals("No encontrado")){
                cantidad.requestFocus();
            }
        }
    }

    public void enterCantidad(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER){
            agregarProduccion();
            producto_id.requestFocus();
        }
    }

    public void agregarProduccion() throws SQLException {
        ConeccionBD.getInstancia().agregarProduccion(id.getText(), producto_id.getText(), cantidad.getText());
        actualizarTabla();
        clear();
    }

    public void dobleClick(MouseEvent event) throws SQLException {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            removerProduccion();
        }
    }

    public void removerProduccion() throws SQLException {
        ConeccionBD.getInstancia().removerProduccion(id.getText(), tablaProduccion.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
        actualizarTabla();
    }

    private void clear() {
        producto_id.setText("");
        producto.setText("");
        cantidad.setText("");
    }

    private void setFormatos() {
        empleado_id.setTextFormatter(General.soloNumero());
        producto_id.setTextFormatter(General.soloNumero());
        cantidad.setTextFormatter(General.soloNumero());
    }

    private void setDatos() {
        Produccion produccion = Produccion.getProduccion();
        if (produccion.getId_produccion() != null){
            edicion = true;
            aperturar.setDisable(true);
            fecha.setDisable(true);
            nota.setEditable(false);
            empleado_id.setEditable(false);
            buscarEmpleado.setDisable(true);
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
            fecha.setValue(java.time.LocalDate.now());
        }
    }

    private void actualizarTabla() throws SQLException {
        General.llenarTabla(tablaProduccion,"SubProduccion", "WHERE id_produccion = " + id.getText());
        tablaProduccion.getColumns().remove(3);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFormatos();
        setDatos();
    }
}