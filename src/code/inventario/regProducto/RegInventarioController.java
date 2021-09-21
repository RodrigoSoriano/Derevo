package code.inventario.regProducto;

import code.ConeccionBD;
import code.General;
import code.inventario.InventarioController;
import code.inventario.Producto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegInventarioController implements Initializable {
    //region Variables FXML
    @FXML
    private Button salirBoton;

    @FXML
    private TextField id;

    @FXML
    private TextField descripcion;

    @FXML
    private TextField nombre;

    @FXML
    private TextField existencia;

    @FXML
    private TextField peso;

    @FXML
    private TextField mano_obra;

    @FXML
    private CheckBox producto_final;

    @FXML
    private CheckBox paga_fundidor;
    //endregion

    private InventarioController inventarioController;

    private boolean edicion = false;

    private String ventana = "Inventario";

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    private boolean validaDatosRegistro(){
        if(     !nombre.getText().isBlank() &&
                !peso.getText().isBlank() &&
                !mano_obra.getText().isBlank() &&
                !existencia.getText().isBlank()
        ){
            return true;
        }else{
            return false;
        }
    }

    public void regProductoButton() throws SQLException {
        if(validaDatosRegistro()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registro de producto");
            alert.setHeaderText("Se procedera a registrar el producto");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                regProducto();
            }
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Datos incompletos");
        }
    }

    private void regProducto() throws SQLException {
        String mensaje = "";
        if (ConeccionBD.getInstancia().regProducto(edicion, id.getText(), nombre.getText(), descripcion.getText(), peso.getText(), mano_obra.getText(), existencia.getText(), producto_final.isSelected(), paga_fundidor.isSelected())){
            inventarioController.actualizarTabla();
            if(!edicion){
                mensaje = "Los datos del prducto han sido registrados correctamente";
                General.mensaje(Alert.AlertType.INFORMATION, ventana, mensaje);
                clear();
            }else{
                mensaje = "Los datos del producto han sido editados correctamente";
                General.mensaje(Alert.AlertType.INFORMATION, ventana, mensaje);
                salirBotonOnAction();
            }
        }else{
            if(!edicion){
                mensaje = "Los datos del prducto no fueron registrados";
            }else{
                mensaje = "Los datos del producto no fueron editados";
            }
            General.mensaje(Alert.AlertType.WARNING, ventana, mensaje);
        }
    }

    private void clear(){
        id.setText("");
        descripcion.setText("");
        nombre.setText("");
        existencia.setText("");
        peso.setText("");
        mano_obra.setText("");
        producto_final.setSelected(true);
        paga_fundidor.setSelected(true);
    }

    public void loadParentController(InventarioController inventarioController) {
        this.inventarioController = inventarioController;
    }

    private void setFormatos(){
        peso.setTextFormatter(General.soloNumero(true));
        mano_obra.setTextFormatter(General.soloNumero(true));
        existencia.setTextFormatter(General.soloNumero());
    }

    private void setDatos() {
        Producto producto = Producto.getInstancia();
        if (producto.getId_producto() != null){
            edicion = true;
            id.setText(producto.getId_producto());
            nombre.setText(producto.getNombre());
            descripcion.setText(producto.getDescripcion());
            peso.setText(producto.getPeso());
            mano_obra.setText(producto.getMano_obra());
            existencia.setText(producto.getExistencia());
            producto_final.setSelected(producto.getProducto_final());
            paga_fundidor.setSelected(producto.getPaga_fundidor());
        }else{
            clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFormatos();
        setDatos();
    }
}