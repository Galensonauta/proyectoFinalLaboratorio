package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class DetalleTicket {
    private int idDetalle, cantidad, subtotal;
    private Proyeccion idProyeccion;
    private ArrayList<LugarAsiento> lugares;
    private LocalDate fechProyeccion;

    public LocalDate getFechProyeccion() {
        return fechProyeccion;
    }

    public void setFechProyeccion(LocalDate fechProyeccion) {
        this.fechProyeccion = fechProyeccion;
    }


    public DetalleTicket() {
        this.lugares = new ArrayList<>();        
    }

    public DetalleTicket(int cantidad, int subtotal, Proyeccion idProyeccion, ArrayList<LugarAsiento>lugares, LocalDate fechProyeccion) {
        this.idProyeccion = idProyeccion;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.lugares = lugares;
        this.fechProyeccion=fechProyeccion;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public Proyeccion getIdProyeccion() {
        return idProyeccion;
    }

    public void setIdProyeccion(Proyeccion idProyeccion) {
        this.idProyeccion = idProyeccion;
    }

    public ArrayList<LugarAsiento> getLugares() {
        return lugares;
    }

    public void setLugares(ArrayList<LugarAsiento> lugares) {
        this.lugares = lugares;
    }
    public void agregarLugar(LugarAsiento lugar) {
        this.lugares.add(lugar);
    }
    
}
