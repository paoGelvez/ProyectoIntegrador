package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Hogar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HogarDAO {

    // 1. CREAR (INSERT)
    public boolean registrarHogar(Hogar hogar) {
       String sql = "INSERT INTO PROYECTOINTEGRADOR1097780309.HOGAR (id_hogar, nombre_familia, direccion, ciudad, barrio, zona, jefe_hogar, numero_integrantes, numero_trabajadores, responsable_compras) "
           + "VALUES (PROYECTOINTEGRADOR1097780309.SEC_HOGAR.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hogar.getNombreFamilia());
            ps.setString(2, hogar.getDireccion());
            ps.setString(3, hogar.getCiudad());
            ps.setString(4, hogar.getBarrio());
            ps.setString(5, hogar.getZona());
            ps.setString(6, hogar.getJefeHogar());
            ps.setInt(7, hogar.getNumeroIntegrantes());
            ps.setInt(8, hogar.getNumeroTrabajadores());
            ps.setString(9, hogar.getResponsableCompras());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar hogar: " + e.getMessage());
            return false;
        }
    }

    // 2. LEER (SELECT)
    public List<Hogar> listarHogares() {
        List<Hogar> lista = new ArrayList<>();
        String sql = "SELECT * FROM PROYECTOINTEGRADOR1097780309.HOGAR ORDER BY id_hogar DESC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Hogar h = new Hogar();
                h.setIdHogar(rs.getInt("id_hogar"));
                h.setNombreFamilia(rs.getString("nombre_familia"));
                h.setCiudad(rs.getString("ciudad"));
                h.setJefeHogar(rs.getString("jefe_hogar"));
                h.setNumeroIntegrantes(rs.getInt("numero_integrantes"));
                h.setResponsableCompras(rs.getString("responsable_compras"));
                lista.add(h);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar hogares: " + e.getMessage());
        }

        return lista;
    }

    // 3. ACTUALIZAR (UPDATE)
    public boolean actualizarHogar(Hogar hogar) {
        String sql = "UPDATE PROYECTOINTEGRADOR1097780309.HOGAR "
                   + "SET nombre_familia = ?, ciudad = ?, jefe_hogar = ?, numero_integrantes = ?, responsable_compras = ? "
                   + "WHERE id_hogar = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hogar.getNombreFamilia());
            ps.setString(2, hogar.getCiudad());
            ps.setString(3, hogar.getJefeHogar());
            ps.setInt(4, hogar.getNumeroIntegrantes());
            ps.setString(5, hogar.getResponsableCompras());
            ps.setInt(6, hogar.getIdHogar());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar hogar: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR (DELETE)
    public boolean eliminarHogar(int idHogar) {
        String sql = "DELETE FROM PROYECTOINTEGRADOR1097780309.HOGAR WHERE id_hogar = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHogar);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar hogar: " + e.getMessage());
            return false;
        }
    }
}