package frontend;


import backend.ConeccionBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Label errorSesion;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField contrasenaTextField;

    public void accederBotonOnAction(ActionEvent event) {
        if (usuarioTextField.getText().isBlank() == false && contrasenaTextField.getText().isBlank() == false) {
            validarAcceso();
        } else {
            errorSesion.setText("Porfavor introduzca usuario y contraseña");
        }
    }

    @FXML
    private Button cancelarBoton;

    public void cancelarBotonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelarBoton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button configuracionBoton;

    public void configuracionBotonOnAction(ActionEvent event) {
    }

    public void validarAcceso() {
        ConeccionBD conectar = new ConeccionBD();
        Connection coneccion = conectar.getConnection();

        String verificarAcceso = "select COUNT(1) from Usuario where usuario = '" + usuarioTextField.getText() + "' and contrasena = '" + contrasenaTextField.getText() + "'";

        try {
            Statement statement = coneccion.createStatement();
            ResultSet queryResult = statement.executeQuery(verificarAcceso);
            while (queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    Stage stage = (Stage) cancelarBoton.getScene().getWindow();
                    stage.close();
                    abrirPrincipal();
                }else{
                    errorSesion.setText("Usuario o contraseña incorrectos");
                }
            }
            coneccion.close();
            System.out.println("sesion con DB cerrada");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void abrirPrincipal() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("principal.fxml"));
            Stage principalStage = new Stage();
            principalStage.setTitle("Magnallum, E.I.R.L.");
            principalStage.setScene(new Scene(root, 355, 426));
            principalStage.setMaximized(true);
            principalStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
