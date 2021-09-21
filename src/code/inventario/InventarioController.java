package code.inventario;

import code.ConeccionBD;
import code.General;
import code.inventario.regProducto.RegInventarioController;
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

public class InventarioController implements Initializable {
    private String ventana = "Inventario";

    @FXML
    private TableView tablaInventario;

    @FXML
    private TextField busqueda;

    private void regProducto(String titulo){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("regProducto/regProductoo.fxml").openStream());
            RegInventarioController regInventarioController = loader.getController();
            regInventarioController.loadParentController(this);
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
        Producto.setInstancia(new Producto());
        regProducto("Registro de Producto");
    }

    public void dobleClick(MouseEvent event) throws SQLException {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarProducto();
        }
    }

    public void editarRegistrarProducto() throws SQLException {
        if(tablaInventario.getSelectionModel().getSelectedItem() != null){
            ConeccionBD.getInstancia().setProductoHolder(tablaInventario.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            regProducto("Edición de Producto");
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione un producto para editar");
        }
    }

    public void deleteProducto() throws SQLException {
        if (tablaInventario.getSelectionModel().getSelectedItem() != null) {
            String id = tablaInventario.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminación de Producto");
            alert.setHeaderText("Se procedera a eliminar el producto: " + id);
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                ConeccionBD.getInstancia().deleteProducto(id);
                actualizarTabla();
            }
        } else {
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione un producto para borrar");
        }
    }

    public void actualizarTabla() throws SQLException {
        String busca = busqueda.getText().replace("'", "''");
        General.llenarTabla(tablaInventario, ventana, "WHERE ID like '%"+busca+"%' or Nombre like '%"+busca+"%' or Descripcion like '%"+busca+"%' or Existencia like '%"+busca+"%'");
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