package code;

import code.empleados.Empleadoo;
import code.inventario.Producto;
import code.produccion.Produccion;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.*;

public class ConexionBD {
    public Connection BaseDeDatosLink;
    private final static ConexionBD instancia = new ConexionBD();

    private String usuarioBD = "derevo";
    private String contrasenaBD = "abc12345";

    public static ConexionBD getInstancia(){
        return instancia;
    }

    //region ACCESO
    public boolean validarAcceso(String user, String pass) {
        boolean exito = false;

        String verificarAcceso = "select COUNT(1) from Usuario where usuario = '" + user + "' and contrasena = '" + pass + "'";

        try {
            Statement statement = getConnection().createStatement();
            ResultSet queryResult = statement.executeQuery(verificarAcceso);
            while (queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    exito = true;
                }
            }
            getConnection().close();
            return exito;
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return exito;
    }
    //endregion

    //region EMPLEADO
    public boolean regEmpleado(boolean edicion,String id, String cedula, String nombres, String apellidos, String telefono, String fecha, String sueldo, String id_departamento, String id_nacionalidad, boolean inactivo) {
        boolean exito = false;
        if(!edicion){
            try{
                getConnection().createStatement().executeUpdate("INSERT INTO Empleado (cedula, nombres, apellidos, telefono, fecha, sueldo_base, id_departamento, id_nacionalidad, inactivo) " +
                        "VALUES ('" + cedula + "', '" + nombres + "', '" + apellidos + "', '" + telefono + "', '" + fecha + "', " + sueldo + ", " + id_departamento + ", " + id_nacionalidad + ", '" + inactivo +"')");
                exito = true;
            }catch (Exception e){
                error(e);
            }
        }else{
            try{
                getConnection().createStatement().executeUpdate("UPDATE Empleado " +
                        "SET cedula = '" + cedula + "', nombres = '" + nombres + "', apellidos = '" + apellidos + "', telefono = '" + telefono + "', fecha = '" + fecha + "', sueldo_base = '" + sueldo + "', id_departamento = '" + id_departamento + "', id_nacionalidad = '" + id_nacionalidad + "', inactivo = '" + inactivo +"' " +
                        "WHERE id_empleado = '" + id + "'");
                exito = true;
            }catch (Exception e){
                error(e);
            }
        }
        return exito;
    }
    public void setEmpleadoHolder(String id) throws SQLException {
        ResultSet queryResult = ejecutarQuery("select * from Empleado where id_empleado =" + id);
        Empleadoo empleadoo = new Empleadoo();
        while (true){
            assert queryResult != null;
            if (!queryResult.next()) break;
            empleadoo.setId_empleado(queryResult.getString("id_empleado"));
            empleadoo.setCedula(queryResult.getString("cedula"));
            empleadoo.setNombres(queryResult.getString("nombres"));
            empleadoo.setApellidos(queryResult.getString("apellidos"));
            empleadoo.setNumero(queryResult.getString("telefono"));
            empleadoo.setFecha(queryResult.getDate("fecha").toLocalDate());
            empleadoo.setSueldo_base(queryResult.getString("sueldo_base"));
            empleadoo.setId_departamento(queryResult.getString("id_departamento"));
            empleadoo.setId_nacionalidad(queryResult.getString("id_nacionalidad"));
            empleadoo.setInactivo(queryResult.getString("inactivo").equals("1"));
        }
        Empleadoo.setEmpleadoo(empleadoo);
    }
    public void deleteEmpleado(String id){
        ejecutarQuery("delete from Empleado where id_empleado = " + id);
    }
    public String getEmpleadoById(String id) throws SQLException {
        ResultSet query = ejecutarQuery("select top 1 nombres + ' ' + apellidos as resultado from Empleado where id_empleado = " + (id.equals("") ? "0": id));
        if(query.next()){
            return query.getString("resultado");
        }else{
            return "No encontrado";
        }
    }
    //endregion

