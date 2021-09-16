package code.principal;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PrincipalController {

    @FXML
    private BorderPane panelPrincipal;

    public void empleadosBotonOnAction() {
        FxmlLoader carga = new FxmlLoader();
        Pane view = carga.getPage("empleados");
        panelPrincipal.setCenter(view);
    }

    public void inventarioBotonOnAction() {
        FxmlLoader carga = new FxmlLoader();
        Pane view = carga.getPage("inventario");
        panelPrincipal.setCenter(view);
    }

    public void produccionBotonOnAction() {
        FxmlLoader carga = new FxmlLoader();
        Pane view = carga.getPage("produccion");
        panelPrincipal.setCenter(view);
    }

    public void departamentosBotonOnAction() {
        FxmlLoader carga = new FxmlLoader();
        Pane view = carga.getPage("departamentos");
        panelPrincipal.setCenter(view);
    }

    public void cerrarBotonOnAction() {
        System.exit(0);
    }

}
