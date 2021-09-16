package code.produccion;

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

public class ProduccionController implements Initializable {
    @FXML
    private TableView<ModeloTablaProduccion> tablaProduccion;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_id;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_empleado;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_producto;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_cantidad;

    @FXML
    private TableColumn<ModeloTablaProduccion,String> colum_fecha;

    @FXML
    private TextField busqueda;

    ObservableList<ModeloTablaProduccion> oblist = FXCollections.observableArrayList();

    private void regProduccion(String titulo){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("regEmpleado/regProduccion.fxml"));
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

    public void abrirRegistrarProduccion() {
        Produccion produccion = new Produccion(null,null,null,null,null);
        ProduccionHolder.getInstancia().setProduccion(produccion);
        regProduccion("Registro de Produccion");
    }

    public void dobleClick(MouseEvent event) throws SQLException {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarProduccion();
        }
    }

    public void editarRegistrarProduccion() throws SQLException {
        if(tablaProduccion.getSelectionModel().getSelectedItem() != null){
            ConeccionBD.getInstancia().setProduccionHolder(tablaProduccion.getSelectionModel().getSelectedItem().id);
            regProduccion("Edición de Producción");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edición de producción");
            alert.setHeaderText("Seleccione una producción para editar");
            alert.showAndWait();
        }
    }

    public void deleteProduccion() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminación de producción");
        alert.setHeaderText("Se procedera a eliminar la producción: " + tablaProduccion.getSelectionModel().getSelectedItem().id);
        alert.setContentText("¿Seguro que desea preceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ConeccionBD.getInstancia().deleteEmpleado(tablaProduccion.getSelectionModel().getSelectedItem().id);
            actualizarTabla();
        }
    }

    public void actualizarTabla() throws SQLException {
        oblist.clear();
        ResultSet queryResult = ConeccionBD.getInstancia().getListaProduccion(busqueda.getText());
        while (queryResult.next()){
            oblist.add(new ModeloTablaProduccion(queryResult.getString("id"), queryResult.getString("empleado"), queryResult.getString("producto"), queryResult.getString("cantidad"), queryResult.getString("fecha")));
        }
        colum_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colum_empleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
        colum_producto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colum_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colum_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tablaProduccion.setItems(oblist);
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
