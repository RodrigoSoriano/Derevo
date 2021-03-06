package code.generales;
import code.ConexionBD;
import code.generales.buscador.BuscadorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;


public class General {
    public static String valor = "";
    public static String getValor() {
        return valor;
    }
    public static void setValor(String valor) {
        General.valor = valor;
    }

    static public void mensaje(Alert.AlertType alerta, String ventana, String mensaje){
        Alert alert = new Alert(alerta);
        alert.setTitle(ventana);
        //alert.setHeaderText(accion + no +" completado");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    static public TextFormatter soloNumero(boolean ... decimales){
        String regex = (decimales.length == 1) ? "([0-9]*)(\\.?)([0-9]?[0-9])?" : "([0-9]*)?";
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches(regex)) {
                return change;
            }
            return null;
        };
        return new TextFormatter<>(new DefaultStringConverter(), null, integerFilter);
    }

    static public void llenarTabla(TableView tabla, String origen, String ... filtro) {
        if (filtro.length == 0){
            filtro = new String[1];
            filtro[0] = "";
        }
        origen = origen.replace(" ", "");
        tabla.getColumns().clear();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        ResultSet rs = ConexionBD.getInstancia().getData(origen, filtro[0]);

        try {
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tabla.getColumns().addAll(col);
            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                data.add(row);

            }
        } catch (SQLException throwables) {
            ConexionBD.getInstancia().error(throwables);
        }

        tabla.setItems(data);
    }

    static public void llenarCombobo(ComboBox combo, String tabla, Integer columnaCodigo, Integer columnaDato, String ... filt) {
        String filtro = "";
        if (filt.length > 0) {
            filtro = filt[0];
        }
        try {
            ResultSet rs = ConexionBD.getInstancia().getData(tabla, filtro);
            combo.getItems().add("");
            while(rs.next()){
                combo.getItems().add(rs.getString(columnaCodigo) + "   |   " + rs.getString(columnaDato));
            }
        } catch (SQLException throwables) {
            ConexionBD.getInstancia().error(throwables);
        }
    }

    public static void abrirBuscador(String nombre, boolean ... soloBuscar) {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(General.class.getResource("/code/generales/buscador/buscador.fxml").openStream());
        } catch (IOException e) {
            ConexionBD.getInstancia().error(e);
        }
        BuscadorController buscadorController = loader.getController();
        buscadorController.llenarVentana(nombre);
        if (soloBuscar.length > 0) {
            buscadorController.soloBuscar(soloBuscar[0]);
        }
        buscadorController.actualizarTabla();
        Stage regStage = new Stage();
        regStage.setTitle(nombre);
        regStage.setScene(new Scene(root, 700, 500));
        regStage.setResizable(false);
        regStage.initModality(Modality.APPLICATION_MODAL);
        regStage.showAndWait();
    }
}
