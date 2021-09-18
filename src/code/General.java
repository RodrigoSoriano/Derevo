package code;

import javafx.scene.control.Alert;

public class General {
    static public void mensaje(Alert.AlertType alerta, String ventana, String mensaje){
        Alert alert = new Alert(alerta);
        alert.setTitle(ventana);
        //alert.setHeaderText(accion + no +" completado");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
