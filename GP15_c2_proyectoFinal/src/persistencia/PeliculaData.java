
package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelo.Pelicula;


/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */

public class PeliculaData {

    private Connection con;

    public PeliculaData() {
            con = Conexion.getConexion();
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
            ps.close();
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
            ps.close();
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
            ps.close();
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
            rs.close();
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error al buscar pelicula por titulo" + ex.getMessage());
        }
        return peli;
    }
    
    public List<Pelicula> listarTodasLasPeliculas(){
        List<Pelicula> listaPelis = new ArrayList<>();
        
        String query = "SELECT * FROM pelicula";
        
        try{
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Pelicula peli = new Pelicula();
                peli.setTitulo(rs.getString("titulo"));                
                peli.setDirector(rs.getString("director"));
                peli.setActores(rs.getString("actores"));
                peli.setOrigen(rs.getString("origen"));
                peli.setGenero(rs.getString("genero"));
                peli.setEstreno(rs.getDate("estreno").toLocalDate());
                peli.setEnCartelera(rs.getBoolean("enCartelera"));
                
                if(peli != null){
                    listaPelis.add(peli);
                }
            }
        rs.close();
        ps.close();
        }catch(SQLException ex){
            System.out.println("No pudo listarse todas las peliculas: " + ex.getMessage());
        
        }
        return listaPelis;
    }
    
    

    public List<Pelicula> listarPeliculasEnCartelera() {

        List<Pelicula> listaPelis = new ArrayList<>();

        
        String query = "SELECT * FROM pelicula WHERE enCartelera = 1"; 

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                

                Pelicula peli = new Pelicula();
                peli.setTitulo(rs.getString("titulo"));
                peli.setDirector(rs.getString("director"));
                peli.setActores(rs.getString("actores"));
                peli.setOrigen(rs.getString("origen"));
                peli.setGenero(rs.getString("genero"));
                peli.setEstreno(rs.getDate("estreno").toLocalDate());
                peli.setEnCartelera(rs.getBoolean("enCartelera"));

                listaPelis.add(peli);
            }
        } catch (SQLException ex) {
            System.out.println("No pudo listarse las peliculas en cartelera: " + ex.getMessage());
        }

        return listaPelis;
        }
    
    
 

    public void actualizarEstadoCartelera(String titulo, boolean nuevoEstado) {

  
        String query = "UPDATE pelicula SET enCartelera = ? WHERE titulo = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setBoolean(1, nuevoEstado);
            ps.setString(2, titulo);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Estado 'enCartelera' actualizado a " + nuevoEstado + " para la película: " + titulo);
            }

        } catch (SQLException ex) {
            System.out.println("Error al actualizar estado 'enCartelera': " + ex.getMessage());
        }
    }
    
    

    public List<Pelicula> listarProximosEstrenos() {

        List<Pelicula> listaPelis = new ArrayList<>();

        // Esta consulta SQL busca películas que NO estén en cartelera (false)
        // Y cuya fecha de estreno sea en el FUTURO (después de hoy)
        // Ordenadas por la fecha de estreno más cercana.
        String query = "SELECT * FROM pelicula WHERE enCartelera = 0 AND estreno > CURDATE() ORDER BY estreno ASC"; 

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Construimos la película directamente desde el ResultSet
                Pelicula peli = new Pelicula();
                peli.setTitulo(rs.getString("titulo"));
                peli.setDirector(rs.getString("director"));
                peli.setActores(rs.getString("actores"));
                peli.setOrigen(rs.getString("origen"));
                peli.setGenero(rs.getString("genero"));
                peli.setEstreno(rs.getDate("estreno").toLocalDate());
                peli.setEnCartelera(rs.getBoolean("enCartelera"));

                listaPelis.add(peli);
            }
        } catch (SQLException ex) {
            System.out.println("No pudo listarse los próximos estrenos: " + ex.getMessage());
        }

        return listaPelis;
    }
    
    public Map<String, Integer> obtenerEstadisticasPeliculas() {
    Map<String, Integer> estadisticas = new LinkedHashMap<>();
    
    String sql = "SELECT p.titulo, SUM(dt.cantidad) as total " +
                 "FROM detalle_ticket dt " +
                 "JOIN proyeccion pr ON dt.idProyeccion = pr.idProyeccion " +
                 "JOIN pelicula p ON pr.pelicula = p.titulo " +
                 "GROUP BY p.titulo " +
                 "ORDER BY total DESC";

    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String titulo = rs.getString("titulo");
            int cantidad = rs.getInt("total");            
            estadisticas.put(titulo, cantidad);
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener estadísticas: " + ex.getMessage());
    }    
    return estadisticas;
}    
}
