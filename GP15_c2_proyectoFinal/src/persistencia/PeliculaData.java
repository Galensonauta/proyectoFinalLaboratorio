
package persistencia;

import java.sql.*;
import modelo.Pelicula;


/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */

public class PeliculaData { /*Vamos a necesitar cambiar la PK y poner una compuesta con si es 3D o 2D puede ser.*/

    private Connection con;

    public PeliculaData() {
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            System.out.println("Error de conexion en clase PeliculaData" + e.getMessage());
        }
    }
    
    public void guardarPelicula(Pelicula peli){
        
        String query = "INSERT INTO pelicula(titulo, director, actores, origen, genero, estreno, enCartelera) VALUE (?,?,?,?,?,?,?)";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, peli.getTitulo());
            ps.setString(2, peli.getDirector());
            ps.setString(3, peli.getActores());
            ps.setString(4, peli.getOrigen());
            ps.setString(5, peli.getGenero());
            ps.setDate(6, Date.valueOf(peli.getEstreno()));
            ps.setBoolean(7, peli.isEnCartelera());
            
            ps.executeUpdate();
            
            System.out.println("Pelicula guardada Correctamente (PeliculaData)");
        }catch(SQLException ex){
            System.out.println("Error al tratar de guardar Pelicula");
        }
    
    }
    
    public void eliminarPelicula(String titulo) {

        String query = "DELETE FROM pelicula WHERE titulo = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, titulo);

            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Pelicula con titulo: " + titulo + ", eliminada con exito");
            } else {
                System.out.println("Pelicula no encontrada! (Eliminar PeliculaData)");
            }

        } catch (SQLException ex) {
            System.out.println("Error al tratar de eliminar la pelicula (PeliculaData)");
        }
    }
    
    public void modificarPelicula(Pelicula peli, String titulo){
    
        String query = "UPDATE pelicula SET titulo = ?, director = ?, actores = ?, origen = ?," +
                       " genero = ?, estreno = ?, enCartelera = ? WHERE titulo = ?";
        
        try{
            PreparedStatement  ps = con.prepareStatement(query);
            
            ps.setString(1, peli.getTitulo());
            ps.setString(2, peli.getDirector());
            ps.setString(3, peli.getActores());
            ps.setString(4, peli.getOrigen());
            ps.setString(5, peli.getGenero());
            ps.setDate(6, Date.valueOf(peli.getEstreno()));
            ps.setBoolean(7, peli.isEnCartelera());
            
            ps.setString(8, titulo);
        
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Pelicula con titulo : " + titulo + ", modificada con exito");
            } else {
                System.out.println("Pelicula no encontrada! (Modificar PeliculaData)");
            }
        
        }catch(SQLException ex){
            System.out.println("Error al modificar pelicula (PeliculaData)");
        
        }
    }
    
    
    public Pelicula obtenerPeliculaPorTitulo(String titulo) {
        Pelicula peli = null;
        
        String query = "SELECT * FROM pelicula WHERE titulo = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, titulo);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                peli = new Pelicula();
                peli.setTitulo(titulo);
                
                peli.setDirector(rs.getString("director"));
                peli.setActores(rs.getString("actores"));
                peli.setOrigen(rs.getString("origen"));
                peli.setGenero(rs.getString("genero"));
                peli.setEstreno(rs.getDate("estreno").toLocalDate());
                peli.setEnCartelera(rs.getBoolean("enCartelera"));
            }
        
        }catch(SQLException ex){
            System.out.println("Error al buscar pelicula por titulo" + ex.getMessage());
        }
        return peli;
    }
    
}
