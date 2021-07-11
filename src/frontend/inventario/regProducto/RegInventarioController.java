package frontend.inventario.regProducto;

import backend.ConeccionBD;
import backend.inventario.Producto;
import backend.inventario.ProductoHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
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

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();

    }

    public void formatoObra() {
        if (!mano_obra.getText().matches("\\d{0,9}([\\.]\\d{0,2})?")) {
            mano_obra.setText(oldValue_obra);
            mano_obra.positionCaret(caretPosition_obra);
        }else{
            oldValue_obra = mano_obra.getText();
            caretPosition_obra = mano_obra.getCaretPosition();
        }
    }

    public void formatoPeso() {
        if (!peso.getText().matches("\\d{0,9}([\\.]\\d{0,2})?")) {
            peso.setText(oldValue_peso);
            peso.positionCaret(caretPosition_peso);
        }else{
            oldValue_peso = peso.getText();
            caretPosition_peso = peso.getCaretPosition();
        }
    }

    public void formatoExist() {
        if (!existencia.getText().matches("\\d{0,9}?")) {
            existencia.setText(oldValue_exist);
            existencia.positionCaret(caretPosition_exist);
        }else{
            oldValue_exist = existencia.getText();
            caretPosition_exist = existencia.getCaretPosition();
        }
    }

    public void regProductoButton() {
        if(true){
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
        ConeccionBD conectar = new ConeccionBD();
        Connection coneccion = conectar.getConnection();
        if(id.getText().isBlank()){
            try{
                Statement statement = coneccion.createStatement();
                statement.executeUpdate("INSERT INTO Producto (nombre, descripcion, peso, mano_obra, existencia) VALUES ('" + nombre.getText() + "', '" + descripcion.getText() + "', '" + peso.getText() + "', '" + mano_obra.getText() + "', '" + existencia.getText() + "')");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro de producto");
                alert.setHeaderText("Registro completado");
                alert.setContentText("Los datos del producto han sido registrados correctamente");

                alert.showAndWait();
                clear();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            try{
                Statement statement = coneccion.createStatement();
                statement.executeUpdate("UPDATE Producto SET nombre = '" + nombre.getText() + "', descripcion = '" + descripcion.getText() + "', peso = '" + peso.getText() + "', mano_obra = '" + mano_obra.getText() + "', existencia = '" + existencia.getText() + "' WHERE id_producto = '" + id.getText() + "'");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edición de producto");
                alert.setHeaderText("Edición completada");
                alert.setContentText("Los datos del producto han sido editados correctamente");

                alert.showAndWait();
                salirBotonOnAction();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
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

        id.setText(producto.getId_producto());
        nombre.setText(producto.getNombre());
        descripcion.setText(producto.getDescripcion());
        peso.setText(producto.getPeso());
        mano_obra.setText(producto.getMano_obra());
        existencia.setText(producto.getExistencia());

        if(id.getText() == null){
            clear();
        }
    }
}
