/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.*;



/**
 *
 * @author Rolan
 */
public class Conectar {
    Connection cn;
    Statement st;
    
    public Connection conexion(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //user: admin
            //PW: admin123
            String url = "jdbc:sqlserver://Roland:1433;databaseName=InscribaTec;encrypt=false;trustServerCertificate=true";
            String user = "administrador"; // 
            String password = "Admin123"; // 
            
            cn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");

        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexion SQL: " + e.getMessage());
        }
        return cn;
    }
    
    public void cerrarConexion() {
        if (cn != null) {
            try {
                cn.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