    //region INVENTARIO
    public boolean regProducto(boolean edicion, String id, String id_clasificacionProducto, String descripcion, String peso, String mano_obra, String existencia, boolean producto_final, boolean paga_fundidor, String precio_costo, String precio_venta, ObservableList<ObservableList> data, String producidas){
        boolean exito = false;
        if(!edicion){
            try{
                boolean dependencia = false;
                if(data != null){
                    dependencia = true;
                }
                getConnection().createStatement().executeUpdate("INSERT INTO Producto (id_clasificacionProducto, descripcion, peso, mano_obra, existencia, producto_final, paga_fundidor, precio_costo, precio_venta, dependencia, producidas) " +
                        "VALUES ('" + id_clasificacionProducto + "', '" + descripcion + "', '" + peso + "', '" + mano_obra + "', '" + existencia + "', '" + producto_final + "', '" + paga_fundidor + "', '" + precio_costo + "', '" + precio_venta + "', '" + dependencia + "', '" + producidas + "')");
                if(data != null){
                    ResultSet rs = ejecutarQuery("SELECT MAX(id_producto) AS id FROM Producto");
                    rs.next();
                    int id_producto = rs.getInt("id");
                    int id_productoDependencia;
                    int cantidad;
                    for (int i = 0; i < data.size(); i++){
                        id_productoDependencia = Integer.parseInt(data.get(i).get(0).toString());
                        cantidad = Integer.parseInt(data.get(i).get(3).toString());
                        getConnection().createStatement().executeUpdate("INSERT INTO Dependencia(id_producto, id_productoDependencia, cantidad)" +
                                "values(" + id_producto + ", " + id_productoDependencia + ", " + cantidad + ")");
                    }
                }
                exito = true;
            }catch (Exception e){
                error(e);
            }
        }else{
            try{
                getConnection().createStatement().executeUpdate("UPDATE Producto SET id_clasificacionProducto = '" + id_clasificacionProducto + "', descripcion = '" + descripcion + "', peso = '" + peso + "', mano_obra = '" + mano_obra + "', existencia = '" + existencia + "', producidas = '" + producidas + "', producto_final = '" + producto_final + "', paga_fundidor = '" + paga_fundidor + "', precio_costo = '" + precio_costo + "', precio_venta = '" + precio_venta + "' "+
                        "WHERE id_producto = '" + id + "'");
                exito = true;
            }catch (Exception e){
                error(e);
            }
        }
        return exito;
    }
    public void setProductoHolder(String id) throws SQLException {
        ResultSet queryResult = ejecutarQuery("select * from Producto where id_producto =" + id);
        Producto producto = new Producto();
        while (true){
            assert queryResult != null;
            if (!queryResult.next()) break;
            producto.setId_producto(queryResult.getString("id_producto"));
            producto.setId_clasificacionProducto(queryResult.getString("id_clasificacionProducto"));
            producto.setDescripcion(queryResult.getString("descripcion"));
            producto.setPeso(queryResult.getString("peso"));
            producto.setMano_obra(queryResult.getString("mano_obra"));
            producto.setExistencia(queryResult.getString("existencia"));
            //producto.setProducidas(queryResult.getString("producidas"));
            producto.setProducto_final(queryResult.getString("producto_final").equals("1"));
            producto.setPaga_fundidor(queryResult.getString("paga_fundidor").equals("1"));
            producto.setPrecio_costo(queryResult.getString("precio_costo"));
            producto.setPrecio_venta(queryResult.getString("precio_venta"));
            producto.setDependencia(queryResult.getString("dependencia").equals("1"));
        }
        Producto.setInstancia(producto);
    }
    public void deleteProducto(String id){
        ejecutarQuery("EXEC BorrarProducto @id_producto = " + id);
    }
    public String getProductoById(String id) throws SQLException {
        ResultSet query = ejecutarQuery("select top 1 descripcion as resultado from Producto where id_producto = " + (id.equals("") ? "0": id));
        if(query.next()){
            return query.getString("resultado");
        }else{
            return "No encontrado";
        }
    }
    public ResultSet getDatosProductoById(String id){
        return ejecutarQuery("select * from vInventario where ID = " + (id.equals("") ? "0": id));
    }
    public void salidaInventario(String fecha, String producto_id, String cantidad, String razon) {
        try {
            getConnection().createStatement().executeUpdate("INSERT INTO salidaInventario(id_producto, cantidad, razon, fecha) VALUES('" + producto_id + "', '" + cantidad + "', '" + razon + "', '" + fecha + "')");
            getConnection().createStatement().executeUpdate("UPDATE Producto SET existencia = (SELECT existencia FROM Producto WHERE id_producto = " + producto_id + ") - " + cantidad + " WHERE id_producto = " + producto_id);
        } catch (SQLException throwables) {
            error(throwables);
        }
    }
    //endregion

