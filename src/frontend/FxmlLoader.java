package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {

    private Pane view;

    public Pane getPage (String fileName){
        try{
            URL fileUrl = PrincipalController.class.getResource("/frontend/" + fileName + ".fxml");
            if(fileUrl == null){
                throw new java.io.FileNotFoundException("Archivo FXML no encontrado");
            }

            view = new FXMLLoader().load(fileUrl);

        }catch(Exception e){
            System.out.println("Archivo FXML corrupto");
        }
        return view;
    }
}
