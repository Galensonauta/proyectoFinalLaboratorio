/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import modelo.Sala;

/**
 *
 * @author y
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
            ps.setBoolean(2, s.getApta3D());
            ps.setInt(3, s.getCapacidad());
            ps.setBoolean(4, s.isEstado());
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println("Error al guardar el alumno");
        }   
    }     
     
     public void actualizarSala(int nroSala, boolean nuevoApta3D, int nuevaCapacidad, boolean nuevoEstado){
        String sql = "UPDATE sala SET apta3D = ?, capacidad = ?, estado = ?, WHERE nroSala = ?";
               try{
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setBoolean(2, nuevoApta3D);
            ps.setInt(3, nuevaCapacidad);
            ps.setBoolean(4, nuevoEstado);
                                    
            int registros = ps.executeUpdate();
            if(registros>0){
                System.out.println("La sala numero" + nroSala + "fue modificada ");
            }else{
                System.out.println("No se encontro ninguna sala numero "+ nroSala);
            }            
        }catch(SQLException e){
            System.out.println("Error al modificar al sala"+ e.getMessage());
        }
    }
}
