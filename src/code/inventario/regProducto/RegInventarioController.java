package code.inventario.regProducto;

import code.ConeccionBD;
import code.generales.General;
import code.inventario.InventarioController;
import code.inventario.Producto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegInventarioController implements Initializable {
    //region Variables FXML
    @FXML
    private Button guardarBoton;

    @FXML
    private Button salirBoton;

    @FXML
    private TextField id;

    @FXML
    private TextField descripcion;

    @FXML
    private TextField existencia;

    @FXML
    private TextField producidas;

    @FXML
    private TextField peso;

    @FXML
    private TextField mano_obra;

    @FXML
    private CheckBox producto_final;

    @FXML
    private CheckBox paga_fundidor;

    @FXML
    private ComboBox clasificacion;

    @FXML
    private TextField precio_costo;

    @FXML
    private TextField precio_venta;

    @FXML
    private CheckBox dependencia;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Button boton1;

    @FXML
    private Button boton2;

    @FXML
    private Button boton3;

    @FXML
    private Button botonDependencias;

    @FXML
    private TextField id_productoDependencia;

    @FXML
    private TextField productoDependencia;

    @FXML
    private TextField cantidadDependencia;

    @FXML
    private TableView tablaDependencia;

    @FXML
    private BorderPane panel;

    @FXML
    private BorderPane panel2;
    //endregion

    private InventarioController inventarioController;

    ObservableList<ObservableList> data = FXCollections.observableArrayList();

    private boolean edicion = false;

    private String ventana = "Inventario";

    public void salirBotonOnAction() {
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        stage.close();
    }

    private boolean validaDatosRegistro(){
        if(     clasificacion.getSelectionModel().getSelectedItem().toString().isBlank() ||
                peso.getText().isBlank() ||
                mano_obra.getText().isBlank() ||
                existencia.getText().isBlank()
        ){
            return false;
        }else{
            return true;
        }
    }

    public void regProductoButton() throws SQLException {
        if(validaDatosRegistro()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registro de producto");
            alert.setHeaderText("Se procedera a registrar el producto");
            alert.setContentText("¿Seguro que desea preceder?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                regProducto();
            }
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "Datos incompletos");
        }
    }

    private void regProducto() throws SQLException {
        String mensaje = "";
        if(!dependencia.isSelected()){
            data = null;
        }
        if (ConeccionBD.getInstancia().regProducto(edicion, id.getText(), clasificacion.getSelectionModel().getSelectedItem().toString().split("|")[0],
                descripcion.getText(), peso.getText(), mano_obra.getText(), existencia.getText(), producto_final.isSelected(), paga_fundidor.isSelected(),
                precio_costo.getText(), precio_venta.getText(), data, producidas.getText())){
            inventarioController.actualizarTabla();
            if(!edicion){
                mensaje = "Los datos del prducto han sido registrados correctamente";
                General.mensaje(Alert.AlertType.INFORMATION, ventana, mensaje);
                clear();
                tieneDependencias();
            }else{
                mensaje = "Los datos del producto han sido editados correctamente";
                General.mensaje(Alert.AlertType.INFORMATION, ventana, mensaje);
                salirBotonOnAction();
            }
        }else{
            if(!edicion){
                mensaje = "Los datos del prducto no fueron registrados";
            }else{
                mensaje = "Los datos del producto no fueron editados";
            }
            General.mensaje(Alert.AlertType.WARNING, ventana, mensaje);
        }
    }

    private void clear(){
        id.setText("");
        descripcion.setText("");
        clasificacion.getSelectionModel().select(0);
        existencia.setText("0");
        producidas.setText("0");
        peso.setText("0");
        mano_obra.setText("0");
        producto_final.setSelected(true);
        paga_fundidor.setSelected(true);
        precio_costo.setText("0");
        precio_venta.setText("0");
        dependencia.setSelected(false);
        tablaDependencia.getItems().clear();
        if (data != null) {
            data.clear();
        }
    }

    private void clearDependencia(){
        id_productoDependencia.setText("");
        productoDependencia.setText("");
        cantidadDependencia.setText("");
    }

    public void loadParentController(InventarioController inventarioController) {
        this.inventarioController = inventarioController;
    }

    private void setFormatos(){
        peso.setTextFormatter(General.soloNumero(true));
        mano_obra.setTextFormatter(General.soloNumero(true));
        existencia.setTextFormatter(General.soloNumero());
        producidas.setTextFormatter(General.soloNumero());
        id_productoDependencia.setTextFormatter(General.soloNumero());
        cantidadDependencia.setTextFormatter(General.soloNumero());
    }

    private void enableDependencias(boolean cargar){
        label1.setVisible(cargar);
        label2.setVisible(cargar);
        label3.setVisible(cargar);
        boton1.setVisible(cargar);
        boton2.setVisible(cargar);
        boton3.setVisible(cargar);
        id_productoDependencia.setVisible(cargar);
        productoDependencia.setVisible(cargar);
        cantidadDependencia.setVisible(cargar);
        tablaDependencia.setVisible(cargar);
        panel2.setVisible(cargar);
    }

    private void loadDependencias(boolean cargar){
        Stage stage = (Stage) salirBoton.getScene().getWindow();
        double pantalla;
        if (cargar) {
            pantalla = 595;
        }else{
            pantalla = 350;
        }
        stage.setHeight(pantalla);
        enableDependencias(cargar);
    }

    public void tieneDependencias(){
        loadDependencias(dependencia.isSelected());
        botonDependencias.setVisible(dependencia.isSelected());
        if(!label1.isVisible()){
            botonDependencias.setText("Mostrar dependencias");
        }else{
            botonDependencias.setText("Ocultar dependencias");
        }
        clearDependencia();
    }

    public void botonDependencia(){
        if(label1.isVisible()){
            loadDependencias(false);
            botonDependencias.setText("Mostrar dependencias");
        }else{
            loadDependencias(true);
            botonDependencias.setText("Ocultar dependencias");
        }
        clearDependencia();
    }

    public void buscarProducto() throws SQLException, IOException {
        General.abrirBuscador("Inventario", true);
        if (!General.getValor().isBlank()) {
            id_productoDependencia.setText(General.getValor());
            productoDependencia.setText(ConeccionBD.getInstancia().getProductoById(General.getValor()));
            cantidadDependencia.requestFocus();
        }
    }

    public void llenarProducto(KeyEvent event) throws SQLException {
        productoDependencia.setText("");
        if(event.getCode() == KeyCode.ENTER) {
            productoDependencia.setText(ConeccionBD.getInstancia().getProductoById(id_productoDependencia.getText()));
            if (!productoDependencia.getText().equals("No encontrado")){
                cantidadDependencia.requestFocus();
            }else{
                id_productoDependencia.setText("");
            }
        }
    }

    public void enterCantidad(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER){
            agregarDependencia();
            id_productoDependencia.requestFocus();
        }
    }

    private boolean validaAgregarDependencia(){
        if(     id_productoDependencia.getText().isBlank() ||
                productoDependencia.getText().isBlank() ||
                cantidadDependencia.getText().isBlank()){
            return false;
        }else{
            return true;
        }
    }

    public void agregarDependencia() throws SQLException {
        if (validaAgregarDependencia()) {
            ResultSet rs = ConeccionBD.getInstancia().getDatosProductoById(id_productoDependencia.getText());
            if(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("ID"));
                row.add(rs.getString("Clasificacion"));
                row.add(rs.getString("Descripcion"));

                for (int i = 0; i < data.size(); i++){
                    if(row.get(0).equals(data.get(i).get(0))){
                        row.add(Integer.toString(Integer.parseInt(cantidadDependencia.getText()) + Integer.parseInt(data.get(i).get(3).toString())));
                        data.remove(i);
                    }
                }

                if (row.size() == 3) {
                    row.add(cantidadDependencia.getText());
                }
                data.add(row);
            }
            actualizarTabla(false);
            clearDependencia();
        }else{
            General.mensaje(Alert.AlertType.WARNING, ventana, "No se puede agregar dependencia, revise los campos.");
        }
    }

    public void removerDependencia() throws SQLException {
        if (tablaDependencia.getSelectionModel().getSelectedItem() != null) {
            for (int i = 0; i < data.size(); i++){
                if(tablaDependencia.getSelectionModel().getSelectedItem().toString().split(",")[0].substring(1).equals(data.get(i).get(0))){
                    data.remove(i);
                }
            }
            actualizarTabla(false);
        } else {
            General.mensaje(Alert.AlertType.WARNING, ventana, "Seleccione una dependencia para remover");
        }
    }

    private void setDatos() throws SQLException {
        llenarCombobox();
        enableDependencias(false);
        Producto producto = Producto.getInstancia();
        if (producto.getId_producto() != null){
            edicion = true;
            id.setText(producto.getId_producto());
            String id_clasificacion = producto.getId_clasificacionProducto();
            for (int i = 0; i < clasificacion.getItems().size(); i++){
                if(clasificacion.getItems().get(i).toString().split("|")[0].equals(id_clasificacion)){
                    clasificacion.getSelectionModel().select(i);
                }
            }
            descripcion.setText(producto.getDescripcion());
            peso.setText(producto.getPeso());
            mano_obra.setText(producto.getMano_obra());
            existencia.setText(producto.getExistencia());
            producidas.setText(producto.getProducidas());
            producto_final.setSelected(producto.getProducto_final());
            paga_fundidor.setSelected(producto.getPaga_fundidor());
            precio_costo.setText(producto.getPrecio_costo());
            precio_venta.setText(producto.getPrecio_venta());
            dependencia.setSelected(producto.isDependencia());
        }else{
            clear();
        }
        botonDependencias.setVisible(dependencia.isSelected());

        String[] columnas = {"ID", "Clasificación", "Producto", "Cantidad"};
        for(int i=0 ; i<columnas.length; i++){
            final int j = i;
            TableColumn col = new TableColumn(columnas[i]);
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            tablaDependencia.getColumns().addAll(col);
        }
        actualizarTabla(dependencia.isSelected());
    }

    private void llenarCombobox() throws SQLException {
        ResultSet rs = ConeccionBD.getInstancia().getData("ClasificacionProducto", "");
        clasificacion.getItems().add("");
        while(rs.next()){
            clasificacion.getItems().add(rs.getString(1) + "   |   " + rs.getString(2));
        }
    }

    private void actualizarTabla(boolean database) throws SQLException {
        if (database){
            General.llenarTabla(tablaDependencia,"Dependencia", "WHERE id_producto = " + id.getText());
            tablaDependencia.getColumns().remove(4);
            data = tablaDependencia.getItems();
        }else{
            tablaDependencia.setItems(data);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFormatos();
        try {
            setDatos();
        } catch (SQLException throwables) {
            ConeccionBD.getInstancia().error(throwables);
        }
    }
}