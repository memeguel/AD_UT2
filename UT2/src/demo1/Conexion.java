package demo1;

import java.sql.*;

//import com.sun.jdi.connect.spi.Connection;  <-- Esto no
/**
 *
 * @author CFGS
 */
public class Conexion {

    private Connection conexion;

    //private int edad;
    public Conexion() {
        this.conexion = null;
    }//Fin constructor vacio

    //getters y setters
    public Connection getConexion() {
        String url = "jdbc:sqlite:./src/db/ejemplo.db";
        try {
            this.conexion = DriverManager.getConnection(url);
            System.out.println("Conexion con la base de datos establecida");
        } catch (SQLException ex) {
            System.err.println("Se ha producido SQLException. " + ex.getMessage());
        }
        return this.conexion;
    }//Fin getConexion

    public void cerrarConexion(Connection conn) {
        try {
            if (conn!=null) {
                conn.close();
                System.out.println("Conexion cerrada");
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexion. "+ex.getMessage());
        }
    }
}//Fin class
