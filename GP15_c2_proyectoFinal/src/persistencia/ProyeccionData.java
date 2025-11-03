
package persistencia;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo
 * Fornes, Santiago Girardi)
 */
public class ProyeccionData {

    private Connection con;
    private PeliculaData peliculaData;
    private SalaData salaData;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ProyeccionData() {
        con = Conexion.getConexion();
        peliculaData = new PeliculaData();
        salaData = new SalaData();
    }

    //alta
    public void guardarProyeccion(Proyeccion proyec) {
        String horaInicioStr = proyec.getHoraInicio().format(FORMAT);
        String horaFinStr = proyec.getHoraFin().format(FORMAT);
        
        String query = "INSERT INTO proyeccion (pelicula, idioma, es3D, subtitulada, horaInicio, horaFin, lugaresDisponibles, sala, precioLugar)"
                + " VALUE (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, proyec.getPelicula().getTitulo());
            ps.setString(2, proyec.getIdioma());
            ps.setBoolean(3, proyec.isEs3D());
            ps.setBoolean(4, proyec.isSubtitulada());
            ps.setString(5, horaInicioStr);
            ps.setString(6, horaFinStr);
            ps.setInt(7, proyec.getLugaresDisponibles());
            ps.setInt(8, proyec.getSala().getNroSala());
            ps.setInt(9, proyec.getPrecioLugar());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                proyec.setIdProyeccion(rs.getInt(1));
                System.out.println("Proyeccion guardada correctamente con el ID: " + proyec.getIdProyeccion());
            }

        } catch (SQLException ex) {
            System.out.println("Error al tratar de guardar Proyeccion" + ex.getMessage());
        }
    }

    //baja
    public void eliminarProyeccion(int idProyec) {
        String query = "DELETE FROM proyeccion WHERE idProyeccion = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, idProyec);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Proyección con el ID: " + idProyec + ", eliminado.");
            } else {
                System.out.println("Proyección no encontrada!");
            }
        } catch (SQLException ex) {
            System.out.println("Error al tratar de eliminar Proyección: " + ex.getMessage());
        }
    }

    //actualizar o modificar
    public void actualizarProyeccion(Proyeccion proyec) {
        String horaInicioStr = proyec.getHoraInicio().format(FORMAT);
        String horaFinStr = proyec.getHoraFin().format(FORMAT);
        
        
        String query = "UPDATE proyeccion SET pelicula = ?, idioma = ?, es3D = ?, subtitulada = ?,"
                + "horaInicio = ?, horaFin = ?, lugaresDisponibles = ?, sala = ?, precioLugar = ? WHERE idProyeccion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, proyec.getPelicula().getTitulo());
            ps.setString(2, proyec.getIdioma());
            ps.setBoolean(3, proyec.isEs3D());
            ps.setBoolean(4, proyec.isSubtitulada());
            ps.setString(5, horaInicioStr);
            ps.setString(6, horaFinStr);
            ps.setInt(7, proyec.getLugaresDisponibles());
            ps.setInt(8, proyec.getSala().getNroSala());
            ps.setInt(9, proyec.getPrecioLugar());
            ps.setInt(10, proyec.getIdProyeccion());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Proyección con el ID: " + proyec.getIdProyeccion() + ", modificado.");
            } else {
                System.out.println("Proyección no encontrada!");
            }

        } catch (SQLException ex) {
            System.out.println("Error al tratar de modificar proyección" + ex.getMessage());
        }

    }

    public Proyeccion buscarProyeccionPorID(int idProyec) {
        Proyeccion pro = null;
        String query = "SELECT * FROM proyeccion WHERE idProyeccion = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idProyec);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pro = new Proyeccion();
                pro.setIdProyeccion(idProyec);

                Pelicula peli = peliculaData.obtenerPeliculaPorTitulo(rs.getString("pelicula"));
                Sala sala = salaData.buscarSala(rs.getInt("sala"));

                String horaInicioStr = rs.getString("horaInicio");
                String horaFinStr = rs.getString("horaFin");

                pro.setPelicula(peli);
                pro.setIdioma(rs.getString("idioma"));
                pro.setEs3D(rs.getBoolean("es3D"));
                pro.setSubtitulada(rs.getBoolean("subtitulada"));
                pro.setHoraInicio(LocalDateTime.parse(horaInicioStr, FORMAT));
                pro.setHoraFin(LocalDateTime.parse(horaFinStr, FORMAT));
                pro.setLugaresDisponibles(rs.getInt("lugaresDisponibles"));
                pro.setSala(sala);
                pro.setPrecioLugar(rs.getInt("precioLugar"));
            }
        } catch (SQLException | java.time.format.DateTimeParseException ex) {
            System.out.println("Error al buscar Proyección o parsear fecha" + ex.getMessage());
        }
        return pro;
    }

    public List<Proyeccion>listarPorSalaYHora(int nroSala, LocalDateTime hora){
    
        List<Proyeccion> listaProyec = new ArrayList<>();
        String horaStr = hora.format(FORMAT);
        
        String query = "SELECT idProyeccion FROM proyeccion WHERE sala = ? AND horaInicio = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, nroSala);
            ps.setString(2, horaStr);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idPro = rs.getInt("idProyeccion");
                
                Proyeccion p = buscarProyeccionPorID(idPro);
                
                if(p != null){
                    listaProyec.add(p);
                }
            }
        
        }catch(SQLException ex){
            System.out.println("Error al tratar de listar proyecciones (ProyeccionData): " + ex.getMessage());
        }
        return listaProyec;
    }

    public List<Proyeccion> listarTodasLasProyecciones() {

        List<Proyeccion> lista = new ArrayList<>();
        String query = "SELECT * FROM proyeccion";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Proyeccion p = new Proyeccion();
                p.setIdProyeccion(rs.getInt("idProyeccion"));

                String titulo = rs.getString("pelicula");
                int nroSala = rs.getInt("sala");

                Pelicula peli = peliculaData.obtenerPeliculaPorTitulo(titulo);
                Sala sala = salaData.buscarSala(nroSala);
                String inicioStr = rs.getString("horaInicio");
                String finStr = rs.getString("horaFin");

                p.setPelicula(peli);
                p.setIdioma(rs.getString("idioma"));
                p.setEs3D(rs.getBoolean("es3D"));
                p.setSubtitulada(rs.getBoolean("subtitulada"));
                p.setHoraInicio(LocalDateTime.parse(inicioStr, FORMAT));
                p.setHoraFin(LocalDateTime.parse(finStr, FORMAT));
                p.setLugaresDisponibles(rs.getInt("lugaresDisponibles"));
                p.setSala(sala);
                p.setPrecioLugar(rs.getInt("precioLugar"));
                
                
                System.out.println("Proyeccion cargada: " + p.toString());
                lista.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar todas las Proyecciones" + ex.getMessage());
        }
        return lista;
    }

}
