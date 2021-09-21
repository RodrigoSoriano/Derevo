package code;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;


public class General {
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

    static public void llenarTabla(TableView tabla, String origen, String ... filtro) throws SQLException {
        if (filtro.length == 0){
            filtro = new String[1];
            filtro[0] = "";
        }
        tabla.getColumns().clear();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        ResultSet rs = ConeccionBD.getInstancia().getData(origen, filtro[0]);

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

        tabla.setItems(data);
    }
}
