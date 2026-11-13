package com.udi.proyectointegrador.vista;

import com.udi.proyectointegrador.ConexionBD;
import com.udi.proyectointegrador.dao.CategoriaDAO;
import com.udi.proyectointegrador.dao.FormaAdquisicionDAO;
import com.udi.proyectointegrador.dao.GastoDAO;
import com.udi.proyectointegrador.dao.LugarCompraDAO;
import com.udi.proyectointegrador.dao.ProductoDAO;
import com.udi.proyectointegrador.dao.VisitaDAO;
import com.udi.proyectointegrador.modelo.Categoria;
import com.udi.proyectointegrador.modelo.FormaAdquisicion;
import com.udi.proyectointegrador.modelo.Gasto;
import com.udi.proyectointegrador.modelo.LugarCompra;
import com.udi.proyectointegrador.modelo.Producto;
import com.udi.proyectointegrador.modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

public class FrmPrincipal extends JFrame {

    private Usuario usuarioActual;
    private JPanel pnlLateral;
    private JPanel pnlContenedorDerecho;
    private CardLayout cardLayout;

    private JButton btnPaginaPrincipal;
    private JButton btnRegistroDatos;
    private JButton btnRevisionDatos;
    private JButton btnCuadernilloGastos;
    private JButton btnAnalisisDatos;
    private JButton btnGestionUsuarios;
    private JButton btnAyuda;
    private JButton btnCerrarSesion;
    private JLabel lblSesionActiva;

    private GastoDAO gastoDAO;
    private VisitaDAO visitaDAO;
    private ProductoDAO productoDAO;
    private LugarCompraDAO lugarCompraDAO;
    private FormaAdquisicionDAO formaAdquisicionDAO;
    private CategoriaDAO categoriaDAO;

    // Componentes CU01 (Visitas 1-14 y Gastos)
    private JComboBox<String> cmbVisita;
    private JComboBox<String> cmbProducto;
    private JComboBox<String> cmbLugar;
    private JComboBox<String> cmbForma;
    private JTextField txtCantidad;
    private JTextField txtValorUnitario;
    private JTable tblGastos;
    private DefaultTableModel modeloTablaGastos;
    private JLabel lblTotalVisita;

    // Componentes CU03 - CU06 (Análisis Estadístico)
    private DefaultTableModel modCu03Moda;
    private DefaultTableModel modCu04Capacidad;
    private DefaultTableModel modCu05Deficit;
    private DefaultTableModel modCu06Extremos;

    public FrmPrincipal() {
        this.gastoDAO = new GastoDAO();
        this.visitaDAO = new VisitaDAO();
        this.productoDAO = new ProductoDAO();
        this.lugarCompraDAO = new LugarCompraDAO();
        this.formaAdquisicionDAO = new FormaAdquisicionDAO();
        this.categoriaDAO = new CategoriaDAO();

        initComponents();
        this.setLocationRelativeTo(null);
    }

    public FrmPrincipal(Usuario usuario) {
        this();
        this.usuarioActual = usuario;
        aplicarControlDeRoles();
    }

    private String obtenerRol() {
        if (usuarioActual == null || usuarioActual.getRol() == null) {
            return "Administrador";
        }
        return usuarioActual.getRol().trim();
    }

    private void aplicarControlDeRoles() {
        String rol = obtenerRol();
        if (usuarioActual != null) {
            String nombreCompleto = usuarioActual.getNombre()
                    + (usuarioActual.getApellido() != null ? " " + usuarioActual.getApellido() : "");
            lblSesionActiva.setText("<html><b>Usuario:</b> " + nombreCompleto
                    + "<br><b>Rol:</b> " + rol + "</html>");
        }

        boolean esEncuestador = "Encuestador".equalsIgnoreCase(rol);
        boolean esAnalista = "Analista".equalsIgnoreCase(rol);
        boolean esAdmin = "Administrador".equalsIgnoreCase(rol);

        // Reglas RBAC según documento ENPH (Sección 5 y 7.1):
        // - Encuestador: Registro (CU01), Revisión (CU02) y Cuadernillo/Gastos (CU01)
        // - Analista: Revisión (CU02) y Análisis Estadístico (CU03-CU06)
        // - Administrador: Acceso total + Gestión de Usuarios
        btnRegistroDatos.setEnabled(esEncuestador || esAdmin);
        btnCuadernilloGastos.setEnabled(esEncuestador || esAdmin);
        btnAnalisisDatos.setEnabled(esAnalista || esAdmin);
        btnGestionUsuarios.setVisible(esAdmin);

        if (esEncuestador) {
            btnAnalisisDatos.setToolTipText("Módulo exclusivo para Analista o Administrador");
        }
        if (esAnalista) {
            btnRegistroDatos.setToolTipText("Módulo exclusivo para Encuestador o Administrador");
            btnCuadernilloGastos.setToolTipText("Módulo exclusivo para Encuestador o Administrador");
        }
    }

