package modelo;

import java.util.List;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class DetalleTicket {
    private int codD, cantidad, subtotal;
    private Proyeccion idProyeccion;
    private TicketCompra ticket;
    private List<LugarAsiento> lugaresDisp;

    public DetalleTicket() {
    }

    public DetalleTicket(int codD, int cantidad, int subtotal, Proyeccion idProyeccion, TicketCompra ticket, List<LugarAsiento> lugaresDisp) {
        this.codD = codD;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.idProyeccion = idProyeccion;
        this.ticket = ticket;
        this.lugaresDisp = lugaresDisp;
    }
    
    public DetalleTicket(int cantidad, int subtotal, Proyeccion idProyeccion, TicketCompra ticket, List<LugarAsiento> lugaresDisp) {
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.idProyeccion = idProyeccion;
        this.ticket = ticket;
        this.lugaresDisp = lugaresDisp;
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

    public TicketCompra getTicket() {
        return ticket;
    }

    public void setTicket(TicketCompra ticket) {
        this.ticket = ticket;
    }

    public List<LugarAsiento> getLugaresDisp() {
        return lugaresDisp;
    }

    public void setLugaresDisp(List<LugarAsiento> lugaresDisp) {
        this.lugaresDisp = lugaresDisp;
    }

    @Override
    public String toString() {
        return "Detalle De Ticket[" + "Código: " + codD + ", Cantidad: " + cantidad + ", Subtotal: " + subtotal + ']';
    }
    
    
    
}
