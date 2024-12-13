/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import Clases.Conectar;
import Frames.Admin.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rolan
 */
public class GestionNotas extends javax.swing.JFrame {

    /**
     * Creates new form GestionNotas
     */
    public GestionNotas() {
        initComponents();
        setSize(800, 500);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        // Evitar que la ventana sea redimensionable
        setResizable(false);
        // Opcional: Establecer un título para la ventana
        setTitle("Mantenimiento Notas - Admin");
        cargarcbMaterias();
        cargarNombreCompletoPorCarnet();
        txtCarnet.setEditable(false);
        
        
        
    }
    
    public void cargarcbMaterias() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consulta para obtener los nombres de las materias
            String sql = "SELECT Nombre_Materia FROM Materias";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            // Limpiar el JComboBox antes de llenarlo
            cmbMaterias.removeAllItems();
            cmbMaterias.addItem("Seleccione una materia"); // Opción por defecto

            // Llenar el JComboBox con los nombres de las materias
            while (rs.next()) {
                cmbMaterias.addItem(rs.getString("Nombre_Materia"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar materias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
    public void cargarNombreCompletoPorCarnet() {
    try {
        // Verificar que el campo txtCarnet no esté vacío
        String carnet = txtCarnet.getText().trim();
        if (carnet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un carnet válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Consulta SQL para obtener el nombre completo basado en el carnet
        String sql = "SELECT Nombre, Apellido FROM Estudiantes WHERE Carnet = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, carnet); // Asignar el valor del carnet
        ResultSet rs = ps.executeQuery();

        // Verificar si se encontró un estudiante
        if (rs.next()) {
            // Obtener los datos y asignarlos al campo txtNombre
            String nombreCompleto = rs.getString("Nombre") + " " + rs.getString("Apellido");
            txtNombre.setText(nombreCompleto);
        } else {
            // Limpiar el campo txtNombre si no se encuentra el estudiante
            txtNombre.setText("");
            JOptionPane.showMessageDialog(this, "No se encontró un estudiante con el carnet proporcionado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar el estudiante: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al buscar el estudiante. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    public void cargarDatosCarnet(String carnet) {
        txtCarnet.setText(carnet); // Muestra el carnet recibido en el campo de texto
        txtCarnet.setEditable(false); // Bloquea la edición del campo
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCalificaciones = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCarnet = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCalificacion = new javax.swing.JTextField();
        cmbMaterias = new javax.swing.JComboBox<>();
        cbTareas = new javax.swing.JComboBox<>();
        BackAndFooter6 = new javax.swing.JPanel();
        btnBack6 = new javax.swing.JButton();
        lblfooter3 = new javax.swing.JLabel();
        btnActualizarCurso = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Carnet");

        tblCalificaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblCalificaciones);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Actividad");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Calificacion");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Materia");

        txtCarnet.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtNombre.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtCalificacion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cmbMaterias.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbMaterias.setToolTipText("");
        cmbMaterias.setName(""); // NOI18N

        cbTareas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbTareas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona la actividad", "Tarea 1 - 25%", "Tarea 2 - 25%", "Parcial - 50%" }));

        btnBack6.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        btnBack6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back.png"))); // NOI18N
        btnBack6.setText("Vover");
        btnBack6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack6ActionPerformed(evt);
            }
        });

        lblfooter3.setText("InscribaTec @ Universiad Tecnologica");

        javax.swing.GroupLayout BackAndFooter6Layout = new javax.swing.GroupLayout(BackAndFooter6);
        BackAndFooter6.setLayout(BackAndFooter6Layout);
        BackAndFooter6Layout.setHorizontalGroup(
            BackAndFooter6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackAndFooter6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblfooter3)
                .addContainerGap())
        );
        BackAndFooter6Layout.setVerticalGroup(
            BackAndFooter6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackAndFooter6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BackAndFooter6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblfooter3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBack6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnActualizarCurso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnActualizarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnActualizarCurso.setText("Actualizar");
        btnActualizarCurso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCursoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCarnet, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 25, Short.MAX_VALUE)))
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BackAndFooter6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCarnet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cmbMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbTareas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addComponent(btnActualizarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackAndFooter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBack6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack6ActionPerformed
        // TODO add your handling code here:
        Principal principal = new Principal();
        principal.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBack6ActionPerformed

    private void btnActualizarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCursoActionPerformed
        
    }//GEN-LAST:event_btnActualizarCursoActionPerformed

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
            java.util.logging.Logger.getLogger(GestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionNotas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackAndFooter6;
    private javax.swing.JToggleButton btnActualizarCurso;
    private javax.swing.JButton btnBack6;
    private javax.swing.JComboBox<String> cbTareas;
    private javax.swing.JComboBox<String> cmbMaterias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblfooter3;
    private javax.swing.JTable tblCalificaciones;
    private javax.swing.JTextField txtCalificacion;
    private javax.swing.JTextField txtCarnet;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
    Conectar conectado = new Conectar();
    Connection cn = conectado.conexion();
}