    private void initComponents() {
        setTitle("DANE - Encuesta Nacional de Presupuestos de los Hogares (ENPH)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 580);
        setResizable(false);
        setLayout(null);

        pnlLateral = new JPanel();
        pnlLateral.setBackground(new Color(225, 225, 225));
        pnlLateral.setBounds(0, 0, 235, 580);
        pnlLateral.setLayout(null);

        JLabel lblBarraTitulo = new JLabel("BARRA DE FUNCIONES", SwingConstants.CENTER);
        lblBarraTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBarraTitulo.setBounds(10, 15, 215, 25);
        pnlLateral.add(lblBarraTitulo);

        lblSesionActiva = new JLabel("<html><b>Rol:</b> Administrador</html>", SwingConstants.CENTER);
        lblSesionActiva.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSesionActiva.setForeground(new Color(40, 40, 40));
        lblSesionActiva.setBounds(15, 42, 205, 35);
        pnlLateral.add(lblSesionActiva);

        btnPaginaPrincipal = new JButton("• Pagina Principal");
        btnPaginaPrincipal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnPaginaPrincipal.setBounds(20, 88, 195, 32);
        btnPaginaPrincipal.addActionListener(e -> cardLayout.show(pnlContenedorDerecho, "INICIO"));
        pnlLateral.add(btnPaginaPrincipal);

        btnRegistroDatos = new JButton("• Registro Hogar (CU01)");
        btnRegistroDatos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRegistroDatos.setBounds(20, 130, 195, 32);
        btnRegistroDatos.addActionListener(e -> new FrmRegistroDatos(usuarioActual).setVisible(true));
        pnlLateral.add(btnRegistroDatos);

        btnCuadernilloGastos = new JButton("• Cuadernillo Gastos (CU01)");
        btnCuadernilloGastos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCuadernilloGastos.setBounds(20, 172, 195, 32);
        btnCuadernilloGastos.addActionListener(e -> abrirModuloCuadernillo());
        pnlLateral.add(btnCuadernilloGastos);

        btnRevisionDatos = new JButton("• Revision Datos (CU02)");
        btnRevisionDatos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRevisionDatos.setBounds(20, 214, 195, 32);
        btnRevisionDatos.addActionListener(e -> new FrmRevisionDatos().setVisible(true));
        pnlLateral.add(btnRevisionDatos);

        btnAnalisisDatos = new JButton("• Funciones Análisis (CU03-06)");
        btnAnalisisDatos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAnalisisDatos.setBounds(20, 256, 195, 32);
        btnAnalisisDatos.addActionListener(e -> abrirModuloAnalisis());
        pnlLateral.add(btnAnalisisDatos);

        btnGestionUsuarios = new JButton("• Gestionar Usuarios");
        btnGestionUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGestionUsuarios.setForeground(new Color(0, 70, 140));
        btnGestionUsuarios.setBounds(20, 298, 195, 32);
        btnGestionUsuarios.addActionListener(e -> abrirDialogoNuevoUsuario());
        pnlLateral.add(btnGestionUsuarios);

        btnAyuda = new JButton("• Ayuda");
        btnAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAyuda.setBounds(20, 340, 195, 32);
        btnAyuda.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "SISTEMA ENPH - CONTROL DE ROLES (RBAC):\n"
                + "• Encuestador: Registra hogares (CU01), visitas días 1-14, gastos y consulta registros (CU02).\n"
                + "• Analista: Consulta todos los hogares (CU02) y ejecuta indicadores CU03 a CU06.\n"
                + "• Administrador: Acceso total al sistema y creación de cuentas de usuario.",
                "Ayuda del Sistema ENPH", JOptionPane.INFORMATION_MESSAGE));
        pnlLateral.add(btnAyuda);

