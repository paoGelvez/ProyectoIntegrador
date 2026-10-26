package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.FormaAdquisicion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormaAdquisicionDAO {

    public List<FormaAdquisicion> listarTodas() {
        List<FormaAdquisicion> lista = new ArrayList<>();
        String sql = "SELECT * FROM FORMA_ADQUISICION ORDER BY nombre ASC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FormaAdquisicion fa = new FormaAdquisicion(
                    rs.getInt("id_forma_adquisicion"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                lista.add(fa);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar formas de adquisición: " + e.getMessage());
        }
        return lista;
    }
}
