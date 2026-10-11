package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Visita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitaDAO {

    public boolean insertar(Visita v) {
        String sql = "INSERT INTO VISITA (id_visita, numero_dia, fecha_visita, estado_visita, observaciones, id_encuesta) " +
                     "VALUES (seq_visita.NEXTVAL, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, v.getNumeroDia());
            ps.setDate(2, v.getFechaVisita());
            ps.setString(3, v.getEstadoVisita());
            ps.setString(4, v.getObservaciones());
            ps.setInt(5, v.getIdEncuesta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar visita: " + e.getMessage());
            return false;
        }
    }

    public List<Visita> listarPorEncuesta(int idEncuesta) {
        List<Visita> lista = new ArrayList<>();
        String sql = "SELECT * FROM VISITA WHERE id_encuesta = ? ORDER BY numero_dia ASC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEncuesta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Visita v = new Visita(
                        rs.getInt("id_visita"),
                        rs.getInt("numero_dia"),
                        rs.getDate("fecha_visita"),
                        rs.getString("estado_visita"),
                        rs.getString("observaciones"),
                        rs.getInt("id_encuesta")
                    );
                    lista.add(v);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar visitas: " + e.getMessage());
        }
        return lista;
    }
}