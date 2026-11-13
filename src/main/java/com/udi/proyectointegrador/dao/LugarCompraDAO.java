package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.LugarCompra;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class LugarCompraDAO {

    public boolean insertar(LugarCompra lc) {
        String sql = "{call SP_INSERTAR_LUGAR_COMPRA(?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, lc.getNombre());
            cs.setString(2, lc.getTipo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar lugar de compra: " + e.getMessage());
            return false;
        }
    }

    public List<LugarCompra> listarTodos() {
        List<LugarCompra> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_LUGARES_COMPRA(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new LugarCompra(
                        rs.getInt("id_lugar_compra"),
                        rs.getString("nombre"),
                        rs.getString("tipo")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar lugares de compra: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(LugarCompra lc) {
        String sql = "{call SP_ACTUALIZAR_LUGAR_COMPRA(?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, lc.getIdLugarCompra());
            cs.setString(2, lc.getNombre());
            cs.setString(3, lc.getTipo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar lugar de compra: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idLugarCompra) {
        String sql = "{call SP_ELIMINAR_LUGAR_COMPRA(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idLugarCompra);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar lugar de compra: " + e.getMessage());
            return false;
        }
    }
}