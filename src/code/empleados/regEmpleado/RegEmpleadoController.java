package code.empleados.regEmpleado;

import code.ConeccionBD;
import code.empleados.Empleadoo;
import code.empleados.EmpleadoHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
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

    private boolean edicion = false;

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

    private boolean validaDatos(){
        if(     nombres.getText().isBlank() ||
                sueldo.getText().isBlank() ||
                fecha.getValue().toString().isBlank()){
            return false;
        }else{
            return true;
        }
    }

    public void regEmpleadoButton() {
        if(validaDatos()){
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
        if (ConeccionBD.getInstancia().regEmpleado(edicion, id.getText(), cedula.getText(), nombres.getText(), apellidos.getText(), telefono.getText(), fecha.getValue().toString(), sueldo.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(!edicion){
                alert.setTitle("Registro de empleado");
                alert.setHeaderText("Registro completado");
                alert.setContentText("Los datos del empleado han sido registrados correctamente");
                alert.showAndWait();
                clear();
            }else{
                alert.setTitle("Edición de empleado");
                alert.setHeaderText("Edición completada");
                alert.setContentText("Los datos del empleado han sido editados correctamente");
                alert.showAndWait();
                salirBotonOnAction();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(!edicion){
                alert.setTitle("Registro de empleado");
                alert.setHeaderText("No se pudo completar el registro");
                alert.setContentText("Los datos del empleado no fueron registrados");
                alert.showAndWait();
            }else{
                alert.setTitle("Edición de empleado");
                alert.setHeaderText("No se pudo completar la edición");
                alert.setContentText("Los datos del empleado no fueron editados");
                alert.showAndWait();
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
        if (!id.getText().isBlank()){
            edicion = true;
        }
    }
}