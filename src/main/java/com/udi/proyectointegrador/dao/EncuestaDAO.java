package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Encuesta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EncuestaDAO {

    public boolean insertar(Encuesta enc) {
        String sql = "INSERT INTO ENCUESTA (id_encuesta, codigo_encuesta, fecha_inicio, fecha_fin, estado, id_hogar, id_usuario) " +
                     "VALUES (seq_encuesta.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, enc.getCodigoEncuesta());
            ps.setDate(2, enc.getFechaInicio());
            ps.setDate(3, enc.getFechaFin());
            ps.setString(4, enc.getEstado());
            ps.setInt(5, enc.getIdHogar());
            ps.setInt(6, enc.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar encuesta: " + e.getMessage());
            return false;
        }
    }
}
