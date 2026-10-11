package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO ORDER BY nombre ASC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre"),
                    rs.getBoolean("es_neces_basica"),
                    rs.getInt("id_categoria"),
                    rs.getInt("id_unidad_medida")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }
}