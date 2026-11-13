package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class UsuarioDAO {

    public Usuario autenticar(String identificacion, String contrasena) {
        String sql = "{call SP_AUTENTICAR_USUARIO(?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, identificacion);
            cs.setString(2, contrasena);
            cs.registerOutParameter(3, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(3)) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("identificacion"),
                            rs.getString("rol"),
                            rs.getString("contrasena")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en autenticación: " + e.getMessage());
        }
        return null;
    }

    public boolean insertar(Usuario u) {
        String sql = "{call SP_INSERTAR_USUARIO(?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, u.getNombre());
            cs.setString(2, u.getApellido());
            cs.setString(3, u.getIdentificacion());
            cs.setString(4, u.getRol());
            cs.setString(5, u.getContrasena());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_USUARIOS(?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("identificacion"),
                            rs.getString("rol"),
                            rs.getString("contrasena")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Usuario u) {
        String sql = "{call SP_ACTUALIZAR_USUARIO(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, u.getIdUsuario());
            cs.setString(2, u.getNombre());
            cs.setString(3, u.getApellido());
            cs.setString(4, u.getIdentificacion());
            cs.setString(5, u.getRol());
            cs.setString(6, u.getContrasena());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idUsuario) {
        String sql = "{call SP_ELIMINAR_USUARIO(?)}";

        try (Connection con = ConexionBD.obtenerConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
