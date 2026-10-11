package com.udi.proyectointegrador.vista;

import com.udi.proyectointegrador.dao.HogarDAO;
import com.udi.proyectointegrador.modelo.Hogar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class FrmRevisionDatos extends JFrame {

    private JTable tblHogares;
    private DefaultTableModel modeloTabla;
    private HogarDAO hogarDAO;

    public FrmRevisionDatos() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.hogarDAO = new HogarDAO();
        cargarDatosOracle();
    }

    private void initComponents() {
        setTitle("DANE - Consulta de Datos del Hogar (CU02)");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("HOGARES REGISTRADOS EN LA ENPH (CU02)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(220, 220, 220));
        add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"ID", "Familia", "Ciudad", "Jefe de Hogar", "Integrantes", "Responsable Compras"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblHogares = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tblHogares);
        add(scrollPane, BorderLayout.CENTER);
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
}