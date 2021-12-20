package demo1;

import java.sql.*;

/**
 *
 * @author CFGS
 */

public class Principal {

    public static void main(String[] args) {

        //        Connection conn=new Conexion().getConexion();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        crearTabla(conn);
        consultarDatos(conn);

        conexion.cerrarConexion(conn);
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
        String sql="SELECT * FROM alumnos;";
        String sql2="SELECT id, nombre FROM alumnos;";
        
        try {
            Statement sentencia=conn.createStatement();
            ResultSet rs=sentencia.executeQuery(sql);
            System.out.println("A continuación se mostraran los datos");
            System.out.printf("|%-3s|%-10s|%-10s|%n","ID","NOMBRE","CICLO");
            System.out.println("|---|----------|----------|");
            while(rs.next()){
                //System.out.printf("|%-3d|%-10s|%-10s|%n", rs.getInt(1),rs.getString(2),rs.getString(3)); <-- EN VEZ DEL NOMBRE DEL CAMPO SE PONE EL NUMERO DE LA COLUMNA
                System.out.printf("|%-3d|%-10s|%-10s|%n", rs.getInt("id"),rs.getString("nombre"),rs.getString("ciclo"));
                System.out.println("|---|----------|----------|");
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar datos"+ex.getMessage());
        }
    }

} //Fin de la clase Principal