        btnCerrarSesion = new JButton("• Cerrar sesion");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCerrarSesion.setBounds(20, 382, 195, 32);
        btnCerrarSesion.addActionListener(e -> {
            new FrmLogin().setVisible(true);
            this.dispose();
        });
        pnlLateral.add(btnCerrarSesion);

        add(pnlLateral);

        cardLayout = new CardLayout();
        pnlContenedorDerecho = new JPanel(cardLayout);
        pnlContenedorDerecho.setBounds(235, 0, 735, 545);

        pnlContenedorDerecho.add(crearPanelInicio(), "INICIO");
        pnlContenedorDerecho.add(crearPanelCuadernillo(), "CUADERNILLO");
        pnlContenedorDerecho.add(crearPanelAnalisis(), "ANALISIS");

        add(pnlContenedorDerecho);
        cardLayout.show(pnlContenedorDerecho, "INICIO");
    }

    private JPanel crearPanelInicio() {
        JPanel pnlInicio = new JPanel();
        pnlInicio.setBackground(new Color(245, 245, 245));
        pnlInicio.setLayout(null);

        JLabel lblBienvenida = new JLabel("DANE - Encuesta Nacional de Presupuestos de los Hogares (ENPH)");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblBienvenida.setBounds(30, 25, 660, 30);
        pnlInicio.add(lblBienvenida);

        String textoInfo = "<html><body>"
                + "Bienvenido al sistema de recolección y análisis del Cuadernillo 2 de la ENPH (DANE).<br><br>"
                + "• <b>Registro Hogar (CU01):</b> Captura variables demográficas y socioeconómicas del hogar e inicia la encuesta.<br>"
                + "• <b>Cuadernillo Gastos (CU01):</b> Gestiona los días de seguimiento (1 a 14), gastos diarios, correcciones, inactivaciones y cierre de encuesta.<br>"
                + "• <b>Revisión Datos (CU02):</b> Consulta y edición operativa de hogares registrados.<br>"
                + "• <b>Funciones Análisis (CU03 a CU06):</b> Calcula Moda de consumo, Capacidad adquisitiva, Hogares en déficit y Valores extremos (mín/máx)."
                + "</body></html>";

        JLabel lblDescripcion = new JLabel(textoInfo);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescripcion.setBounds(30, 70, 660, 230);
        pnlInicio.add(lblDescripcion);

        return pnlInicio;
    }

    private JPanel crearPanelCuadernillo() {
        JPanel pnl = new JPanel(new BorderLayout(8, 8));
        pnl.setBackground(new Color(245, 245, 245));
        pnl.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        JLabel lblTitulo = new JLabel("CU01: CUADERNILLO DE SEGUIMIENTO (DÍAS 1-14) Y GASTOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlTop.add(lblTitulo, BorderLayout.WEST);

        JPanel pnlBtnsEncuesta = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlBtnsEncuesta.setOpaque(false);
        JButton btnAbrirDia = new JButton("+ Abrir Día (1-14)");
        JButton btnCerrarEncuesta = new JButton("Cerrar Encuesta");
        pnlBtnsEncuesta.add(btnAbrirDia);
        pnlBtnsEncuesta.add(btnCerrarEncuesta);
        pnlTop.add(pnlBtnsEncuesta, BorderLayout.EAST);
        pnl.add(pnlTop, BorderLayout.NORTH);

        JPanel pnlCentro = new JPanel(new BorderLayout(6, 6));
        pnlCentro.setOpaque(false);

        JPanel pnlForm = new JPanel(new GridLayout(4, 4, 6, 6));
        pnlForm.setBackground(new Color(245, 245, 245));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Registrar Artículo en la Visita"));

        cmbVisita = new JComboBox<>();
        cmbProducto = new JComboBox<>();
        cmbLugar = new JComboBox<>();
        cmbForma = new JComboBox<>();
        txtCantidad = new JTextField("1");
        txtValorUnitario = new JTextField();

        JButton btnNuevoProducto = new JButton("+ Nuevo Prod.");
        JButton btnGuardarGasto = new JButton("Guardar Gasto");
        btnGuardarGasto.setFont(new Font("Segoe UI", Font.BOLD, 11));

        pnlForm.add(new JLabel("Encuesta / Día:"));
        pnlForm.add(cmbVisita);
        pnlForm.add(new JLabel("Producto:"));
        pnlForm.add(cmbProducto);

        pnlForm.add(new JLabel("Lugar Compra:"));
        pnlForm.add(cmbLugar);
        pnlForm.add(new JLabel("Forma Adq.:"));
        pnlForm.add(cmbForma);

        pnlForm.add(new JLabel("Cantidad:"));
        pnlForm.add(txtCantidad);
        pnlForm.add(new JLabel("Valor Unit. ($):"));
        pnlForm.add(txtValorUnitario);

        pnlForm.add(new JLabel("Catálogo:"));
        pnlForm.add(btnNuevoProducto);
        pnlForm.add(new JLabel(""));
        pnlForm.add(btnGuardarGasto);

        pnlCentro.add(pnlForm, BorderLayout.NORTH);

        String[] columnas = {"ID", "Fecha", "ID Prod.", "Cant.", "V. Unitario", "V. Total", "Estado"};
        modeloTablaGastos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblGastos = new JTable(modeloTablaGastos);
        pnlCentro.add(new JScrollPane(tblGastos), BorderLayout.CENTER);
        pnl.add(pnlCentro, BorderLayout.CENTER);

        JPanel pnlPie = new JPanel(new BorderLayout());
        pnlPie.setOpaque(false);

        lblTotalVisita = new JLabel("Total Visita Activa (PL/SQL): $ 0.00");
        lblTotalVisita.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotalVisita.setForeground(new Color(0, 102, 51));
        pnlPie.add(lblTotalVisita, BorderLayout.WEST);

        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlAcciones.setOpaque(false);
        JButton btnCorregir = new JButton("Corregir");
        JButton btnInactivar = new JButton("Inactivar");
        pnlAcciones.add(btnCorregir);
        pnlAcciones.add(btnInactivar);
        pnlPie.add(pnlAcciones, BorderLayout.EAST);

        pnl.add(pnlPie, BorderLayout.SOUTH);

        cmbVisita.addActionListener(e -> actualizarTablaGastos());
        btnAbrirDia.addActionListener(e -> abrirNuevoDiaVisita());
        btnCerrarEncuesta.addActionListener(e -> cerrarEncuestaActual());
        btnNuevoProducto.addActionListener(e -> crearProductoRapido());
        btnGuardarGasto.addActionListener(e -> registrarGasto());
        btnCorregir.addActionListener(e -> corregirGastoSeleccionado());
        btnInactivar.addActionListener(e -> inactivarGastoSeleccionado());

        return pnl;
    }

    private JPanel crearPanelAnalisis() {
        JPanel pnl = new JPanel(new BorderLayout(8, 8));
        pnl.setBackground(new Color(245, 245, 245));
        pnl.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        JLabel lblTitulo = new JLabel("FUNCIONES DE ANÁLISIS ESTADÍSTICO ENPH (CU03 - CU06)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlTop.add(lblTitulo, BorderLayout.WEST);

        JButton btnRefrescar = new JButton("Actualizar Indicadores PL/SQL");
        btnRefrescar.addActionListener(e -> cargarReportesAnaliticos());
        pnlTop.add(btnRefrescar, BorderLayout.EAST);
        pnl.add(pnlTop, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // CU03: Moda de Consumo
        modCu03Moda = new DefaultTableModel(new String[]{"Categoría", "Producto Más Frecuente (Moda)", "Frecuencia Compras"}, 0);
        tabs.addTab("CU03: Moda Consumo", new JScrollPane(new JTable(modCu03Moda)));

        // CU04: Capacidad Adquisitiva
        modCu04Capacidad = new DefaultTableModel(new String[]{"Familia", "Ingreso ($)", "Costo Nec. Básicas ($)", "Promedio Gasto ($)", "Índice Cobertura (%)"}, 0);
        tabs.addTab("CU04: Capacidad Adquisitiva", new JScrollPane(new JTable(modCu04Capacidad)));

        // CU05: Hogares en Déficit
        modCu05Deficit = new DefaultTableModel(new String[]{"Familia en Déficit", "Ingreso ($)", "Gasto Consolidado ($)", "Costo Nec. Básicas ($)", "Diferencia ($)"}, 0);
        tabs.addTab("CU05: Hogares en Déficit", new JScrollPane(new JTable(modCu05Deficit)));

        // CU06: Valores Mínimos y Máximos
        modCu06Extremos = new DefaultTableModel(new String[]{"Producto", "Unidad", "Valor Mín ($)", "Valor Máx ($)", "Lugar Mínimo", "Lugar Máximo", "Forma Adq."}, 0);
        tabs.addTab("CU06: Valores Mín/Máx", new JScrollPane(new JTable(modCu06Extremos)));

        pnl.add(tabs, BorderLayout.CENTER);
        return pnl;
    }

    private void abrirModuloCuadernillo() {
        cardLayout.show(pnlContenedorDerecho, "CUADERNILLO");
        recargarCombosCuadernillo();
    }

    private void recargarCombosCuadernillo() {
        try {
            cmbVisita.removeAllItems();
            for (String v : visitaDAO.listarVisitasDescriptivas()) {
                cmbVisita.addItem(v);
            }
            cargarComboProductos();
            cmbLugar.removeAllItems();
            for (LugarCompra lc : lugarCompraDAO.listarTodos()) {
                cmbLugar.addItem(lc.getIdLugarCompra() + " - " + lc.getNombre());
            }
            cmbForma.removeAllItems();
            for (FormaAdquisicion fa : formaAdquisicionDAO.listarTodas()) {
                cmbForma.addItem(fa.getIdFormaAdquisicion() + " - " + fa.getNombre());
            }
            actualizarTablaGastos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando datos: " + ex.getMessage());
        }
    }

    private void abrirModuloAnalisis() {
        cardLayout.show(pnlContenedorDerecho, "ANALISIS");
        cargarReportesAnaliticos();
    }

    private void cargarReportesAnaliticos() {
        try (Connection con = ConexionBD.obtenerConexion()) {
            // CU03
            modCu03Moda.setRowCount(0);
            try (CallableStatement cs = con.prepareCall("{call SP_CU03_MODA_CONSUMO(?)}")) {
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.execute();
                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    while (rs.next()) {
                        modCu03Moda.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getInt(3)});
                    }
                }
            }

            // CU04
            modCu04Capacidad.setRowCount(0);
            try (CallableStatement cs = con.prepareCall("{call SP_CU04_CAPACIDAD_ADQUISITIVA(?)}")) {
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.execute();
                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    while (rs.next()) {
                        modCu04Capacidad.addRow(new Object[]{
                            rs.getString(1),
                            String.format("$ %,.0f", rs.getDouble(2)),
                            String.format("$ %,.0f", rs.getDouble(3)),
                            String.format("$ %,.2f", rs.getDouble(4)),
                            rs.getDouble(5) + " %"
                        });
                    }
                }
            }

            // CU05
            modCu05Deficit.setRowCount(0);
            try (CallableStatement cs = con.prepareCall("{call SP_CU05_HOGARES_DEFICIT(?)}")) {
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.execute();
                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    while (rs.next()) {
                        modCu05Deficit.addRow(new Object[]{
                            rs.getString(1),
                            String.format("$ %,.0f", rs.getDouble(2)),
                            String.format("$ %,.0f", rs.getDouble(3)),
                            String.format("$ %,.0f", rs.getDouble(4)),
                            String.format("$ %,.0f", rs.getDouble(5))
                        });
                    }
                }
            }

            // CU06
            modCu06Extremos.setRowCount(0);
            try (CallableStatement cs = con.prepareCall("{call SP_CU06_VALORES_EXTREMOS(?)}")) {
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.execute();
                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    while (rs.next()) {
                        modCu06Extremos.addRow(new Object[]{
                            rs.getString(1),
                            rs.getString(2),
                            String.format("$ %,.0f", rs.getDouble(3)),
                            String.format("$ %,.0f", rs.getDouble(4)),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7)
                        });
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error ejecutando reportes analíticos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int extraerIdEncuestaActual() {
        if (cmbVisita.getSelectedItem() == null) return -1;
        String texto = cmbVisita.getSelectedItem().toString();
        if (!texto.contains(" - ") || !texto.contains("|")) return -1;
        String parteDespuesGuion = texto.split(" - ", 2)[1];
        return Integer.parseInt(parteDespuesGuion.split("\\|")[0].trim());
    }

    private void abrirNuevoDiaVisita() {
        int idEncuesta = extraerIdEncuestaActual();
        if (idEncuesta <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione primero una encuesta activa en el desplegable.");
            return;
        }
        String diaStr = JOptionPane.showInputDialog(this, "Ingrese el número de día de seguimiento (1 a 14) para la Encuesta #" + idEncuesta + ":");
        if (diaStr == null || diaStr.trim().isEmpty()) return;

        try {
            int numDia = Integer.parseInt(diaStr.trim());
            try (Connection con = ConexionBD.obtenerConexion();
                 CallableStatement cs = con.prepareCall("{call SP_ABRIR_DIA_VISITA(?, ?, ?)}")) {
                cs.setInt(1, idEncuesta);
                cs.setInt(2, numDia);
                cs.setString(3, "Visita digitalizada día " + numDia);
                cs.execute();
                JOptionPane.showMessageDialog(this, "Día " + numDia + " agregado a la Encuesta #" + idEncuesta + ".");
                recargarCombosCuadernillo();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación de Seguimiento", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido entre 1 y 14.");
        }
    }

    private void cerrarEncuestaActual() {
        int idEncuesta = extraerIdEncuestaActual();
        if (idEncuesta <= 0) return;

        try (Connection con = ConexionBD.obtenerConexion();
             CallableStatement cs = con.prepareCall("{call SP_CERRAR_ENCUESTA(?)}")) {
            cs.setInt(1, idEncuesta);
            cs.execute();
            JOptionPane.showMessageDialog(this, "Encuesta #" + idEncuesta + " cerrada correctamente tras completar los 14 días.");
            recargarCombosCuadernillo();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se puede cerrar la encuesta", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void corregirGastoSeleccionado() {
        int fila = tblGastos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un gasto de la tabla para corregir.");
            return;
        }
        int idGasto = Integer.parseInt(tblGastos.getValueAt(fila, 0).toString());
        String cantActual = tblGastos.getValueAt(fila, 3).toString();
        String valActual = tblGastos.getValueAt(fila, 4).toString();

        JTextField txtNuevaCant = new JTextField(cantActual);
        JTextField txtNuevoVal = new JTextField(valActual);
        JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));
        p.add(new JLabel("Nueva Cantidad:"));
        p.add(txtNuevaCant);
        p.add(new JLabel("Nuevo Valor Unitario ($):"));
        p.add(txtNuevoVal);

        if (JOptionPane.showConfirmDialog(this, p, "Corregir Gasto #" + idGasto, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try (Connection con = ConexionBD.obtenerConexion();
                 CallableStatement cs = con.prepareCall("{call SP_CORREGIR_GASTO(?, ?, ?)}")) {
                cs.setInt(1, idGasto);
                cs.setDouble(2, Double.parseDouble(txtNuevaCant.getText().trim()));
                cs.setDouble(3, Double.parseDouble(txtNuevoVal.getText().trim()));
                cs.execute();
                actualizarTablaGastos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al corregir gasto: " + ex.getMessage());
            }
        }
    }

    private void inactivarGastoSeleccionado() {
        int fila = tblGastos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un gasto de la tabla para inactivar.");
            return;
        }
        int idGasto = Integer.parseInt(tblGastos.getValueAt(fila, 0).toString());
        if (JOptionPane.showConfirmDialog(this, "¿Inactivar gasto #" + idGasto + "? (Eliminación lógica según CU01)", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try (Connection con = ConexionBD.obtenerConexion();
                 CallableStatement cs = con.prepareCall("{call SP_INACTIVAR_GASTO(?)}")) {
                cs.setInt(1, idGasto);
                cs.execute();
                actualizarTablaGastos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al inactivar gasto: " + ex.getMessage());
            }
        }
    }

    private void abrirDialogoNuevoUsuario() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtIdentificacion = new JTextField();
        JComboBox<String> cmbRol = new JComboBox<>(new String[]{"Encuestador", "Analista", "Administrador"});
        JPasswordField txtPass = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 6, 6));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Identificación:"));
        panel.add(txtIdentificacion);
        panel.add(new JLabel("Rol:"));
        panel.add(cmbRol);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPass);

        if (JOptionPane.showConfirmDialog(this, panel, "Registrar Nuevo Usuario (Administrador)", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try (Connection con = ConexionBD.obtenerConexion();
                 CallableStatement cs = con.prepareCall("{call SP_INSERTAR_USUARIO(?, ?, ?, ?, ?)}")) {
                cs.setString(1, txtNombre.getText().trim());
                cs.setString(2, txtApellido.getText().trim());
                cs.setString(3, txtIdentificacion.getText().trim());
                cs.setString(4, cmbRol.getSelectedItem().toString());
                cs.setString(5, new String(txtPass.getPassword()));
                cs.execute();
                JOptionPane.showMessageDialog(this, "Usuario creado correctamente con rol " + cmbRol.getSelectedItem());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarComboProductos() {
        cmbProducto.removeAllItems();
        for (Producto p : productoDAO.listarTodos()) {
            cmbProducto.addItem(p.getIdProducto() + " - " + p.getNombre());
        }
    }

    private void crearProductoRapido() {
        String nombreProd = JOptionPane.showInputDialog(this, "Nombre del nuevo producto:");
        if (nombreProd == null || nombreProd.trim().isEmpty()) return;

        List<Categoria> cats = categoriaDAO.listarTodas();
        if (cats.isEmpty()) return;

        Producto p = new Producto();
        p.setCodigoProducto("ENPH-" + (System.currentTimeMillis() % 10000));
        p.setNombre(nombreProd.trim());
        p.setIdCategoria(cats.get(0).getIdCategoria());
        p.setIdUnidadMedida(1);

        if (productoDAO.insertar(p)) {
            cargarComboProductos();
            cmbProducto.setSelectedIndex(cmbProducto.getItemCount() - 1);
            JOptionPane.showMessageDialog(this, "Producto '" + nombreProd + "' agregado al catálogo.");
        }
    }

    private int extraerId(String item) {
        if (item == null || !item.contains(" - ")) return -1;
        return Integer.parseInt(item.split(" - ")[0].trim());
    }

    private void actualizarTablaGastos() {
        if (cmbVisita.getSelectedItem() == null) return;
        int idVisita = extraerId(cmbVisita.getSelectedItem().toString());
        if (idVisita <= 0) return;

        modeloTablaGastos.setRowCount(0);
        List<Gasto> gastos = gastoDAO.listarPorVisita(idVisita);
        for (Gasto g : gastos) {
            modeloTablaGastos.addRow(new Object[]{
                g.getIdGasto(),
                g.getFechaGasto(),
                g.getIdProducto(),
                g.getCantidad(),
                g.getValorUnitario(),
                g.getValorTotal(),
                g.getEstadoGasto()
            });
        }
        double total = gastoDAO.obtenerTotalPorVisita(idVisita);
        lblTotalVisita.setText(String.format("Total Visita Activa (PL/SQL): $ %,.2f", total));
    }

    private void registrarGasto() {
        try {
            if (cmbVisita.getSelectedItem() == null || cmbProducto.getSelectedItem() == null || cmbForma.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione Encuesta/Día, Producto y Forma de Adquisición.");
                return;
            }

            int idVisita = extraerId(cmbVisita.getSelectedItem().toString());
            int idProducto = extraerId(cmbProducto.getSelectedItem().toString());
            Integer idLugar = cmbLugar.getSelectedItem() != null ? extraerId(cmbLugar.getSelectedItem().toString()) : null;
            int idForma = extraerId(cmbForma.getSelectedItem().toString());

            double cantidad = Double.parseDouble(txtCantidad.getText().trim());
            double valorUnit = Double.parseDouble(txtValorUnitario.getText().trim());

            if (cantidad <= 0 || valorUnit < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero y el valor unitario no puede ser negativo.");
                return;
            }

            Gasto g = new Gasto();
            g.setFechaGasto(Date.valueOf(LocalDate.now()));
            g.setCantidad(cantidad);
            g.setValorUnitario(valorUnit);
            g.setValorTotal(cantidad * valorUnit);
            g.setEstadoGasto("Activo");
            g.setIdVisita(idVisita);
            g.setIdProducto(idProducto);
            g.setIdLugarCompra(idLugar);
            g.setIdFormaAdquisicion(idForma);

            if (gastoDAO.insertar(g)) {
                txtCantidad.setText("1");
                txtValorUnitario.setText("");
                txtValorUnitario.requestFocus();
                actualizarTablaGastos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el gasto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Formato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FrmPrincipal().setVisible(true));
    }
}
