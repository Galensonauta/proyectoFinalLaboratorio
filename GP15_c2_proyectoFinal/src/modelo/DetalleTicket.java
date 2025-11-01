package modelo;

import java.util.List;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class DetalleTicket {
    private int codD, cantidad, subtotal;
    private Proyeccion idProyeccion;
    private List<LugarAsiento> lugares;

    public DetalleTicket() {
    }

    public DetalleTicket(int codD, int cantidad, int subtotal, Proyeccion idProyeccion , List<LugarAsiento> lugares) {
        this.codD = codD;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.idProyeccion = idProyeccion;
        this.lugares = lugares;
    }
    
    public DetalleTicket(int cantidad, int subtotal, Proyeccion idProyeccion, TicketCompra ticket, List<LugarAsiento> lugares) {
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.idProyeccion = idProyeccion;
        this.lugares = lugares;
    }
    
    public int getCodD() {
        return codD;
    }

    public void setCodD(int codD) {
        this.codD = codD;
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

   

    public List<LugarAsiento> getLugares() {
        return lugares;
    }

    public void setLugares(List<LugarAsiento> lugares) {
        this.lugares = lugares;
    }

    @Override
    public String toString() {
        return "Detalle De Ticket[" + "Código: " + codD + ", Cantidad: " + cantidad + ", Subtotal: " + subtotal + ']';
    }
    
    
    
}
