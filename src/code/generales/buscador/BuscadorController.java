package code.generales.buscador;

import code.ConeccionBD;
import code.generales.General;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BuscadorController implements Initializable {
    @FXML
    private Button salirBoton;

    @FXML
    private Button nuevoBoton;

    @FXML
    private Button editarBoton;

    @FXML
    private Button borrarBoton;

    @FXML
    private TableView tabla;

    private String ventana = "";
    private boolean soloBuscar = false;

    public void botonNuevo(){

    }

    public void botonEditar(){

    }

    public void botonBorrar(){

    }

    public void dblClic(MouseEvent event){
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 && soloBuscar){
            General.setValor(tabla.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            Stage stage = (Stage) salirBoton.getScene().getWindow();
            stage.close();
        }
    }

    public void botonSalir(){
        General.setValor("");
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    public void actualizarTabla() throws SQLException {
        General.llenarTabla(tabla, ventana);
    }

    public void llenarVentana(String ventana){
        this.ventana = ventana;
    }

    public void soloBuscar(boolean soloBuscar) {
        this.soloBuscar = soloBuscar;
        nuevoBoton.setVisible(false);
        editarBoton.setVisible(false);
        borrarBoton.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
