package ventanas;

import archivos.Archivo;
import archivos.ArchivoIndice;
import archivos.ArchivoMaestro;
import clausula.Clausula;
import clausula.ParsearClausula;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistema.experto.MotorInferencia;
import sistema.experto.arbol.Arbol;
import sistema.experto.arbol.Nodo;


public class Clausulas extends javax.swing.JFrame {
    ArchivoMaestro maestro, maestroTemp;
    ArchivoIndice indice, indiceTemp;
    Arbol arbol;
    
    
    public Clausulas(){
        initComponents();
        this.setLocationRelativeTo(null);
        taMostrar.setEditable(false);
        
        try
        {
            maestro = new ArchivoMaestro(Archivo.Maestro);
            indice = new ArchivoIndice(Archivo.Indice);

            arbol = indice.obtenerIndice();
            
            mostrarTodas();
        }catch(Exception e){
        }
        
    }
    
    private void mostrarTodas() throws IOException {
        ArrayList<Nodo> direcciones;
        Clausula clausula;
        
        direcciones = arbol.obtenerDatos();
        
        taMostrar.setText("");
        for(int i=0;i<direcciones.size();i++)
        {
            clausula = maestro.obtener(direcciones.get(i).direccion);
            taMostrar.setText(taMostrar.getText()+"\n"+clausula);
        }
    }
    
    private void agregarClausula() throws IOException{
        String cadena;
        String clausulaCadena;
        int llave, posicion;
        ParsearClausula pc = new ParsearClausula();
        Clausula clausula;
        
        try{
            cadena = JOptionPane.showInputDialog("Ingresa la llave");
            llave = Integer.parseInt(cadena);
            clausulaCadena = JOptionPane.showInputDialog("Ingresa la clausula");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error, Introduce un numero entero");
            
            return;
        }
        
        if(pc.aClausula(clausulaCadena))
            JOptionPane.showMessageDialog(null, "Error: formato de la clusula incorrecta.");
        else
        {
            clausula = pc.clausula;
            
            clausula.clave = llave;

            posicion = maestro.agregar(clausula,0);
            indice.agregar(llave, posicion);

            arbol = indice.obtenerIndice();
            
            mostrarTodas();
        }
        
    }
    
    public void eliminarClausula() throws IOException{
        int llave;
        Nodo nodo;
        Clausula estructura;
        ArrayList<Nodo> direcciones;
        int direccion;
        String cadena;
        
        try{
            cadena = JOptionPane.showInputDialog("Ingresa la llave");
            llave = Integer.parseInt(cadena);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error, Introduce un numero entero");
            return;
        }
        
        nodo = arbol.buscar(llave);
        if(nodo != null)
        {
            estructura = maestro.obtener(nodo.direccion);
            
            maestroTemp = new ArchivoMaestro(Archivo.MaestroTemp);
            indiceTemp = new ArchivoIndice(Archivo.IndiceTemp);
            
            direcciones = arbol.obtenerDatos();
            for(int i=0;i<direcciones.size();i++)
            {
                estructura = maestro.obtener(direcciones.get(i).direccion);
                if(nodo.direccion != direcciones.get(i).direccion)
                {
                    direccion = maestroTemp.agregar(estructura, 0);
                    
                    indiceTemp.agregar(direcciones.get(i).llave, direccion);
                }
            }
            
            indiceTemp.cerrar();
            indice.cerrar();
            maestroTemp.cerrar();
            maestro.cerrar();
            
            Archivo.eliminar(Archivo.Indice);
            Archivo.eliminar(Archivo.Maestro);
            
            Archivo.renombrar(Archivo.IndiceTemp, Archivo.Indice);
            Archivo.renombrar(Archivo.MaestroTemp, Archivo.Maestro);
            
            
            maestro = new ArchivoMaestro(Archivo.Maestro);
            indice = new ArchivoIndice(Archivo.Indice);
            arbol = indice.obtenerIndice();
            
            mostrarTodas();
            
            JOptionPane.showMessageDialog(null, "Se elimino la clausula:\n"+estructura);
        }
        else
            JOptionPane.showMessageDialog(null, "Error, no existe la llave");
    }
    
