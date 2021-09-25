package code.empleados;

import code.ConeccionBD;
import code.empleados.regEmpleado.RegEmpleadoController;
import code.generales.General;
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

public class EmpleadosController implements Initializable {
    private String ventana = "Empleado";

    @FXML
    private TableView tablaEmpleados;

    @FXML
    private TextField busqueda;

    private void regEmpleado(String titulo){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("regEmpleado/regEmpleado.fxml").openStream());
            RegEmpleadoController regEmpleadoController = loader.getController();
            regEmpleadoController.loadParentController(this);
            Stage regStage = new Stage();
            regStage.setTitle(titulo);
            regStage.setScene(new Scene(root, 375, 314));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    
    public void abrirRegistrarEmpleado() {
        Empleadoo.setEmpleadoo(new Empleadoo());
        regEmpleado("Registro de Empleado");
    }

    public void dobleClick(MouseEvent event) throws SQLException {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarEmpleado();
        }
    }

    public void editarRegistrarEmpleado() throws SQLException {
        if(tablaEmpleados.getSelectionModel().getSelectedItem() != null){
            ConeccionBD.getInstancia().setEmpleadoHolder(tablaEmpleados.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            regEmpleado("Edición de Empleado");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edición de empleado");
            alert.setHeaderText("Seleccione un empleado para editar");
            alert.showAndWait();
        }
    }

    public void deleteEmpleado() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminación de empleado");
        alert.setHeaderText("Se procedera a eliminar el empleado: " + tablaEmpleados.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
        alert.setContentText("¿Seguro que desea preceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ConeccionBD.getInstancia().deleteEmpleado(tablaEmpleados.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            actualizarTabla();
        }
    }

    public void actualizarTabla() throws SQLException {
        String busca = busqueda.getText().replace("'", "''");
        General.llenarTabla(tablaEmpleados, ventana, "WHERE ID like '%"+busca+"%' or Identificación like '%"+busca+"%' or Nombres like '%"+busca+"%' or Apellidos like '%"+busca+"%' or Departamento like '%"+busca+"%'");
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
