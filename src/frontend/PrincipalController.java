package frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PrincipalController {

    @FXML
    private BorderPane panelPrincipal;

    public void empleadosBotonOnAction(ActionEvent event) {
        FxmlLoader carga = new FxmlLoader();
        Pane view = carga.getPage("empleados");
        panelPrincipal.setCenter(view);
    }

    public void inventarioBotonOnAction(ActionEvent event) {
        FxmlLoader carga = new FxmlLoader();
        Pane view = carga.getPage("inventario");
        panelPrincipal.setCenter(view);
    }

    public void departamentosBotonOnAction(ActionEvent event) {
        FxmlLoader carga = new FxmlLoader();
        Pane view = carga.getPage("departamentos");
        panelPrincipal.setCenter(view);
    }

    public void cerrarBotonOnAction(ActionEvent event) {
        System.exit(0);
    }

}
