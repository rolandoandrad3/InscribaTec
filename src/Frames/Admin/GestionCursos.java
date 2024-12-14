/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames.Admin;

import Clases.Conectar;
import Frames.Admin.Principal;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rolan
 */
public class GestionCursos extends javax.swing.JFrame {

    /**
     * Creates new form GestionCursos
     */
    
    
    DefaultTableModel modelo = new DefaultTableModel();
    
    
    public GestionCursos() {
        initComponents();
        cerrar();
        //size 800*520
        //center
        //resizable false
        // Establecer tamaño de la ventana (800 x 520)
        setSize(1000, 600);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        // Evitar que la ventana sea redimensionable
        setResizable(true);
        // Opcional: Establecer un título para la ventana
        setTitle("Gestion de Ciclos - Admin");
        idCurso.setEnabled(false);
        mostrarTabla("");
        
        
        cargarcbMaterias();
        cargarcbProfesores();
        
        
    }
    public void mostrarTabla(String valor){
        try {
        // Consulta SQL con JOIN para obtener los nombres de la materia y el profesor
        String sql = "SELECT c.ID_Curso, m.Nombre_Materia AS Materia, CONCAT(p.Nombre, ' ', p.Apellido) AS Profesor, " +
                     "c.Cupo_Maximo, c.Cupo_Disponible, c.Anio " +
                     "FROM Cursos c " +
                     "JOIN Materias m ON c.ID_Materia = m.ID_Materia " +
                     "JOIN Profesores p ON c.ID_Profesor = p.ID_Profesor";
        
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Crear el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();

        // Agregar las columnas a la tabla
        modelo.addColumn("ID");
        modelo.addColumn("Materia");
        modelo.addColumn("Profesor");
        modelo.addColumn("Cupo Maximo");
        modelo.addColumn("Cupo Disponible");
        modelo.addColumn("Año");

        // Llenar la tabla con los datos obtenidos de la base de datos
        while (rs.next()) {
            Object[] fila = new Object[6];
            fila[0] = rs.getInt("ID_Curso");           // ID del curso
            fila[1] = rs.getString("Materia");         // Nombre de la materia
            fila[2] = rs.getString("Profesor");        // Nombre del profesor
            fila[3] = rs.getInt("Cupo_Maximo");        // Cupo máximo
            fila[4] = rs.getInt("Cupo_Disponible");    // Cupo disponible
            fila[5] = rs.getInt("Anio");               // Año
            modelo.addRow(fila); // Agregar la fila al modelo
        }

        // Asignar el modelo a la JTable
        tblCursos.setModel(modelo);

    } catch (SQLException e) {
        System.err.println("Error en el llenado de tabla cursos: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al cargar los cursos. Contacte al administrador", "Error", JOptionPane.ERROR_MESSAGE);
    }
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
    
    public void limpiar(){
        idCurso.setText("");
        cbMaterias.setSelectedItem(0);
        cbProfesor.setSelectedItem(0);
        txtCupoMax.setText("");
        txtCupoDisp.setText("");
        cbSemestre.setSelectedItem(0);
        cbYear.setSelectedItem(0);         
     }
    
    public void cargarcbProfesores(){
    try {
        // Consulta SQL para obtener el ID del profesor y concatenar Nombre y Apellido
        String sql = "SELECT CONCAT(Nombre, ' ', Apellido) AS NombreCompleto FROM Profesores;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        // Limpia los elementos previos en el combo box
        cbProfesor.removeAllItems();
        cbProfesor.addItem("Seleccione un profesor"); // Opción por defecto
        
        // Recorre el resultado y llena el combo box
        while (rs.next()) {
            String nombreCompleto = rs.getString("NombreCompleto");
            cbProfesor.addItem(nombreCompleto);
        }
    } catch (Exception e) {
        System.err.println(e);
        JOptionPane.showMessageDialog(null, "Error al cargar los profesores: " + e.getMessage());
    }
        
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
            cbMaterias.removeAllItems();
            cbMaterias.addItem("Seleccione una materia"); // Opción por defecto

            // Llenar el JComboBox con los nombres de las materias
            while (rs.next()) {
                cbMaterias.addItem(rs.getString("Nombre_Materia"));
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        idCurso = new javax.swing.JTextField();
        txtCupoDisp = new javax.swing.JTextField();
        txtCupoMax = new javax.swing.JTextField();
        cbSemestre = new javax.swing.JComboBox<>();
        cbProfesor = new javax.swing.JComboBox<>();
        btnGuardarCurso = new javax.swing.JToggleButton();
        btnActualizarCurso = new javax.swing.JToggleButton();
        cbYear = new javax.swing.JComboBox<>();
        cbMaterias = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCursos = new javax.swing.JTable();

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

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Materias del curso");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Profesor Asignado");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Cupo Maximo");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Cupo Disponible");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Semestre");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Año");

