package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class UsuarioDAO {

    public Usuario autenticar(String identificacion, String contrasena) {
        // Invocación al procedimiento almacenado sin sentencias SQL directas en Java
        String sql = "{call PR_AUTENTICAR_USUARIO(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            // 1. Parámetros de entrada (IN)
            cs.setString(1, identificacion.trim());
            cs.setString(2, contrasena.trim());

            // 2. Parámetros de salida (OUT)
            cs.registerOutParameter(3, Types.INTEGER); // id_usuario
            cs.registerOutParameter(4, Types.VARCHAR); // nombre
            cs.registerOutParameter(5, Types.VARCHAR); // apellido
            cs.registerOutParameter(6, Types.VARCHAR); // rol

            // 3. Ejecutar el procedimiento
            cs.execute();

            int idUsuario = cs.getInt(3);

            // Verificar si el usuario fue encontrado en la BD
            if (!cs.wasNull() && idUsuario > 0) {
                String nombre = cs.getString(4);
                String apellido = cs.getString(5);
                String rol = cs.getString(6);

                return new Usuario(
                    idUsuario,
                    nombre,
                    apellido,
                    identificacion.trim(),
                    rol,
                    contrasena.trim()
                );
            }

        } catch (SQLException e) {
            System.out.println("Error en autenticación via PL/SQL: " + e.getMessage());
        }
        return null;
    }
}