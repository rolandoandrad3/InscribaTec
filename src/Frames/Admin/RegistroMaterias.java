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
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rolan
 */
public class RegistroMaterias extends javax.swing.JFrame {

    /**
     * Creates new form RegistroMaterias
     */
    public RegistroMaterias() {
        initComponents();
        setSize(1000, 600);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        // Evitar que la ventana sea redimensionable
        setResizable(true);
        // Opcional: Establecer un título para la ventana
        setTitle("Registro de Materias - Admin");
        cerrar();
        mostrartabla("");
        cargarCBCarrera(cbCarrera);
        cbCarrera.setSelectedItem(6);
        txtIdMateria.setEnabled(false);
        
        TextPrompt idprofe = new TextPrompt("Autogenerado",txtIdMateria);
        TextPrompt nombre = new TextPrompt("Nombre Materia",txtNombreMateria);
        TextPrompt cod = new TextPrompt("Codigo de Materia",txtCodMateria);
        
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
    
    void mostrartabla(String valor){
        DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID");
    modelo.addColumn("Nombre de la Materia");
    modelo.addColumn("Codigo de Materia");
    modelo.addColumn("Carrera");
    modelo.addColumn("Estado");

    tblMaterias.setModel(modelo);

    // Consulta SQL con filtro por valor
    String sql = "SELECT m.ID_Materia, m.Nombre_Materia, m.Codigo_Materia, c.Nombre_Carrera, m.Estado " +
                 "FROM Materias m " +
                 "INNER JOIN Carreras c ON m.ID_Carrera = c.ID_Carrera " +
                 "WHERE CONCAT(m.ID_Materia, m.Nombre_Materia, m.Codigo_Materia, c.Nombre_Carrera, m.Estado) LIKE ?";

    String[] datos = new String[5]; // 5 columnas
    try {
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, "%" + valor + "%"); // Busca por cualquier coincidencia
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            datos[0] = rs.getString("ID_Materia");
            datos[1] = rs.getString("Nombre_Materia");
            datos[2] = rs.getString("Codigo_Materia");
            datos[3] = rs.getString("Nombre_Carrera");
            datos[4] = rs.getString("Estado");
            modelo.addRow(datos); // Agrega cada fila al modelo
        }
        tblMaterias.setModel(modelo); // Actualiza la tabla
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar datos: " + e.getMessage());
    }
    }
    public void cargarCBCarrera(JComboBox cbCarrera){
        try {
            String sql = "SELECT Nombre_Carrera FROM Carreras";
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                cbCarrera.addItem(rs.getString("Nombre_Carrera"));
            }
                
        } 
        catch (SQLException e) {
            System.err.println(e);
        }
    }

    // Método para buscar materias por nombre

    private void buscarMateriaPorNombre(String nombreMateria) {
        try {
        // Consulta SQL para buscar materias por nombre
        String sql = "SELECT m.ID_Materia, m.Nombre_Materia, m.Codigo_Materia, c.Nombre_Carrera, m.Estado "
                   + "FROM Materias m "
                   + "INNER JOIN Carreras c ON m.ID_Carrera = c.ID_Carrera "
                   + "WHERE m.Nombre_Materia LIKE ?";

        // Preparar la consulta
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, "%" + nombreMateria + "%"); // Usar % para buscar coincidencias parciales
        ResultSet rs = ps.executeQuery();

        // Limpiar el modelo de la tabla antes de llenarla con nuevos datos
        DefaultTableModel model = (DefaultTableModel) tblMaterias.getModel();
        model.setRowCount(0);

        // Si se encuentra un resultado, llenar los campos y la tabla
        boolean found = false;
        while (rs.next()) {
            found = true;
            // Llenar los campos con el primer resultado
            if (model.getRowCount() == 0) {
                txtNombreMateria.setText(rs.getString("Nombre_Materia"));
                txtCodMateria.setText(rs.getString("Codigo_Materia"));
                cbCarrera.setSelectedItem(rs.getString("Nombre_Carrera"));
                cbEstadoMateria.setSelectedItem(rs.getString("Estado")); // Llenar el combo de Estado
            }

            // Llenar la tabla
            Object[] fila = new Object[]{
                rs.getInt("ID_Materia"),
                rs.getString("Nombre_Materia"),
                rs.getString("Codigo_Materia"),
                rs.getString("Nombre_Carrera"),
                rs.getString("Estado")
            };
            model.addRow(fila);
        }

        // Si no se encontró nada, limpiar los campos
        if (!found) {
            limpiarCamposMateria();
        }

    } catch (SQLException e) {
        System.err.println("Error al buscar materia por nombre: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al buscar la materia. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
// Método para limpiar los campos si no hay resultados

    private void limpiarCamposMateria() {
        txtNombreMateria.setText("");
    txtCodMateria.setText("");
    cbCarrera.setSelectedIndex(0); // Reiniciar selección de carrera
    cbEstadoMateria.setSelectedIndex(0); // Reiniciar selección de estado
    DefaultTableModel model = (DefaultTableModel) tblMaterias.getModel();
    model.setRowCount(0); // Vaciar la tabla
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
        BackAndFooter2 = new javax.swing.JPanel();
        btnBack2 = new javax.swing.JButton();
        lblfooter = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaterias = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnActualizarMateria = new javax.swing.JToggleButton();
        btnGuardarMateria = new javax.swing.JToggleButton();
        txtIdMateria = new javax.swing.JTextField();
        txtCodMateria = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombreMateria = new javax.swing.JTextField();
        cbEstadoMateria = new javax.swing.JComboBox<>();
        cbCarrera = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        popEliminar.setText("Eliminar");
        popEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popEliminarActionPerformed(evt);
            }
        });
        popBorrar.add(popEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBack2.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        btnBack2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back.png"))); // NOI18N
        btnBack2.setText("Vover");
        btnBack2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack2ActionPerformed(evt);
            }
        });

        lblfooter.setText("InscribaTec @ Universiad Tecnologica");

        javax.swing.GroupLayout BackAndFooter2Layout = new javax.swing.GroupLayout(BackAndFooter2);
        BackAndFooter2.setLayout(BackAndFooter2Layout);
        BackAndFooter2Layout.setHorizontalGroup(
            BackAndFooter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackAndFooter2Layout.createSequentialGroup()
                .addComponent(btnBack2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 651, Short.MAX_VALUE)
                .addComponent(lblfooter)
                .addContainerGap())
        );
        BackAndFooter2Layout.setVerticalGroup(
            BackAndFooter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackAndFooter2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BackAndFooter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblfooter, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBack2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblMaterias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre de la Materia", "Codigo de la Materia", "Carrera", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaterias.setComponentPopupMenu(popBorrar);
        tblMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMateriasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMaterias);
        if (tblMaterias.getColumnModel().getColumnCount() > 0) {
            tblMaterias.getColumnModel().getColumn(0).setResizable(false);
            tblMaterias.getColumnModel().getColumn(0).setPreferredWidth(8);
            tblMaterias.getColumnModel().getColumn(2).setResizable(false);
            tblMaterias.getColumnModel().getColumn(3).setResizable(false);
            tblMaterias.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("ID Materia");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Nombre de la materia");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Codigo de la materia");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Carrera Asociada");

        btnActualizarMateria.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnActualizarMateria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnActualizarMateria.setText("Actualizar");
        btnActualizarMateria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizarMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarMateriaActionPerformed(evt);
            }
        });

        btnGuardarMateria.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGuardarMateria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        btnGuardarMateria.setText("Guardar");
        btnGuardarMateria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardarMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMateriaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Estado");

        cbEstadoMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activa", "Inactiva" }));
        cbEstadoMateria.setToolTipText("");

        cbCarrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona Carrera" }));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/buscar.png"))); // NOI18N
        jLabel6.setText("Buscar:");

        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField1MousePressed(evt);
            }
        });
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BackAndFooter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdMateria)
                                    .addComponent(txtCodMateria, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(txtNombreMateria, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(cbEstadoMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbCarrera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGuardarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(119, 119, 119))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtIdMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbEstadoMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(BackAndFooter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBack2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack2ActionPerformed
        // TODO add your handling code here:
        Principal principal = new Principal();
        principal.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBack2ActionPerformed

    private void btnActualizarMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarMateriaActionPerformed
        // TODO add your handling code here:
        // Obtener los valores de los campos
    String idMateria = txtIdMateria.getText();  //
    String nombreMateria = txtNombreMateria.getText();
    String codigoMateria = txtCodMateria.getText();
    String carrera = cbCarrera.getSelectedItem().toString();  // Carrera seleccionada
    String estado = cbEstadoMateria.getSelectedItem().toString();  // Estado seleccionado

    // Validar que los campos no estén vacíos
    if (nombreMateria.isEmpty() || codigoMateria.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
        return;
    }

    // Crear la consulta SQL para actualizar los datos de la materia
    String sql = "UPDATE Materias SET Nombre_Materia = ?, Codigo_Materia = ?, ID_Carrera = ?, Estado = ? WHERE ID_Materia = ?";

    try {
          // Crear el PreparedStatement para ejecutar el UPDATE
        PreparedStatement pst = cn.prepareStatement(sql);
        
        // Establecer los valores en el PreparedStatement
        pst.setString(1, nombreMateria);
        pst.setString(2, codigoMateria);
        pst.setString(3, carrera);
        pst.setString(4, estado);
        pst.setString(5, idMateria);  // Usa el ID de la materia para actualizar la fila correcta
        
        // Ejecutar el update
        int filasAfectadas = pst.executeUpdate();
        
        // Verificar si se actualizó alguna fila
        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "Materia actualizada correctamente.");
            
            // Limpiar los campos después de actualizar
            txtIdMateria.setText("");
            txtNombreMateria.setText("");
            txtCodMateria.setText("");
            cbCarrera.setSelectedIndex(0);
            cbEstadoMateria.setSelectedIndex(0);

            // Refrescar la tabla con los datos actualizados (esto depende de cómo tengas implementado el método de mostrar)
            mostrartabla("");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la materia.");
        }

        // Cerrar la conexión
        cn.close();
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al actualizar la materia: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnActualizarMateriaActionPerformed

    private void btnGuardarMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMateriaActionPerformed
        // TODO add your handling code here:
        // Obtener los valores de los campos
        String nombreMateria = txtNombreMateria.getText();
        String codigoMateria = txtCodMateria.getText();
        String carrera = cbCarrera.getSelectedItem().toString(); // Asumiendo que cbCarrera es un JComboBox
        String estado = cbEstadoMateria.getSelectedItem().toString();  // Asumiendo que cbEstado es un JComboBox

        // Validar si los campos no están vacíos
        if (nombreMateria.isEmpty() || codigoMateria.isEmpty() || carrera.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
            return;
        }

        // Consulta SQL para insertar una nueva materia
        String sql = "INSERT INTO Materias (Nombre_Materia, Codigo_Materia, ID_Carrera, Estado) "
                + "VALUES (?, ?, (SELECT ID_Carrera FROM Carreras WHERE Nombre_Carrera = ?), ?)";

        try {
            // Preparar la sentencia SQL
            PreparedStatement ps = cn.prepareStatement(sql);

            // Establecer los valores de los parámetros en la consulta SQL
            ps.setString(1, nombreMateria);
            ps.setString(2, codigoMateria);
            ps.setString(3, carrera);  // Se obtiene el ID de carrera con el nombre seleccionado
            ps.setString(4, estado);   // Estado de la materia (activo/inactivo)

            // Ejecutar la inserción
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Materia guardada correctamente.");
                // Limpiar los campos después de guardar
                txtNombreMateria.setText("");
                txtCodMateria.setText("");
                cbCarrera.setSelectedIndex(0); // Reiniciar selección de carrera
                cbEstadoMateria.setSelectedIndex(0);  // Reiniciar selección de estado
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar la materia.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
        }

    }//GEN-LAST:event_btnGuardarMateriaActionPerformed

    private void tblMateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMateriasMouseClicked
        // TODO add your handling code here:
        int fila = tblMaterias.getSelectedRow();

    // Comprobar que se haya seleccionado una fila (es decir, fila >= 0)
    if (fila >= 0) {
        // Obtener los valores de la fila seleccionada
        String nombreMateria = tblMaterias.getValueAt(fila, 1).toString();  // Columna 1: Nombre de la Materia
        String codigoMateria = tblMaterias.getValueAt(fila, 2).toString();  // Columna 2: Código de la Materia
        String carrera = tblMaterias.getValueAt(fila, 3).toString();        // Columna 3: Carrera
        String estado = tblMaterias.getValueAt(fila, 4).toString();         // Columna 4: Estado

        // Llenar los campos con los valores obtenidos
        txtNombreMateria.setText(nombreMateria);
        txtCodMateria.setText(codigoMateria);
        
        // Asignar la carrera seleccionada en el JComboBox (suponiendo que cbCarrera tiene las opciones correctas)
        for (int i = 0; i < cbCarrera.getItemCount(); i++) {
            if (cbCarrera.getItemAt(i).toString().equals(carrera)) {
                cbCarrera.setSelectedIndex(i);
                break;
            }
        }

        // Asignar el estado seleccionado en el JComboBox (suponiendo que cbEstadoMateria tiene las opciones correctas)
        for (int i = 0; i < cbEstadoMateria.getItemCount(); i++) {
            if (cbEstadoMateria.getItemAt(i).toString().equals(estado)) {
                cbEstadoMateria.setSelectedIndex(i);
                break;
            }
        }
    }
    }//GEN-LAST:event_tblMateriasMouseClicked

    private void popEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popEliminarActionPerformed
        // Validar si hay una fila seleccionada en la tabla
    int filaSeleccionada = tblMaterias.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona una materia para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Obtener el ID de la materia de la fila seleccionada
    int idMateria = Integer.parseInt(tblMaterias.getValueAt(filaSeleccionada, 0).toString());

    // Confirmación antes de eliminar
    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar la materia con ID " + idMateria + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirmacion != JOptionPane.YES_OPTION) {
        return; // Cancelar la operación si el usuario selecciona "No"
    }

    // Realizar la eliminación en la base de datos
    try {
        String sql = "DELETE FROM Materias WHERE ID_Materia = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, idMateria);

        int resultado = ps.executeUpdate();
        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Materia eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrartabla(""); // Método para actualizar la tabla después de eliminar
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar la materia", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        System.err.println("Error al eliminar la materia: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Error al eliminar la materia. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_popEliminarActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // Falta poner la logica de buscar
        // Obtener el texto ingresado en tiempo real
        String valorBusqueda = jTextField1.getText().trim() + evt.getKeyChar();

        // Realizar la búsqueda
        buscarMateriaPorNombre(valorBusqueda);
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1MousePressed

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
            java.util.logging.Logger.getLogger(RegistroMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroMaterias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackAndFooter2;
    private javax.swing.JToggleButton btnActualizarMateria;
    private javax.swing.JButton btnBack2;
    private javax.swing.JToggleButton btnGuardarMateria;
    private javax.swing.JComboBox<String> cbCarrera;
    private javax.swing.JComboBox<String> cbEstadoMateria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblfooter;
    private javax.swing.JPopupMenu popBorrar;
    private javax.swing.JMenuItem popEliminar;
    private javax.swing.JTable tblMaterias;
    private javax.swing.JTextField txtCodMateria;
    private javax.swing.JTextField txtIdMateria;
    private javax.swing.JTextField txtNombreMateria;
    // End of variables declaration//GEN-END:variables
    Conectar conectado = new Conectar();
    Connection cn = conectado.conexion();
}

