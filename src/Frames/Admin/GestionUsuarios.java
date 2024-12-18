/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames.Admin;

import Clases.Conectar;
import Frames.Admin.Principal;
import Frames.TextPrompt;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JComboBox;

/**
 *
 * @author Rolan
 */
public class GestionUsuarios extends javax.swing.JFrame {

    /**
     * Creates new form RegistroUsuarios
     */
    public GestionUsuarios() {
        initComponents();
        setSize(800, 520);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        // Evitar que la ventana sea redimensionable
        setResizable(false);
        // Opcional: Establecer un título para la ventana
        setTitle("Gestion de Usuarios - Admin");  
        cerrar();
        mostrartabla("");
        cargarCBRol(cbRol);
        txtIdUsuario.setEnabled(false);
        
        TextPrompt idUsuario = new TextPrompt("ID Usuario",txtIdUsuario);
        TextPrompt Username = new TextPrompt("Nombre del Usuario",txtNombreUsuario);
        TextPrompt contrasena = new TextPrompt("Contraseña",txtPassword);
        
        limpiar();
        txtIdUsuario.setEnabled(false);
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
     void limpiar(){
        txtIdUsuario.setText("");
        txtNombreUsuario.setText("");
        txtPassword.setText("");
        cbRol.setSelectedIndex(0);
        cbEstado.setSelectedItem(0);
    }
    void mostrartabla(String valor) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre de Usuario");
        modelo.addColumn("Contraseña");
        modelo.addColumn("Rol");
        modelo.addColumn("Estado");

        tblUsuarios.setModel(modelo);

        String sql = "SELECT * FROM Usuarios";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("ID_Usuario"),
                    rs.getString("Nombre_Usuario"),
                    rs.getString("Contraseña"),
                    rs.getString("Rol"),
                    rs.getString("Estado")
                });
            }

            tblUsuarios.setModel(modelo);

        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error al cargar los datos. Contacte al administrador.");
        }
    }
    public void cargarCBRol(JComboBox Rol){
        try {
            String sql = "SELECT Rol FROM Usuarios";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        cbRol.addItem(rs.getString("Rol"));
                    }
                    
        } catch (SQLException e) {
            System.err.println(e);
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

        popBorrar = new javax.swing.JPopupMenu();
        popEliminar = new javax.swing.JMenuItem();
        BackAndFooter5 = new javax.swing.JPanel();
        btnBack5 = new javax.swing.JButton();
        lblfooter3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnGuardarUsuario = new javax.swing.JToggleButton();
        btnActualizarEstudiante = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbRol = new javax.swing.JComboBox<>();
        txtNombreUsuario = new javax.swing.JTextField();
        txtIdUsuario = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();

        popEliminar.setText("Eliminar");
        popEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popEliminarActionPerformed(evt);
            }
        });
        popBorrar.add(popEliminar);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 457, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Usuario", "Nombre Usuario", "Contraseña", "Rol", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuarios.setColumnSelectionAllowed(true);
        tblUsuarios.setComponentPopupMenu(popBorrar);
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(0).setResizable(false);
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(12);
        }

        btnGuardarUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGuardarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        btnGuardarUsuario.setText("Guardar");
        btnGuardarUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuarioActionPerformed(evt);
            }
        });

        btnActualizarEstudiante.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnActualizarEstudiante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnActualizarEstudiante.setText("Actualizar");
        btnActualizarEstudiante.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarEstudianteActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("ID Usuario");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Nombre de Usuario");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Contraseña");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Rol");

        cbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccion de Rol" }));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/buscar.png"))); // NOI18N
        jLabel5.setText("Buscar:");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Estado");

        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbRol, 0, 185, Short.MAX_VALUE)
                                    .addComponent(txtNombreUsuario)
                                    .addComponent(txtIdUsuario)
                                    .addComponent(txtPassword)
                                    .addComponent(cbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGuardarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizarEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(85, 85, 85)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addComponent(BackAndFooter5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(btnGuardarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(BackAndFooter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBack5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack5ActionPerformed
        // TODO add your handling code here:
        Principal principal = new Principal();
        principal.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBack5ActionPerformed

    private void btnGuardarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuarioActionPerformed
        // TODO add your handling code here:
        try {
            // Capturar los valores de los campos
            String idUsuario = txtIdUsuario.getText(); // En caso de ser editable
            String nombreUsuario = txtNombreUsuario.getText();
            String password = String.valueOf(txtPassword.getText());
            String rol = cbRol.getSelectedItem().toString();
            String estado = cbEstado.getSelectedItem().toString();

            // Validar que los campos no estén vacíos
            if (nombreUsuario.isEmpty() || password.isEmpty() || rol.isEmpty() || estado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos antes de guardar.");
                return;
            }

            // Consulta SQL para insertar un nuevo usuario
            String sql = "INSERT INTO Usuarios (Nombre_Usuario, Contraseña, Rol, Estado) VALUES (?, ?, ?, ?)";

            // Preparar la consulta
            PreparedStatement ps = cn.prepareStatement(sql);

            // Asignar valores a los parámetros
            ps.setString(1, nombreUsuario);
            ps.setString(2, password);
            ps.setString(3, rol);
            ps.setString(4, estado);

            // Ejecutar la consulta
            int respuesta = ps.executeUpdate();

            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Usuario guardado con éxito.");
                limpiar(); // Limpiar los campos después de guardar
                mostrartabla("");  // Refrescar la tabla para mostrar el nuevo registro
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el usuario. Inténtalo nuevamente.");
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e);
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario. Contacte al administrador.");
        }

    }//GEN-LAST:event_btnGuardarUsuarioActionPerformed

    private void btnActualizarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarEstudianteActionPerformed
        // TODO add your handling code here:
    String rol = cbRol.getSelectedItem().toString();
    String estado = cbEstado.getSelectedItem().toString();

    try {
        // Capturar los valores de los campos
        String idUsuario = txtIdUsuario.getText();
        String nombreUsuario = txtNombreUsuario.getText();
        String password = String.valueOf(txtPassword.getText());

        // Validar que los campos no estén vacíos
        if (idUsuario.isEmpty() || nombreUsuario.isEmpty() || password.isEmpty() || rol.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos antes de actualizar.");
            return;
        }

        // Consulta SQL para actualizar los datos del usuario
        String sql = "UPDATE Usuarios SET Nombre_Usuario = ?, Contraseña = ?, Rol = ?, Estado = ? WHERE ID_Usuario = ?";

        // Preparar la consulta
        PreparedStatement ps = cn.prepareStatement(sql);

        // Asignar valores a los parámetros
        ps.setString(1, nombreUsuario); // Nombre de usuario
        ps.setString(2, password);      // Contraseña
        ps.setString(3, rol);           // Rol
        ps.setString(4, estado);        // Estado
        ps.setInt(5, Integer.parseInt(idUsuario)); // ID del usuario

        // Ejecutar la consulta
        int respuesta = ps.executeUpdate();

        // Verificar si se realizó la actualización
        if (respuesta > 0) {
            JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito.");
            limpiar(); // Limpiar los campos después de actualizar
            mostrartabla("");  // Refrescar la tabla para mostrar los datos actualizados
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con el ID proporcionado.");
        }

    } catch (SQLException e) {
        System.err.println("Error al actualizar usuario: " + e);
        JOptionPane.showMessageDialog(null, "Error al actualizar usuario. Contacte al administrador.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "El ID del usuario debe ser un número válido.");
    }
    }//GEN-LAST:event_btnActualizarEstudianteActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        // TODO add your handling code here:
        int fila = this.tblUsuarios.getSelectedRow();
        this.txtIdUsuario.setText(this.tblUsuarios.getValueAt(fila, 0).toString());
        this.txtNombreUsuario.setText(this.tblUsuarios.getValueAt(fila, 1).toString());
        this.txtPassword.setText(this.tblUsuarios.getValueAt(fila, 2).toString());
        this.cbRol.setSelectedItem(this.tblUsuarios.getValueAt(fila, 3));
        this.cbEstado.setSelectedItem(this.tblUsuarios.getValueAt(fila, 4));
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void popEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popEliminarActionPerformed
    // Validar si hay una fila seleccionada en la tabla
    int filaSeleccionada = tblUsuarios.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Obtener el ID del usuario de la fila seleccionada
    int idUsuario = Integer.parseInt(tblUsuarios.getValueAt(filaSeleccionada, 0).toString());

    // Confirmación antes de eliminar
    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el usuario con ID " + idUsuario + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirmacion != JOptionPane.YES_OPTION) {
        return; // Cancelar la operación si el usuario selecciona "No"
    }

    // Realizar la eliminación en la base de datos
    try {
        String sql = "DELETE FROM Usuarios WHERE ID_Usuario = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, idUsuario);

        int resultado = ps.executeUpdate();
        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrartabla(""); // Método para actualizar la tabla después de eliminar
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        System.err.println("Error al eliminar el usuario: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al eliminar el usuario. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
    }    }//GEN-LAST:event_popEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackAndFooter5;
    private javax.swing.JToggleButton btnActualizarEstudiante;
    private javax.swing.JButton btnBack5;
    private javax.swing.JToggleButton btnGuardarUsuario;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblfooter3;
    private javax.swing.JPopupMenu popBorrar;
    private javax.swing.JMenuItem popEliminar;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
    Conectar conectado = new Conectar();
    Connection cn = conectado.conexion();

}
