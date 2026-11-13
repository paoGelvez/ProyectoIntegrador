package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Encuesta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class EncuestaDAO {

    public boolean insertar(Encuesta enc) {
        String sql = "{call SP_INSERTAR_ENCUESTA(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, enc.getCodigoEncuesta());
            cs.setDate(2, enc.getFechaInicio());
            cs.setDate(3, enc.getFechaFin());
            cs.setString(4, enc.getEstado());
            cs.setInt(5, enc.getIdHogar());
            cs.setInt(6, enc.getIdUsuario());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar encuesta: " + e.getMessage());
            return false;
        }
    }

    public List<Encuesta> listarTodas() {
        List<Encuesta> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_ENCUESTAS(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Encuesta(
                        rs.getInt("id_encuesta"),
                        rs.getString("codigo_encuesta"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getString("estado"),
                        rs.getInt("id_hogar"),
                        rs.getInt("id_usuario")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar encuestas: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Encuesta enc) {
        String sql = "{call SP_ACTUALIZAR_ENCUESTA(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, enc.getIdEncuesta());
            cs.setString(2, enc.getCodigoEncuesta());
            cs.setDate(3, enc.getFechaInicio());
            cs.setDate(4, enc.getFechaFin());
            cs.setString(5, enc.getEstado());
            cs.setInt(6, enc.getIdHogar());
            cs.setInt(7, enc.getIdUsuario());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar encuesta: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idEncuesta) {
        String sql = "{call SP_ELIMINAR_ENCUESTA(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idEncuesta);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar encuesta: " + e.getMessage());
            return false;
        }
    }
}