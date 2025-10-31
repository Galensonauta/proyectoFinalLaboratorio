package modelo;

import java.time.LocalDate;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class TicketCompra {
    private int idTicket;
    private LocalDate fechaCompra, fechaProyeccion;
    int monto; 
    private Comprador comprador;
    private LugarAsiento lugarAsiento;

    public TicketCompra(){}
    
    public TicketCompra(int idTicket, LocalDate fechaCompra, LocalDate fechaProyeccion, int monto, Comprador comprador, LugarAsiento lugarAsiento) {
        this.idTicket = idTicket;
        this.fechaCompra = fechaCompra;
        this.fechaProyeccion = fechaProyeccion;
        this.monto = monto;
        this.comprador = comprador;
        this.lugarAsiento=lugarAsiento;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public LugarAsiento getLugarAsiento() {
        return lugarAsiento;
    }

    public void setLugarAsiento(LugarAsiento lugarAsiento) {
        this.lugarAsiento = lugarAsiento;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public LocalDate getFechaProyeccion() {
        return fechaProyeccion;
    }

    public void setFechaProyeccion(LocalDate fechaProyeccion) {
        this.fechaProyeccion = fechaProyeccion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Ticket de Compra[" + "Nro. Ticket: " + idTicket + ", Comprador: " + comprador.getDni() +", Monto: $" + monto + ']';
    }
    
    
    
    

    
    


}
