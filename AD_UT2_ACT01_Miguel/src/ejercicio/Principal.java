package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author CFGS
 */
public class Principal {

    public static void main(String[] args) throws SQLException {
        Connection conexion = crearConexion();
        crearTabla(conexion);
        insertarUsuario(conexion);
        insertarUsuarios(conexion);
        modificarUsuario(conexion);
        eliminarAlumno(conexion);
        cerrarConexion(conexion);

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

    private static void crearTabla(Connection conn) {

        String sql = "CREATE TABLE IF NOT EXISTS users (user_id int(11) NOT NULL AUTO_INCREMENT,"
                + "username TEXT NOT NULL,"
                + "password TEXT NOT NULL,"
                + "nombre TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "PRIMARY KEY(user_id));";
        try {
            conn.createStatement().execute(sql);
        } catch (SQLException ex) {
            System.err.println("Error al creal la tabla" + ex.getMessage());
        }
    }

    private static void mostrarDatos(Connection conn) {
        String sql = "SELECT * FROM users;";
        try {
            Statement sentencia = conn.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            System.out.println("ID USUARIO |USUARIO            |CONTRASEÑA         |NOMBRE                |EMAIL                      |");

            while (rs.next()) {
                System.out.printf("|%-11d|%-19s|%-19s|%-23s|%-27s|%n", rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("nombre"), rs.getString("email"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar datos" + ex.getMessage());
        }
    }

    private static void insertarUsuario(Connection conexion) throws SQLException {
        Scanner sc=new Scanner(System.in).useDelimiter("\n");
        String sql = "INSERT INTO users (username, password, nombre, email) VALUES(?,?,?,?);";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            System.out.println("Introduzca el nombre de usuario: ");
            ps.setString(1, sc.next());
            System.out.println("Introduzca la contraseña: ");
            ps.setString(2, sc.next());
            System.out.println("Introduzca el nombre: ");
            ps.setString(3, sc.next());
            System.out.println("Introduzca el email: ");
            ps.setString(4, sc.next());

            ps.executeUpdate();
            System.out.println("Se ha insertado el usuario correctamente");
            mostrarDatos(conexion);
        } catch (Exception e) {
            System.out.println("Se ha producido un error" + e.getMessage());
        }

    }

    private static void insertarUsuarios(Connection conexion) throws SQLException {
        Scanner sc = new Scanner(System.in).useDelimiter("\n");
        String sql = "INSERT INTO users (username, password, nombre, email) VALUES(?,?,?,?);";
        try{
            System.out.println("Introduzca el numero de usuarios que desea introducir: ");
            int usuarios=sc.nextInt();
            PreparedStatement ps = conexion.prepareStatement(sql);

            for (int i = 0; i < usuarios; i++) {
                System.out.println("Introduzca el nombre de usuario:");
                ps.setString(1, sc.next());
                System.out.println("Introduzca la contraseña:");
                ps.setString(2, sc.next());
                System.out.println("Introduzca el nombre:");
                ps.setString(3, sc.next());
                System.out.println("Introduzca el email:");
                ps.setString(4, sc.next());

                ps.executeUpdate();
                System.out.println("Se ha insertado el usuario correctamente");
            }
            
            mostrarDatos(conexion);
        }catch (Exception e) {
            System.out.println("Se ha producido un error" + e.getMessage());
        }
    }
    
    private static void modificarUsuario(Connection conexion) throws SQLException {
        String sql = "UPDATE alumnos SET username = ?, password = ?, nombre = ?, email = ? WHERE user_id = ?";    
        try{
            PreparedStatement ps = conexion.prepareStatement(sql);

            Scanner sc = new Scanner(System.in).useDelimiter("\n");

            //Pedimos el id del alumno que queremos modificar
            System.out.print("Id del usuario a modificar a modificar: ");
            int id = sc.nextInt();

            System.out.print("Introduzca el nuevo nombre usuario: ");
            String nuevoUsuario = sc.next();
            System.out.print("Introduzca la nueva contraseña: ");
            String nuevaContraseña = sc.next();
            System.out.print("Introduzca la nuevo nombre: ");
            String nuevoNombre = sc.next();
            System.out.print("Introduzca la nuevo email: ");
            String nuevoEmail = sc.next();

            ps.setString(1, nuevoUsuario);
            ps.setString(2, nuevaContraseña);
            ps.setString(3, nuevoNombre);
            ps.setString(4, nuevoEmail);
            ps.setInt(5, id);

            ps.executeUpdate();
            mostrarDatos(conexion);
        }catch (Exception e) {
            System.out.println("Se ha producido un error" + e.getMessage());
        }
    }
    
    private static void eliminarAlumno(Connection conexion) throws SQLException {
        String sql = "DELETE FROM alumnos WHERE username = ?";
        try{
            PreparedStatement ps=conexion.prepareStatement(sql);

            Scanner sc=new Scanner(System.in).useDelimiter("\n");
            System.out.println("Introduce el id a borrar:");
            ps.setString(1, sc.next());
            
            ps.executeUpdate();
            mostrarDatos(conexion);
        }catch (Exception e) {
            System.out.println("Se ha producido un error" + e.getMessage());
        }
    }
    
    private static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("Conexion cerrada");
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexion. " + ex.getMessage());
        }
    }

} //Fin de la clase Principal
