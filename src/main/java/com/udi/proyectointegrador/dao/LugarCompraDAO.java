package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.LugarCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LugarCompraDAO {

    public List<LugarCompra> listarTodos() {
        List<LugarCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM LUGAR_COMPRA ORDER BY nombre ASC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LugarCompra lc = new LugarCompra(
                    rs.getInt("id_lugar_compra"),
                    rs.getString("nombre"),
                    rs.getString("tipo")
                );
                lista.add(lc);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar lugares de compra: " + e.getMessage());
        }
        return lista;
    }
}