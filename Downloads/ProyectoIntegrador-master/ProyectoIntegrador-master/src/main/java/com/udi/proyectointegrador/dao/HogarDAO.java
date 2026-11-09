package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Hogar;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class HogarDAO {

    public boolean registrarHogar(Hogar hogar) {
        return guardarHogar(hogar);
    }

    public boolean guardarHogar(Hogar hogar) {
        String sql = "{call PR_INSERTAR_HOGAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, hogar.getNombreFamilia());
            cs.setString(2, hogar.getDireccion());
            cs.setString(3, hogar.getCiudad());
            cs.setString(4, hogar.getBarrio());
            cs.setString(5, hogar.getZona());
            cs.setString(6, hogar.getJefeHogar());
            cs.setInt(7, hogar.getNumeroIntegrantes());
            cs.setInt(8, hogar.getNumeroTrabajadores());
            cs.setString(9, hogar.getResponsableCompras());
            cs.setDouble(10, hogar.getIngresePromMon());
            cs.setDouble(11, hogar.getCostoNeceBasicas());

            cs.registerOutParameter(12, Types.INTEGER);
            cs.registerOutParameter(13, Types.VARCHAR);

            cs.execute();

            String resultado = cs.getString(13);

            if ("OK".equals(resultado)) {
                hogar.setIdHogar(cs.getInt(12));
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar hogar via PL/SQL: " + e.getMessage());
        }
        return false;
    }

    public List<Hogar> listarHogares() {
        List<Hogar> lista = new ArrayList<>();
        String sql = "{call PR_LISTAR_HOGARES(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    Hogar h = new Hogar(
                        rs.getInt("ID_HOGAR"),
                        rs.getString("NOMBRE_FAMILIA"),
                        rs.getString("DIRECCION"),
                        rs.getString("CIUDAD"),
                        rs.getString("BARRIO"),
                        rs.getString("ZONA"),
                        rs.getInt("NUMERO_INTEGRANTES"),
                        rs.getString("JEFE_HOGAR"),
                        rs.getInt("NUMERO_TRABAJADORES"),
                        rs.getString("RESPONSABLE_COMPRAS"),
                        rs.getDouble("INGRESE_PROM_MON"),
                        rs.getDouble("COSTO_NECE_BASICAS")
                    );
                    lista.add(h);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar hogares via PL/SQL: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarHogar(Hogar hogar) {
        String sql = "{call PR_ACTUALIZAR_HOGAR(?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, hogar.getIdHogar());
            cs.setString(2, hogar.getNombreFamilia());
            cs.setString(3, hogar.getCiudad());
            cs.setString(4, hogar.getJefeHogar());
            cs.registerOutParameter(5, Types.VARCHAR);

            cs.execute();

            String resultado = cs.getString(5);
            return "OK".equals(resultado);

        } catch (SQLException e) {
            System.out.println("Error al actualizar hogar via PL/SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarHogar(int idHogar) {
        String sql = "{call PR_ELIMINAR_HOGAR(?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idHogar);
            cs.registerOutParameter(2, Types.VARCHAR);

            cs.execute();

            String resultado = cs.getString(2);
            return "OK".equals(resultado);

        } catch (SQLException e) {
            System.out.println("Error al eliminar hogar via PL/SQL: " + e.getMessage());
            return false;
        }
    }
}