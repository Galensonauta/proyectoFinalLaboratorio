package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public void buscarComprador(int dni){
        String sql ="SELECT  * FROM comprador WHERE dni = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,dni);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            System.out.println("Comprador con DNI: "+ dni +" encontrado:");
            }else{
                System.out.println("No se encontro ningun comrpador con ese DNI");
            }
        }catch(SQLException e){
            System.out.println("Error al obtener comprador"+ e.getMessage());
        }
    }
    public void actualizarComprador(int dni,int nuevoDni, String nuevoNombre, String nuevoPass, String nuevoMedioPago, LocalDate nuevaFechaNac){
        String sql = "UPDATE comprador SET dni = ?, nombre = ?, pass = ?,medioPago=?,fechaNac=? WHERE dni = ?";
               try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nuevoDni);
            ps.setString(2, nuevoNombre);
            ps.setString(3, nuevoPass);
            ps.setString(4, nuevoMedioPago);
            ps.setDate(5, Date.valueOf(nuevaFechaNac));
             ps.setInt(6, dni);
                                    
            int registros = ps.executeUpdate();
            if(registros>0){
                System.out.println("el comprador con el dni:  " + dni + "fue modificada ");
            }else{
                System.out.println("No se encontro ningun comrpador con ese DNI "+ dni);
            }            
        }catch(SQLException e){
            System.out.println("Error al modificar al comprador"+ e.getMessage());
        }
    }
     public void borrarComprador(int dni){
        String sql = "DELETE FROM comprador WHERE dni = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,dni);
            int registros = ps.executeUpdate();            
            if(registros>0){
                System.out.println("el comprador con el dni:  "+ dni +" fue elimanado de la base de datos");
            }else{
                System.out.println("No se encontro ningun comrpador con ese DNI "+ dni);
            }
        }catch(SQLException e){
            System.out.println("Error al eliminar comprador"+ e.getMessage());
        }       
    }

}
