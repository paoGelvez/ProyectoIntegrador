package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario autenticar(String identificacion, String contrasena) {
        String sql = "SELECT * FROM USUARIO WHERE identificacion = ? AND contrasena = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, identificacion.trim());
            ps.setString(2, contrasena.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("identificacion"),
                        rs.getString("rol"),
                        rs.getString("contrasena")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en autenticación: " + e.getMessage());
        }
        return null;
    }
}