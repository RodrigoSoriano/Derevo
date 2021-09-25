package code.principal;

import code.generales.General;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;

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

    public void departamentosBotonOnAction() throws SQLException, IOException {
        General.abrirBuscador("Departamento");
    }

    public void nacionalidadBotonOnAction() throws SQLException, IOException {
        General.abrirBuscador("Nacionalidad");
    }

    public void clasificacionesProductosBotonOnAction() throws SQLException, IOException {
        General.abrirBuscador("Clasificacion Producto");
    }

    public void cerrarBotonOnAction() {
        System.exit(0);
    }

}
