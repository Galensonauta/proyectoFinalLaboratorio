package persistencia;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import modelo.Comprador;

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
    public Comprador buscarComprador(int dni){
        Comprador c = null;
        String sql ="SELECT  * FROM comprador WHERE dni = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,dni);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c = new Comprador();                        
                c.setDni(rs.getInt("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setPass(rs.getString("pass"));
                LocalDate localDate = rs.getObject("fechaNac", LocalDate.class);
                c.setFechaNac(localDate);
                c.setMedioPago(rs.getString("medioPago"));
                
            System.out.println("Comprador con DNI: "+ dni +" encontrado:");
            }else{
                System.out.println("No se encontro ningun comrpador con ese DNI");
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            System.out.println("Error al obtener comprador"+ e.getMessage());
        }
        return c;
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
     
     
     public ArrayList<Comprador> obtenerTodosLosCompradores() {
    
            ArrayList<Comprador> compradores = new ArrayList<>();
    
        String sql = "SELECT * FROM comprador"; 

        try {
            PreparedStatement ps = con.prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            
                Comprador comprador = new Comprador();            
            
                comprador.setDni(rs.getInt("dni"));
                comprador.setNombre(rs.getString("nombre"));
                comprador.setPass(rs.getString("pass"));
                java.sql.Date utilDate = rs.getDate("fechaNac");
                LocalDate localDate = utilDate.toLocalDate();
                comprador.setFechaNac(localDate);
                comprador.setMedioPago(rs.getString("medioPago"));                
            
                compradores.add(comprador);
        }
        
        
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de compradores: " + e.getMessage());
        }
    
        return compradores;
    }
     
     
     public Comprador buscarCompradorDevuelveComprador(int dni) {
    
        String sql = "SELECT * FROM comprador WHERE dni = ?";
        Comprador comprador = null; // 1. Inicia el objeto como nulo

        // Usamos try-with-resources para que 'ps' y 'rs' se cierren solos
        try (PreparedStatement ps = con.prepareStatement(sql)) {
        
            ps.setInt(1, dni);
        
            try (ResultSet rs = ps.executeQuery()) {
            
            // 2. Si 'rs.next()' es verdadero, encontramos al comprador
                if (rs.next()) {
                
                // 3. Creamos la instancia
                    comprador = new Comprador(); 
                
                // 4. Llenamos el objeto con los datos (uso los campos de tu método anterior)
                    comprador.setDni(rs.getInt("dni"));
                    comprador.setNombre(rs.getString("nombre"));
                    comprador.setPass(rs.getString("pass"));
                    comprador.setMedioPago(rs.getString("medioPago"));
                
                // 5. Manejamos la fecha (con la corrección que hicimos)
                    java.sql.Date sqlDate = rs.getDate("fechaNac");
                    if (sqlDate != null) {
                        comprador.setFechaNac(sqlDate.toLocalDate());
                    } else {
                        comprador.setFechaNac(null);
                    }

                // 6. Si tienes un campo 'estado', también iría aquí
                // comprador.setEstado(rs.getBoolean("estado"));
                
                } else {
                // Si no hay 'next()', no se encontró
                    System.out.println("No se encontro ningun comprador con ese DNI");
                }
            } // 'rs' se cierra aquí
        
            } catch (SQLException e) {
                System.out.println("Error al buscar comprador: " + e.getMessage());
                e.printStackTrace(); // Muestra el error completo en consola
            }
    
            // 7. Devuelve el objeto (será 'null' si no se encontró)
        return comprador;
        }
     
     public ArrayList<Comprador> listarCompradoresPorFechaAsistencia(LocalDate fecha) {
    ArrayList<Comprador> compradores = new ArrayList<>();
    String sql = "SELECT DISTINCT c.dni " + 
                 "FROM comprador c " +
                 "JOIN ticket_compra tc ON c.dni = tc.comprador " +
                 "JOIN detalle_ticket dt ON tc.idTicket = dt.codD " +
                 "WHERE dt.fechProyeccion = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, java.sql.Date.valueOf(fecha));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int dniComprador = rs.getInt("dni");
            Comprador c = this.buscarCompradorDevuelveComprador(dniComprador); 
            compradores.add(c);
        }
    }catch (SQLException ex) {
        System.out.println("Error al listar compradores por fecha: " + ex.getMessage());
    }
    return compradores;
     }
}
