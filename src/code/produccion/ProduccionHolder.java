package code.produccion;

public class ProduccionHolder {
    private Produccion produccion;
    private final static ProduccionHolder instancia = new ProduccionHolder();

    public Produccion getProduccion() {
        return produccion;
    }

    public void setProduccion(Produccion produccion) {
        this.produccion = produccion;
    }

    public static ProduccionHolder getInstancia() {
        return instancia;
    }
}
