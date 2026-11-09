package com.udi.proyectointegrador.vista;

import com.udi.proyectointegrador.dao.HogarDAO;
import com.udi.proyectointegrador.modelo.Hogar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class FrmRevisionDatos extends JFrame {

    private JTable tblHogares;
    private DefaultTableModel modeloTabla;
    private HogarDAO hogarDAO;
    private JButton btnActualizar;
    private JButton btnEliminar;

    public FrmRevisionDatos() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.hogarDAO = new HogarDAO();
        cargarDatosOracle();
    }

    private void initComponents() {
        setTitle("DANE - Consulta de Datos del Hogar (CU02)");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Encabezado
        JLabel lblTitulo = new JLabel("HOGARES REGISTRADOS EN LA ENPH (CU02)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(220, 220, 220));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla de datos
        String[] columnas = {"ID", "Familia", "Ciudad", "Jefe de Hogar", "Integrantes", "Responsable Compras"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace la tabla no editable directamente
            }
        };
        tblHogares = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tblHogares);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de Botones para CRUD (Actualizar / Eliminar)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnActualizar = new JButton("ACTUALIZAR");
        btnEliminar = new JButton("ELIMINAR");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // Evento para el botón ACTUALIZAR
        btnActualizar.addActionListener(e -> accionActualizar());

        // Evento para el botón ELIMINAR
        btnEliminar.addActionListener(e -> accionEliminar());
    }

    private void cargarDatosOracle() {
        modeloTabla.setRowCount(0);
        List<Hogar> lista = hogarDAO.listarHogares();

        for (Hogar h : lista) {
            Object[] fila = {
                h.getIdHogar(),
                h.getNombreFamilia(),
                h.getCiudad(),
                h.getJefeHogar(),
                h.getNumeroIntegrantes(),
                h.getResponsableCompras()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void accionActualizar() {
        int filaSeleccionada = tblHogares.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int idHogar = Integer.parseInt(tblHogares.getValueAt(filaSeleccionada, 0).toString());

            String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese nuevo nombre de familia:",
                    tblHogares.getValueAt(filaSeleccionada, 1).toString());
            if (nuevoNombre == null) return;

            String nuevaCiudad = JOptionPane.showInputDialog(this, "Ingrese nueva ciudad:",
                    tblHogares.getValueAt(filaSeleccionada, 2).toString());
            if (nuevaCiudad == null) return;

            String nuevoJefe = JOptionPane.showInputDialog(this, "Ingrese nuevo jefe de hogar:",
                    tblHogares.getValueAt(filaSeleccionada, 3).toString());
            if (nuevoJefe == null) return;

            Hogar h = new Hogar();
            h.setIdHogar(idHogar);
            h.setNombreFamilia(nuevoNombre);
            h.setCiudad(nuevaCiudad);
            h.setJefeHogar(nuevoJefe);
            h.setNumeroIntegrantes(Integer.parseInt(tblHogares.getValueAt(filaSeleccionada, 4).toString()));
            h.setResponsableCompras(tblHogares.getValueAt(filaSeleccionada, 5).toString());

            if (hogarDAO.actualizarHogar(h)) {
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
                cargarDatosOracle();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el registro en la base de datos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila de la tabla para actualizar.");
        }
    }

    private void accionEliminar() {
        int filaSeleccionada = tblHogares.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int idHogar = Integer.parseInt(tblHogares.getValueAt(filaSeleccionada, 0).toString());

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el hogar con ID " + idHogar + "?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (hogarDAO.eliminarHogar(idHogar)) {
                    JOptionPane.showMessageDialog(this, "Hogar eliminado correctamente.");
                    cargarDatosOracle();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el hogar en la base de datos.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila de la tabla para eliminar.");
        }
    }
}