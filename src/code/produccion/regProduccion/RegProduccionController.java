package code.produccion.regProduccion;

import code.ConexionBD;
import code.generales.General;
import code.produccion.Produccion;
import code.produccion.ProduccionController;
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

    @FXML
    private Button buscarProducto;

    @FXML
    private Button agregarBoton;

    @FXML
    private Button removerBoton;
    //endregion

    private String ventana = "Producción";
    private ProduccionController produccionController;

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    private boolean validaDatosAperturar(){
        if(     fecha.getValue().toString().isBlank() ||
                empleado_id.getText().isBlank() ||
                empleado.getText().equals("No encontrado")
        ){
            return false;
        }else{
            return true;
        }
    }

    private boolean validaDatosAgregar(){
        if(     producto_id.getText().isBlank() ||
                producto.getText().equals("No encontrado") ||
                cantidad.getText().isBlank()
        ){
            return false;
        }else{
            return true;
        }
    }

    private boolean validaDatosRemover(){
        if(tablaProduccion.getSelectionModel().getSelectedItem() == null){
            return false;
        }else{
            return true;
        }
    }

    public void aperturarProduccionBoton() {
        if(validaDatosAperturar()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Apertura de producción");
            alert.setHeaderText("Se procedera a aprturar la producción");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                ConexionBD.getInstancia().aperturarProduccion(empleado_id.getText(), fecha.getValue().toString(), nota.getText());
                setDatos();
                produccionController.actualizarTabla();
            }
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Datos incompletos");
        }
    }

    public void BuscarEmpleado()  {
        General.abrirBuscador("Empleado", true);
        if (!General.getValor().isBlank()) {
            empleado_id.setText(General.getValor());
            empleado.setText(ConexionBD.getInstancia().getEmpleadoById(General.getValor()));
        }
    }

    public void llenarEmpleado(KeyEvent event) {
        if (empleado_id.isEditable()) {
            empleado.setText("");
            if(event.getCode() == KeyCode.ENTER) {
                empleado.setText(ConexionBD.getInstancia().getEmpleadoById(empleado_id.getText()));
                if (empleado.getText().equals("No encontrado")){
                    empleado_id.setText("");
                }
            }
        }
    }

    public void BuscarProducto() {
        General.abrirBuscador("Inventario", true);
        if (!General.getValor().isBlank()) {
            producto_id.setText(General.getValor());
            producto.setText(ConexionBD.getInstancia().getProductoById(General.getValor()));
            cantidad.requestFocus();
        }
    }

    public void llenarProducto(KeyEvent event)  {
        if (producto_id.isEditable()) {
            producto.setText("");
            if(event.getCode() == KeyCode.ENTER) {
                producto.setText(ConexionBD.getInstancia().getProductoById(producto_id.getText()));
                if (!producto.getText().equals("No encontrado")){
                    cantidad.requestFocus();
                }else{
                    producto_id.setText("");
                }
            }
        }
    }

    public void enterCantidad(KeyEvent event)  {
        if (event.getCode() == KeyCode.ENTER){
            agregarProduccion();
            producto_id.requestFocus();
        }
    }

    public void agregarProduccion() {
        if (validaDatosAgregar()) {
            ConexionBD.getInstancia().agregarProduccion(id.getText(), producto_id.getText(), cantidad.getText());
            actualizarTabla();
            clear();
            produccionController.actualizarTabla();
        } else {
            General.mensaje(Alert.AlertType.WARNING, ventana, "No se puede agregar la produccion, revise los datos");
        }
    }

    public void dobleClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            removerProduccion();
        }
    }

    public void removerProduccion() {
        if (validaDatosRemover()) {
            ConexionBD.getInstancia().removerProduccion(id.getText(), tablaProduccion.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            actualizarTabla();
            produccionController.actualizarTabla();
        } else {
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione una produccion para remover");
        }
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

    private void cambiarEditable(boolean editable){
        fecha.setDisable(editable);
        nota.setEditable(!editable);
        empleado_id.setEditable(!editable);
        buscarEmpleado.setDisable(editable);
        producto_id.setEditable(editable);
        buscarProducto.setDisable(!editable);
        cantidad.setEditable(editable);
        agregarBoton.setDisable(!editable);
        removerBoton.setDisable(!editable);
        aperturar.setDisable(editable);
    }

    private void setDatos() {
        Produccion produccion = Produccion.getInstancia();
        if (produccion.getId_produccion() != null){
            cambiarEditable(true);
            id.setText(produccion.getId_produccion());
            nota.setText(produccion.getNota());
            empleado_id.setText(produccion.getId_empleado());
            fecha.setValue(produccion.getFecha());
            try {
                empleado.setText(ConexionBD.getInstancia().getEmpleadoById(empleado_id.getText()));
                actualizarTabla();
            } catch (Exception e) {
                ConexionBD.getInstancia().error(e);
            }
        }else{
            cambiarEditable(false);
            fecha.setValue(java.time.LocalDate.now());
        }
    }

    private void actualizarTabla() {
        General.llenarTabla(tablaProduccion,"SubProduccion", "WHERE id_produccion = " + id.getText());
        tablaProduccion.getColumns().remove(4);
    }

    public void loadParentController(ProduccionController produccionController) {
        this.produccionController = produccionController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFormatos();
        setDatos();
    }

}