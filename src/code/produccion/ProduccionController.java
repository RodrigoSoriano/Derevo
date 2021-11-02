package code.produccion;

import code.ConexionBD;
import code.generales.General;
import code.produccion.regProduccion.RegProduccionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProduccionController implements Initializable {
    @FXML
    private TableView tablaProduccion;

    @FXML
    private TextField busqueda;

    @FXML
    private ProduccionController ProduccionController;

    private String ventana = "Produccion";

    private void regProduccion(String titulo){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("regProduccion/regProduccion.fxml").openStream());
            RegProduccionController regProduccionController = loader.getController();
            regProduccionController.loadParentController(this);
            Stage regStage = new Stage();
            regStage.setTitle(titulo);
            regStage.setScene(new Scene(root, 375, 464));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            ConexionBD.getInstancia().error(e);
        }
    }

    public void abrirRegistrarProduccion() {
        Produccion.setInstancia(new Produccion());
        regProduccion("Registro de Produccion");
    }

    public void dobleClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarProduccion();
        }
    }

    public void editarRegistrarProduccion() {
        if(tablaProduccion.getSelectionModel().getSelectedItem() != null){
            ConexionBD.getInstancia().setProduccionHolder(tablaProduccion.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            regProduccion("Edición de Producción");
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione una producción para editar");
        }
    }

    public void deleteProduccion() {
        if (tablaProduccion.getSelectionModel().getSelectedItem() != null) {
            String id = tablaProduccion.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminación de producción");
            alert.setHeaderText("Se procedera a eliminar la producción: " + id);
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                ConexionBD.getInstancia().deleteProduccion(id);
                actualizarTabla();
            }
        } else {
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione una producción para borrar");
        }
    }

    public void actualizarTabla() {
        String busca = busqueda.getText().replace("'", "''");
        General.llenarTabla(tablaProduccion, ventana, "WHERE ID like '%"+busca+"%' or Empleado like '%"+busca+"%' or Fecha like '%"+busca+"%' or Nota like '%"+busca+"%'");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarTabla();
    }
}