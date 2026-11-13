package com.udi.proyectointegrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexionBD {

    // 1. Conexión servidor de la Universidad
    private static final String URL_U = "jdbc:oracle:thin:@192.168.254.215:1521:orcl";

    // 2. Conexión local en tu computador (Oracle 21c XE)
    private static final String URL_LOCAL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";

    private static final String USUARIO = "PROYECTOINTEGRADOR1097780309";
    private static final String CLAVE = "PROYECTOINTEGRADOR1097780309";

    // Caché en memoria: una vez detectado que la red de la U no está, va directo a Local
    private static boolean redUDescartada = false;

    public static Connection obtenerConexion() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            Properties props = new Properties();
            props.setProperty("user", USUARIO);
            props.setProperty("password", CLAVE);
            // Timeout real de socket TCP (1000 ms = 1 segundo máximo)
            props.setProperty("oracle.net.CONNECT_TIMEOUT", "1000");

            if (!redUDescartada) {
                try {
                    con = DriverManager.getConnection(URL_U, props);
                    System.out.println("¡CONEXION ESTABLECIDA CON LA BASE DE DATOS DE LA U!");
                } catch (SQLException eU) {
                    redUDescartada = true;
                    System.out.println("Red de la U no disponible. Cambiando a Oracle Local...");
                    con = DriverManager.getConnection(URL_LOCAL, props);
                    System.out.println("¡CONEXION ESTABLECIDA CON ORACLE LOCAL (localhost)!");
                }
            } else {
                con = DriverManager.getConnection(URL_LOCAL, props);
            }

            if (con != null) {
                try (Statement stmt = con.createStatement()) {
                    stmt.execute("ALTER SESSION SET CURRENT_SCHEMA = PROYECTOINTEGRADOR1097780309");
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error crítico: No se pudo conectar ni a la U ni a Local: " + e.getMessage());
        }
        return con;
    }

    public static void main(String[] args) {
        obtenerConexion();
    }
}