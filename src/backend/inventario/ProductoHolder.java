package backend.inventario;

public class ProductoHolder {
    private Producto producto;
    private final static ProductoHolder instancia = new ProductoHolder();

    private ProductoHolder() {}

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public static ProductoHolder getInstancia() {
        return instancia;
    }
}