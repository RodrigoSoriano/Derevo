package code.generales.buscador;

import code.ConeccionBD;
import code.generales.General;
import code.generales.buscador.regBuscador.regBuscadorController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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

    @FXML
    private Label titulo;

    private String ventana = "";
    private boolean soloBuscar = false;

    public void botonNuevo(){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/code/generales/buscador/regBuscador/regBuscador.fxml").openStream());
            regBuscadorController regBuscadorController = loader.getController();
            regBuscadorController.loadParentController(this);
            regBuscadorController.setData("", "", "", ventana);
            Stage regStage = new Stage();
            regStage.setTitle(ventana);
            regStage.setScene(new Scene(root, 298, 186));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void botonEditar(){
        String data = tabla.getSelectionModel().getSelectedItem().toString();
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/code/generales/buscador/regBuscador/regBuscador.fxml").openStream());
            regBuscadorController regBuscadorController = loader.getController();
            regBuscadorController.loadParentController(this);
            regBuscadorController.setData(data.split(",")[0].substring(1), data.split(",")[1].substring(1), data.split(",")[2].substring(1), ventana);
            Stage regStage = new Stage();
            regStage.setTitle(ventana);
            regStage.setScene(new Scene(root, 298, 186));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void botonBorrar() throws SQLException {
        String id = tabla.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1);
        ConeccionBD.getInstancia().borrarGeneral1(id, ventana);
        actualizarTabla();
    }

    public void dblClic(MouseEvent event){
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 && tabla.getSelectionModel().getSelectedItem() != null){
            if (soloBuscar) {
                General.setValor(tabla.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
                Stage stage = (Stage) salirBoton.getScene().getWindow();
                stage.close();
            }else{
                botonEditar();
            }
        }
    }

    public void botonSalir(){
        General.setValor("");
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    public void actualizarTabla() throws SQLException {
        General.llenarTabla(tabla, ventana);
        if(ventana.equals("Inventario")){
            tabla.getColumns().remove(tabla.getColumns().size() - 1);
        }
    }

    public void llenarVentana(String ventana){
        this.ventana = ventana;
        titulo.setText("  " + ventana);
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
