package com.udi.proyectointegrador.dao;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.modelo.Producto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ProductoDAO {

    public boolean insertar(Producto p) {
        String sql = "{call SP_INSERTAR_PRODUCTO(?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, p.getCodigoProducto());
            cs.setString(2, p.getNombre());
            cs.setInt(3, p.isEsNecesBasica() ? 1 : 0);
            cs.setInt(4, p.getIdCategoria());
            cs.setInt(5, p.getIdUnidadMedida());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "{call SP_LISTAR_PRODUCTOS(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("codigo_producto"),
                        rs.getString("nombre"),
                        rs.getBoolean("es_neces_basica"),
                        rs.getInt("id_categoria"),
                        rs.getInt("id_unidad_medida")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Producto p) {
        String sql = "{call SP_ACTUALIZAR_PRODUCTO(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, p.getIdProducto());
            cs.setString(2, p.getCodigoProducto());
            cs.setString(3, p.getNombre());
            cs.setInt(4, p.isEsNecesBasica() ? 1 : 0);
            cs.setInt(5, p.getIdCategoria());
            cs.setInt(6, p.getIdUnidadMedida());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idProducto) {
        String sql = "{call SP_ELIMINAR_PRODUCTO(?)}";

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idProducto);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}