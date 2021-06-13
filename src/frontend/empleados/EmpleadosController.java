package frontend.empleados;

import backend.ConeccionBD;
import backend.Empleadoo;
import backend.EmpleadoHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

    ObservableList<ModeloTablaEmpleados> oblist = FXCollections.observableArrayList();


    public void abrirRegistrarEmpleado() {
        Empleadoo empleadoo = new Empleadoo(null, null, null, null, null, null, null);
        EmpleadoHolder holder = EmpleadoHolder.getInstancia();
        holder.setEmpleado(empleadoo);

        try{
            Parent root = FXMLLoader.load(getClass().getResource("regEmpleado/regEmpleado.fxml"));
            Stage regStage = new Stage();
            regStage.setTitle("Registro de Empleado");
            regStage.setScene(new Scene(root, 375, 272));
            regStage.setResizable(false);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void editarRegistrarEmpleado() {
        if(tablaEmpleados.getSelectionModel().getSelectedItem() != null){
            ConeccionBD conectar = new ConeccionBD();
            Connection coneccion = conectar.getConnection();

            String query = "select * from Empleado where id_empleado =" + tablaEmpleados.getSelectionModel().getSelectedItem().id;
            Empleadoo empleadoo = new Empleadoo(null, null, null, null, null, null, null);

            try {
                Statement statement = coneccion.createStatement();
                ResultSet queryResult = statement.executeQuery(query);

                while (queryResult.next()){
                    empleadoo.setId_empleado(queryResult.getString("id_empleado"));
                    empleadoo.setCedula(queryResult.getString("cedula"));
                    empleadoo.setNombres(queryResult.getString("nombres"));
                    empleadoo.setApellidos(queryResult.getString("apellidos"));
                    empleadoo.setNumero(queryResult.getString("telefono"));
                    empleadoo.setFecha(queryResult.getDate("fecha").toLocalDate());
                    empleadoo.setSueldo_base(queryResult.getString("sueldo_base"));
                }

                coneccion.close();
                System.out.println("sesion con DB cerrada");
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

            EmpleadoHolder holder = EmpleadoHolder.getInstancia();
            holder.setEmpleado(empleadoo);

            try{
                Parent root = FXMLLoader.load(getClass().getResource("regEmpleado/regEmpleado.fxml"));
                Stage regStage = new Stage();
                regStage.setTitle("Edición de Empleado");
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
            alert.setTitle("Edición de empleado");
            alert.setHeaderText("Seleccione un empleado para editar");
            //alert.setContentText("");
            alert.showAndWait();
        }
    }

    public void deleteEmpleado(){
        String id = tablaEmpleados.getSelectionModel().getSelectedItem().id;
        ConeccionBD conectar = new ConeccionBD();
        Connection coneccion = conectar.getConnection();

        String query = "delete from Empleado where id_empleado = " + id;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminación de empleado");
        alert.setHeaderText("Se procedera a eliminar el empleado: " + tablaEmpleados.getSelectionModel().getSelectedItem().nombres + " " + tablaEmpleados.getSelectionModel().getSelectedItem().apellidos);
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

        String query = "select * from Empleado";
        oblist.clear();

        try {
            Statement statement = coneccion.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while (queryResult.next()){
                oblist.add(new ModeloTablaEmpleados(queryResult.getString("id_empleado"), queryResult.getString("cedula"), queryResult.getString("nombres"), queryResult.getString("apellidos")));
            }

            coneccion.close();
            System.out.println("sesion con DB cerrada");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        tablaEmpleados.setItems(oblist);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarTabla();
    }
}
