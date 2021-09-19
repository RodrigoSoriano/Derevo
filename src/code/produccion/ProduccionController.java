package code.produccion;

import code.ConeccionBD;
import code.General;
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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProduccionController implements Initializable {
    @FXML
    private TableView tablaProduccion;

    @FXML
    private TextField busqueda;

    private String ventana = "Produccion";

    private void regProduccion(String titulo){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("regProduccion/regProduccion.fxml"));
            Stage regStage = new Stage();
            regStage.setTitle(titulo);
            regStage.setScene(new Scene(root, 375, 464));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void abrirRegistrarProduccion() {
        Produccion.setProduccion(new Produccion());
        regProduccion("Registro de Produccion");
    }

    public void dobleClick(MouseEvent event) throws SQLException {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarProduccion();
        }
    }

    public void editarRegistrarProduccion() throws SQLException {
        if(tablaProduccion.getSelectionModel().getSelectedItem() != null){
            ConeccionBD.getInstancia().setProduccionHolder(tablaProduccion.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            regProduccion("Edición de Producción");
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione una producción para editar");
        }
    }

    public void deleteProduccion() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminación de producción");
        alert.setHeaderText("Se procedera a eliminar la producción: " + tablaProduccion.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
        alert.setContentText("¿Seguro que desea preceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ConeccionBD.getInstancia().deleteEmpleado(tablaProduccion.getSelectionModel().getSelectedItem().toString());
            actualizarTabla();
        }
    }

    public void actualizarTabla() throws SQLException {
        General.llenarTabla(tablaProduccion, ventana);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            actualizarTabla();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
