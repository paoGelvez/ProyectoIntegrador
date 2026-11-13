package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Visita;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class VisitaDAO {

    public boolean insertar(Visita v) {
        String sql = "{call SP_INSERTAR_VISITA(?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, v.getNumeroDia());
            cs.setDate(2, v.getFechaVisita());
            cs.setString(3, v.getEstadoVisita());
            cs.setString(4, v.getObservaciones());
            cs.setInt(5, v.getIdEncuesta());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar visita: " + e.getMessage());
            return false;
        }
    }

    public List<Visita> listarTodas() {
        List<Visita> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_VISITAS(?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Visita(
                            rs.getInt("id_visita"),
                            rs.getInt("numero_dia"),
                            rs.getDate("fecha_visita"),
                            rs.getString("estado_visita"),
                            rs.getString("observaciones"),
                            rs.getInt("id_encuesta")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar todas las visitas: " + e.getMessage());
        }
        return lista;
    }

    public List<String> listarVisitasDescriptivas() {
        List<String> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_VISITAS_CON_HOGAR(?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(rs.getInt("id_visita") + " - " + rs.getString("descripcion"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar visitas descriptivas: " + e.getMessage());
        }
        return lista;
    }

    public List<Visita> listarPorEncuesta(int idEncuesta) {
        List<Visita> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_VISITAS_ENCUESTA(?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idEncuesta);
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                while (rs.next()) {
                    lista.add(new Visita(
                            rs.getInt("id_visita"),
                            rs.getInt("numero_dia"),
                            rs.getDate("fecha_visita"),
                            rs.getString("estado_visita"),
                            rs.getString("observaciones"),
                            rs.getInt("id_encuesta")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar visitas por encuesta: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Visita v) {
        String sql = "{call SP_ACTUALIZAR_VISITA(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, v.getIdVisita());
            cs.setInt(2, v.getNumeroDia());
            cs.setDate(3, v.getFechaVisita());
            cs.setString(4, v.getEstadoVisita());
            cs.setString(5, v.getObservaciones());
            cs.setInt(6, v.getIdEncuesta());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar visita: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idVisita) {
        String sql = "{call SP_ELIMINAR_VISITA(?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idVisita);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar visita: " + e.getMessage());
            return false;
        }
    }
}

