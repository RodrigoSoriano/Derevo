package code.inventario;

public class Producto {
    private String id_producto;
    private String nombre;
    private String descripcion;
    private String peso;
    private String mano_obra;
    private String existencia;
    private boolean producto_final;
    private boolean paga_fundidor;
    private static Producto instancia = new Producto();

    public Producto(String id_producto, String nombre, String descripcion, String peso, String mano_obra, String existencia, boolean producto_final, boolean paga_fundidor) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peso = peso;
        this.mano_obra = mano_obra;
        this.existencia = existencia;
        this.producto_final = producto_final;
        this.paga_fundidor = paga_fundidor;
    }

    public Producto(){

    }

    public static Producto getInstancia() {
        return instancia;
    }

    public static void setInstancia(Producto instancia) {
        Producto.instancia = instancia;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getMano_obra() {
        return mano_obra;
    }

    public void setMano_obra(String mano_obra) {
        this.mano_obra = mano_obra;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public boolean getProducto_final() {
        return producto_final;
    }

    public void setProducto_final(boolean producto_final) {
        this.producto_final = producto_final;
    }

    public boolean getPaga_fundidor() {
        return paga_fundidor;
    }

    public void setPaga_fundidor(boolean paga_fundidor) {
        this.paga_fundidor = paga_fundidor;
    }
}
