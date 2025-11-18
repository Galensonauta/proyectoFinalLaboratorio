package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import modelo.Sala;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */

public class SalaData {

    private Connection con;

    public SalaData() {
        con = Conexion.getConexion();
    }     

    public void guardarSala(Sala s) {
        String sql = "INSERT INTO sala (nroSala, apta3D, capacidad, estado) values (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, s.getNroSala());
            ps.setBoolean(2, s.isApta3D());
            ps.setInt(3, s.getCapacidad());
            ps.setBoolean(4, s.isEstado());
            ps.executeUpdate();
            System.out.println("Sala guardada con éxito");

        } catch (SQLException e) {
            System.out.println("Error al guardar la sala");
        }
    }

    public void actualizarSala(int nroSala, boolean nuevoApta3D, int nuevaCapacidad, boolean nuevoEstado) {
        String sql = "UPDATE sala SET apta3D = ?, capacidad = ?, estado = ? WHERE nroSala = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, nuevoApta3D);
            ps.setInt(2, nuevaCapacidad);
            ps.setBoolean(3, nuevoEstado);
            ps.setInt(4, nroSala);

            int registros = ps.executeUpdate();
            if (registros > 0) {
                System.out.println("La sala nro: " + nroSala + " fue modificada");
            } else {
                System.out.println("No se encontro ninguna sala nro: " + nroSala);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar al sala: " + e.getMessage());
        }
    }

    public void borrarSala(int nroSala) {
        String sql = "DELETE FROM sala WHERE nroSala = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroSala);
            int registros = ps.executeUpdate();
            if (registros > 0) {
                System.out.println("La sala " + nroSala + " fue elimanada de la base de datos");
            } else {
                System.out.println("No se encontro ninguna sala con el numero: " + nroSala);
            }
        } catch (SQLException e) {
            System.out.println("Error al borar la sala" + e.getMessage());
        }
    }

    public void bajaSala(int nroSala) {
        String sql = "UPDATE sala SET estado = ? WHERE nroSala = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setInt(2, nroSala);
            int registros = ps.executeUpdate();
            if (registros > 0) {
                System.out.println("La sala " + nroSala + " fue dada de baja");
            } else {
                System.out.println("No se encontro ninguna sala con el nro: " + nroSala);
            }
        } catch (SQLException e) {
            System.out.println("Error al dar de baja la sala" + e.getMessage());
        }
    }

    public void altaSala(int nroSala) {
        String sql = "UPDATE sala SET estado = ? WHERE nroSala = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setInt(2, nroSala);
            int registros = ps.executeUpdate();
            if (registros > 0) {
                System.out.println("La sala nro: " + nroSala + " fue dada de alta");
            } else {
                System.out.println("No se encontro ninguna sala con ese numero: " + nroSala);
            }
        } catch (SQLException e) {
            System.out.println("Error al dar de alta la sala" + e.getMessage());
        }
    }

    public Sala buscarSala(int nroSala) {
        Sala sala = null;
        String sql = "SELECT  * FROM sala WHERE nroSala = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroSala);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sala = new Sala();

                sala.setNroSala(nroSala);
                sala.setApta3D(rs.getBoolean("apta3D"));
                sala.setCapacidad(rs.getInt("capacidad"));
                sala.setEstado(rs.getBoolean("estado"));

                System.out.println("Sala numero : " + nroSala + " encontrada");
            } else {
                System.out.println("No se encontro ninguna sala con ese nro");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la sala: " + e.getMessage());
        }
        return sala;
    }
    
    public ArrayList<Sala> obtenerTodasLasSalas() {

        ArrayList<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM sala"; 
        try {
            PreparedStatement ps = con.prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Sala sala = new Sala();
                sala.setNroSala(rs.getInt("nroSala"));
                sala.setApta3D(rs.getBoolean("apta3D"));
                sala.setCapacidad(rs.getInt("capacidad"));
                sala.setEstado(rs.getBoolean("estado"));
                salas.add(sala);
            }
        rs.close();
        ps.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de salas: " + e.getMessage());
        }    
        return salas;
    }
    
    
    

    public List<Sala> obtenerSalasHabilitadas() {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM sala WHERE estado = 1"; // <-- El filtro clave

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sala sala = new Sala();
                sala.setNroSala(rs.getInt("nroSala"));
                sala.setApta3D(rs.getBoolean("apta3D"));
                sala.setCapacidad(rs.getInt("capacidad"));
                sala.setEstado(true); // Ya sabemos que es true
                salas.add(sala);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener salas habilitadas: " + e.getMessage());
        }
        return salas;
    }
    

}
