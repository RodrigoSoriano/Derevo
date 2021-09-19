package code;

import code.empleados.EmpleadoHolder;
import code.empleados.Empleadoo;
import code.inventario.Producto;
import code.inventario.ProductoHolder;
import code.produccion.Produccion;
import javafx.scene.control.Alert;
import java.sql.*;

public class ConeccionBD {
    public Connection BaseDeDatosLink;
    private final static ConeccionBD instancia = new ConeccionBD();

    public static ConeccionBD getInstancia(){
        return instancia;
    }

    public Connection getConnection(){
        String usuarioBD = "derevo";
        String contrasenaBD = "abc12345";
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Derevo";
        try{
            BaseDeDatosLink = DriverManager.getConnection(url, usuarioBD, contrasenaBD);
        }catch (Exception e){
            error(e);
        }

        return  BaseDeDatosLink;
    }

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

    //EMPLEADO
    public boolean regEmpleado(boolean edicion,String id, String cedula, String nombres, String apellidos, String telefono, String fecha, String sueldo) {
        boolean exito = false;
        if(!edicion){
            try{
                getConnection().createStatement().executeUpdate("INSERT INTO Empleado (cedula, nombres, apellidos, telefono, fecha, sueldo_base) " +
                        "VALUES ('" + cedula + "', '" + nombres + "', '" + apellidos + "', '" + telefono + "', '" + fecha + "', " + sueldo +")");
                exito = true;
            }catch (Exception e){
                error(e);
            }
        }else{
            try{
                getConnection().createStatement().executeUpdate("UPDATE Empleado " +
                        "SET cedula = '" + cedula + "', nombres = '" + nombres + "', apellidos = '" + apellidos + "', telefono = '" + telefono + "', fecha = '" + fecha + "', sueldo_base = '" + sueldo +"' " +
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
        Empleadoo empleadoo = new Empleadoo(null, null, null, null, null, null, null);
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
        }
        EmpleadoHolder.getInstancia().setEmpleado(empleadoo);
    }
    public void deleteEmpleado(String id){
        ejecutarQuery("delete from Empleado where id_empleado = " + id);
    }
    public ResultSet getListaEmpleados(String busqueda){
        return ejecutarQuery("EXEC BusquedaEmpleado @Busqueda = '" + busqueda.replace("'", "''") + "'");
    }
    public String getEmpleadoById(String id) throws SQLException {
        ResultSet query = ejecutarQuery("select top 1 nombres + ' ' + apellidos as resultado from Empleado where id_empleado = " + (id.equals("") ? "0": id));
        if(query.next()){
            return query.getString("resultado");
        }else{
            return "No encontrado";
        }
    }

    //PRODUCTO
    public boolean regProducto(boolean edicion, String id, String nombre, String descripcion, String peso, String mano_obra, String existencia, boolean producto_final, boolean paga_fundidor){
        boolean exito = false;
        if(!edicion){
            try{
                getConnection().createStatement().executeUpdate("INSERT INTO Producto (nombre, descripcion, peso, mano_obra, existencia, producto_final, paga_fundidor) " +
                        "VALUES ('" + nombre + "', '" + descripcion + "', '" + peso + "', '" + mano_obra + "', '" + existencia + "', '" + producto_final + "', '" + paga_fundidor + "')");
                exito = true;
            }catch (Exception e){
                error(e);
            }
        }else{
            try{
                getConnection().createStatement().executeUpdate("UPDATE Producto SET nombre = '" + nombre + "', descripcion = '" + descripcion + "', peso = '" + peso + "', mano_obra = '" + mano_obra + "', existencia = '" + existencia + "', producto_final = '" + producto_final + "', paga_fundidor = '" + paga_fundidor + "' "+
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
        Producto producto = new Producto(null, null, null, null, null, null, true, true);
        while (true){
            assert queryResult != null;
            if (!queryResult.next()) break;
            producto.setId_producto(queryResult.getString("id_producto"));
            producto.setNombre(queryResult.getString("nombre"));
            producto.setDescripcion(queryResult.getString("descripcion"));
            producto.setPeso(queryResult.getString("peso"));
            producto.setMano_obra(queryResult.getString("mano_obra"));
            producto.setExistencia(queryResult.getString("existencia"));
            producto.setProducto_final(queryResult.getString("producto_final").equals("1"));
            producto.setPaga_fundidor(queryResult.getString("paga_fundidor").equals("1"));
        }
        ProductoHolder.getInstancia().setProducto(producto);
    }
    public void deleteProducto(String id){
        ejecutarQuery("delete from Producto where id_producto = " + id);
    }
    public ResultSet getListaProductos(String busqueda){
        return ejecutarQuery("EXEC BusquedaInventario @Busqueda = '" + busqueda.replace("'", "''") + "'");
    }
    public String getProductoById(String id) throws SQLException {
        ResultSet query = ejecutarQuery("select top 1 nombre as resultado from Producto where id_producto = " + (id.equals("") ? "0": id));
        if(query.next()){
            return query.getString("resultado");
        }else{
            return "No encontrado";
        }
    }

    //PRODUCCION
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
        Produccion.setProduccion(produccion);
    }
    public ResultSet getListaProduccion(String busqueda){
        return ejecutarQuery("EXEC BusquedaProduccion @Busqueda = '" + busqueda.replace("'", "''") + "'");
    }
    public void agregarProduccion(String produccion_id, String producto_id, String cantidad){
        ejecutarQuery("EXEC AgregarProduccion @id_produccion = " + produccion_id + ", @id_producto = " + producto_id + ", @cantidad = " + cantidad);
    }
    public void removerProduccion(String produccion_id, String producto_id){
        ejecutarQuery("EXEC RemoverProduccion @id_produccion = " + produccion_id + ", @id_producto = " + producto_id);
    }
    
    //OTROS
    public void testConection(){
        try {
            getConnection().close();
        } catch (Exception e) {
            error(e);
        }
    }
    public ResultSet getData(String origen) {
        return ejecutarQuery("select * from v" + origen);
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
}