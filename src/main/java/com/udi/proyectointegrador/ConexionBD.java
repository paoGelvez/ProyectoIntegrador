package com.udi.proyectointegrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    private static final String URL = "jdbc:oracle:thin:@192.168.254.215:1521:orcl";
    private static final String USUARIO = "PROYECTOINTEGRADOR1097780309";
    private static final String CLAVE = "PROYECTOINTEGRADOR1097780309";

    public static Connection obtenerConexion() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
            
            // Fija el esquema activo para que NO tengas que cambiar ninguna consulta en los DAO
            try (Statement stmt = con.createStatement()) {
                stmt.execute("ALTER SESSION SET CURRENT_SCHEMA = PROYECTOINTEGRADOR1097780309");
            }
            
            System.out.println("¡CONEXION ESTABLECIDA CON EXITO A ORACLE!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }

    public static void main(String[] args) {
        obtenerConexion();
    }
}