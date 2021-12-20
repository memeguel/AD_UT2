package demo3;

import demo2.*;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author CFGS
 */
public class Principal3 {

    public static void main(String[] args) throws SQLException {

        //        Connection conn=new Conexion().getConexion();
        Connection conexion = conectar();

        //crearTabla(conexion);
        //consultarDatos(conexion);
        //Insertar Alumno
        insertarAlumno(conexion);
        //insertarAlumnoDatosConsola(conexion);
        insertarAlumnos(conexion);
        consultarDatos(conexion);
        //Modificar Alumno
        //modificarAlumno(conexion);
        //consultarDatos(conexion);

        //Eliminar Alumno
        //eliminarAlumno(conexion);
        //consultarDatos(conexion);

        //cerrarConexion(conexion);
    } //Fin del main

    private static void crearTabla(Connection conn) {
//        String sql="CREATE TABLE IF NOT EXISTS alumnos (id INTEGER NOT NULL PRIMARY KEY,"
//                + "nombre TEXT NOT NULL,"
//                + "ciclo TEXT NOT NULL);";
        String sql="CREATE TABLE IF NOT EXISTS alumnos (id INTEGER NOT NULL,"
                + "nombre TEXT NOT NULL,"
                + "ciclo TEXT NOT NULL,"
                + "PRIMARY KEY(id));";
        
        try {
//            Statement sentencia=conn.createStatement();
//            sentencia.execute(sql);
            conn.createStatement().execute(sql);
            System.out.println("La tabla se creo correctamente");
        } catch (SQLException ex) {
            System.err.println("Error al creal la tabla"+ex.getMessage());
        }
    }
    private static void consultarDatos(Connection conn) {
        String sql = "SELECT * FROM alumnos;";
        //String sql2="SELECT id, nombre FROM alumnos;";

        try {
            Statement sentencia = conn.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            System.out.println("A continuación se mostraran los datos");
            System.out.printf("|%-4s|%-10s|%-10s|%n", "ID", "NOMBRE", "CICLO");
            System.out.println("|----|----------|----------|");
            while (rs.next()) {
                //System.out.printf("|%-3d|%-10s|%-10s|%n", rs.getInt(1),rs.getString(2),rs.getString(3)); <-- EN VEZ DEL NOMBRE DEL CAMPO SE PONE EL NUMERO DE LA COLUMNA
                System.out.printf("|%-4d|%-10s|%-10s|%n", rs.getInt("id"), rs.getString("nombre"), rs.getString("ciclo"));
                System.out.println("|----|----------|----------|");
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar datos" + ex.getMessage());
        }
    }

    private static Connection conectar() {
        Connection conexion = null;
        String url = "jdbc:mysql://localhost:3306/prueba";
        try {
            conexion = DriverManager.getConnection(url,"root","ovejita123");
            System.out.println("Conexion con la base de datos establecida");
        } catch (SQLException ex) {
            System.err.println("Se ha producido SQLException. " + ex.getMessage());
        }
        return conexion;
    }

    private static void insertarAlumno(Connection conexion) throws SQLException {
        //String url="INSERT INTO ejemplo VALUES(4,'Pepe',\"DAM\");";
        String sql = "INSERT INTO alumnos VALUES(?,?,?);";
        PreparedStatement ps = conexion.prepareStatement(sql);

        ps.setInt(1, 1000);
        ps.setString(2, "Sara Perez");
        ps.setString(3, "SMR");

        ps.executeUpdate();
    }

    private static void insertarAlumnoDatosConsola(Connection conexion) throws SQLException {
        Scanner sc = new Scanner(System.in).useDelimiter("\n");
        String sql = "INSERT INTO alumnos VALUES(?,?,?);";
        PreparedStatement ps = conexion.prepareStatement(sql);

        System.out.println("Introduzca el id:");
        ps.setInt(1, sc.nextInt());
        System.out.println("Introduzca el nombre del alumno:");
        ps.setString(2, sc.next());
        System.out.println("Introduzca el nombre del ciclo:");
        ps.setString(3, sc.next());

        ps.executeUpdate();
    }

    private static void insertarAlumnos(Connection conexion) throws SQLException {
        int[] id = {12, 13, 14, 15};
        String[] nombre = {"Alejandra", "María Luisa", "Juan", "José"};
        String[] ciclo = {"DAM", "DAM", "DAW", "DAW"};
        String sql = "INSERT INTO alumnos VALUES(?,?,?);";
        PreparedStatement ps = conexion.prepareStatement(sql);
        for (int i = 0; i < 4; i++) {
            ps.setInt(1, id[i]);
            ps.setString(2, nombre[i]);
            ps.setString(3, ciclo[i]);

            ps.executeUpdate();
        }
    }

    private static void modificarAlumno(Connection conexion) throws SQLException {
        String sql = "UPDATE alumnos SET nombre = ?, ciclo = ? WHERE id = ?";    
       
        PreparedStatement ps = conexion.prepareStatement(sql);
       
        Scanner sc = new Scanner(System.in).useDelimiter("\n");
       
        //Pedimos el id del alumno que queremos modificar
        System.out.print("Id alumno a modificar: ");
        int id = sc.nextInt();
       
        System.out.print("Introduzca el nuevo nombre del alumno: ");
        String nuevoNombre = sc.next();
        System.out.print("Introduzca el nuevo ciclo: ");
        String nuevoCiclo = sc.next();
       
        ps.setString(1, nuevoNombre);
        ps.setString(2, nuevoCiclo);
        ps.setInt(3, id);
       
        ps.executeUpdate();
    }

    private static void eliminarAlumno(Connection conexion) throws SQLException {
        String sql = "DELETE FROM alumnos WHERE id = ? OR id = ?";
        PreparedStatement ps=conexion.prepareStatement(sql);
        
        Scanner sc=new Scanner(System.in).useDelimiter("\n");
        System.out.println("Introduce el id a borrar:");
        ps.setInt(1, sc.nextInt());
        System.out.println("Introduce el id a borrar:");
        ps.setInt(2, sc.nextInt());
        ps.executeUpdate();
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
