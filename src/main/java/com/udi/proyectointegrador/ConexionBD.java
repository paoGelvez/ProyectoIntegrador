package com.udi.proyectointegrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Cambia la IP, puerto y SID por los de tu servidor/local
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USUARIO = "system";
    private static final String CLAVE = "PROYECTOINTEGRADOR1097";

    public static Connection obtenerConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("¡Conexión establecida con éxito a Oracle!");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }

    public static void main(String[] args) {
        obtenerConexion();
    }
}