package code.inventario.regProducto;

import code.ConeccionBD;
import code.inventario.Producto;
import code.inventario.ProductoHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegInventarioController implements Initializable {
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

    private String oldValue_obra = "";
    private int caretPosition_obra = 0;

    private String oldValue_peso = "";
    private int caretPosition_peso = 0;

    private String oldValue_exist = "";
    private int caretPosition_exist = 0;

    private boolean edicion = false;

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    public void formatoObra() {
        caretPosition_obra = mano_obra.getCaretPosition();
        if (!mano_obra.getText().matches("\\d{0,9}([\\.]\\d{0,2})?")) {
            mano_obra.setText(oldValue_obra);
            mano_obra.positionCaret(caretPosition_obra);
        }else{
            oldValue_obra = mano_obra.getText();
        }
    }

    public void formatoPeso() {
        caretPosition_peso = peso.getCaretPosition();
        if (!peso.getText().matches("\\d{0,9}([\\.]\\d{0,2})?")) {
            peso.setText(oldValue_peso);
            peso.positionCaret(caretPosition_peso);
        }else{
            oldValue_peso = peso.getText();
        }
    }

    public void formatoExist() {
        caretPosition_exist = existencia.getCaretPosition();
        if (!existencia.getText().matches("\\d{0,9}?")) {
            existencia.setText(oldValue_exist);
            existencia.positionCaret(caretPosition_exist);
        }else{
            oldValue_exist = existencia.getText();
        }
    }

    public void regProductoButton() {
        if(!nombre.getText().isBlank() && !peso.getText().isBlank() && !mano_obra.getText().isBlank() && !existencia.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registro de producto");
            alert.setHeaderText("Se procedera a registrar el producto");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                regProducto();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Registro Producto");
            alert.setHeaderText("No se puede registrar");
            alert.setContentText("Revise los campos");
            alert.showAndWait();
        }
    }

    private void regProducto() {
        if (ConeccionBD.getInstancia().regProducto(edicion, id.getText(), nombre.getText(), descripcion.getText(), peso.getText(), mano_obra.getText(), existencia.getText(), producto_final.isSelected(), paga_fundidor.isSelected())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(!edicion){
                alert.setTitle("Registro de producto");
                alert.setHeaderText("Registro completado");
                alert.setContentText("Los datos del prducto han sido registrados correctamente");
                alert.showAndWait();
                clear();
            }else{
                alert.setTitle("Edición de producto");
                alert.setHeaderText("Edición completada");
                alert.setContentText("Los datos del producto han sido editados correctamente");
                alert.showAndWait();
                salirBotonOnAction();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(!edicion){
                alert.setTitle("Registro de producto");
                alert.setHeaderText("No se pudo completar el registro");
                alert.setContentText("Los datos del prducto no fueron registrados");
                alert.showAndWait();
            }else{
                alert.setTitle("Edición de producto");
                alert.setHeaderText("No se pudo completar la edición");
                alert.setContentText("Los datos del producto no fueron editados");
                alert.showAndWait();
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Producto producto = ProductoHolder.getInstancia().getProducto();
        if (!producto.getId_producto().isBlank()){
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
}