package code.generales.buscador.regBuscador;

import code.ConeccionBD;
import code.generales.General;
import code.generales.buscador.BuscadorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.sql.SQLException;

public class regBuscadorController {
    @FXML
    private Button salirBoton;

    @FXML
    private TextField id;

    @FXML
    private TextField descripcion;

    @FXML
    private TextField nombre;

    @FXML
    private Label titulo;

    private BuscadorController buscadorController;

    private String ventana = "";

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    public void setData(String id, String nombre, String descripcion, String ventana){
        this.id.setText(id);
        this.nombre.setText(nombre);
        this.descripcion.setText(descripcion);
        this.ventana = ventana;
        titulo.setText("Registro de " + ventana);
    }

    public void loadParentController(BuscadorController buscadorController) {
        this.buscadorController = buscadorController;
    }

    private boolean validaData(){
        if(nombre.getText().isBlank()){
            return false;
        }else{
            return true;
        }
    }

    private void clear(){
        id.setText("");
        nombre.setText("");
        descripcion.setText("");
    }

    public void enter(KeyEvent event) throws SQLException {
        if(event.getCode() == KeyCode.ENTER){
            guardar();
        }
    }

    public void guardar() throws SQLException {
        if (validaData()) {
            ConeccionBD.getInstancia().regGeneral1(id.getText(), nombre.getText(), descripcion.getText(), ventana);
            General.mensaje(Alert.AlertType.INFORMATION, ventana, "Registro exitoso.");
            clear();
            buscadorController.actualizarTabla();
        } else {
            General.mensaje(Alert.AlertType.WARNING, ventana, "No se puede guardar, revise los campos.");
        }
    }
}
