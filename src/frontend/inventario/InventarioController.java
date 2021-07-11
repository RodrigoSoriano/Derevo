package frontend.inventario;

import backend.ConeccionBD;
import backend.inventario.Producto;
import backend.inventario.ProductoHolder;
import frontend.empleados.ModeloTablaEmpleados;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public void abrirRegistrarProducto() {
        Producto producto = new Producto(null, null, null, null, null, null);
        ProductoHolder holder = ProductoHolder.getInstancia();
        holder.setProducto(producto);

        try{
            Parent root = FXMLLoader.load(getClass().getResource("regProducto/regProducto.fxml"));
            Stage regStage = new Stage();
            regStage.setTitle("Registro de Producto");
            regStage.setScene(new Scene(root, 375, 272));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void editarRegistrarProducto() {
        if(tablaInventario.getSelectionModel().getSelectedItem() != null){
            ConeccionBD conectar = new ConeccionBD();
            Connection coneccion = conectar.getConnection();

            String query = "select * from Producto where id_producto =" + tablaInventario.getSelectionModel().getSelectedItem().id;
            Producto producto = new Producto(null, null, null, null, null, null);

            try {
                Statement statement = coneccion.createStatement();
                ResultSet queryResult = statement.executeQuery(query);

                while (queryResult.next()){
                    producto.setId_producto(queryResult.getString("id_producto"));
                    producto.setNombre(queryResult.getString("nombre"));
                    producto.setDescripcion(queryResult.getString("descripcion"));
                    producto.setPeso(queryResult.getString("peso"));
                    producto.setMano_obra(queryResult.getString("mano_obra"));
                    producto.setExistencia(queryResult.getString("existencia"));
                }

                coneccion.close();
                System.out.println("sesion con DB cerrada");
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

            ProductoHolder holder = ProductoHolder.getInstancia();
            holder.setProducto(producto);

            try{
                Parent root = FXMLLoader.load(getClass().getResource("regProducto/regProducto.fxml"));
                Stage regStage = new Stage();
                regStage.setTitle("Edición de Producto");
                regStage.setScene(new Scene(root, 375, 272));
                regStage.setResizable(false);
                regStage.initModality(Modality.APPLICATION_MODAL);
                regStage.show();
            } catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edición de producto");
            alert.setHeaderText("Seleccione un producto para editar");
            //alert.setContentText("");
            alert.showAndWait();
        }
    }

    public void deleteProducto(){
        String id = tablaInventario.getSelectionModel().getSelectedItem().id;
        ConeccionBD conectar = new ConeccionBD();
        Connection coneccion = conectar.getConnection();

        String query = "delete from Producto where id_producto = " + id;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminación de Producto");
        alert.setHeaderText("Se procedera a eliminar el producto: " + tablaInventario.getSelectionModel().getSelectedItem().nombre + " " + tablaInventario.getSelectionModel().getSelectedItem().descripcion);
        alert.setContentText("¿Seguro que desea preceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                Statement statement = coneccion.createStatement();
                statement.executeUpdate(query);

                coneccion.close();
                System.out.println("sesion con DB cerrada");
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            actualizarTabla();
        }
    }

    public void actualizarTabla() {
        ConeccionBD conectar = new ConeccionBD();
        Connection coneccion = conectar.getConnection();

        String query = "EXEC BusquedaInventario @Busqueda = '" + busqueda.getText() + "'";
        oblist.clear();

        try {
            Statement statement = coneccion.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while (queryResult.next()){
                oblist.add(new ModeloTablaInventario(queryResult.getString("id_producto"), queryResult.getString("nombre"), queryResult.getString("descripcion"), queryResult.getString("existencia")));
            }

            coneccion.close();
            System.out.println("sesion con DB cerrada");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        colum_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colum_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colum_desc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colum_existen.setCellValueFactory(new PropertyValueFactory<>("existencia"));

        tablaInventario.setItems(oblist);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarTabla();
    }
}