    //region PRODUCCION
    public void aperturarProduccion(String id_empelado, String fecha, String nota) throws SQLException {
        ResultSet rs = ejecutarQuery("EXEC AperturarProduccion @id_empleado = " + id_empelado + ", @fecha = '" + fecha + "', @nota = '" + nota + "'");
        rs.next();
        setProduccionHolder(rs.getString("id_produccion"));
    }
    public void deleteProduccion(String id){
        ejecutarQuery("EXEC BorrarProduccion @id_produccion = " + id);
    }
    public void setProduccionHolder(String id) throws SQLException {
        ResultSet queryResult = ejecutarQuery("select * from Produccion where id_produccion = " + id);
        Produccion produccion = new Produccion();
        while (true){
            assert queryResult != null;
            if (!queryResult.next()) break;
            produccion.setId_produccion(queryResult.getString("id_produccion"));
            produccion.setId_empleado(queryResult.getString("id_empleado"));
            produccion.setFecha(queryResult.getDate("fecha").toLocalDate());
            produccion.setNota(queryResult.getString("nota"));
        }
        Produccion.setInstancia(produccion);
    }
    public void agregarProduccion(String produccion_id, String producto_id, String cantidad){
        ejecutarQuery("EXEC AgregarProduccion @id_produccion = " + produccion_id + ", @id_producto = " + producto_id + ", @cantidad = " + cantidad);
    }
    public void removerProduccion(String produccion_id, String producto_id){
        ejecutarQuery("EXEC RemoverProduccion @id_produccion = " + produccion_id + ", @id_producto = " + producto_id);
    }
    //endregion

    //region GENERAL
    public boolean regGeneral1(String id, String nombre, String descripcion, String ventana){
        ventana = ventana.replace(" ", "");
        if (id.isBlank()){
            id = "0";
        }
        try {
            ejecutarQuery("EXEC Guardar" + ventana + " @id_" + ventana + " = " + id + ", @nombre = '" + nombre + "', @descripcion = '" + descripcion + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean borrarGeneral1(String id, String ventana) {
        ventana = ventana.replace(" ", "");
        if (id.isBlank()){
            id = "0";
        }
        try {
            ejecutarQuery("EXEC Borrar" + ventana + " @id_" + ventana + " = " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //endregion

    //region OTROS
    public void testConection(){
        try {
            getConnection().close();
        } catch (Exception e) {
            error(e);
        }
    }
    public ResultSet getData(String origen, String filtro) {
        return ejecutarQuery("select * from v" + origen + " " + filtro);
    }
    private ResultSet ejecutarQuery(String query){
        try {
            Statement statement = getConnection().createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            getConnection().close();
            return queryResult;
        } catch (Exception e) {
            error(e);
        }
        return null;
    }
    public Connection getConnection(){
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Derevo";
        try{
            BaseDeDatosLink = DriverManager.getConnection(url, usuarioBD, contrasenaBD);
        }catch (Exception e){
            error(e);
        }

        return  BaseDeDatosLink;
    }
    public void error(Exception e){
        System.out.println("e.printStackTrace()");
        e.printStackTrace();
        System.out.println("e.getCause()");
        e.getCause();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Base de datos");
        alert.setHeaderText("Error en la base de datos");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    //endregion
}