package code.acceder;

import code.ConeccionBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Label errorSesion;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField contrasenaTextField;

    public void manejarTeclasPresionadas(KeyEvent event){
        switch (event.getCode()){
            case ENTER:
                accederBotonOnAction();
                break;
            case TAB:
                if(!usuarioTextField.isFocused()) {
                    contrasenaTextField.requestFocus();
                }else{
                    usuarioTextField.requestFocus();
                }
                break;
        }
    }

    public void accederBotonOnAction() {
        if (usuarioTextField.getText().isBlank()) {
            errorSesion.setText("Porfavor introduzca el usuario");
            usuarioTextField.requestFocus();
        } else if(contrasenaTextField.getText().isBlank()) {
            errorSesion.setText("Porfavor introduzca la contraseña");
            contrasenaTextField.requestFocus();
        }else{
            validarAcceso();
        }
    }

    @FXML
    private Button cancelarBoton;

    public void cancelarBotonOnAction() {
        Stage stage = (Stage) cancelarBoton.getScene().getWindow();
        stage.close();
    }

    public void validarAcceso() {
        if(ConeccionBD.getInstancia().validarAcceso(usuarioTextField.getText(), contrasenaTextField.getText())){
            cancelarBotonOnAction();
            abrirPrincipal();
        }else{
            errorSesion.setText("Usuario o contraseña incorrectos");
        }
    }

    public void abrirPrincipal() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/code/principal/principal.fxml"));
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

    @FXML
    private Button configuracionBoton;

    public void configuracionBotonOnAction() {
    }
}
