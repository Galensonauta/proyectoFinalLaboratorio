
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.DetalleTicket;
import modelo.LugarAsiento;
import modelo.Proyeccion;

/**
 *
 * @author y
 */
public class DetalleTicketData {
    
private Connection con;
    private CompradorData compradorData;
    private LugarAsientoData lugarAsientoData;
    private ProyeccionData proyeccionData;

    public DetalleTicketData() {
        con = Conexion.getConexion();
        compradorData = new CompradorData();
        lugarAsientoData = new LugarAsientoData();
        proyeccionData = new ProyeccionData();
    }
    public void guardarDetalleTicketCompra(DetalleTicket dt, int codD) {
        String sqlDetalles = "INSERT INTO detalle_ticket (codD,cantidad,subTotal,idProyeccion, fechProyeccion) values (?,?,?,?,?)";
        String sqlLugares = "INSERT INTO detalle_ticket_lugares (idDetalleTicket, codLugar) VALUES (?, ?)";
        try {
            PreparedStatement psDetalle = con.prepareStatement(sqlDetalles, Statement.RETURN_GENERATED_KEYS);            
            psDetalle.setInt(1, codD);
            psDetalle.setInt(2, dt.getCantidad());
            psDetalle.setDouble(3, dt.getSubtotal());
            psDetalle.setInt(4, dt.getProyeccion().getIdProyeccion());    
            psDetalle.setDate(5, java.sql.Date.valueOf(dt.getFechProyeccion()));

            psDetalle.executeUpdate();
            int idDetalleGenerado;
        try (ResultSet rs = psDetalle.getGeneratedKeys()) {
            if (rs.next()) {
                idDetalleGenerado = rs.getInt(1); // Se obtiene por índice (columna 1)
                dt.setIdDetalle(idDetalleGenerado); // Actualizamos el objeto
            } else {
                throw new SQLException("No se pudo obtener el ID del detalle.");
            }
        }
            PreparedStatement psLugares = con.prepareStatement(sqlLugares);
            
            for(LugarAsiento lugar : dt.getLugares()){
               psLugares.setInt(1,idDetalleGenerado);
               psLugares.setInt(2, lugar.getCodLugar());
               psLugares.addBatch();
            }
            psLugares.executeBatch();                     
        } catch(SQLException e){
            System.out.println("Error al guardar el Ticket"+e);
        }   
    }
    
    public void eliminarDetalleTicket(int idDetalle) {
        String query = "DELETE FROM detalle_ticket WHERE idDetalle = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idDetalle);
            int filas = ps.executeUpdate();            
            if (filas > 0) {
                System.out.println("El detalle ticket con el id: " + idDetalle + ", fue eliminado con exito");
            } else {
                System.out.println("Detalle Ticket no encontrado)");
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar detalle ticket de compra de la base de datos");
        }
    }
    // Solo se puede modificar el subtotal por algún descuento. 
    // Si queres modificar otra variable borra el detalle completo y crea uno nuevo que es mas fácil, papu
    public void modificarDetalleTicket(int nuevoSubtotal, int idDetalle){    
        String query = "UPDATE detalle_ticket SET subTotal = ? WHERE idDetalle = ?";
        try{
            PreparedStatement  ps = con.prepareStatement(query);            
            ps.setInt(1, nuevoSubtotal);
            ps.setInt(2, idDetalle);
            
            int filas = ps.executeUpdate();            
            if (filas > 0) {
                System.out.println("Detalle Ticket id : " + idDetalle + ", modificado con exito");
            } else {
                System.out.println("Detalle Ticket no encontrado");
            }
        
        }catch(SQLException ex){
            System.out.println("Error al modificar el detalle ticket de compra de la base de datos");        
        }
    }
    
    public ArrayList<DetalleTicket> buscarDetalleTicket(int codD) {
        ArrayList<DetalleTicket>detalles = new ArrayList<>();
        String sqlDetalles = "SELECT * FROM detalle_ticket WHERE codD = ?";
        String sqlLugares = "SELECT codLugar FROM detalle_ticket_lugares WHERE idDetalleTicket = ?";
        try {
            PreparedStatement psDetalles = con.prepareStatement(sqlDetalles);
            psDetalles.setInt(1, codD);
            ResultSet rsDetalles = psDetalles.executeQuery();
            while (rsDetalles.next()) {
                DetalleTicket dt = new DetalleTicket();              
                
                int idDetalle = rsDetalles.getInt("idDetalle");
                dt.setIdDetalle(idDetalle);
                dt.setCantidad(rsDetalles.getInt("cantidad"));
                dt.setSubtotal(rsDetalles.getInt("subtotal"));
                dt.setFechProyeccion(rsDetalles.getObject("fechProyeccion", LocalDate.class));
                
                Proyeccion p = proyeccionData.buscarProyeccionPorID(rsDetalles.getInt("idProyeccion"));
                dt.setProyeccion(p);
               
                    PreparedStatement psLugares = con.prepareStatement(sqlLugares);
                    psLugares.setInt(1, idDetalle);
                    ResultSet rsLugares = psLugares.executeQuery();
                    while(rsLugares.next()){
                        LugarAsiento la = lugarAsientoData.obtenerAsientoPorCod(rsLugares.getInt("codLugar"));
                        dt.agregarLugar(la);
                    }
                    detalles.add(dt);
                }
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar el detalle del ticket en la base de datos" + ex.getMessage());
        }
        return detalles;        
    }
    public void eliminarDetalleSegunProyeccion(int idPro) {
        String query = "DELETE FROM detalle_ticket WHERE idProyeccion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idPro);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al tratar de eliminar detalle según ID Proyeccion: " + e.getMessage());
        }
    }

}


