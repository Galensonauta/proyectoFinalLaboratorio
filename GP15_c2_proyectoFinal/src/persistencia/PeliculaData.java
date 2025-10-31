
package persistencia;

import java.sql.*;
import java.util.ArrayList;
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
    
    
    public ArrayList<Pelicula> obtenerTodasLasPeliculas() {
    
        // 1. Crear la lista vacía
        ArrayList<Pelicula> peliculas = new ArrayList<>();
    
        // 2. La consulta SQL no necesita 'WHERE'
        String query = "SELECT * FROM pelicula";

        // 3. Usamos try-with-resources (más seguro, cierra ps y rs solos)
        try (PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            // 4. Usamos 'while' para recorrer TODOS los resultados
            while (rs.next()) {
            
                // 5. Creamos un objeto por CADA fila
                Pelicula peli = new Pelicula();
            
                // 6. Llenamos el objeto (ahora 'titulo' viene del ResultSet)
                peli.setTitulo(rs.getString("titulo")); 
                peli.setDirector(rs.getString("director"));
                peli.setActores(rs.getString("actores"));
                peli.setOrigen(rs.getString("origen"));
                peli.setGenero(rs.getString("genero"));
            
                // 7. (Recomendado) Verificamos si la fecha es nula
                java.sql.Date sqlDate = rs.getDate("estreno");
                if (sqlDate != null) {
                    peli.setEstreno(sqlDate.toLocalDate());
                } else {
                    peli.setEstreno(null);
                }
            
                peli.setEnCartelera(rs.getBoolean("enCartelera"));
            
                // 8. Añadimos la película a la lista
                peliculas.add(peli);
            }
            // No necesitas .close() gracias al try-with-resources

        } catch (SQLException ex) {
            System.out.println("Error al obtener la lista de peliculas: " + ex.getMessage());
            ex.printStackTrace(); // Es bueno ver el error completo
        }
    
        // 9. Devolvemos la lista (llena o vacía)
        return peliculas;
    }
    
}
