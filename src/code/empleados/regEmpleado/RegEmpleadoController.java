package code.empleados.regEmpleado;

import code.ConexionBD;
import code.empleados.Empleadoo;
import code.empleados.EmpleadosController;
import code.generales.General;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @FXML
    private ComboBox departamento;

    @FXML
    private  ComboBox nacionalidad;

    @FXML
    private CheckBox inactivo;

    private boolean edicion = false;

    private EmpleadosController empleadosController;

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
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
        if (ConexionBD.getInstancia().regEmpleado(edicion, id.getText(), cedula.getText(), nombres.getText(), apellidos.getText(), telefono.getText(), fecha.getValue().toString(),
                sueldo.getText(), departamento.getSelectionModel().getSelectedItem().toString().split("|")[0], nacionalidad.getSelectionModel().getSelectedItem().toString().split("|")[0],
                inactivo.isSelected())){

            empleadosController.actualizarTabla();

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

    public void loadParentController(EmpleadosController empleadosController) {
        this.empleadosController = empleadosController;
    }

    private void clear(){
        id.setText("");
        cedula.setText("");
        nombres.setText("");
        apellidos.setText("");
        telefono.setText("");
        sueldo.setText("");
        fecha.setValue(java.time.LocalDate.now());
        departamento.getSelectionModel().select(0);
        nacionalidad.getSelectionModel().select(0);
        inactivo.setSelected(false);
    }

    private void setFormatos(){
        sueldo.setTextFormatter(General.soloNumero(true));
    }

    private void setDatos(){
        llenarCombobox();
        Empleadoo empleadoo = Empleadoo.getEmpleadoo();
        if (empleadoo.getId_empleado() != null){
            edicion = true;
            String id_departamento = empleadoo.getId_departamento();
            String id_nacionalidad = empleadoo.getId_nacionalidad();
            for (int i = 0; i < departamento.getItems().size(); i++) {
                if (departamento.getItems().get(i).toString().split("|")[0].equals(id_departamento)) {
                    departamento.getSelectionModel().select(i);
                }
            }
            id.setText(empleadoo.getId_empleado());
            cedula.setText(empleadoo.getCedula());
            nombres.setText(empleadoo.getNombres());
            apellidos.setText(empleadoo.getApellidos());
            telefono.setText(empleadoo.getNumero());
            sueldo.setText(empleadoo.getSueldo_base());
            fecha.setValue(empleadoo.getFecha());
            for (int i = 0; i < nacionalidad.getItems().size(); i++){
                if(nacionalidad.getItems().get(i).toString().split("|")[0].equals(id_nacionalidad)){
                    nacionalidad.getSelectionModel().select(i);
                }
            }
            inactivo.setSelected(empleadoo.getInactivo());
        }else{
            clear();
        }
    }

    private void llenarCombobox() {
        General.llenarCombobo(departamento, "Departamento", 1, 2);
        General.llenarCombobo(nacionalidad, "Nacionalidad", 1, 2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFormatos();
        setDatos();
    }
}