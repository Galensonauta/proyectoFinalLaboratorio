/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Hueso
 */
public class LugarAsientoVista extends javax.swing.JInternalFrame {

    private int filasSala = 10; //estos datos podrían venir de la tabla sala    
    private int columnasSala = 7;
    
    public ArrayList<String> asientosSeleccionadosEtiquetas = new ArrayList<>(); 
    
    public ArrayList<String> getAsientosSeleccionadosEtiquetas(){
        return asientosSeleccionadosEtiquetas;
    }
    
    // Clase separada dentro del paquete Vista-Controlador
    private class SimpleToggleListener implements ActionListener {

        

        public SimpleToggleListener(ArrayList<String> seleccionados) {
            asientosSeleccionadosEtiquetas = seleccionados;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton boton = (JToggleButton) e.getSource();

            
            String etiquetaAsiento = boton.getText();

            
            if (boton.isSelected() && asientosSeleccionadosEtiquetas.size() < 4) {
                
                asientosSeleccionadosEtiquetas.add(etiquetaAsiento);

            } else if (boton.isSelected() && asientosSeleccionadosEtiquetas.size() == 4) {
                
                JOptionPane.showMessageDialog(null, "Sólo puede comprar 4 plazas", "Límite de Asientos", JOptionPane.INFORMATION_MESSAGE);
                boton.setSelected(false);
                
            } else {
                
                asientosSeleccionadosEtiquetas.remove(etiquetaAsiento);
            }
            
            if(!asientosSeleccionadosEtiquetas.isEmpty()){
                btnGuardar.setEnabled(true);
            } else {
                btnGuardar.setEnabled(false);
            }
            
            System.out.println("Selección actual: " + asientosSeleccionadosEtiquetas);
        }

        
    }
   
    public void setearFilasYColumnas(){
        //ObtenerAsientos(nrSala){ desde SalaData}
    }
    
    
    



    /*public void crearPanelAsientos(){
        setearFilasYColumnas();
        JToggleButton asiento;

        jPanel.setLayout(new GridLayout(filasSala, columnasSala, 5, 5));
        
        for (int i = 0; i < filasSala; i++) {
            for (int j = 0; j < columnasSala; j++) {
                asiento = new JToggleButton(""+ (i+1) + " - " + (j+1));
                jPanel.add(asiento);
            }
        }
    }*/
    
    

    private String generarEtiqueta(int r, int c) {
        // Convierte el índice numérico de la fila (r) en una letra (A=0, B=1, etc.)
        char letraFila = (char) ('A' + r);

        // La columna (c) se muestra sumándole 1, ya que los asientos empiezan en 1
        int numeroColumna = c + 1;

        return String.valueOf(letraFila) + numeroColumna;
    }
    
    public void dibujarSala(int filas, int columnas) {
        
        ActionListener selectorListener = new SimpleToggleListener(asientosSeleccionadosEtiquetas);
        
        jPanel.setLayout(new GridLayout(filasSala, columnasSala, 5, 5));
        
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {

                JToggleButton asiento = new JToggleButton();

                // 1. Obtener datos (de la BD)
                boolean ocupado = false; //como no los tengo pongo todo false
                String etiqueta = generarEtiqueta(r, c);

                // 2. Configurar el botón
                asiento.setText(etiqueta); 

                if (ocupado) {
                    
                    asiento.setEnabled(false); 
                } else {
                    
                    asiento.setEnabled(true);
                    asiento.addActionListener(selectorListener); 
                }

                jPanel.add(asiento);
            }
        }
    }
 
    public LugarAsientoVista() {
        initComponents();
        //crearPanelAsientos();
        dibujarSala(filasSala, columnasSala);
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setText("Elegir Asiento/s");

        jLabel2.setText("(Hasta 4) ");

        jPanel.setMaximumSize(new java.awt.Dimension(1024, 768));

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        btnGuardar.setText("Guardar Selección");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2)
                        .addGap(0, 296, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addGap(153, 153, 153)
                        .addComponent(btnSalir)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(btnGuardar)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel;
    // End of variables declaration//GEN-END:variables
}
