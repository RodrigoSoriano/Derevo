package code.produccion;

import java.time.LocalDate;

public class Produccion {
    private String id_produccion;
    private String id_empleado;
    private LocalDate fecha;
    private String nota;
    private static Produccion instancia = new Produccion();

    public Produccion(String id_produccion, String id_empleado, LocalDate fecha, String nota) {
        this.id_produccion = id_produccion;
        this.id_empleado = id_empleado;
        this.fecha = fecha;
        this.nota = nota;
    }

    public Produccion() {

    }

    public static Produccion getInstancia() {
        return instancia;
    }

    public static void setInstancia(Produccion produccion) {
        instancia = produccion;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
