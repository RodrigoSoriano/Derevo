package frontend.empleados;

import backend.ConeccionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        try{
            Parent root = FXMLLoader.load(getClass().getResource("regEmpleado/regEmpleado.fxml"));
            Stage regStage = new Stage();
            regStage.setTitle("Registro de Empleado");
            regStage.setScene(new Scene(root, 375, 272));
            regStage.setResizable(false);
            regStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConeccionBD conectar = new ConeccionBD();
        Connection coneccion = conectar.getConnection();

        String verificarAcceso = "select * from Empleado";

        try {
            Statement statement = coneccion.createStatement();
            ResultSet queryResult = statement.executeQuery(verificarAcceso);

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
}