    public void actualizarClausula() throws IOException
    {
        int llave;
        Nodo nodo;
        String cadena;
        Clausula clausula, clausulaAnt;
        ParsearClausula pc = new ParsearClausula();
        
         try{
            cadena = JOptionPane.showInputDialog("Ingresa la llave");
            llave = Integer.parseInt(cadena);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error, Introduce un numero entero");
            return;
        }
        
        nodo = arbol.buscar(llave);
        if(nodo != null){
            clausulaAnt = maestro.obtener(nodo.direccion);
            
            cadena  = JOptionPane.showInputDialog("Ingresa la nueva clausula\n"+clausulaAnt);
            if(pc.aClausula(cadena))
                JOptionPane.showMessageDialog(null, "Error: formato de la clusula incorrecta.");
            else{
                clausula = pc.clausula;
                clausula.clave = llave;

                maestro.agregar(clausula,nodo.direccion);
                mostrarTodas();
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Error, no existe la llave");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taMostrar = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDatos = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        taResultados = new javax.swing.JTextArea();
        btnInferir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        taMostrar.setColumns(20);
        taMostrar.setRows(5);
        jScrollPane1.setViewportView(taMostrar);

        taDatos.setColumns(20);
        taDatos.setRows(5);
        jScrollPane2.setViewportView(taDatos);

        taResultados.setEditable(false);
        taResultados.setColumns(20);
        taResultados.setRows(5);
        jScrollPane3.setViewportView(taResultados);

        btnInferir.setText("Inferir");
        btnInferir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInferirActionPerformed(evt);
            }
        });

        jLabel1.setText("Inferir");

        jLabel2.setText("Clausulas de entrada");

        jLabel3.setText("Resultados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnInferir)
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel2)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel1)
                        .addGap(194, 194, 194)
                        .addComponent(jLabel3)
                        .addGap(0, 129, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnInferir)
                        .addGap(75, 75, 75))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            eliminarClausula();
        } catch (IOException ex) {
            Logger.getLogger(Clausulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            actualizarClausula();
        } catch (IOException ex) {
            Logger.getLogger(Clausulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        Principal vp = new Principal();
        vp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            // TODO add your handling code here:
            agregarClausula();
        } catch (IOException ex) {
            Logger.getLogger(Clausulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnInferirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInferirActionPerformed
        String datos[] = taDatos.getText().split("\n");
        ArrayList<Clausula> clausulas = new ArrayList();
        ParsearClausula pc = new ParsearClausula();
        ArrayList<Clausula> clausulasConocimiento;
        MotorInferencia mi;
        ArchivoMaestro am;
        
        for(int i=0;i<datos.length;i++)
        {
            if(datos[i].length() > 0)
            {
                if(pc.aClausula(datos[i]))
                {
                    JOptionPane.showMessageDialog(null, "Error: formato de la clusula incorrecta.");
                    return;
                }
                else
                {
                    clausulas.add(pc.clausula);
                }
                
            }
        }
        
        try {
            am = new ArchivoMaestro(Archivo.Maestro);
            
            clausulasConocimiento = am.clausulas();
            am.cerrar();

            mi = new MotorInferencia(clausulasConocimiento);
            
            
            taResultados.setText(mi.inferir(clausulas));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Clausulas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Clausulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInferirActionPerformed

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
            java.util.logging.Logger.getLogger(Clausulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clausulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clausulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clausulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clausulas().setVisible(true);

                //Logger.getLogger(Clausulas.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInferir;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea taDatos;
    private javax.swing.JTextArea taMostrar;
    private javax.swing.JTextArea taResultados;
    // End of variables declaration//GEN-END:variables
}
