/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.*;
import persistencia.*;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class ModificarProyeccionVista extends javax.swing.JInternalFrame {

    private Proyeccion proyec;
    private ProyeccionData proData;
    private PeliculaData peliData;
    private SalaData salaData;
    private LugarAsientoData butacaData;
    private List<Pelicula> listaPelis;
    private List<Sala> listaSalas;
    private List<Proyeccion> listaProyeccion;
    
    public ModificarProyeccionVista(Proyeccion p, ProyeccionData proData, PeliculaData peliData, SalaData salaData) {
        initComponents();
        proyec = p;
        this.proData = proData;
        this.peliData = peliData;
        this.salaData = salaData;
        this.butacaData = new LugarAsientoData();
        listaPelis = peliData.listarTodasLasPeliculas();
        listaSalas = salaData.obtenerTodasLasSalas();
        cargarComboPelis();
        cargarComboSalas();
        cargarComboIdiomas();
        cargarHorarios();
        conectarEventos();
        cargarCampos(proyec);
        
    }
    private void cargarComboPelis() {
        for (Pelicula aux : listaPelis) {
            JCBPelicula.addItem(aux);
        }
        
    }

    private void cargarComboSalas() {
        for (Sala aux : listaSalas) {
            JCBSala.addItem(aux);
        }
        
    }
    
    private void cargarHorarios() {
        String[] horarios = {
            "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
            "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
            "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
            "22:00", "22:30", "23:00", "23:30"
        };

        JCBHoraInicio.removeAllItems();

        for (String aux : horarios) {
            JCBHoraInicio.addItem(aux);
        }

        JCBHoraInicio.setSelectedIndex(-1);
        JTFHoraFin.setText("");
    }

    private void actualizarHoraFin() {
        String horaInicioStr = (String) JCBHoraInicio.getSelectedItem();

        if (horaInicioStr == null) {
            JTFHoraFin.setText("");
            return;
        }

        try {
            LocalTime inicio = LocalTime.parse(horaInicioStr);
            LocalTime fin = inicio.plusHours(2);

            String horaFinStr = fin.format(DateTimeFormatter.ofPattern("HH:mm"));
            
            JTFHoraFin.setText(horaFinStr); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al calcular horario de finalización: " + e.getMessage());
            JTFHoraFin.setText("");
        }
    }
    
    private void cargarComboIdiomas() {
        String[] idiomas = {"Español", "Inglés", "Portugués"};

        JCBIdioma.removeAllItems();
        for (String aux : idiomas) {
            JCBIdioma.addItem(aux);
        }
        JCBIdioma.setSelectedIndex(-1);
    }

    private void cargarCampos(Proyeccion pro){
        JTFID.setText(String.valueOf(pro.getIdProyeccion()));
        JTFID.setEditable(false);
        
        Pelicula peliPro = pro.getPelicula();
        for (int i = 0; i < JCBPelicula.getItemCount(); i++) {
            Pelicula peliCombo = JCBPelicula.getItemAt(i);
        
            if(peliCombo.getTitulo().equals(peliPro.getTitulo())){
                JCBPelicula.setSelectedIndex(i);
                break;
            }
        }
        
        Sala salaPro = pro.getSala();
        for (int i = 0; i < JCBSala.getItemCount(); i++) {
            Sala salaCombo = JCBSala.getItemAt(i);
            
            if(salaCombo.getNroSala() == salaPro.getNroSala()){
                JCBSala.setSelectedIndex(i);
                break;
            }
        }
        
        
        JCBIdioma.setSelectedItem(pro.getIdioma());
        
        LocalDateTime inicio = pro.getHoraInicio();
        
        Date fecha = Date.from(inicio.atZone(ZoneId.systemDefault()).toInstant());
        // Asegura que sea del tipo correcto
        JDCCalendario.setDate(new java.util.Date(fecha.getTime()));
        
        String inicioStr = inicio.format(DateTimeFormatter.ofPattern("HH:mm"));
        String finStr = pro.getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm"));
        
        JCBHoraInicio.setSelectedItem(inicioStr);
        JTFHoraFin.setText(finStr);
        
        asignarPrecioSegunSala();
        actualizarHoraFin();
    }
    
    private void asignarPrecioSegunSala() {
        JTFPrecioAsiento.setEditable(false);
        JTFLugaresDispo.setEditable(false);
        
        final int PRECIO_2D = 5500;
        final int PRECIO_3D = 8000;

        Sala salaSeleccionada = (Sala) JCBSala.getSelectedItem();
        if (salaSeleccionada != null) {
            //logica para lugares disponibles obteniendo la capacidad de la sala
            int capacidad = salaSeleccionada.getCapacidad();
            JTFLugaresDispo.setText(String.valueOf(capacidad));
            
            //-------------------------------------------------------------------

            if (salaSeleccionada.isApta3D()) {
                JCBox3DSI.setEnabled(true);
                JCBox3DNO.setEnabled(true);
                
                if (JCBox3DSI.isSelected()){
                    JTFPrecioAsiento.setText(String.valueOf(PRECIO_3D));
                    JCBox3DNO.setSelected(false);
                } else{
                    JTFPrecioAsiento.setText(String.valueOf(PRECIO_2D));
                    JCBox3DSI.setSelected(false);
                    JCBox3DNO.setSelected(true);
                }
            } else {
                JCBox3DSI.setSelected(false);
                JCBox3DSI.setEnabled(false);
                
                JCBox3DNO.setSelected(true);
                JCBox3DNO.setEnabled(true);
                
                JTFPrecioAsiento.setText(String.valueOf(PRECIO_2D));
            }
        } else {
            JTFPrecioAsiento.setText("");
            JTFLugaresDispo.setText("");
            JCBox3DSI.setEnabled(true);
            JCBox3DNO.setEnabled(true);
        }
    }
    
    private void conectarEventos(){
        //este metodo es para agregarle los listener al comboBoxSala, y los checkBox de 3D o 2D 
        // ----------- Al cambiar de sala ----------
        JCBSala.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == ItemEvent.SELECTED){
                    asignarPrecioSegunSala();
                }
            }
        });
        // --------- checkbox 3D -------------
        JCBox3DSI.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == ItemEvent.SELECTED){
                    asignarPrecioSegunSala();
                }
            }
        });
        // -----------checkbox 2D -----------
        JCBox3DNO.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == ItemEvent.SELECTED){
                    asignarPrecioSegunSala();
                }
            }
        });
    }
    
    private boolean validarCampos() {
    
    // -------------ComboBox----------
    if (JCBPelicula.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una Película.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBPelicula.requestFocus();
        return false;
    }

    if (JCBSala.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una Sala.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBSala.requestFocus();
        return false;
    }

    if (JCBIdioma.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un Idioma.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBIdioma.requestFocus();
        return false;
    }

    // ------------Fecha----------
    if (JDCCalendario.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una Fecha de Proyección.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JDCCalendario.requestFocus();
        return false;
    }

    //-------------Horarios-------------
    if (JCBHoraInicio.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una Hora de Inicio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBHoraInicio.requestFocus();
        return false;
    }

    if (JTFHoraFin.getText().trim().isEmpty()) { 
        JOptionPane.showMessageDialog(this, "La hora de finalización no pudo ser calculada. Verifique la hora de inicio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBHoraInicio.requestFocus();
        return false;
    }

    // ------------campos de texto----------
    if (JTFLugaresDispo.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El precio del asiento no puede estar vacío. Verifique la Sala.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBSala.requestFocus();
        return false;
    }
    
    //--------------CheckBox-------
    if (!JCBox3DSI.isSelected() && !JCBox3DNO.isSelected()) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una opción para 'Es 3D'.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBox3DSI.requestFocus();
        return false;
    }
    
    if (!JCBoxSubSI.isSelected() && !JCBoxSubNO.isSelected()) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una opción para 'Subtitulada'.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        JCBoxSubSI.requestFocus();
        return false;
    }

    return true; 
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGroup3D = new javax.swing.ButtonGroup();
        BgroupSub = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jpGuardarProyeccion = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        JCBPelicula = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        JCBSala = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        JCBIdioma = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        JCBox3DSI = new javax.swing.JCheckBox();
        JCBox3DNO = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        JCBoxSubSI = new javax.swing.JCheckBox();
        JCBoxSubNO = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        JTFPrecioAsiento = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        JTFLugaresDispo = new javax.swing.JTextField();
        JDCCalendario = new com.toedter.calendar.JDateChooser();
        JCBHoraInicio = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        JTFID = new javax.swing.JTextField();
        JTFHoraFin = new javax.swing.JTextField();
        JBActualizar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Modificar Proyección");

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingrese los datos que desea modificar");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 102, 102), null, new java.awt.Color(153, 153, 153)));

        jpGuardarProyeccion.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(153, 153, 153)));
        jpGuardarProyeccion.setToolTipText("Guardar Proyección");

        jLabel3.setText("Pelicula:");

        jLabel4.setText("Sala:");

        jLabel5.setText("Idiomas:");

        jLabel6.setText("¿Es 3D?");

        BGroup3D.add(JCBox3DSI);
        JCBox3DSI.setText("SI");

        BGroup3D.add(JCBox3DNO);
        JCBox3DNO.setText("NO");

        jLabel7.setText("Hora Inicio:");

        jLabel8.setText("Hora Fin:");

        jLabel9.setText("Subtitulada:");

        BgroupSub.add(JCBoxSubSI);
        JCBoxSubSI.setText("SI");

        BgroupSub.add(JCBoxSubNO);
        JCBoxSubNO.setText("NO");

        jLabel10.setText("Precio Asiento:");

        jLabel11.setText("Lugares Disponibles: ");

        JDCCalendario.setDateFormatString("yyyy-MM-dd");
        JDCCalendario.setDoubleBuffered(false);
        JDCCalendario.setMaxSelectableDate(new java.util.Date(1767236399000L));
        JDCCalendario.setMinSelectableDate(new Date());

        JCBHoraInicio.setSelectedItem(-1);
        JCBHoraInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBHoraInicioActionPerformed(evt);
            }
        });

        jLabel12.setText("Fecha de Proyección:");

        jLabel2.setText("ID:");

        javax.swing.GroupLayout jpGuardarProyeccionLayout = new javax.swing.GroupLayout(jpGuardarProyeccion);
        jpGuardarProyeccion.setLayout(jpGuardarProyeccionLayout);
        jpGuardarProyeccionLayout.setHorizontalGroup(
            jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JTFLugaresDispo, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(JTFPrecioAsiento))
                        .addGap(31, 176, Short.MAX_VALUE))
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(60, 60, 60)
                                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                                        .addComponent(JCBoxSubSI)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JCBoxSubNO))
                                    .addComponent(JTFHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(147, Short.MAX_VALUE))))
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)))
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JCBSala, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JCBPelicula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JCBIdioma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                                .addComponent(JCBox3DSI)
                                .addGap(18, 18, 18)
                                .addComponent(JCBox3DNO))
                            .addComponent(JDCCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JCBHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JTFID, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 70, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpGuardarProyeccionLayout.setVerticalGroup(
            jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(JTFID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JCBPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(JCBSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(JCBIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(JCBox3DSI)
                    .addComponent(JCBox3DNO))
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel12))
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(JDCCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(JCBHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel8)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTFHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JCBoxSubSI)
                        .addComponent(JCBoxSubNO)))
                .addGap(28, 28, 28)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(JTFPrecioAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTFLugaresDispo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        JBActualizar.setText("Actualizar");
        JBActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(JBActualizar)
                .addGap(49, 49, 49))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpGuardarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jpGuardarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JBActualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JBActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBActualizarActionPerformed
        if(!validarCampos()){
            return;
        }
        try {
            
            LocalDate fecha = JDCCalendario.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String inicioStr = (String) JCBHoraInicio.getSelectedItem();
            String finStr = JTFHoraFin.getText();

            LocalTime inicio = LocalTime.parse(inicioStr);
            LocalTime fin = LocalTime.parse(finStr);

            LocalDateTime horaInicio = LocalDateTime.of(fecha, inicio);
            LocalDateTime horaFin = LocalDateTime.of(fecha, fin);

            Pelicula peli = (Pelicula) JCBPelicula.getSelectedItem();
            Sala sala = (Sala) JCBSala.getSelectedItem();
            String idioma = (String) JCBIdioma.getSelectedItem();
            int precioButaca = Integer.parseInt(JTFPrecioAsiento.getText());
            boolean es3D = JCBox3DSI.isSelected();
            boolean subtitulada = JCBoxSubSI.isSelected();
            int lugarDispo = Integer.parseInt(JTFLugaresDispo.getText());

            proyec.setPelicula(peli);
            proyec.setSala(sala);
            proyec.setIdioma(idioma);
            proyec.setPrecioLugar(precioButaca);
            proyec.setEs3D(es3D);
            proyec.setSubtitulada(subtitulada);
            proyec.setLugaresDisponibles(lugarDispo);
            proyec.setHoraInicio(horaInicio);
            proyec.setHoraFin(horaFin);

            proData.actualizarProyeccion(proyec);

            JOptionPane.showMessageDialog(this, "Proyección modificada correctamente, ");
            this.dispose();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID no válido: ");
            ex.getStackTrace();
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar la proyección: ");
            e.getStackTrace();
        }

    }//GEN-LAST:event_JBActualizarActionPerformed

    private void JCBHoraInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBHoraInicioActionPerformed
        actualizarHoraFin();
    }//GEN-LAST:event_JCBHoraInicioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGroup3D;
    private javax.swing.ButtonGroup BgroupSub;
    private javax.swing.JButton JBActualizar;
    private javax.swing.JComboBox<String> JCBHoraInicio;
    private javax.swing.JComboBox<String> JCBIdioma;
    private javax.swing.JComboBox<Pelicula> JCBPelicula;
    private javax.swing.JComboBox<Sala> JCBSala;
    private javax.swing.JCheckBox JCBox3DNO;
    private javax.swing.JCheckBox JCBox3DSI;
    private javax.swing.JCheckBox JCBoxSubNO;
    private javax.swing.JCheckBox JCBoxSubSI;
    private com.toedter.calendar.JDateChooser JDCCalendario;
    private javax.swing.JTextField JTFHoraFin;
    private javax.swing.JTextField JTFID;
    private javax.swing.JTextField JTFLugaresDispo;
    private javax.swing.JTextField JTFPrecioAsiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jpGuardarProyeccion;
    // End of variables declaration//GEN-END:variables
}