        idCurso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtCupoDisp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtCupoMax.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cbSemestre.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbSemestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona Ciclo", "Ciclo 01", "Ciclo 02", "Ciclo 03" }));
        cbSemestre.setToolTipText("");
        cbSemestre.setName(""); // NOI18N

        cbProfesor.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbProfesor.setToolTipText("");
        cbProfesor.setName(""); // NOI18N

        btnGuardarCurso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGuardarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        btnGuardarCurso.setText("Guardar");
        btnGuardarCurso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCursoActionPerformed(evt);
            }
        });

        btnActualizarCurso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnActualizarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnActualizarCurso.setText("Actualizar");
        btnActualizarCurso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCursoActionPerformed(evt);
            }
        });

        cbYear.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona año", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        cbYear.setToolTipText("");
        cbYear.setName(""); // NOI18N

        cbMaterias.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbMaterias.setToolTipText("");
        cbMaterias.setName(""); // NOI18N

        tblCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCursos.setComponentPopupMenu(popBorrar);
        jScrollPane1.setViewportView(tblCursos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(8, 8, 8)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(idCurso)
                                .addComponent(txtCupoDisp)
                                .addComponent(txtCupoMax)
                                .addComponent(cbSemestre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnGuardarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnActualizarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BackAndFooter5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(idCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCupoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCupoDisp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(41, 41, 41)
                        .addComponent(btnGuardarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(BackAndFooter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        cbSemestre.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBack5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack5ActionPerformed
        // TODO add your handling code here:
        Principal principal = new Principal();
        principal.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBack5ActionPerformed

    private void btnGuardarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCursoActionPerformed
        // Validar que todos los campos estén llenos
    if (cbMaterias.getSelectedIndex() == 0 || cbProfesor.getSelectedIndex() == 0
            || txtCupoMax.getText().isEmpty() || txtCupoDisp.getText().isEmpty()
            || cbSemestre.getSelectedIndex() == 0 || cbYear.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Obtener los valores de los campos
    String nombreMateria = cbMaterias.getSelectedItem().toString();
    String nombreProfesor = cbProfesor.getSelectedItem().toString();
    int cupoMaximo = Integer.parseInt(txtCupoMax.getText());
    int cupoDisponible = Integer.parseInt(txtCupoDisp.getText());
    String semestre = cbSemestre.getSelectedItem().toString();
    int anio = Integer.parseInt(cbYear.getSelectedItem().toString());

    try {
        // Obtener el ID_Materia usando el nombre de la materia
        String sqlMateria = "SELECT ID_Materia FROM Materias WHERE Nombre = ?";
        PreparedStatement ps = cn.prepareStatement(sqlMateria);
        ps.setString(1, nombreMateria);  // Establecer el valor para el parámetro 1 (nombre de la materia)
        ResultSet rs = ps.executeQuery();

        int idMateria = 0;
        if (rs.next()) {
            idMateria = rs.getInt("ID_Materia");
        }

        // Obtener el ID_Profesor usando el nombre del profesor
        String sqlProfesor = "SELECT ID_Profesor FROM Profesores WHERE Nombre = ?";
        ps = cn.prepareStatement(sqlProfesor);
        ps.setString(1, nombreProfesor);  // Establecer el valor para el parámetro 1 (nombre del profesor)
        rs = ps.executeQuery();

        int idProfesor = 0;
        if (rs.next()) {
            idProfesor = rs.getInt("ID_Profesor");
        }

        // Verificar que se encontraron los IDs
        if (idMateria == 0 || idProfesor == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron los IDs correspondientes a la materia o profesor.");
            return;
        }

        // Insertar el curso en la base de datos utilizando los IDs de la materia y el profesor
        String sqlInsert = "INSERT INTO Cursos (ID_Materia, ID_Profesor, Cupo_Maximo, Cupo_Disponible, Semestre, Anio) VALUES (?, ?, ?, ?, ?, ?)";
        ps = cn.prepareStatement(sqlInsert);
        ps.setInt(1, idMateria);  // Establecer el ID_Materia
        ps.setInt(2, idProfesor); // Establecer el ID_Profesor
        ps.setInt(3, cupoMaximo); // Establecer el Cupo_Maximo
        ps.setInt(4, cupoDisponible); // Establecer el Cupo_Disponible
        ps.setString(5, semestre); // Establecer el Semestre
        ps.setInt(6, anio); // Establecer el Año

        // Ejecutar la inserción
        int resultado = ps.executeUpdate();
        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Curso guardado exitosamente");
            limpiar(); // Llamar al método para limpiar los campos
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el curso", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnGuardarCursoActionPerformed

    private void btnActualizarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCursoActionPerformed
        // TODO add your handling code here:
        // Validar que todos los campos estén llenos
    if (idCurso.getText().isEmpty() || cbMaterias.getSelectedIndex() == 0 || cbProfesor.getSelectedIndex() == 0 || 
        txtCupoMax.getText().isEmpty() || txtCupoDisp.getText().isEmpty() || 
        cbSemestre.getSelectedIndex() == 0 || cbYear.getSelectedIndex() == 4) {
        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Obtener valores de los campos
    int cursoId = Integer.parseInt(idCurso.getText());
    int idMateria = Integer.parseInt(cbMaterias.getSelectedItem().toString());
    int idProfesor = Integer.parseInt(cbProfesor.getSelectedItem().toString());
    int cupoMaximo = Integer.parseInt(txtCupoMax.getText());
    int cupoDisponible = Integer.parseInt(txtCupoDisp.getText());
    String semestre = cbSemestre.getSelectedItem().toString();
    int anio = Integer.parseInt(cbYear.getSelectedItem().toString());

    PreparedStatement ps = null;

    try {
        String sql = "UPDATE Cursos SET ID_Materia = ?, ID_Profesor = ?, Cupo_Maximo = ?, Cupo_Disponible = ?, Semestre = ?, Anio = ? WHERE ID_Curso = ?";
        ps = cn.prepareStatement(sql);

        // Asignar parámetros
        ps.setInt(1, idMateria);
        ps.setInt(2, idProfesor);
        ps.setInt(3, cupoMaximo);
        ps.setInt(4, cupoDisponible);
        ps.setString(5, semestre);
        ps.setInt(6, anio);
        ps.setInt(7, cursoId);

        // Ejecutar consulta
        int resultado = ps.executeUpdate();

        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Curso actualizado exitosamente");
            limpiar(); // Llamar al método para limpiar los campos
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el curso", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (ps != null) ps.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cerrar la conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    }//GEN-LAST:event_btnActualizarCursoActionPerformed

    private void popEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popEliminarActionPerformed
    // Validar si hay una fila seleccionada en la tabla
    int filaSeleccionada = tblCursos.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un curso para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Obtener el ID del curso de la fila seleccionada
    int idCurso = Integer.parseInt(tblCursos.getValueAt(filaSeleccionada, 0).toString());

    // Confirmación antes de eliminar
    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el curso con ID " + idCurso + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirmacion != JOptionPane.YES_OPTION) {
        return; // Cancelar la operación si el usuario selecciona "No"
    }

    // Realizar la eliminación en la base de datos
    try {
        String sql = "DELETE FROM Cursos WHERE ID_Curso = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, idCurso);

        int resultado = ps.executeUpdate();
        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Curso eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrarTabla(""); // Método para actualizar la tabla después de eliminar
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el curso", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        System.err.println("Error al eliminar el curso: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al eliminar el curso. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
    }        
        
    }//GEN-LAST:event_popEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(GestionCursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionCursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionCursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionCursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionCursos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackAndFooter5;
    private javax.swing.JToggleButton btnActualizarCurso;
    private javax.swing.JButton btnBack5;
    private javax.swing.JToggleButton btnGuardarCurso;
    private javax.swing.JComboBox<String> cbMaterias;
    private javax.swing.JComboBox<String> cbProfesor;
    private javax.swing.JComboBox<String> cbSemestre;
    private javax.swing.JComboBox<String> cbYear;
    private javax.swing.JTextField idCurso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblfooter3;
    private javax.swing.JPopupMenu popBorrar;
    private javax.swing.JMenuItem popEliminar;
    private javax.swing.JTable tblCursos;
    private javax.swing.JTextField txtCupoDisp;
    private javax.swing.JTextField txtCupoMax;
    // End of variables declaration//GEN-END:variables
    Conectar conectado = new Conectar();
    Connection cn = conectado.conexion();

}
