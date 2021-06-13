package backend;

import java.time.LocalDate;
import java.util.Date;

public class Empleadoo {
    private String id_empleado;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String numero;
    private LocalDate fecha;
    private String sueldo_base;

    public Empleadoo(String id_empleado, String cedula, String nombres, String apellidos, String numero, LocalDate fecha, String sueldo_base) {
        this.id_empleado = id_empleado;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numero = numero;
        this.fecha = fecha;
        this.sueldo_base = sueldo_base;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getSueldo_base() {
        return sueldo_base;
    }

    public void setSueldo_base(String sueldo_base) {
        this.sueldo_base = sueldo_base;
    }
}
