package code.inventario;

import code.ConexionBD;
import code.generales.General;
import code.inventario.regProducto.RegInventarioController;
import code.inventario.salidaInventario.SalidaInventarioController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {
    private String ventana = "Inventario";

    @FXML
    private TableView tablaInventario;

    @FXML
    private TextField busqueda;

    @FXML
    private CheckBox nofinales;

    private void regProducto(String titulo){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("regProducto/regProductoo.fxml").openStream());
            RegInventarioController regInventarioController = loader.getController();
            regInventarioController.loadParentController(this);
            Stage regStage = new Stage();
            regStage.setTitle(titulo);
            regStage.setScene(new Scene(root, 392, 315));
            regStage.setY(0);
            regStage.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3);
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            ConexionBD.getInstancia().error(e);
        }
    }

    public void salidaInventario(){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("salidaInventario/salidaInventario.fxml").openStream());
            SalidaInventarioController salidaInventarioController = loader.getController();
            salidaInventarioController.loadParentController(this);
            if(tablaInventario.getSelectionModel().getSelectedItem() != null) {
                salidaInventarioController.setDatos(tablaInventario.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            }
            Stage movInve = new Stage();
            movInve.setTitle("Salida de Inventario");
            movInve.setScene(new Scene(root));
            movInve.setResizable(false);
            movInve.initModality(Modality.APPLICATION_MODAL);
            movInve.show();
        } catch (Exception e){
            ConexionBD.getInstancia().error(e);
        }
    }

    public void abrirRegistrarProducto() {
        Producto.setInstancia(new Producto());
        regProducto("Registro de Producto");
    }

    public void dobleClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            editarRegistrarProducto();
        }
    }

    public void editarRegistrarProducto() {
        if(tablaInventario.getSelectionModel().getSelectedItem() != null){
            ConexionBD.getInstancia().setProductoHolder(tablaInventario.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1));
            regProducto("Edici??n de Producto");
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione un producto para editar");
        }
    }

    public void deleteProducto() {
        if (tablaInventario.getSelectionModel().getSelectedItem() != null) {
            String id = tablaInventario.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminaci??n de Producto");
            alert.setHeaderText("Se procedera a eliminar el producto: " + id);
            alert.setContentText("??Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                ConexionBD.getInstancia().deleteProducto(id);
                actualizarTabla();
            }
        } else {
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione un producto para borrar");
        }
    }

    public void actualizarTabla() {
        String busca = busqueda.getText().replace("'", "''");
        String nofinal = "AND [Producto Final] = 'SI'" ;
        if (!nofinales.isSelected()) {
            nofinal = "";
        }
        General.llenarTabla(tablaInventario, ventana,
                "WHERE (ID like '%"+busca+"%' OR Clasificacion LIKE '%"+busca+"%' OR Descripcion LIKE '%"+busca+"%' OR Existencia LIKE '%"+busca+"%'" + ") " + nofinal + " ORDER BY Clasificacion");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarTabla();
    }
}