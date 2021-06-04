package frontend;

import backend.ConeccionBD;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

public class RegEmpleadoController {
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
        if(nombres.getText().isBlank() == false && sueldo.getText().isBlank() == false && fecha.getValue() != null){
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
    }

    private void clear(){
        cedula.setText("");
        nombres.setText("");
        apellidos.setText("");
        telefono.setText("");
        sueldo.setText("");
    }
}
