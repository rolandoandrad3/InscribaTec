/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import Frames.Admin.ListarEstudiantes;
import Clases.Conectar;
import Frames.Admin.Principal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rolan
 */
public class GestionAlumnos extends javax.swing.JFrame {
    public static int idAlumno = 0;
    DefaultTableModel model = new DefaultTableModel();
    
    /**
     * Creates new form RegistrarCalificaciones
     */
    public GestionAlumnos() {
        initComponents();
        setSize(1000, 600);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null); 
        // Opcional: Establecer un título para la ventana
        setTitle("Registro de Calificaciones - Admin");
        mostrarTabla("");
        cerrar();
        System.out.println("antes del click ");
       
        ///------------------------------------------------------------------
        System.out.println("dar click");
        tblGestionAlumnos.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // Obtener la fila seleccionada
            int fila_point = tblGestionAlumnos.rowAtPoint(e.getPoint());
            
            // Verificar que se haya seleccionado una fila válida
            if (fila_point > -1) {
                try {
                    // Obtener el ID_Estudiante de la primera columna de la fila seleccionada
                    Object value = model.getValueAt(fila_point, 0); // Columna 0 corresponde a ID_Estudiante
                    if (value != null && value instanceof Integer) {
                        idAlumno = (int) value; // Asignamos el ID_Estudiante

                        // Mostrar información y cambiar a la siguiente ventana
                        System.out.println("ID del Alumno seleccionado: " + idAlumno);
                        ListarEstudiantes infEstudiante = new ListarEstudiantes();
                        infEstudiante.setVisible(true);
                        dispose(); // Cerrar la ventana actual si es necesario
                    } else {
                        JOptionPane.showMessageDialog(null, "El ID del estudiante no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al seleccionar el ID del alumno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se seleccionó ninguna fila.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    }
    
        public void cerrar(){
        try {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e){
                 confirmarsalida();
             }
            });
        } catch (Exception e) {
        }
     }
        
     public void confirmarsalida(){
         int valor = JOptionPane.showConfirmDialog(this,"Desea cerrar la aplicacion?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
         if(valor==JOptionPane.YES_OPTION){
             JOptionPane.showMessageDialog(null, "Cerrando Aplicacion","",JOptionPane.INFORMATION_MESSAGE );
             System.exit(0);
         } 
     }
     public void mostrarTabla(String valor) {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellido");
    modelo.addColumn("Carnet");
    modelo.addColumn("Fecha de Nacimiento");
    modelo.addColumn("Correo");
    modelo.addColumn("Telefono");
    modelo.addColumn("Estado");

    tblGestionAlumnos.setModel(modelo);

    // Consulta SQL para filtrar por nombre, apellido o carnet
    String sql = "SELECT ID_Estudiante, Nombre, Apellido, Carnet, Fecha_Nacimiento, Correo, Telefono, Estado " +
                 "FROM Estudiantes " +
                 "WHERE CONCAT(Nombre, ' ', Apellido, ' ', Carnet) LIKE ?";

    try {
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, "%" + valor + "%"); // Agregar el valor de búsqueda entre % para usar LIKE

        ResultSet rs = ps.executeQuery();

        // Llenar el modelo de la tabla con los datos obtenidos
        while (rs.next()) {
            Object[] fila = new Object[8];
            fila[0] = rs.getInt("ID_Estudiante");
            fila[1] = rs.getString("Nombre");
            fila[2] = rs.getString("Apellido");
            fila[3] = rs.getString("Carnet");
            fila[4] = rs.getString("Fecha_Nacimiento");
            fila[5] = rs.getString("Correo");
            fila[6] = rs.getString("Telefono");
            fila[7] = rs.getString("Estado");
            modelo.addRow(fila);
        }

        tblGestionAlumnos.setModel(modelo); // Asignar el modelo a la tabla
    } catch (SQLException e) {
        System.err.println("Error al cargar los datos: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al cargar los datos. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackAndFooter5 = new javax.swing.JPanel();
        btnBack5 = new javax.swing.JButton();
        lblfooter3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGestionAlumnos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addComponent(btnBack5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 416, Short.MAX_VALUE)
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

        tblGestionAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblGestionAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGestionAlumnosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGestionAlumnos);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/buscar.png"))); // NOI18N

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackAndFooter5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(235, 235, 235))
            .addGroup(layout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addGap(9, 9, 9)
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

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // Obtener el texto ingresado en el campo de búsqueda
        String valorBusqueda = jTextField1.getText();

    // Llamar al método que carga los datos en la tabla filtrados por el valor de búsqueda
        mostrarTabla(valorBusqueda);
    }//GEN-LAST:event_jTextField1KeyTyped

    private void tblGestionAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGestionAlumnosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGestionAlumnosMouseClicked

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
            java.util.logging.Logger.getLogger(GestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionAlumnos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackAndFooter5;
    private javax.swing.JButton btnBack5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblfooter3;
    private javax.swing.JTable tblGestionAlumnos;
    // End of variables declaration//GEN-END:variables
    Conectar conectado = new Conectar();
    Connection cn = conectado.conexion();
}
