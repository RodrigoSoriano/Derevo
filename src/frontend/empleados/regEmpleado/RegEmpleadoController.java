package frontend.empleados.regEmpleado;

import backend.ConeccionBD;
import backend.empleados.Empleadoo;
import backend.empleados.EmpleadoHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegEmpleadoController implements Initializable {
    @FXML
    private Button salirBoton;

    @FXML
    private DatePicker fecha;

    @FXML
    private TextField id;

    @FXML
    private TextField cedula;

    @FXML
    private TextField nombres;

    @FXML
    private TextField apellidos;

    @FXML
    private TextField telefono;

    @FXML
    private TextField sueldo;

    private String oldValue_sueldo = "";
    private int caretPosition_sueldo = 0;

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();

    }

    public void formatoSueldo() {
        if (!sueldo.getText().matches("\\d{0,9}([\\.]\\d{0,2})?")) {
            sueldo.setText(oldValue_sueldo);
            sueldo.positionCaret(caretPosition_sueldo);
        }else{
            oldValue_sueldo = sueldo.getText();
            caretPosition_sueldo = sueldo.getCaretPosition();
        }
    }

    public void regEmpleadoButton() {
        if(nombres.getText().isBlank() == false && sueldo.getText().isBlank() == false && fecha.getValue().toString().isBlank() == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registro de empleado");
            alert.setHeaderText("Se procedera a registrar el empleado");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                regEmpleado();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Registro Empleado");
            alert.setHeaderText("No se puede registrar");
            alert.setContentText("Revise los campos");

            alert.showAndWait();
        }
    }

    private void regEmpleado() {
        ConeccionBD conectar = new ConeccionBD();
        Connection coneccion = conectar.getConnection();
        if(id.getText().isBlank()){
            try{
                Statement statement = coneccion.createStatement();
                statement.executeUpdate("INSERT INTO Empleado (cedula, nombres, apellidos, telefono, fecha, sueldo_base) VALUES ('" + cedula.getText() + "', '" + nombres.getText() + "', '" + apellidos.getText() + "', '" + telefono.getText() + "', '" + fecha.getValue().toString() + "', " + sueldo.getText() +")");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro de empleado");
                alert.setHeaderText("Registro completado");
                alert.setContentText("Los datos del empleado han sido registrados correctamente");

                alert.showAndWait();
                clear();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            try{
                Statement statement = coneccion.createStatement();
                statement.executeUpdate("UPDATE Empleado SET cedula = '" + cedula.getText() + "', nombres = '" + nombres.getText() + "', apellidos = '" + apellidos.getText() + "', telefono = '" + telefono.getText() + "', fecha = '" + fecha.getValue().toString() + "', sueldo_base = '" + sueldo.getText() +"' WHERE id_empleado = '" + id.getText() + "'");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro de empleado");
                alert.setHeaderText("Registro completado");
                alert.setContentText("Los datos del empleado han sido registrados correctamente");

                alert.showAndWait();
                clear();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }

    }

    private void clear(){
        id.setText("");
        cedula.setText("");
        nombres.setText("");
        apellidos.setText("");
        telefono.setText("");
        sueldo.setText("");
        fecha.setValue(java.time.LocalDate.now());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Empleadoo empleadoo = EmpleadoHolder.getInstancia().getEmpleado();

        id.setText(empleadoo.getId_empleado());
        cedula.setText(empleadoo.getCedula());
        nombres.setText(empleadoo.getNombres());
        apellidos.setText(empleadoo.getApellidos());
        telefono.setText(empleadoo.getNumero());
        sueldo.setText(empleadoo.getSueldo_base());
        if(empleadoo.getFecha() == null){
            fecha.setValue(java.time.LocalDate.now());
            clear();
        }else{
            fecha.setValue(empleadoo.getFecha());
        }
    }
}
