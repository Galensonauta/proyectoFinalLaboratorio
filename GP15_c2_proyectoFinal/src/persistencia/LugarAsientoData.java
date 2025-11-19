package persistencia;

import java.sql.*;
import java.util.HashSet;
import modelo.*;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo
 * Fornes, Santiago Girardi)
 */

public class LugarAsientoData {

    private Connection con;
    private ProyeccionData proyeccionData;
    

    public LugarAsientoData() {
        try {
            con = Conexion.getConexion();
            if (con != null) {
                proyeccionData = new ProyeccionData();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion en clase LugarAsientoData" + e.getMessage());
        }
    }

    public void guardarAsiento(LugarAsiento l) {

        String query = "INSERT INTO lugar_asiento (filaAsiento, numeroAsiento, estado, proyeccion) VALUE (?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            
            ps.setString(1, l.getFila());
            ps.setInt(2, l.getNumeroAsiento());
            ps.setBoolean(3, l.isEstado());
            ps.setInt(4, l.getIdProyeccion());

            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int codLugarGenerado = rs.getInt(1);
                
                // 2. Setear el ID generado de vuelta en el objeto
                l.setCodLugar(codLugarGenerado); 
                
                System.out.println("Asiento guardado y CodLugar generado: " + codLugarGenerado);
            } else {
                throw new SQLException("Error: No se pudo obtener la clave generada para el asiento.");
            }
        }

        } catch (SQLException ex) {
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

    public void modificarAsiento(LugarAsiento la) {

        String query = "UPDATE lugar_asiento SET filaAsiento = ?, numeroAsiento = ?, estado = ?, proyeccion = ? WHERE titulo = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, la.getFila());
            ps.setInt(2, la.getNumeroAsiento());
            ps.setBoolean(3, la.isEstado());
            ps.setInt(4, la.getIdProyeccion());
            ps.setInt(5, la.getCodLugar());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Asiento con el código : " + la.getCodLugar() + ", modificado con exito");
            } else {
                System.out.println("Asiento no encontrado para modificarlo");
            }

        } catch (SQLException ex) {
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

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, codLugar);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                asiento = new LugarAsiento();
                asiento.setCodLugar(codLugar);

                Proyeccion p = proyeccionData.buscarProyeccionPorID(rs.getInt("proyeccion"));

                asiento.setIdProyeccion(p.getIdProyeccion());
                asiento.setFila(rs.getString("filaAsiento"));
                asiento.setNumeroAsiento(rs.getInt("numeroAsiento"));
                asiento.setEstado(rs.getBoolean("estado"));

            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar Asiento: " + ex.getMessage());
        }
        return asiento;
    }

    public int contarLugaresDisponibles(int idProyeccion) {
        int count = 0;
        String sql = "SELECT COUNT(codLugar) AS libres FROM lugar_asiento WHERE proyeccion = ? AND estado = 0";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyeccion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("libres"); // Devuelve las butacas libres
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al contar lugares disponibles: " + ex.getMessage());
        }
        return count;
    }
    
    public void eliminarButacaSegunProyeccion(int idPro) {
        String query = "DELETE FROM lugar_asiento WHERE proyeccion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idPro);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al tratar de eliminar lugar asiento según ID Proyeccion: " + e.getMessage());
        }
    }
    
    
    public HashSet<String> obtenerAsientosOcupados(int idProyeccion){
        HashSet<String> ocupados = new HashSet();
        
        String query = "SELECT filaAsiento, numeroAsiento FROM lugar_asiento WHERE proyeccion = ? AND estado = 1";
        
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idProyeccion);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            
            // Obtener los valores de las columnas
            String fila = rs.getString("filaAsiento");
            int numero = rs.getInt("numeroAsiento");
            
            //Construir la etiqueta 
            String etiqueta = fila + String.valueOf(numero);
            
            // Agregar la etiqueta al HashSet
            ocupados.add(etiqueta);
        }
            
        rs.close(); 
        ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al obtener Asiento: " + ex.getMessage());
        }
        
        return ocupados;
    }
}
