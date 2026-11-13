package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Gasto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class GastoDAO {

    public boolean insertar(Gasto g) {
        String sql = "{call SP_INSERTAR_GASTO(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setDate(1, g.getFechaGasto());
            cs.setDouble(2, g.getCantidad());
            cs.setDouble(3, g.getValorUnitario());
            cs.setDouble(4, g.getValorTotal());
            cs.setString(5, g.getEstadoGasto());
            cs.setInt(6, g.getIdVisita());
            cs.setInt(7, g.getIdProducto());

            if (g.getIdLugarCompra() != null && g.getIdLugarCompra() > 0) {
                cs.setInt(8, g.getIdLugarCompra());
            } else {
                cs.setNull(8, Types.INTEGER);
            }

            cs.setInt(9, g.getIdFormaAdquisicion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar gasto: " + e.getMessage());
            return false;
        }
    }

    public List<Gasto> listarTodos() {
        List<Gasto> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_GASTOS(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    Object lugarObj = rs.getObject("id_lugar_compra");
                    Integer idLugar = (lugarObj != null) ? ((Number) lugarObj).intValue() : null;

                    lista.add(new Gasto(
                        rs.getInt("id_gasto"),
                        rs.getDate("fecha_gasto"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("valor_unitario"),
                        rs.getDouble("valor_total"),
                        rs.getString("estado_gasto"),
                        rs.getInt("id_visita"),
                        rs.getInt("id_producto"),
                        idLugar,
                        rs.getInt("id_forma_adquisicion")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar todos los gastos: " + e.getMessage());
        }
        return lista;
    }

    public List<Gasto> listarPorVisita(int idVisita) {
        List<Gasto> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_GASTOS_VISITA(?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idVisita);
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                while (rs.next()) {
                    Object lugarObj = rs.getObject("id_lugar_compra");
                    Integer idLugar = (lugarObj != null) ? ((Number) lugarObj).intValue() : null;

                    lista.add(new Gasto(
                        rs.getInt("id_gasto"),
                        rs.getDate("fecha_gasto"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("valor_unitario"),
                        rs.getDouble("valor_total"),
                        rs.getString("estado_gasto"),
                        rs.getInt("id_visita"),
                        rs.getInt("id_producto"),
                        idLugar,
                        rs.getInt("id_forma_adquisicion")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar gastos por visita: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Gasto g) {
        String sql = "{call SP_ACTUALIZAR_GASTO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, g.getIdGasto());
            cs.setDate(2, g.getFechaGasto());
            cs.setDouble(3, g.getCantidad());
            cs.setDouble(4, g.getValorUnitario());
            cs.setDouble(5, g.getValorTotal());
            cs.setString(6, g.getEstadoGasto());
            cs.setInt(7, g.getIdVisita());
            cs.setInt(8, g.getIdProducto());

            if (g.getIdLugarCompra() != null && g.getIdLugarCompra() > 0) {
                cs.setInt(9, g.getIdLugarCompra());
            } else {
                cs.setNull(9, Types.INTEGER);
            }

            cs.setInt(10, g.getIdFormaAdquisicion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar gasto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idGasto) {
        String sql = "{call SP_ELIMINAR_GASTO(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idGasto);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar gasto: " + e.getMessage());
            return false;
        }
    }

    public double obtenerTotalPorVisita(int idVisita) {
        String sql = "{? = call FN_TOTAL_GASTOS_VISITA(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.DOUBLE);
            cs.setInt(2, idVisita);
            cs.execute();

            return cs.getDouble(1);

        } catch (SQLException e) {
            System.out.println("Error al calcular total de gastos: " + e.getMessage());
            return 0.0;
        }
    }
}