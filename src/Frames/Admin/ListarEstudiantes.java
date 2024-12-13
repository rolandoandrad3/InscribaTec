/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames.Admin;

import Clases.Conectar;
import Frames.Admin.Principal;
import Frames.RegistrarCalificacion;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rolan
 */
public class ListarEstudiantes extends javax.swing.JFrame {

    /**
     * Creates new form ListarEstudiantes
     */
    DefaultTableModel modelo= new DefaultTableModel();
    public static int idCalificacion=0;
    
    public String carnet="";
    
    public static int idAlumno = 0;
    
    
    public ListarEstudiantes() {
        initComponents();
        setSize(1000, 600);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        // Evitar que la ventana sea redimensionable
        setResizable(false);
        // Opcional: Establecer un título para la ventana
        String busqueda = txtBuscar.getText();
        buscarPorCarnetONombre(busqueda);
        limpiarCampos();
        llenarTablaCalificaciones(txtCarnet.getText());

        cerrar();
        //Al ser listado, no deben poder modificarse
        txtNombres.setEnabled(false);
        txtApellidos.setEnabled(false);
        txtFechaNac.setEnabled(false);
        txtCarnet.setEnabled(false);
        txtEmail.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtEstado.setEnabled(false);
        txtCurso.setEnabled(false);
        txtCUM.setEnabled(false);

        // Evento que detecta la tecla al presionar en txtBuscar (por ejemplo, al presionar Enter o tecla modificada)
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String busqueda = txtBuscar.getText();
                if (!busqueda.isEmpty()) {
                    buscarPorCarnetONombre(busqueda); // Ejecutar búsqueda por Carnet o Nombre
                }
            }
        });
    }
    
    public void llenarTablaCalificaciones(String carnet) {
    try {
        // Consulta para obtener datos de las calificaciones
        String sql = "SELECT ID_Nota, Nombre_Materia, Actividad, Calificacion FROM Notas WHERE Carnet = ?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setString(1, carnet.trim()); // Usar el carnet recibido como parámetro
        ResultSet rs = pst.executeQuery();

        // Crear modelo de tabla con columnas definidas
        String[] columnas = {"ID Nota", "Materia", "Actividad", "Calificación"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0); // Modelo vacío

        // Llenar el modelo con los datos obtenidos de la consulta
        while (rs.next()) {
            Object[] fila = {
                rs.getInt("ID_Nota"),           // ID de la Nota
                rs.getString("Nombre_Materia"), // Nombre de la materia
                rs.getString("Actividad"),      // Actividad
                rs.getDouble("Calificacion")    // Calificación
            };
            modelo.addRow(fila);
        }

        // Asignar el modelo a la tabla existente
        tblCalificaciones.setModel(modelo);
        tblCalificaciones.setBounds(10, 10, 600, 400); // Ajustar posición y tamaño si es necesario
        tblCalificaciones.repaint(); // Refrescar la tabla visualmente

    } catch (SQLException e) {
        System.err.println("Error al obtener calificaciones: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al cargar las calificaciones. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private boolean verificarCarnetEnDB(String carnet) {
   boolean existe = false;

    // Agregar mensaje para verificar el valor recibido
    System.out.println("Carnet recibido para verificación: " + carnet);

    String query = "SELECT COUNT(*) AS Total FROM Estudiantes WHERE Carnet = ?";
    try (PreparedStatement ps = cn.prepareStatement(query)) { // Cerrar automáticamente el PreparedStatement
        ps.setString(1, carnet.trim()); // Eliminar espacios innecesarios del Carnet
        try (ResultSet rs = ps.executeQuery()) { // Cerrar automáticamente el ResultSet
            if (rs.next()) {
                int total = rs.getInt("Total");
                System.out.println("Resultados encontrados: " + total);
                existe = total > 0; // Verificar si hay al menos un registro
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al consultar la base de datos: " + ex.getMessage());
    }

    return existe;
}

    public void cerrar() {
        try {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    confirmarsalida();
                }
            });
        } catch (Exception e) {
        }
    }
    

    public void confirmarsalida() {
        int valor = JOptionPane.showConfirmDialog(this, "Desea cerrar la aplicacion?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (valor == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Cerrando Aplicacion", "", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }// Método para buscar estudiantes por Carnet
    private void buscarPorCarnetONombre(String busqueda) {
        try {
            // Consulta SQL para buscar por Carnet o Nombre
            String sql = "SELECT e.Nombre, e.Apellido, e.Carnet, e.Fecha_Nacimiento, "
                    + "e.Correo, e.Telefono, e.Estado, cur.Semestre "
                    + "FROM Estudiantes e "
                    + "INNER JOIN Inscripciones i ON e.ID_Estudiante = i.ID_Estudiante "
                    + "INNER JOIN Cursos cur ON i.ID_Curso = cur.ID_Curso "
                    + "WHERE e.Carnet LIKE ? OR e.Nombre LIKE ?";

            // Preparar la consulta
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + busqueda + "%"); // Coincidencia parcial por Carnet
            ps.setString(2, "%" + busqueda + "%"); // Coincidencia parcial por Nombre
            ResultSet rs = ps.executeQuery();

            // Si se encuentra un resultado, llenar los campos
            if (rs.next()) {
                txtNombres.setText(rs.getString("Nombre"));
                txtApellidos.setText(rs.getString("Apellido"));
                txtCarnet.setText(rs.getString("Carnet"));
                txtFechaNac.setText(rs.getDate("Fecha_Nacimiento").toString()); // Formato yyyy-MM-dd
                txtEmail.setText(rs.getString("Correo"));
                txtTelefono.setText(rs.getString("Telefono"));
                txtEstado.setText(rs.getString("Estado"));
                txtCurso.setText(rs.getString("Semestre"));
                // Llenar tabla con el nuevo carnet encontrado
                llenarTablaCalificaciones(rs.getString("Carnet"));
            } else {
                // Limpiar los campos si no hay coincidencias
                limpiarCampos();
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar por Carnet o Nombre: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al buscar el estudiante. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
    txtNombres.setText("");
    txtApellidos.setText("");
    txtCarnet.setText("");
    txtFechaNac.setText("");
    txtEmail.setText("");
    txtTelefono.setText("");
    txtEstado.setText("");
    txtCurso.setText("");
    txtCUM.setText("");
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        BackAndFooter5 = new javax.swing.JPanel();
        btnBack5 = new javax.swing.JButton();
        lblfooter3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtFechaNac = new javax.swing.JTextField();
        txtCarnet = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        txtCurso = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCalificaciones = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnRegCalificacion = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        txtCUM = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/buscar.png"))); // NOI18N

        btnBack5.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        btnBack5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back.png"))); // NOI18N
        btnBack5.setText("Vover");
        btnBack5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack5ActionPerformed(evt);
            }
        });

        lblfooter3.setText("InscribaTec @ Universiad Tecnologica");

        javax.swing.GroupLayout BackAndFooter5Layout = new javax.swing.GroupLayout(BackAndFooter5);
        BackAndFooter5.setLayout(BackAndFooter5Layout);
        BackAndFooter5Layout.setHorizontalGroup(
            BackAndFooter5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackAndFooter5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnBack5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblfooter3)
                .addContainerGap())
        );
        BackAndFooter5Layout.setVerticalGroup(
            BackAndFooter5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackAndFooter5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BackAndFooter5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblfooter3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBack5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setText("Apellidos");

        jLabel2.setText("Nombres");

        jLabel3.setText("Carnet");

        jLabel4.setText("Fecha de Nacimiento");

        jLabel5.setText("Email");

        jLabel6.setText("Telefono");

        jLabel7.setText("Curso");

        jLabel8.setText("Estado");

        jScrollPane1.setViewportView(tblCalificaciones);

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        btnRegCalificacion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRegCalificacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/registro_notas.png"))); // NOI18N
        btnRegCalificacion.setText("Registrar calificaciones");
        btnRegCalificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegCalificacionActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jLabel10.setText("CUM");

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Edit.png"))); // NOI18N
        jButton1.setText("Corregir Notas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCarnet, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 82, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCUM, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(btnRegCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnImprimir))
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addComponent(BackAndFooter5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCarnet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnImprimir)
                            .addComponent(btnRegCalificacion)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCUM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addComponent(BackAndFooter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBack5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack5ActionPerformed
        // TODO add your handling code here:
        Principal principal = new Principal();
        principal.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBack5ActionPerformed

    private void btnRegCalificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegCalificacionActionPerformed
        // TODO add your handling code here:
        carnet = txtCarnet.getText(); // Captura el valor del campo txtCarnet
        if (verificarCarnetEnDB(carnet)) {
            RegistrarCalificacion registrarCalificacion = new RegistrarCalificacion();
            registrarCalificacion.cargarDatosCarnet(carnet); // Pasa el carnet al nuevo frame
            registrarCalificacion.setVisible(true);
            this.dispose(); // Opcional: Cierra el frame actual
        } else {
            JOptionPane.showMessageDialog(null, "El carnet ingresado no existe en la base de datos.");
        }
    }//GEN-LAST:event_btnRegCalificacionActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        // TODO add your handling code here:
        // Obtener el texto que el usuario está escribiendo
        String valorBusqueda = txtBuscar.getText().trim() + evt.getKeyChar();

    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        Document documento = new Document();
        Calendar calendario = Calendar.getInstance();
        Date fecha = new Date(calendario.getTimeInMillis());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String verfecha = formato.format(fecha);

        try {
            // Ruta donde se generará el archivo
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/"
                    + txtNombres.getText() + "_" + txtApellidos.getText() + "_" + txtCarnet.getText() + ".pdf"));

            // Crear encabezado y fecha
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.setFont(FontFactory.getFont("Arial", 20, BaseColor.BLACK));
            parrafo.add("Información del Estudiante\n\n");

            Paragraph poner_fecha = new Paragraph();
            poner_fecha.setAlignment(Paragraph.ALIGN_RIGHT);
            poner_fecha.add("Fecha: " + verfecha + "\n\n");

            // Abrir documento y añadir contenido
            documento.open();
            documento.add(parrafo);
            documento.add(poner_fecha);

            // Crear tabla para mostrar la información del estudiante
            PdfPTable tablaAlumno = new PdfPTable(7); // 7 columnas
            tablaAlumno.addCell("Nombres");
            tablaAlumno.addCell("Apellidos");
            tablaAlumno.addCell("Carnet");
            tablaAlumno.addCell("Fecha de Nacimiento");
            tablaAlumno.addCell("Email");
            tablaAlumno.addCell("Teléfono");
            tablaAlumno.addCell("Semestre");

            try {
                // Consulta para obtener la información del estudiante
                String sql = "SELECT e.Nombre, e.Apellido, e.Carnet, e.Fecha_Nacimiento, "
                        + "e.Correo, e.Telefono, cur.Semestre "
                        + "FROM Estudiantes e "
                        + "INNER JOIN Inscripciones i ON e.ID_Estudiante = i.ID_Estudiante "
                        + "INNER JOIN Cursos cur ON i.ID_Curso = cur.ID_Curso "
                        + "WHERE e.Carnet = ?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, txtCarnet.getText());
                ResultSet rs = ps.executeQuery();

                // Llenar la tabla con los resultados
                if (rs.next()) {
                    tablaAlumno.addCell(rs.getString("Nombre"));
                    tablaAlumno.addCell(rs.getString("Apellido"));
                    tablaAlumno.addCell(rs.getString("Carnet"));
                    tablaAlumno.addCell(rs.getDate("Fecha_Nacimiento").toString());
                    tablaAlumno.addCell(rs.getString("Correo"));
                    tablaAlumno.addCell(rs.getString("Telefono"));
                    tablaAlumno.addCell(rs.getString("Semestre"));
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró información para el estudiante con el carnet proporcionado.");
                    return;
                }

                documento.add(tablaAlumno); // Añadir tabla al documento

                // Sección de notas
                Paragraph parrafo2 = new Paragraph();
                parrafo2.setAlignment(Paragraph.ALIGN_CENTER);
                parrafo2.setFont(FontFactory.getFont("Arial", 20, BaseColor.BLACK));
                parrafo2.add("\n\nNotas Registradas\n\n");
                documento.add(parrafo2);

                PdfPTable tablaNotas = new PdfPTable(3); // Tres columnas: Materia, Nota, Anotaciones
                tablaNotas.addCell("Materia");
                tablaNotas.addCell("Calificación");
                tablaNotas.addCell("Anotaciones");

                try {
                    // Consulta para obtener las notas
                    String sqlNotas = "SELECT m.Nombre_Materia, n.Calificacion, n.Anotaciones "
                            + "FROM Notas n "
                            + "INNER JOIN Materias m ON n.ID_Materia = m.ID_Materia "
                            + "WHERE n.ID_Estudiante = ?";
                    PreparedStatement psNotas = cn.prepareStatement(sqlNotas);
                    psNotas.setString(1, txtCarnet.getText());
                    ResultSet rsNotas = psNotas.executeQuery();

                    // Llenar la tabla con los resultados
                    while (rsNotas.next()) {
                        tablaNotas.addCell(rsNotas.getString("Nombre_Materia"));
                        tablaNotas.addCell(rsNotas.getString("Calificacion"));
                        tablaNotas.addCell(rsNotas.getString("Anotaciones"));
                    }

                    documento.add(tablaNotas); // Añadir tabla de notas al documento

                } catch (SQLException e) {
                    System.err.println("Error al obtener las notas del estudiante: " + e.getMessage());
                    JOptionPane.showMessageDialog(this, "Error al obtener las notas. Contacte al administrador.");
                }

            } catch (SQLException e) {
                System.err.println("Error al obtener información del estudiante: " + e.getMessage());
                JOptionPane.showMessageDialog(this, "Error al obtener información del estudiante. Contacte al administrador.");
            }

            documento.close();
            JOptionPane.showMessageDialog(this, "Documento generado con éxito!");

        } catch (Exception e) {
            System.err.println("Error al generar el documento PDF: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al generar el documento. Contacte al administrador.");
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListarEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarEstudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackAndFooter5;
    private javax.swing.JButton btnBack5;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnRegCalificacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblfooter3;
    private javax.swing.JTable tblCalificaciones;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCUM;
    private javax.swing.JTextField txtCarnet;
    private javax.swing.JTextField txtCurso;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFechaNac;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
    Conectar conectado = new Conectar();
    Connection cn = conectado.conexion();
}
