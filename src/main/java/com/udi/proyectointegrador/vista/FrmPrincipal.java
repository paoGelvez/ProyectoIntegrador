package com.udi.proyectointegrador.vista;

import com.udi.proyectointegrador.modelo.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FrmPrincipal extends JFrame {

    private Usuario usuarioActual;

    private JPanel pnlLateral;
    private JPanel pnlContenido;
    private JLabel lblBarraTitulo;
    private JButton btnPaginaPrincipal;
    private JButton btnRegistroDatos;
    private JButton btnRevisionDatos;
    private JButton btnFuncionesDatos;
    private JButton btnAyuda;
    private JButton btnCerrarSesion;
    private JLabel lblBienvenida;
    private JLabel lblDescripcion;

    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public FrmPrincipal(Usuario usuario) {
        this();
        this.usuarioActual = usuario;
    }

    private void initComponents() {
        setTitle("DANE - Encuesta Nacional de Presupuestos de los Hogares (ENPH)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 500);
        setResizable(false);
        setLayout(null);

        // Panel Lateral (Barra de Funciones)
        pnlLateral = new JPanel();
        pnlLateral.setBackground(new Color(225, 225, 225));
        pnlLateral.setBounds(0, 0, 230, 500);
        pnlLateral.setLayout(null);

        lblBarraTitulo = new JLabel("BARRA DE FUNCIONES", SwingConstants.CENTER);
        lblBarraTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBarraTitulo.setBounds(10, 20, 210, 25);
        pnlLateral.add(lblBarraTitulo);

        btnPaginaPrincipal = new JButton("• Pagina Principal");
        btnPaginaPrincipal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnPaginaPrincipal.setBounds(20, 60, 190, 32);
        pnlLateral.add(btnPaginaPrincipal);

        // CU01: Registro de datos
        btnRegistroDatos = new JButton("• Registro de datos");
        btnRegistroDatos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRegistroDatos.setBounds(20, 105, 190, 32);
        btnRegistroDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnRegistroDatosActionPerformed(evt);
            }
        });
        pnlLateral.add(btnRegistroDatos);

        // CU02: Revisión / Consulta de datos
        btnRevisionDatos = new JButton("• Revision de datos");
        btnRevisionDatos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRevisionDatos.setBounds(20, 150, 190, 32);
        btnRevisionDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnRevisionDatosActionPerformed(evt);
            }
        });
        pnlLateral.add(btnRevisionDatos);

        btnFuncionesDatos = new JButton("• Funciones de datos");
        btnFuncionesDatos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnFuncionesDatos.setBounds(20, 195, 190, 32);
        pnlLateral.add(btnFuncionesDatos);

        btnAyuda = new JButton("• Ayuda");
        btnAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAyuda.setBounds(20, 240, 190, 32);
        pnlLateral.add(btnAyuda);

        btnCerrarSesion = new JButton("• Cerrar sesion");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCerrarSesion.setBounds(20, 285, 190, 32);
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        pnlLateral.add(btnCerrarSesion);

        add(pnlLateral);

        // Panel de Contenido Principal
        pnlContenido = new JPanel();
        pnlContenido.setBackground(new Color(245, 245, 245));
        pnlContenido.setBounds(230, 0, 620, 500);
        pnlContenido.setLayout(null);

        lblBienvenida = new JLabel("DANE - Encuesta Nacional de Presupuestos de los Hogares (ENPH)");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblBienvenida.setBounds(30, 25, 560, 30);
        pnlContenido.add(lblBienvenida);

        String textoInfo = "<html><body>"
                + "Bienvenido a la página principal del software de recolección de información sobre la Encuesta Nacional "
                + "de Presupuestos de los Hogares del DANE.<br><br>"
                + "Seleccione alguna de las diferentes funciones del menú en la barra de funciones a la izquierda."
                + "</body></html>";

        lblDescripcion = new JLabel(textoInfo);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescripcion.setBounds(30, 70, 540, 100);
        pnlContenido.add(lblDescripcion);

        add(pnlContenido);
    }

    private void btnRegistroDatosActionPerformed(ActionEvent evt) {
        try {
            FrmRegistroDatos frm = new FrmRegistroDatos(usuarioActual);
            frm.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir la ventana de registro: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRevisionDatosActionPerformed(ActionEvent evt) {
        try {
            FrmRevisionDatos frm = new FrmRevisionDatos();
            frm.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir la ventana de revisión: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnCerrarSesionActionPerformed(ActionEvent evt) {
        FrmLogin login = new FrmLogin();
        login.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }
}