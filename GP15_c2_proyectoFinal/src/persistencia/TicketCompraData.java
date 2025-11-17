/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.Comprador;
import modelo.DetalleTicket;
import modelo.LugarAsiento;
import modelo.Proyeccion;
import modelo.TicketCompra;

/**
 *
 * @author y
 */
public class TicketCompraData {
    private Connection con;
    private CompradorData compradorData;
    private LugarAsientoData lugarAsientoData;
    private DetalleTicketData detalleTicketData;

    public TicketCompraData() {
        con = Conexion.getConexion();
        compradorData = new CompradorData();
        lugarAsientoData = new LugarAsientoData();
        detalleTicketData = new DetalleTicketData();
    }
    public void guardarTicketCompra(TicketCompra tc) {
        double montoTotal = 0;
        if (tc.getDetalles() == null) {
            System.out.println("Error: El TicketCompra no tiene detalles.");
            return;
        }
        for (DetalleTicket dt : tc.getDetalles()) {
        montoTotal += dt.getSubtotal(); 
    }
        tc.setMonto(montoTotal);
        String sql = "INSERT INTO ticket_compra (fechCompra, monto, comprador) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);           
            ps.setDate(1, Date.valueOf(tc.getFechCompra()));
            ps.setDouble(2, tc.getMonto());
            ps.setInt(3,tc.getComprador().getDni());           
            ps.executeUpdate();
            int idTicketGenerado=-1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idTicketGenerado = rs.getInt(1);
                        tc.setIdTicket(idTicketGenerado); // Actualizamos el objeto
                    } else {
                        throw new SQLException("Error grave: No se pudo obtener el ID del TicketCompra.");
                    }
                }
            for (DetalleTicket dt : tc.getDetalles()) {
                detalleTicketData.guardarDetalleTicketCompra(dt, idTicketGenerado);
            }
            System.out.println("Ticket guardado con éxito. ID: " + idTicketGenerado);
        } catch(SQLException e){
            System.out.println("Error al guardar el Ticket"+e);
        }   
    }
    
    public void eliminarTicketCompra(int idTicket) {
        String query = "DELETE FROM ticket_compra WHERE idTicket = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idTicket);
            int filas = ps.executeUpdate();            
            if (filas > 0) {
                System.out.println("El ticket con el id: " + idTicket + ", fue eliminado con exito");
            } else {
                System.out.println("Ticket no encontrado)");
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar ticket de compra de la base de datos");
        }
    }
    
    public void modificarTicketCompra(TicketCompra tc){
    String query = "UPDATE ticket_compra SET fechCompra =?, comprador =? WHERE idTicket=?";
        try{
            PreparedStatement  ps = con.prepareStatement(query);  
            ps.setDate(1, Date.valueOf(tc.getFechCompra()));
            ps.setInt(2,tc.getComprador().getDni());            
            ps.setInt(3, tc.getIdTicket());        
            int filas = ps.executeUpdate();            
            if (filas > 0) {
                System.out.println("Ticket id : " + tc.getIdTicket() + ", modificado con exito");
            } else {
                System.out.println("Ticket no encontrado");
            }
        
        }catch(SQLException ex){
            System.out.println("Error al modificar el ticket de compra de la base de datos");
        
        }
    }
    
    public TicketCompra buscarTicketCompra(int idTicket) {
        TicketCompra tc = null;
        String query = "SELECT * FROM ticket_compra WHERE idTicket = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idTicket);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tc = new TicketCompra();              
                
                java.sql.Date sqlDateFc = rs.getDate("fechCompra");
                LocalDate fc = sqlDateFc.toLocalDate();
                
                tc.setIdTicket(idTicket);                
                tc.setFechCompra(fc);
                
                Comprador c = compradorData.buscarComprador(rs.getInt("comprador"));                
                tc.setComprador(c);
                
                ArrayList<DetalleTicket> detalles = detalleTicketData.buscarDetalleTicket(idTicket);
                tc.setDetalles(detalles);
                
                
                tc.setMonto(rs.getInt("monto"));
                
                System.out.println(
                        "idTciket: "+ idTicket +
                        "\n Comprado el: "+  fc+
                        "\nCosto del ticket: "+  tc.getMonto()+
                        "\n Comprador: "+  c.getNombre()
        );       
                for (DetalleTicket dt : detalles) {
                        System.out.println("  > Renglón ID: " + dt.getIdDetalle());
                        System.out.println("    Proyección: " + dt.getProyeccion().getPelicula().getTitulo());
                        System.out.println("    Fecha Función: " + dt.getFechProyeccion());
                        System.out.println("    Hora Función: " + dt.getProyeccion().getHoraInicio());
                        System.out.println("    Cantidad: " + dt.getCantidad());
                        System.out.println("    Subtotal: $" + dt.getSubtotal());
                        System.out.println("    Asientos: ");
                        for (LugarAsiento la : dt.getLugares()) {
                            System.out.println("      - Fila " + la.getFila() + ", Nro " + la.getNumeroAsiento());
                        }
                    }
                
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar ticket en la base de datos" + ex.getMessage());
        }
        return tc;
    }  
    
    public ArrayList<TicketCompra>listarTicketsPorFecha(LocalDate fecha){
        ArrayList<TicketCompra> tickets = new ArrayList<>();
        String sql  = "Select * FROM ticket_compra WHERE fechCompra =?";
        try{
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, java.sql.Date.valueOf(fecha));
        ResultSet rs = ps.executeQuery(); 
        while(rs.next()){
            int idTicket = rs.getInt("idTicket");
            TicketCompra tc = this.buscarTicketCompra(idTicket);
            tickets.add(tc);
        }
        }catch (SQLException ex) {
        System.out.println("Error al listar tickets por fecha: " + ex.getMessage());
    }
        return tickets;        
    }
    
    public ArrayList<TicketCompra> listarTicketsPorPelicula(String titulo) {
    ArrayList<TicketCompra> tickets = new ArrayList<>();
    
    String sql = "SELECT DISTINCT tc.idTicket " + 
                 "FROM ticket_compra tc " +
                 "JOIN detalle_ticket dt ON tc.idTicket = dt.codD " +
                 "JOIN proyeccion pr ON dt.idProyeccion = pr.idProyeccion " +
                 "WHERE pr.pelicula = ?";
    
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, titulo);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int idTicketEncontrado = rs.getInt("idTicket");
            TicketCompra tc = this.buscarTicketCompra(idTicketEncontrado);
            tickets.add(tc);
        }        
    } catch (SQLException ex) {
        System.out.println("Error al listar tickets por película: " + ex.getMessage());
    }
    return tickets;
}
}
