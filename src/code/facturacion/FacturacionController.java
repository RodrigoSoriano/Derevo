package code.facturacion;

import code.generales.General;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class FacturacionController implements Initializable {
    @FXML
    private TableView tablaFactura;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colUnidad;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private TableColumn colImporte;
    @FXML
    private ComboBox cmbEmpleado;


    private void ajustaTabla() {
        colCantidad.prefWidthProperty().bind(tablaFactura.widthProperty().multiply(0.05));
        colUnidad.prefWidthProperty().bind(tablaFactura.widthProperty().multiply(0.05));
        colCodigo.prefWidthProperty().bind(tablaFactura.widthProperty().multiply(0.05));
        colDescripcion.prefWidthProperty().bind(tablaFactura.widthProperty().multiply(0.75));
        colPrecio.prefWidthProperty().bind(tablaFactura.widthProperty().multiply(0.05));
        colImporte.prefWidthProperty().bind(tablaFactura.widthProperty().multiply(0.05));
    }

    private void llenarCombobox() {
        General.llenarCombobo(cmbEmpleado, "Empleado", 1, 3);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ajustaTabla();
        llenarCombobox();
    }
}
