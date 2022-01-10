package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author CFGS
 */
public class Principal {

    public static void main(String[] args) {

        Connection conexion = crearConexion();

    } //Fin del main

    

    private static Connection crearConexion() {
        Connection conexion = null;
        String url = "jdbc:mysql://localhost:3306/prueba";
        try {
            conexion = DriverManager.getConnection(url, "root", "ovejita123");
        } catch (SQLException ex) {
            System.err.println("Se ha producido SQLException. " + ex.getMessage());
        }
        return conexion;
    }
} //Fin de la clase Principal
