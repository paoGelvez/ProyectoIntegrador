package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Hogar;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class HogarDAO {

    public boolean registrarHogar(Hogar hogar) {
        return registrarHogar(hogar, 1);
    }

    public boolean registrarHogar(Hogar hogar, int idUsuario) {
        String sql = "{call SP_REGISTRAR_HOGAR_COMPLETO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, hogar.getNombreFamilia());
            cs.setString(2, hogar.getDireccion());
            cs.setString(3, hogar.getCiudad());
            cs.setString(4, hogar.getBarrio());
            cs.setString(5, hogar.getZona());
            cs.setString(6, hogar.getJefeHogar());
            cs.setInt(7, hogar.getNumeroIntegrantes());
            cs.setInt(8, hogar.getNumeroTrabajadores());
            cs.setString(9, hogar.getResponsableCompras());
            cs.setInt(10, idUsuario > 0 ? idUsuario : 1);

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar hogar completo: " + e.getMessage());
            return false;
        }
    }

    public List<Hogar> listarHogares() {
        List<Hogar> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_HOGARES(?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    Hogar h = new Hogar();
                    h.setIdHogar(rs.getInt("id_hogar"));
                    h.setNombreFamilia(rs.getString("nombre_familia"));
                    h.setDireccion(rs.getString("direccion"));
                    h.setCiudad(rs.getString("ciudad"));
                    h.setBarrio(rs.getString("barrio"));
                    h.setZona(rs.getString("zona"));
                    h.setJefeHogar(rs.getString("jefe_hogar"));
                    h.setNumeroIntegrantes(rs.getInt("numero_integrantes"));
                    h.setNumeroTrabajadores(rs.getInt("numero_trabajadores"));
                    h.setResponsableCompras(rs.getString("responsable_compras"));
                    lista.add(h);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar hogares: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarHogar(Hogar hogar) {
        String sql = "{call SP_ACTUALIZAR_HOGAR(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, hogar.getIdHogar());
            cs.setString(2, hogar.getNombreFamilia());
            cs.setString(3, hogar.getCiudad());
            cs.setString(4, hogar.getJefeHogar());
            cs.setInt(5, hogar.getNumeroIntegrantes());
            cs.setString(6, hogar.getResponsableCompras());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar hogar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarHogar(int idHogar) {
        String sql = "{call SP_ELIMINAR_HOGAR(?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idHogar);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar hogar: " + e.getMessage());
            return false;
        }
    }
}
