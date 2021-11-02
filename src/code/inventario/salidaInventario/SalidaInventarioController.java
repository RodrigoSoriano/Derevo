package code.inventario.salidaInventario;

import code.ConexionBD;
import code.generales.General;
import code.inventario.InventarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalidaInventarioController implements Initializable {
    private String ventana = "Salida de inventario";
    private InventarioController inventarioController;
    @FXML
    private Button salirBoton;

    @FXML
    private TextField producto_id;

    @FXML
    private TextField producto;

    @FXML
    private TextField existencia;

    @FXML
    private TextField cantidad;

    @FXML
    private TextField razon;

    @FXML
    private DatePicker fecha;

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    public void BuscarProducto() throws SQLException, IOException {
        General.abrirBuscador("Inventario", true);
        if (!General.getValor().isBlank()) {
            setDatos(General.getValor());
            cantidad.requestFocus();
        }
    }

    public void llenarProducto(KeyEvent event) throws SQLException {
        if (producto_id.isEditable()) {
            producto.setText("");
            existencia.setText("");
            if(event.getCode() == KeyCode.ENTER) {
                producto.setText(ConexionBD.getInstancia().getProductoById(producto_id.getText()));
                if (!producto.getText().equals("No encontrado")){
                    setDatos(producto_id.getText());
                    cantidad.requestFocus();
                }else{
                    producto_id.setText("");
                    existencia.setText("");
                }
            }
        }
    }

    public void setDatos(String id) throws SQLException {
        ResultSet RS = ConexionBD.getInstancia().getDatosProductoById(id);
        while (true){
            assert RS != null;
            if (!RS.next()) break;
            producto_id.setText(RS.getString("ID"));
            producto.setText(RS.getString("Descripcion"));
            existencia.setText(RS.getString("Existencia"));
        }
    }

    private void setFormatos() {
        producto_id.setTextFormatter(General.soloNumero());
        cantidad.setTextFormatter(General.soloNumero());
    }

    public void registrarBoton() throws SQLException, IOException {
        if(validaDatosRegistro()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(ventana);
            alert.setHeaderText("Se procederá a dar salida de inventario");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                ConexionBD.getInstancia().salidaInventario(fecha.getValue().toString(), producto_id.getText(), cantidad.getText(), razon.getText());
                inventarioController.actualizarTabla();
                salirBotonOnAction();
            }
        }
    }

    private boolean validaDatosRegistro() {
        if(     producto_id.getText().isBlank() ||
                producto.getText().equals("No encontrado") ||
                cantidad.getText().isBlank()
        ){
            General.mensaje(Alert.AlertType.WARNING, ventana, "Datos incompletos.");
            return false;
        }
        if(Integer.parseInt(cantidad.getText()) > Integer.parseInt(existencia.getText())){
            General.mensaje(Alert.AlertType.WARNING, ventana, "No puede dar salida a mas cantidad de la existente.");
            return false;
        }
        return true;
    }

    public void loadParentController(InventarioController inventarioController) {
        this.inventarioController = inventarioController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fecha.setValue(java.time.LocalDate.now());
        setFormatos();
    }
}
