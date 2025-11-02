package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class TicketCompra {
    private int idTicket;
    private LocalDate fechCompra;
    double monto; 
    private Comprador comprador;
    private ArrayList<DetalleTicket> detalles;

    public TicketCompra(){
        this.detalles=new ArrayList<>();
    }
    
    public TicketCompra(int idTicket, LocalDate fechCompra, int monto, Comprador comprador,ArrayList<DetalleTicket> detalles) {
        this.idTicket = idTicket;
        this.fechCompra = fechCompra;       
        this.monto = monto;
        this.comprador = comprador;
        this.detalles=detalles;
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

    public void setFechCompra(LocalDate fechCompra) {
        this.fechCompra = fechCompra;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public ArrayList<DetalleTicket> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleTicket> detalles) {
        this.detalles = detalles;
    }
    public void agregarDetalles(DetalleTicket detalle){
        if (this.detalles == null) {
            this.detalles = new ArrayList<>();
        }
        this.detalles.add(detalle);
    }
 


}
