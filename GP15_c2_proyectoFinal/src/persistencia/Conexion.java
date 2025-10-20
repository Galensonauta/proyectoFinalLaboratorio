/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author y
 */
public class Conexion {
    private static final String URL = "jdbc:mariadb://localhost:3306/gp15_bd_proyecto_final";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static Connection conexion;
    
    public static Connection getConexion() {
        if (conexion == null){
            try{
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexion a la base de datos");
            
            }catch(ClassNotFoundException a) {
                    System.out.println("error ");
            }
            catch(SQLException ex){
                System.out.println("Error sql");
            }
        }
    return conexion;     
    }
    
}
