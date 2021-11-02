package code.principal;

import code.generales.General;
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
        General.abrirBuscador("Departamento");
    }

    public void nacionalidadBotonOnAction()  {
        General.abrirBuscador("Nacionalidad");
    }

    public void clasificacionesProductosBotonOnAction() {
        General.abrirBuscador("Clasificacion Producto");
    }

    public void cerrarBotonOnAction() {
        System.exit(0);
    }

}
