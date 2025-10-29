package persistencia;

import java.sql.*;
import modelo.*;
/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */

public class LugarAsientoData {
    private Connection con;
    private ProyeccionData proyeccionData;

    public LugarAsientoData() {
        try {
            con = Conexion.getConexion();
            if(con != null){
                proyeccionData = new ProyeccionData();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion en clase LugarAsientoData" + e.getMessage());
        }
    }
    
    public void guardarAsiento(LugarAsiento l){
        
        String query = "INSERT INTO lugar_asiento (codLugar, filaAsiento, numeroAsiento, estado, proyeccion) VALUE (?,?,?,?,?)";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setInt(1, l.getCodLugar());
            ps.setString(2, l.getFila());
            ps.setInt(3, l.getNumeroAsiento());
            ps.setBoolean(4, l.isEstado());
            ps.setInt(5, l.getProyeccion().getIdProyeccion());
            
            ps.executeUpdate();
        
        }catch(SQLException ex){
            System.out.println("Error al tratar de guardar Asiento" + ex.getMessage());
        }
    
    }
    
    public void eliminarAsiento(int codLugar) {

        String query = "DELETE FROM lugar_asiento WHERE codLugar = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, codLugar);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Asiento con el código: " + codLugar + ", eliminado con exito");
            } else {
                System.out.println("Asiento no encontrado!");
            }

        } catch (SQLException ex) {
            System.out.println("Error al tratar de eliminar el Asiento" + ex.getMessage());
        }
    }
    
    public void modificarPelicula(LugarAsiento la){
    
        String query = "UPDATE lugar_asiento SET filaAsiento = ?, numeroAsiento = ?, estado = ?, proyeccion = ? WHERE titulo = ?";
        
        try{
            PreparedStatement  ps = con.prepareStatement(query);
            
            ps.setString(1, la.getFila());
            ps.setInt(2, la.getNumeroAsiento());
            ps.setBoolean(3, la.isEstado());
            ps.setInt(4, la.getProyeccion().getIdProyeccion());
            ps.setInt(5, la.getCodLugar());
        
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Asiento con el código : " + la.getCodLugar() + ", modificado con exito");
            } else {
                System.out.println("Asiento no encontrado para modificarlo");
            }
        
        }catch(SQLException ex){
            System.out.println("Error al modificar Asiento");
        
        }
    }

    public void actualizarEstadoAsiento(int codLugar, boolean estado) {
        //ocupado o libre

        String query = "UPDATE lugar_asiento SET estado = ? WHERE codLugar = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setBoolean(1, estado);
            ps.setInt(2, codLugar);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                String estadoStr;
                if (estado) {
                    estadoStr = "Ocupado";
                } else {
                    estadoStr = "Libre";
                }
                System.out.println("Estado del asiento: " + codLugar + ", actualizado a: " + estadoStr);
            } else {
                System.out.println("Asiento no encontrado para actualizar");
            }

        } catch (SQLException ex) {
            System.out.println("Error al actualizar estado del asiento");

        }
    }

    public LugarAsiento obtenerAsientoPorCod(int codLugar) {
        LugarAsiento asiento = null;
        
        String query = "SELECT * FROM lugar_asiento WHERE codLugar = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, codLugar);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                asiento = new LugarAsiento();
                asiento.setCodLugar(codLugar);
                
                Proyeccion p = proyeccionData.buscarProyeccionPorID(rs.getInt("proyeccion"));
                
                asiento.setProyeccion(p);
                asiento.setFila(rs.getString("filaAsiento"));
                asiento.setNumeroAsiento(rs.getInt("numeroAsiento"));
                asiento.setEstado(rs.getBoolean("estado"));

            }
        
        }catch(SQLException ex){
            System.out.println("Error al buscar Asiento: " + ex.getMessage());
        }
        return asiento;
    }
    
    
    
    
}
