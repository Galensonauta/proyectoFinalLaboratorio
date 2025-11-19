/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import modelo.LugarAsiento;
import persistencia.LugarAsientoData;
import persistencia.SalaData;

/**
 *
 * @author Hueso
 */
public class VistaClienteElegirAsiento extends javax.swing.JInternalFrame {

    private int filasSala = 1; //estos datos podrían venir de la tabla sala    
    private int columnasSala = 10;
    public int idProyeccion = -1;
    
    private VistaClientePrincipal madre;
    private SalaData sd = new SalaData();
    private LugarAsientoData lad = new LugarAsientoData();
    
    private HashSet<String> asientosOcupadosEtiquetas = new HashSet();
    
    public ArrayList<String> asientosSeleccionadosEtiquetas = new ArrayList<>();
    public ArrayList<LugarAsiento> asientosFinales = new ArrayList<>();
    
     public VistaClienteElegirAsiento(VistaClientePrincipal madre) {
        initComponents();
        this.madre = madre;
        this.idProyeccion = madre.getDt().getProyeccion().getIdProyeccion();
        this.asientosOcupadosEtiquetas = lad.obtenerAsientosOcupados(idProyeccion);
        System.out.println(asientosOcupadosEtiquetas);
        setearFilasYColumnas();
         System.out.println("IdProyeccion: " + idProyeccion);
        dibujarSala(filasSala, columnasSala);
    }
    
    
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
                boton.setBackground(new Color(0, 153, 204));
                asientosSeleccionadosEtiquetas.add(etiquetaAsiento);

            } else if (boton.isSelected() && asientosSeleccionadosEtiquetas.size() == 4) {
                
                JOptionPane.showMessageDialog(null, "Sólo puede comprar 4 plazas", "Límite de Asientos", JOptionPane.INFORMATION_MESSAGE);
                boton.setSelected(false);
                
            } else {
                boton.setBackground(Color.lightGray);
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
        int capacidad =  madre.getDt().getProyeccion().getSala().getCapacidad();
        System.out.println("CAPACIDAD SALA:  " + capacidad);
        this.columnasSala = 10;
        this.filasSala = (capacidad / 10);
    }
    
    private void convertirEtiquetasAObjetos(){
        for (String etiqueta : asientosSeleccionadosEtiquetas) {
            try {           
                String fila = etiqueta.substring(0, 1); 

                int numeroAsiento = Integer.parseInt(etiqueta.substring(1));

                LugarAsiento nuevoAsiento = new LugarAsiento(
                    fila, 
                    numeroAsiento, 
                    true, 
                    idProyeccion
                );

                this.asientosFinales.add(nuevoAsiento);

            } catch (NumberFormatException e) {
                System.out.println("Error de formato en número de asiento: " + etiqueta + " .Error " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error al procesar la etiqueta " + etiqueta + ": " + e.getMessage());
            }    
        }
    }
    

    private String generarEtiqueta(int r, int c) {
       
        char letraFila = (char) ('A' + r);

   
        int numeroColumna = c + 1;

        return String.valueOf(letraFila) + numeroColumna;
    }
    
    
    public void dibujarSala(int filas, int columnas) {
        
        ActionListener selectorListener = new SimpleToggleListener(asientosSeleccionadosEtiquetas);
        
        jPanel.setLayout(new GridLayout(filasSala, columnasSala));
        
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {

                JToggleButton asiento = new JToggleButton();

              
               
                String etiqueta = generarEtiqueta(r, c);
                
                boolean ocupado = asientosOcupadosEtiquetas.contains(etiqueta);

                // Configurar el botón
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
 
   

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        btnGuardar = new javax.swing.JButton();
        jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();

        setMaximizable(true);
        setResizable(true);

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(1024, 768));

        btnGuardar.setText("Siguiente >");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel.setAutoscrolls(true);
        jPanel.setMaximumSize(new java.awt.Dimension(768, 480));
        jPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setText("Elegir Asiento/s");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("(Hasta 4) ");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnAtras.setText("< Atrás");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(btnGuardar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btnSalir, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btnAtras, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(448, 448, 448)
                        .addComponent(jLabel1)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)))
                .addContainerGap(222, Short.MAX_VALUE))
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAtras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(512, 512, 512)
                .addComponent(btnSalir)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnAtras)
                    .addComponent(btnSalir))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        convertirEtiquetasAObjetos();
        madre.getDt().setLugares(asientosFinales);
        madre.getDt().setCantidad(asientosFinales.size());
        madre.getDt().setSubtotal(madre.getDt().getProyeccion().getPrecioLugar() * asientosFinales.size());
        madre.avanzarFlujoVenta(3);
        this.dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        madre.avanzarFlujoVenta(1);
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel;
    // End of variables declaration//GEN-END:variables
}
