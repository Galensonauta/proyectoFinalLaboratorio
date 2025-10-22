
package modelo;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author y
 */
public class Comprador {
    private int dni;
    private String nombre, pass,medioPago;
    private LocalDate fechaNac;

    public Comprador() {
    }

    public Comprador(int dni, String nombre, String pass, String medioPago, LocalDate fechaNac) {
        this.dni = dni;
        this.nombre = nombre;
        this.pass = pass;
        this.medioPago = medioPago;
        this.fechaNac = fechaNac;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }
    
    
}
