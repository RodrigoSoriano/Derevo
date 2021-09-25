package code.inventario.regProducto;

import code.ConeccionBD;
import code.generales.General;
import code.inventario.InventarioController;
import code.inventario.Producto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
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
    private TextField existencia;

    @FXML
    private TextField peso;

    @FXML
    private TextField mano_obra;

    @FXML
    private CheckBox producto_final;

    @FXML
    private CheckBox paga_fundidor;

    @FXML
    private ComboBox clasificacion;

    @FXML
    private TextField precio_costo;

    @FXML
    private TextField precio_venta;
    //endregion

    private InventarioController inventarioController;

    private boolean edicion = false;

    private String ventana = "Inventario";

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    private boolean validaDatosRegistro(){
        if(     clasificacion.getSelectionModel().getSelectedItem().toString().isBlank() ||
                peso.getText().isBlank() ||
                mano_obra.getText().isBlank() ||
                existencia.getText().isBlank()
        ){
            return false;
        }else{
            return true;
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
        if (ConeccionBD.getInstancia().regProducto(edicion, id.getText(), clasificacion.getSelectionModel().getSelectedItem().toString().split("|")[0],
                descripcion.getText(), peso.getText(), mano_obra.getText(), existencia.getText(), producto_final.isSelected(), paga_fundidor.isSelected(),
                precio_costo.getText(), precio_venta.getText())){
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
        clasificacion.getSelectionModel().select(0);
        existencia.setText("0");
        peso.setText("0");
        mano_obra.setText("0");
        producto_final.setSelected(true);
        paga_fundidor.setSelected(true);
        precio_costo.setText("0");
        precio_venta.setText("0");
    }

    public void loadParentController(InventarioController inventarioController) {
        this.inventarioController = inventarioController;
    }

    private void setFormatos(){
        peso.setTextFormatter(General.soloNumero(true));
        mano_obra.setTextFormatter(General.soloNumero(true));
        existencia.setTextFormatter(General.soloNumero());
    }

    private void setDatos() throws SQLException {
        llenarCombobox();
        Producto producto = Producto.getInstancia();
        if (producto.getId_producto() != null){
            edicion = true;
            id.setText(producto.getId_producto());
            String id_clasificacion = producto.getId_clasificacionProducto();
            for (int i = 0; i < clasificacion.getItems().size(); i++){
                if(clasificacion.getItems().get(i).toString().split("|")[0].equals(id_clasificacion)){
                    clasificacion.getSelectionModel().select(i);
                }
            }
            descripcion.setText(producto.getDescripcion());
            peso.setText(producto.getPeso());
            mano_obra.setText(producto.getMano_obra());
            existencia.setText(producto.getExistencia());
            producto_final.setSelected(producto.getProducto_final());
            paga_fundidor.setSelected(producto.getPaga_fundidor());
            precio_costo.setText(producto.getPrecio_costo());
            precio_venta.setText(producto.getPrecio_venta());
        }else{
            clear();
        }
    }

    private void llenarCombobox() throws SQLException {
        ResultSet rs = ConeccionBD.getInstancia().getData("ClasificacionProducto", "");
        clasificacion.getItems().add("");
        while(rs.next()){
            clasificacion.getItems().add(rs.getString(1) + "   |   " + rs.getString(2));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFormatos();
        try {
            setDatos();
        } catch (SQLException throwables) {
            ConeccionBD.getInstancia().error(throwables);
        }
    }
}