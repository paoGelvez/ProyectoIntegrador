package com.udi.proyectointegrador.vista;

import com.udi.proyectointegrador.dao.UsuarioDAO;
import com.udi.proyectointegrador.modelo.Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrmLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRecuperar;

    public FrmLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("DANE - Sistema ENPH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(235, 235, 235));
        panelPrincipal.setLayout(null);

        JPanel pnlModal = new JPanel();
        pnlModal.setBackground(new Color(210, 200, 200));
        pnlModal.setBounds(50, 30, 400, 250);
        pnlModal.setLayout(null);

        JLabel lblTitulo = new JLabel("INICIO DE SESION", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setBounds(100, 15, 200, 25);
        pnlModal.add(lblTitulo);

        JLabel lblUsu = new JLabel("USUARIO:");
        lblUsu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsu.setBounds(30, 60, 90, 25);
        pnlModal.add(lblUsu);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(130, 60, 230, 25);
        pnlModal.add(txtUsuario);

        JLabel lblPass = new JLabel("CONTRASEÑA:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPass.setBounds(30, 105, 90, 25);
        pnlModal.add(lblPass);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(130, 105, 230, 25);
        pnlModal.add(txtContrasena);

        btnRecuperar = new JButton("RECUPERAR CONTRASEÑA");
        btnRecuperar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnRecuperar.setBounds(30, 165, 170, 30);
        btnRecuperar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlModal.add(btnRecuperar);

        btnIniciarSesion = new JButton("INICIAR SESION");
        btnIniciarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnIniciarSesion.setBounds(220, 165, 140, 30);
        btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Evento de clic en el botón Iniciar Sesión
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });
        pnlModal.add(btnIniciarSesion);

        panelPrincipal.add(pnlModal);
        add(panelPrincipal);
    }

    private void btnIniciarSesionActionPerformed(ActionEvent evt) {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese usuario y contraseña.",
                    "Campos Vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usr = dao.autenticar(usuario, contrasena);

        if (usr != null) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido " + usr.getNombre() + "!");
            FrmPrincipal principal = new FrmPrincipal();
            principal.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenciales incorrectas o usuario inactivo.",
                    "Error de Autenticación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }
}