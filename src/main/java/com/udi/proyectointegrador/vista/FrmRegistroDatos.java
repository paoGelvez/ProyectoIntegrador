package com.udi.proyectointegrador.vista;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.dao.HogarDAO;
import com.udi.proyectointegrador.modelo.Hogar;
import com.udi.proyectointegrador.modelo.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class FrmRegistroDatos extends javax.swing.JFrame {

    private Usuario usuarioActual;
    private HogarDAO hogarDAO;

    public FrmRegistroDatos() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("DANE - Creación de Registro (CU01)");
        this.hogarDAO = new HogarDAO();
    }

    public FrmRegistroDatos(Usuario usuario) {
        this();
        this.usuarioActual = usuario;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        pnlFondo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblFamilia = new javax.swing.JLabel();
        txtFamilia = new javax.swing.JTextField();
        lblCiudad = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        lblJefe = new javax.swing.JLabel();
        txtJefe = new javax.swing.JTextField();
        lblIntegrantes = new javax.swing.JLabel();
        txtIntegrantes = new javax.swing.JTextField();
        lblIngreso = new javax.swing.JLabel();
        txtIngreso = new javax.swing.JTextField();
        lblCostoBasicas = new javax.swing.JLabel();
        txtCostoBasicas = new javax.swing.JTextField();
        lblResponsable = new javax.swing.JLabel();
        cmbResponsable = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnDeshacer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        pnlFondo.setBackground(new Color(210, 205, 205));

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setText("CREACION DE REGISTRO (CU01)");

        lblFamilia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFamilia.setText("Nombre de la familia:");

        lblCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCiudad.setText("Ciudad de residencia:");

        lblJefe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblJefe.setText("¿Quién es el jefe de hogar?");

        lblIntegrantes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblIntegrantes.setText("Número de residentes:");

        lblIngreso.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblIngreso.setText("Ingreso promedio mensual ($):");

        lblCostoBasicas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCostoBasicas.setText("Costo necesidades básicas ($):");

        lblResponsable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblResponsable.setText("¿Quién es la persona encargada de las compras?");

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Jefe de hogar", "Cónyuge o compañero(a)", "Hijo(a)", "Empleado(a) doméstico(a)", "Otra persona"
        }));

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed(evt));

        btnDeshacer.setText("DESHACER");
        btnDeshacer.addActionListener(evt -> {
            txtFamilia.setText("");
            txtCiudad.setText("");
            txtJefe.setText("");
            txtIntegrantes.setText("");
            txtIngreso.setText("");
            txtCostoBasicas.setText("");
        });

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlFondoLayout.createSequentialGroup()
                            .addComponent(btnDeshacer, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblResponsable)
                            .addComponent(cmbResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblFamilia)
                                    .addComponent(txtFamilia)
                                    .addComponent(lblJefe)
                                    .addComponent(txtJefe, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(lblIngreso)
                                    .addComponent(txtIngreso))
                                .addGap(30, 30, 30)
                                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblCiudad)
                                    .addComponent(txtCiudad)
                                    .addComponent(lblIntegrantes)
                                    .addComponent(txtIntegrantes, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(lblCostoBasicas)
                                    .addComponent(txtCostoBasicas))))))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitulo)
                .addGap(20, 20, 20)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFamilia)
                    .addComponent(lblCiudad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJefe)
                    .addComponent(lblIntegrantes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJefe, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIntegrantes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIngreso)
                    .addComponent(lblCostoBasicas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCostoBasicas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(lblResponsable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeshacer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String familia = txtFamilia.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String jefe = txtJefe.getText().trim();
            String numStr = txtIntegrantes.getText().trim();
            String ingresoStr = txtIngreso.getText().trim();
            String costoStr = txtCostoBasicas.getText().trim();
            String responsable = cmbResponsable.getSelectedItem().toString();

            if (familia.isEmpty() || ciudad.isEmpty() || jefe.isEmpty() || numStr.isEmpty() || ingresoStr.isEmpty() || costoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int numIntegrantes = Integer.parseInt(numStr);
            double ingreso = Double.parseDouble(ingresoStr);
            double costoBasicas = Double.parseDouble(costoStr);

            if (numIntegrantes <= 0 || ingreso < 0 || costoBasicas < 0) {
                JOptionPane.showMessageDialog(this, "Verifique que los residentes sean mayores a 0 y los valores monetarios no sean negativos.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Hogar h = new Hogar();
            h.setNombreFamilia(familia);
            h.setDireccion("Direccion Urbana");
            h.setCiudad(ciudad);
            h.setBarrio("Centro");
            h.setZona("Urbana");
            h.setJefeHogar(jefe);
            h.setNumeroIntegrantes(numIntegrantes);
            h.setNumeroTrabajadores(1);
            h.setResponsableCompras(responsable);

            if (hogarDAO == null) {
                hogarDAO = new HogarDAO();
            }

            int idUsr = (usuarioActual != null) ? usuarioActual.getIdUsuario() : 1;
            boolean exito = hogarDAO.registrarHogar(h, idUsr);

            if (exito) {
                actualizarDatosEconomicosUltimoHogar(familia, ingreso, costoBasicas);
                JOptionPane.showMessageDialog(this, "¡Hogar, Encuesta y Visita #1 creados exitosamente en Oracle!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos en residentes, ingreso y costo de necesidades básicas.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error insospechado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarDatosEconomicosUltimoHogar(String nombreFamilia, double ingreso, double costoBasicas) {
        String sql = "UPDATE HOGAR SET ingreso_prom_mon = ?, costo_nece_basicas = ? "
                   + "WHERE id_hogar = (SELECT MAX(id_hogar) FROM HOGAR WHERE nombre_familia = ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, ingreso);
            ps.setDouble(2, costoBasicas);
            ps.setString(3, nombreFamilia);
            ps.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Error actualizando datos económicos: " + ex.getMessage());
        }
    }

    private javax.swing.JButton btnDeshacer;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblCostoBasicas;
    private javax.swing.JLabel lblFamilia;
    private javax.swing.JLabel lblIngreso;
    private javax.swing.JLabel lblIntegrantes;
    private javax.swing.JLabel lblJefe;
    private javax.swing.JLabel lblResponsable;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtCostoBasicas;
    private javax.swing.JTextField txtFamilia;
    private javax.swing.JTextField txtIngreso;
    private javax.swing.JTextField txtIntegrantes;
    private javax.swing.JTextField txtJefe;
}