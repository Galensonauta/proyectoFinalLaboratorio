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
import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.Comprador;
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

    public TicketCompraData() {
        con = Conexion.getConexion();
        compradorData = new CompradorData();
        lugarAsientoData = new LugarAsientoData();
    }
    public void guardarTicketCompra(TicketCompra tc) {
        String sql = "INSERT INTO ticket_compra (idTicket,fechCompra,fechProyeccion,monto, comprador, lugarAsiento) values (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tc.getIdTicket());
            ps.setDate(2, Date.valueOf(tc.getFechCompra()));
            ps.setDate(3, Date.valueOf(tc.getFechProyeccion()));
            ps.setInt(4, tc.getMonto());
            ps.setInt(5,tc.getComprador().getDni());
            ps.setInt(6, tc.getLugarAsiento().getCodLugar());
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println("Error al guardar el Ticket");
        }   
    }
    
    public void eliminarTicketCompra(int idTicket) {
        String query = "DELETE FROM ticket_compra WHERE ticketCompra = ?";
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
    
    public void modificarTicketCompra(TicketCompra tc, int idTicket){
    
        String query = "UPDATE ticket_compra SET fechCompra = ?, fechProyeccion = ?, monto = ?, comprador = ?," +
                       " lugarAsiento = ? WHERE idTicket = ?";        
        try{
            PreparedStatement  ps = con.prepareStatement(query);            
            ps.setDate(1, Date.valueOf(tc.getFechCompra()));
            ps.setDate(2, Date.valueOf(tc.getFechProyeccion()));
            ps.setInt(3, tc.getMonto());
            ps.setInt(4,tc.getComprador().getDni());            
            ps.setInt(5, idTicket);        
            int filas = ps.executeUpdate();            
            if (filas > 0) {
                System.out.println("Ticket id : " + idTicket + ", modificado con exito");
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
                java.sql.Date sqlDateFp = rs.getDate("fechProyeccion");
                LocalDate fp = sqlDateFp.toLocalDate();
                
                tc.setIdTicket(idTicket);                
                tc.setFechCompra(fc);
                tc.setFechProyeccion(fp);
                
                Comprador c = compradorData.buscarComprador(rs.getInt("comprador"));                
                tc.setComprador(c);
                
                tc.setMonto(rs.getInt("monto"));
                LugarAsiento codLa = lugarAsientoData.obtenerAsientoPorCod(rs.getInt("lugarAsiento"));                
                tc.setLugarAsiento(codLa);
                
                System.out.println(
                "idTciket: "+ idTicket +
                        "\n Comprado el: "+  fc+
                        "\n Para la proyeccion del día: "+  fp+
                        "\nCosto del ticket: "+  tc.getMonto()+
                        "\n Comprador: "+  c.getNombre()+
                        "\n  fila del asiento: "+  tc.getLugarAsiento().getFila()+
                        "\n  numero del asiento: "+  tc.getLugarAsiento().getNumeroAsiento()
        );       
                
            }
        } catch (SQLException | java.time.format.DateTimeParseException ex) {
            System.out.println("Error al buscar ticket en la base de datos" + ex.getMessage());
        }
        return tc;
    }    
}
