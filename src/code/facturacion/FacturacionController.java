package code.facturacion;

import code.generales.General;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class FacturacionController implements Initializable {
    //region Variables FXML
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
    @FXML
    private ComboBox cmbTipoFactura;
    @FXML
    private ComboBox cmbCondicion;
    @FXML
    private ComboBox cmbFormaPago;
    @FXML
    private DatePicker fecha;
    //endregion

    public void FacturaTipoBotonOnAction()  {
        General.abrirBuscador("Factura Tipo");
    }

    public void FacturaCondicionBotonOnAction()  {
        General.abrirBuscador("Factura Condicion");
    }

    public void FacturaFormaPagoBotonOnAction()  {
        General.abrirBuscador("Factura Forma Pago");
    }

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
        General.llenarCombobo(cmbTipoFactura, "FacturaTipo", 1, 2);
        General.llenarCombobo(cmbCondicion, "FacturaCondicion", 1, 2);
        General.llenarCombobo(cmbFormaPago, "FacturaFormaPago", 1, 2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ajustaTabla();
        llenarCombobox();
        fecha.setValue(java.time.LocalDate.now());
    }
}
