package code.principal;

import code.ConexionBD;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {

    private Pane view;

    public Pane getPage (String fileName){
        try{
            URL fileUrl = PrincipalController.class.getResource("/code/" + fileName + "/" + fileName + ".fxml");
            if(fileUrl == null){
                throw new java.io.FileNotFoundException("Archivo FXML no encontrado");
            }

            view = new FXMLLoader().load(fileUrl);

        }catch(Exception e){
            ConexionBD.getInstancia().error(e);
        }
        return view;
    }
}
