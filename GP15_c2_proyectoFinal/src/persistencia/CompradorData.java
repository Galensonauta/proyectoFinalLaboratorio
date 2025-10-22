/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Comprador;
import modelo.Sala;

/**
 *
 * @author y
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
