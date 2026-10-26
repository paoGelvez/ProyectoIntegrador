package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Gasto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class GastoDAO {

    public boolean insertar(Gasto g) {
        String sql = "INSERT INTO GASTO (id_gasto, fecha_gasto, cantidad, valor_unitario, valor_total, estado_gasto, id_visita, id_producto, id_lugar_compra, id_forma_adquisicion) " +
                     "VALUES (seq_gasto.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, g.getFechaGasto());
            ps.setDouble(2, g.getCantidad());
            ps.setDouble(3, g.getValorUnitario());
            ps.setDouble(4, g.getValorTotal());
            ps.setString(5, g.getEstadoGasto());
            ps.setInt(6, g.getIdVisita());
            ps.setInt(7, g.getIdProducto());
            
            if (g.getIdLugarCompra() != null) {
                ps.setInt(8, g.getIdLugarCompra());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            
            ps.setInt(9, g.getIdFormaAdquisicion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar gasto: " + e.getMessage());
            return false;
        }
    }

    public List<Gasto> listarPorVisita(int idVisita) {
        List<Gasto> lista = new ArrayList<>();
        String sql = "SELECT * FROM GASTO WHERE id_visita = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVisita);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Gasto g = new Gasto(
                        rs.getInt("id_gasto"),
                        rs.getDate("fecha_gasto"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("valor_unitario"),
                        rs.getDouble("valor_total"),
                        rs.getString("estado_gasto"),
                        rs.getInt("id_visita"),
                        rs.getInt("id_producto"),
                        (Integer) rs.getObject("id_lugar_compra"),
                        rs.getInt("id_forma_adquisicion")
                    );
                    lista.add(g);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar gastos: " + e.getMessage());
        }
        return lista;
    }
}