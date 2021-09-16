package code.empleados;

import code.ConeccionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmpleadosController implements Initializable {
    @FXML
    private TableView<ModeloTablaEmpleados> tablaEmpleados;

    @FXML
    private TableColumn<ModeloTablaEmpleados,String> id;

    @FXML
    private TableColumn<ModeloTablaEmpleados,String> cedula;

    @FXML
    private TableColumn<ModeloTablaEmpleados,String> nombres;

    @FXML
    private TableColumn<ModeloTablaEmpleados,String> apellidos;

    @FXML
    private TextField busqueda;

    ObservableList<ModeloTablaEmpleados> oblist = FXCollections.observableArrayList();

    private void regEmpleado(String titulo){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("regEmpleado/regEmpleado.fxml"));
            Stage regStage = new Stage();
            regStage.setTitle(titulo);
            regStage.setScene(new Scene(root, 375, 272));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    
    public void abrirRegistrarEmpleado() {
        Empleadoo empleadoo = new Empleadoo(null, null, null, null, null, null, null);
        EmpleadoHolder.getInstancia().setEmpleado(empleadoo);
        regEmpleado("Registro de Empleado");
    }

    public void dobleClick(MouseEvent event) throws SQLException {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarEmpleado();
        }
    }

    public void editarRegistrarEmpleado() throws SQLException {
        if(tablaEmpleados.getSelectionModel().getSelectedItem() != null){
            ConeccionBD.getInstancia().setEmpleadoHolder(tablaEmpleados.getSelectionModel().getSelectedItem().id);
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
        alert.setHeaderText("Se procedera a eliminar el empleado: " + tablaEmpleados.getSelectionModel().getSelectedItem().nombres + " " + tablaEmpleados.getSelectionModel().getSelectedItem().apellidos);
        alert.setContentText("¿Seguro que desea preceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ConeccionBD.getInstancia().deleteEmpleado(tablaEmpleados.getSelectionModel().getSelectedItem().id);
            actualizarTabla();
        }
    }

    public void actualizarTabla() throws SQLException {
        oblist.clear();
        ResultSet queryResult = ConeccionBD.getInstancia().getListaEmpleados(busqueda.getText());
        while (queryResult.next()){
            oblist.add(new ModeloTablaEmpleados(queryResult.getString("id_empleado"), queryResult.getString("cedula"), queryResult.getString("nombres"), queryResult.getString("apellidos")));
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        tablaEmpleados.setItems(oblist);
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
