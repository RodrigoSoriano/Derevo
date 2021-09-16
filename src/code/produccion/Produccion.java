package code.produccion;

public class Produccion {
    private String id_produccion;
    private String id_empleado;
    private String id_producto;
    private String cantidad;
    private String fecha;

    public Produccion(String id_produccion, String id_empleado, String id_producto, String cantidad, String fecha) {
        this.id_produccion = id_produccion;
        this.id_empleado = id_empleado;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public String getId_produccion() {
        return id_produccion;
    }

    public void setId_produccion(String id_produccion) {
        this.id_produccion = id_produccion;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
