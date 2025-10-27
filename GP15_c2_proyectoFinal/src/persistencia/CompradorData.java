package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Comprador;
import modelo.Sala;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class CompradorData {
    private Connection con;

    public CompradorData() {
        con = Conexion.getConexion();
    }    
    
    
    public void guardarComprador(Comprador c) {
        String sql = "INSERT INTO comprador (dni,nombre,pass,medioPago, fechaNac) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getPass());
            ps.setString(4, c.getMedioPago());
            ps.setDate(5, Date.valueOf(c.getFechaNac()));
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println("Error al guardar el Comprador");
        }   
    }  
}
