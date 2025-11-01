package modelo;

import java.time.LocalDate;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class TicketCompra {
    private int idTicket;
    private LocalDate fechCompra, fechProyeccion;
    int monto; 
    private Comprador comprador;
    private LugarAsiento lugarAsiento;

    public TicketCompra(){}
    
    public TicketCompra(int idTicket, LocalDate fechCompra, LocalDate fechProyeccion, int monto, Comprador comprador, LugarAsiento lugarAsiento) {
        this.idTicket = idTicket;
        this.fechCompra = fechCompra;
        this.fechProyeccion = fechProyeccion;
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

    public LocalDate getFechCompra() {
        return fechCompra;
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

    public void setFechCompra(LocalDate fechCompra) {
        this.fechCompra = fechCompra;
    }

    public LocalDate getFechProyeccion() {
        return fechProyeccion;
    }

    public void setFechProyeccion(LocalDate fechProyeccion) {
        this.fechProyeccion = fechProyeccion;
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
