package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.FormaAdquisicion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class FormaAdquisicionDAO {

    public boolean insertar(FormaAdquisicion fa) {
        String sql = "{call SP_INSERTAR_FORMA_ADQUISICION(?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, fa.getNombre());
            cs.setString(2, fa.getDescripcion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar forma de adquisición: " + e.getMessage());
            return false;
        }
    }

    public List<FormaAdquisicion> listarTodas() {
        List<FormaAdquisicion> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_FORMAS_ADQUISICION(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new FormaAdquisicion(
                        rs.getInt("id_forma_adquisicion"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar formas de adquisición: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(FormaAdquisicion fa) {
        String sql = "{call SP_ACTUALIZAR_FORMA_ADQUISICION(?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, fa.getIdFormaAdquisicion());
            cs.setString(2, fa.getNombre());
            cs.setString(3, fa.getDescripcion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar forma de adquisición: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idFormaAdquisicion) {
        String sql = "{call SP_ELIMINAR_FORMA_ADQUISICION(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idFormaAdquisicion);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar forma de adquisición: " + e.getMessage());
            return false;
        }
    }
}