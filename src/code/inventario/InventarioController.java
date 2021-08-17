package code.inventario;

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

public class InventarioController implements Initializable {
    @FXML
    private TableView<ModeloTablaInventario> tablaInventario;

    @FXML
    private TableColumn<ModeloTablaInventario,String> colum_id;

    @FXML
    private TableColumn<ModeloTablaInventario,String> colum_nombre;

    @FXML
    private TableColumn<ModeloTablaInventario,String> colum_desc;

    @FXML
    private TableColumn<ModeloTablaInventario,String> colum_existen;

    @FXML
    private TextField busqueda;

    ObservableList<ModeloTablaInventario> oblist = FXCollections.observableArrayList();

    private void regProducto(String titulo){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("regProducto/regProductoo.fxml"));
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

    public void abrirRegistrarProducto() {
        Producto producto = new Producto("", null, null, null, null, null, true, true);
        ProductoHolder holder = ProductoHolder.getInstancia();
        holder.setProducto(producto);
        regProducto("Registro de Producto");
    }

    public void dobleClick(MouseEvent event) throws SQLException {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarProducto();
        }
    }

    public void editarRegistrarProducto() throws SQLException {
        if(tablaInventario.getSelectionModel().getSelectedItem() != null){
            ConeccionBD.getInstancia().setProductoHolder(tablaInventario.getSelectionModel().getSelectedItem().id);
            regProducto("Edición de Producto");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edición de producto");
            alert.setHeaderText("Seleccione un producto para editar");
            alert.showAndWait();
        }
    }

    public void deleteProducto() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminación de Producto");
        alert.setHeaderText("Se procedera a eliminar el producto: " + tablaInventario.getSelectionModel().getSelectedItem().nombre + " " + tablaInventario.getSelectionModel().getSelectedItem().descripcion);
        alert.setContentText("¿Seguro que desea preceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ConeccionBD.getInstancia().deleteProducto(tablaInventario.getSelectionModel().getSelectedItem().id);
            actualizarTabla();
        }
    }

    public void actualizarTabla() throws SQLException {
        oblist.clear();
        ResultSet queryResult = ConeccionBD.getInstancia().getListaProductos(busqueda.getText());
        while (queryResult.next()){
            oblist.add(new ModeloTablaInventario(queryResult.getString("id_producto"), queryResult.getString("nombre"), queryResult.getString("descripcion"), queryResult.getString("existencia")));
        }
        colum_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colum_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colum_desc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colum_existen.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        tablaInventario.setItems(oblist);
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
