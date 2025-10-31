/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.TicketCompra;

/**
 *
 * @author y
 */
public class TicketCompraData {
    private Connection con;

    public TicketCompraData() {
        con = Conexion.getConexion();
    }
    public void guardarComprador(TicketCompra tc) {
        String sql = "INSERT INTO ticketCompra (idTicket,fechaCompra,fechaProyeccion,monto, comprador, lugarAsiento) values (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tc.getIdTicket());
            ps.setDate(2, Date.valueOf(tc.getFechaCompra()));
            ps.setDate(3, Date.valueOf(tc.getFechaProyeccion()));
            ps.setInt(4, tc.getMonto());
            ps.setInt(5,tc.getComprador().getDni());
            ps.setInt(6, tc.getLugarAsiento().getCodLugar());
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println("Error al guardar el Ticket");
        }   
    }
    public void eliminarTicketCompra(int idTicket) {
        String query = "DELETE FROM ticketCompra WHERE ticketCompra = ?";
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
    
        String query = "UPDATE ticketCompra SET fechaCompra = ?, fechaProyeccion = ?, monto = ?, comprador = ?," +
                       " lugarAsiento = ? WHERE idTicket = ?";        
        try{
            PreparedStatement  ps = con.prepareStatement(query);            
            ps.setDate(1, Date.valueOf(tc.getFechaCompra()));
            ps.setDate(2, Date.valueOf(tc.getFechaProyeccion()));
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
}
