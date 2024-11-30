/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames.Admin;

import Frames.GestionProfesores;
import Frames.GestionAlumnos;
import Frames.Reportes;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;


/**
 *
 * @author Rolan
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        cerrar();
        //size 800*520
        //center
        //resizable false
        // Establecer tamaño de la ventana (800 x 520)
        setSize(800, 520);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        // Evitar que la ventana sea redimensionable
        setResizable(false);
        // Opcional: Establecer un título para la ventana
        setTitle("Menu Principal - Admin");    
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGestionCarreras = new javax.swing.JToggleButton();
        btnNotas = new javax.swing.JToggleButton();
        btnReportes = new javax.swing.JToggleButton();
        btnUsers = new javax.swing.JToggleButton();
        btnEstudiantes = new javax.swing.JToggleButton();
        btnProfesores = new javax.swing.JToggleButton();
        btnGestionMaterias = new javax.swing.JToggleButton();
        btnGestionCursos = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnGestionCarreras.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnGestionCarreras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/carreras.png"))); // NOI18N
        btnGestionCarreras.setText("Gestion de Carreras");
        btnGestionCarreras.setToolTipText("Gestion de Carreras");
        btnGestionCarreras.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGestionCarreras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionCarreras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionCarrerasActionPerformed(evt);
            }
        });

        btnNotas.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnNotas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/registro_notas.png"))); // NOI18N
        btnNotas.setText("Registro de Notas");
        btnNotas.setToolTipText("Registro de notas por Estudiante");
        btnNotas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNotas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotasActionPerformed(evt);
            }
        });

        btnReportes.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/reports.png"))); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setToolTipText("Generacion de reportes por estudiante");
        btnReportes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnUsers.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/registro_estudiantes.png"))); // NOI18N
        btnUsers.setText("Gestion de Usuarios");
        btnUsers.setToolTipText("Registro e Inscripcion de Estudiantes");
        btnUsers.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsersActionPerformed(evt);
            }
        });

        btnEstudiantes.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnEstudiantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/student.png"))); // NOI18N
        btnEstudiantes.setText("Estudiantes");
        btnEstudiantes.setToolTipText("Portal de Alumnos");
        btnEstudiantes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEstudiantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstudiantesActionPerformed(evt);
            }
        });

        btnProfesores.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnProfesores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/proffesor.png"))); // NOI18N
        btnProfesores.setText("Profesores");
        btnProfesores.setToolTipText("Gestion de profesores");
        btnProfesores.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnProfesores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfesores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfesoresActionPerformed(evt);
            }
        });

        btnGestionMaterias.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnGestionMaterias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/materias.png"))); // NOI18N
        btnGestionMaterias.setText("Registro de Materias");
        btnGestionMaterias.setToolTipText("Gestion de profesores");
        btnGestionMaterias.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGestionMaterias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionMaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionMateriasActionPerformed(evt);
            }
        });

        btnGestionCursos.setFont(new java.awt.Font("Myanmar Text", 0, 14)); // NOI18N
        btnGestionCursos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/semester.png"))); // NOI18N
        btnGestionCursos.setText("Gestion de Ciclos");
        btnGestionCursos.setToolTipText("Gestion de profesores");
        btnGestionCursos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGestionCursos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionCursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionCursosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionCarreras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProfesores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionMaterias, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionCursos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGestionCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProfesores, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstudiantesActionPerformed
        // TODO add your handling code here:
        RegistrarEstudiante gestionAlumnos = new RegistrarEstudiante();
        gestionAlumnos.setVisible(true);
        dispose();       
    }//GEN-LAST:event_btnEstudiantesActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
        Reportes reportes = new Reportes();
        reportes.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnGestionCarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionCarrerasActionPerformed
        // TODO add your handling code here:
        RegistroCarrera gestionCarreras = new RegistroCarrera();
        gestionCarreras.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnGestionCarrerasActionPerformed

    private void btnProfesoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfesoresActionPerformed
        // TODO add your handling code here:
        GestionProfesores gestionProfesores = new GestionProfesores();
        gestionProfesores.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnProfesoresActionPerformed

    private void btnNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotasActionPerformed
        // TODO add your handling code here:
        GestionAlumnos rc = new GestionAlumnos();
        rc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNotasActionPerformed

    private void btnUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsersActionPerformed
        // TODO add your handling code here:
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        gestionUsuarios.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnUsersActionPerformed

    private void btnGestionMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionMateriasActionPerformed
        // TODO add your handling code here:
        GestionProfesores gestionProfesores = new GestionProfesores();
        gestionProfesores.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnGestionMateriasActionPerformed

    private void btnGestionCursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionCursosActionPerformed
        // TODO add your handling code here:
        GestionCursos gc = new GestionCursos();
        gc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnGestionCursosActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnEstudiantes;
    private javax.swing.JToggleButton btnGestionCarreras;
    private javax.swing.JToggleButton btnGestionCursos;
    private javax.swing.JToggleButton btnGestionMaterias;
    private javax.swing.JToggleButton btnNotas;
    private javax.swing.JToggleButton btnProfesores;
    private javax.swing.JToggleButton btnReportes;
    private javax.swing.JToggleButton btnUsers;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
