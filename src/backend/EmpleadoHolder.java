package backend;

public class EmpleadoHolder {
    private Empleadoo empleadoo;
    private final static EmpleadoHolder instancia = new EmpleadoHolder();

    private EmpleadoHolder() {}

    public Empleadoo getEmpleado() {
        return empleadoo;
    }

    public void setEmpleado(Empleadoo empleadoo) {
        this.empleadoo = empleadoo;
    }

    public static EmpleadoHolder getInstancia() {
        return instancia;
    }
}
