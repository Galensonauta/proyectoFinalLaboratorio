
package vistas;

import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import persistencia.DetalleTicketData;
import persistencia.LugarAsientoData;
import persistencia.PeliculaData;
import persistencia.ProyeccionData;
import persistencia.SalaData;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class VistaProyeccion extends javax.swing.JInternalFrame {

    private Proyeccion proyeccion;
    private Pelicula pelicula;
    private Sala sala;
    private ProyeccionData proData;
    private PeliculaData peliData;
    private SalaData salaData;
    private DetalleTicketData dtData;
    private LugarAsientoData butacaData;
    private DefaultTableModel modeloTabla;
    private List<Pelicula> listaPelis;
    private List<Sala> listaSalas;
    private List<Proyeccion> listaProyeccion;
    private DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter fechaFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public VistaProyeccion() {
        initComponents();
        proData = new ProyeccionData();
        peliData = new PeliculaData();
        salaData = new SalaData();
        proyeccion = new Proyeccion();
        dtData = new DetalleTicketData();
        butacaData = new LugarAsientoData();
        listaPelis = peliData.listarTodasLasPeliculas();
        listaSalas = salaData.obtenerTodasLasSalas();
        listaProyeccion = proData.listarTodasLasProyecciones();
        cargarComboPelis();
        cargarComboSalas();
        cargarComboIdiomas();
        cargarHorarios();
        cabecera();
        cargarTabla();
        conectarEventos();
        
        
    }

    private void cargarComboPelis() {
        for (Pelicula aux : listaPelis) {
            jCBPelicula.addItem(aux);
        }
        jCBPelicula.setSelectedIndex(-1);
    }

    private void cargarComboSalas() {
        for (Sala aux : listaSalas) {
            jCBSala.addItem(aux);
        }
        jCBSala.setSelectedIndex(-1);
    }

    private void cargarComboIdiomas() {
        String[] idiomas = {"Español", "Inglés", "Portugués"};

        jCBIdioma.removeAllItems();
        for (String aux : idiomas) {
            jCBIdioma.addItem(aux);
        }
        jCBIdioma.setSelectedIndex(-1);
    }

    private void cabecera() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID Proyección");
        modeloTabla.addColumn("Pelicula");
        modeloTabla.addColumn("Sala");
        modeloTabla.addColumn("¿Es 3D?");
        modeloTabla.addColumn("Idioma");
        modeloTabla.addColumn("Fecha de Proyección");
        modeloTabla.addColumn("Hora Inicio");
        modeloTabla.addColumn("Hora Fin");
        modeloTabla.addColumn("¿Subtitulada?");
        modeloTabla.addColumn("Precio Butaca");
        modeloTabla.addColumn("Butacas disponibles");

        jTableProyecciones.setModel(modeloTabla);
    }

    private void cargarTabla() {
        listaProyeccion = proData.listarTodasLasProyecciones();
        modeloTabla.setRowCount(0);
        modeloTabla.fireTableDataChanged();

        for (Proyeccion aux : listaProyeccion) {
            String tituloPeli;
            int nroSala;

            if (aux.getPelicula() != null) {
                tituloPeli = aux.getPelicula().getTitulo();
            } else {
                tituloPeli = "Vacio";
            }

            if (aux.getSala() != null) {
                nroSala = aux.getSala().getNroSala();
            } else {
                nroSala = 0;
            }

            String es3d;
            if (aux.isEs3D()) {
                es3d = "Es 3D";
            } else {
                es3d = "Es 2D";
            }

            String sub;
            if (aux.isSubtitulada()) {
                sub = "SI";
            } else {
                sub = "NO";
            }
            modeloTabla.addRow(new Object[]{
                aux.getIdProyeccion(),
                tituloPeli,
                nroSala,
                es3d,
                aux.getIdioma(),
                aux.getHoraInicio().toLocalDate().format(fechaFormat),
                aux.getHoraInicio().toLocalTime().format(horaFormat),
                aux.getHoraFin().toLocalTime().format(horaFormat),
                sub,
                aux.getPrecioLugar(),
                aux.getLugaresDisponibles()

            });
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
        JTFHoraFin.setEditable(false);
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
    
    private void limpiarCampos(){
        jCBPelicula.setSelectedIndex(-1);
        jCBSala.setSelectedIndex(-1);
        jCBIdioma.setSelectedIndex(-1);
        BGes3D.clearSelection();
        JDCCalendario.setDate(null);
        JCBHoraInicio.setSelectedIndex(-1);
        JTFHoraFin.setText("");
        BGSubtitulada.clearSelection();
        JTFPrecioAsiento.setText("");
        JTFLugaresDispo.setText("");
    }
   
    private void asignarPrecioSegunSala() {
        JTFPrecioAsiento.setEditable(false);
        JTFLugaresDispo.setEditable(false);
        
        final int PRECIO_2D = 5500;
        final int PRECIO_3D = 8000;

        Sala salaSeleccionada = (Sala) jCBSala.getSelectedItem();
        if (salaSeleccionada != null) {
            //logica para lugares disponibles obteniendo la capacidad de la sala
            int capacidad = salaSeleccionada.getCapacidad();
            JTFLugaresDispo.setText(String.valueOf(capacidad));
            
            //-------------------------------------------------------------------

            if (salaSeleccionada.isApta3D()) {
                JTFPrecioAsiento.setText(String.valueOf(PRECIO_3D));
                jCheckBox3DSI.setEnabled(true);
                jCheckBox3DNO.setEnabled(true);
                
                if (jCheckBox3DNO.isSelected()){
                    JTFPrecioAsiento.setText(String.valueOf(PRECIO_2D));
                }
            } else {
                JTFPrecioAsiento.setText(String.valueOf(PRECIO_2D));
                jCheckBox3DSI.setSelected(false);
                jCheckBox3DNO.setSelected(true);
                jCheckBox3DSI.setEnabled(false);
            }

        } else {
            JTFPrecioAsiento.setText("");
            JTFLugaresDispo.setText("");
            jCheckBox3DSI.setEnabled(true);
        }
    }
    
    private void conectarEventos() {
        //este metodo es para agregarle los listener al comboBoxSala, y los checkBox de 3D o 2D 
        // ----------- Al cambiar de sala ----------
        jCBSala.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    asignarPrecioSegunSala();
                }
            }
        });
        // --------- checkbox 3D -------------
        jCheckBox3DSI.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    asignarPrecioSegunSala();
                }
            }
        });
        // -----------checkbox 2D -----------
        jCheckBox3DNO.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    asignarPrecioSegunSala();
                }
            }
        });
        //-------------- ID --------------
        JTFID.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFIDKeyTyped(evt);
            }
        });

    }

    private boolean validarCampos() {

        // -------------ComboBox----------
        if (jCBPelicula.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Película.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jCBPelicula.requestFocus();
            return false;
        }

        if (jCBSala.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Sala.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jCBSala.requestFocus();
            return false;
        }

        if (jCBIdioma.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Idioma.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jCBIdioma.requestFocus();
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
            jCBSala.requestFocus();
            return false;
        }

        //--------------CheckBox-------
        if (!jCheckBox3DSI.isSelected() && !jCheckBox3DNO.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una opción para 'Es 3D'.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jCheckBox3DSI.requestFocus();
            return false;
        }

        if (!jCBSubSI.isSelected() && !jCBSubNO.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una opción para 'Subtitulada'.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jCBSubSI.requestFocus();
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

        BGes3D = new javax.swing.ButtonGroup();
        BGSubtitulada = new javax.swing.ButtonGroup();
        EscritorioProyeccion = new javax.swing.JDesktopPane();
        jLabel11 = new javax.swing.JLabel();
        titulo = new javax.swing.JLabel();
        jpGuardarProyeccion = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jCBPelicula = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jCBSala = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox3DSI = new javax.swing.JCheckBox();
        jCheckBox3DNO = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jCBSubSI = new javax.swing.JCheckBox();
        jCBSubNO = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        JTFPrecioAsiento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        JTFLugaresDispo = new javax.swing.JTextField();
        JDCCalendario = new com.toedter.calendar.JDateChooser();
        JCBHoraInicio = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jCBIdioma = new javax.swing.JComboBox<>();
        JTFHoraFin = new javax.swing.JTextField();
        jpBuscarProyeccion = new javax.swing.JPanel();
        jLabelID = new javax.swing.JLabel();
        JTFID = new javax.swing.JTextField();
        JBBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProyecciones = new javax.swing.JTable();
        JBModificar = new javax.swing.JButton();
        JBEliminar = new javax.swing.JButton();
        JBLimpiar = new javax.swing.JButton();
        JBGuardar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Proyección");
        setAutoscrolls(true);

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Guardar Proyección");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204)));

        titulo.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Proyección");
        titulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        titulo.setFocusable(false);
        titulo.setInheritsPopupMenu(false);

        jpGuardarProyeccion.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(153, 153, 153)));
        jpGuardarProyeccion.setToolTipText("Guardar Proyección");

        jLabel1.setText("Pelicula:");

        jLabel2.setText("Sala:");

        jLabel3.setText("Idiomas:");

        jLabel4.setText("¿Es 3D?");

        BGes3D.add(jCheckBox3DSI);
        jCheckBox3DSI.setText("SI");

        BGes3D.add(jCheckBox3DNO);
        jCheckBox3DNO.setText("NO");

        jLabel5.setText("Hora Inicio:");

        jLabel6.setText("Hora Fin:");

        jLabel7.setText("Subtitulada:");

        BGSubtitulada.add(jCBSubSI);
        jCBSubSI.setText("SI");

        BGSubtitulada.add(jCBSubNO);
        jCBSubNO.setText("NO");

        jLabel8.setText("Precio Butaca:");

        jLabel9.setText("Lugares Disponibles: ");

        JDCCalendario.setDateFormatString("yyyy-MM-dd");
        JDCCalendario.setDoubleBuffered(false);
        JDCCalendario.setMaxSelectableDate(new java.util.Date(1767236399000L));
        JDCCalendario.setMinSelectableDate(java.util.Date.from(java.time.LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));

        JCBHoraInicio.setSelectedItem(-1);
        JCBHoraInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBHoraInicioActionPerformed(evt);
            }
        });

        jLabel12.setText("Fecha de Proyección:");

        javax.swing.GroupLayout jpGuardarProyeccionLayout = new javax.swing.GroupLayout(jpGuardarProyeccion);
        jpGuardarProyeccion.setLayout(jpGuardarProyeccionLayout);
        jpGuardarProyeccionLayout.setHorizontalGroup(
            jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCBSala, 0, 230, Short.MAX_VALUE)
                            .addComponent(jCBPelicula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createSequentialGroup()
                        .addContainerGap(81, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3DSI)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox3DNO)
                        .addGap(128, 128, 128)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCBIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(JTFHoraFin, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(JCBHoraInicio, javax.swing.GroupLayout.Alignment.LEADING, 0, 122, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createSequentialGroup()
                                .addComponent(JTFLugaresDispo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82))
                            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                                .addComponent(jCBSubSI)
                                .addGap(18, 18, 18)
                                .addComponent(jCBSubNO)
                                .addContainerGap())))))
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addGap(12, 12, 12)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTFPrecioAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JDCCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpGuardarProyeccionLayout.setVerticalGroup(
            jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jCBIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(JCBHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(JTFHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jCBPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jCBSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(JDCCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jCBSubSI)
                            .addComponent(jCBSubNO))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jCheckBox3DSI)
                            .addComponent(jCheckBox3DNO))
                        .addGap(21, 21, 21)))
                .addGap(2, 2, 2)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(JTFPrecioAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(JTFLugaresDispo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jpBuscarProyeccion.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(153, 153, 153)));
        jpBuscarProyeccion.setToolTipText("");

        jLabelID.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabelID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelID.setText("ID: ");

        JTFID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFIDKeyTyped(evt);
            }
        });

        JBBuscar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        JBBuscar.setText("Buscar");
        JBBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBBuscarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel10.setText("Buscar/ Modificar Proyección");

        jTableProyecciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableProyecciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jTableProyecciones);

        JBModificar.setText("Modificar");
        JBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBModificarActionPerformed(evt);
            }
        });

        JBEliminar.setText("Eliminar");
        JBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBEliminarActionPerformed(evt);
            }
        });

        JBLimpiar.setText("Limpiar");
        JBLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBuscarProyeccionLayout = new javax.swing.GroupLayout(jpBuscarProyeccion);
        jpBuscarProyeccion.setLayout(jpBuscarProyeccionLayout);
        jpBuscarProyeccionLayout.setHorizontalGroup(
            jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                        .addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JTFID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JBBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                                .addComponent(JBLimpiar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JBModificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JBEliminar))
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
            .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                .addGap(428, 428, 428)
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpBuscarProyeccionLayout.setVerticalGroup(
            jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel10)
                .addGap(13, 13, 13)
                .addGroup(jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBBuscar)
                    .addComponent(JTFID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JBLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JBEliminar)
                        .addComponent(JBModificar)))
                .addContainerGap())
        );

        JBGuardar.setText("Guardar");
        JBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBGuardarActionPerformed(evt);
            }
        });

        EscritorioProyeccion.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        EscritorioProyeccion.setLayer(titulo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        EscritorioProyeccion.setLayer(jpGuardarProyeccion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        EscritorioProyeccion.setLayer(jpBuscarProyeccion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        EscritorioProyeccion.setLayer(JBGuardar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout EscritorioProyeccionLayout = new javax.swing.GroupLayout(EscritorioProyeccion);
        EscritorioProyeccion.setLayout(EscritorioProyeccionLayout);
        EscritorioProyeccionLayout.setHorizontalGroup(
            EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                .addGroup(EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpBuscarProyeccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jpGuardarProyeccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(JBGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 226, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        EscritorioProyeccionLayout.setVerticalGroup(
            EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                .addGroup(EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JBGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                        .addComponent(titulo)
                        .addGap(18, 18, 18)
                        .addComponent(jpBuscarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpGuardarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jpBuscarProyeccion.getAccessibleContext().setAccessibleName("Buscar/Modificar Proyección");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EscritorioProyeccion)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EscritorioProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JCBHoraInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBHoraInicioActionPerformed
        actualizarHoraFin();
    }//GEN-LAST:event_JCBHoraInicioActionPerformed

    private void JBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBGuardarActionPerformed
        if (!validarCampos()) {
            return;
        }
        try {
            LocalDate fecha = JDCCalendario.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String horaInicioStr = (String) JCBHoraInicio.getSelectedItem();
            String horaFinStr = JTFHoraFin.getText();

            LocalTime inicio = LocalTime.parse(horaInicioStr);
            LocalTime fin = LocalTime.parse(horaFinStr);

            LocalDateTime horaInicio = LocalDateTime.of(fecha, inicio);
            LocalDateTime horaFin = LocalDateTime.of(fecha, fin);

            Pelicula peli = (Pelicula) jCBPelicula.getSelectedItem();
            Sala salaa = (Sala) jCBSala.getSelectedItem();
            String idioma = (String) jCBIdioma.getSelectedItem();
            int precioButaca = Integer.parseInt(JTFPrecioAsiento.getText());
            int lugarDispo = Integer.parseInt(JTFLugaresDispo.getText());
            boolean es3D = jCheckBox3DSI.isSelected();
            boolean subtitulada = jCBSubSI.isSelected();

            proyeccion = new Proyeccion(peli, salaa, idioma, subtitulada, horaInicio, horaFin, lugarDispo, es3D, precioButaca);
            proData.guardarProyeccion(proyeccion);

            JOptionPane.showMessageDialog(this, "Proyección guardada correctamente!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al tratar de convertir Horarios");
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error en boton Guardar: " + e.getMessage());
        }

        cargarTabla();
        limpiarCampos();
    }//GEN-LAST:event_JBGuardarActionPerformed

    private void JBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBBuscarActionPerformed
        modeloTabla.setRowCount(0);

        String idStr = JTFID.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID para buscar");
            JTFID.requestFocus();
            return;
        }

        try {
            int id = Integer.valueOf(idStr);
            Proyeccion aux = proData.buscarProyeccionPorID(id);

            if (aux != null) {

                String tituloPeli;
                if (aux.getPelicula() != null) {
                    tituloPeli = aux.getPelicula().getTitulo();
                } else {
                    tituloPeli = "NULL";
                }

                int nroSala;
                if (aux.getSala() != null) {
                    nroSala = aux.getSala().getNroSala();
                } else {
                    nroSala = 0;
                }

                String fechaProyeccion = aux.getHoraInicio().toLocalDate().format(fechaFormat);
                String horaInicio = aux.getHoraInicio().toLocalTime().format(horaFormat);
                String horaFin = aux.getHoraFin().toLocalTime().format(horaFormat);

                String es3d;
                if (aux.isEs3D()) {
                    es3d = "Es 3D";
                } else {
                    es3d = "Es 2D";
                }

                String sub;
                if (aux.isSubtitulada()) {
                    sub = "SI";
                } else {
                    sub = "NO";
                }

                modeloTabla.addRow(new Object[]{
                    aux.getIdProyeccion(),
                    tituloPeli,
                    nroSala,
                    es3d,
                    fechaProyeccion,
                    horaInicio,
                    horaFin,
                    sub,
                    aux.getPrecioLugar(),
                    aux.getLugaresDisponibles()
                });

                jTableProyecciones.setRowSelectionInterval(0, 0);

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna proyección con el ID ingresado");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número de ID válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        }
        JTFID.setText("");
    }//GEN-LAST:event_JBBuscarActionPerformed

    private void JBLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBLimpiarActionPerformed
        limpiarCampos();
        modeloTabla.setRowCount(0);
        cargarTabla();
        
    }//GEN-LAST:event_JBLimpiarActionPerformed

    private void JBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBModificarActionPerformed
        int filaSelec = jTableProyecciones.getSelectedRow();

        if (filaSelec == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Proyección de la tabla para modificar.");
            return;
        }

        try {
            Object idObj = modeloTabla.getValueAt(filaSelec, 0);
            int id = Integer.parseInt(idObj.toString());
            //int id = (Integer) modeloTabla.getValueAt(filaSelec, 0);
            Proyeccion p = proData.buscarProyeccionPorID(id); //la proyeccion traída de la base de datos

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Error, no se pudo cargar la proyección");
            } else {
                Proyeccion proSinModificar = new Proyeccion( //esta va a ser la copia que se va a modificar en la ventana hija. 
                        p.getIdProyeccion(),
                        p.getPelicula(),
                        p.getSala(),
                        p.getIdioma(),
                        p.isSubtitulada(),
                        p.getHoraInicio(),
                        p.getHoraFin(),
                        p.getLugaresDisponibles(),
                        p.isEs3D(),
                        p.getPrecioLugar());

                ModificarProyeccionVista mpv = new ModificarProyeccionVista(proSinModificar, proData, peliData, salaData);

                mpv.addInternalFrameListener(new InternalFrameAdapter() {
                
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    System.out.println("Ventana de modificación cerrada, recargando tabla..."); 
                
                    cargarTabla(); 
                }
            });

                EscritorioProyeccion.add(mpv);
                mpv.setVisible(true);
                mpv.moveToFront();

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID de la tabla no es numérico", "ERROR", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error al abrir nueva ventana. " + e.getMessage());
        }


    }//GEN-LAST:event_JBModificarActionPerformed

    private void JBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBEliminarActionPerformed
        int filaSelec = jTableProyecciones.getSelectedRow();
        
        if(filaSelec == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Proyección de la tabla para eliminar.");
            return;
        }
        int id = Integer.parseInt(modeloTabla.getValueAt(filaSelec, 0).toString());
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la proyeccion seleccionada con el ID: "+ id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION){
            proData.eliminarProyeccion(id, dtData, butacaData);
            JOptionPane.showMessageDialog(this, "Poryección eliminada correctamente!");
            cargarTabla();
        }
    }//GEN-LAST:event_JBEliminarActionPerformed

    private void JTFIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFIDKeyTyped
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE){
            evt.consume();
            return;
        }
        
        if(JTFID.getText().length() >= 3 && c != java.awt.event.KeyEvent.VK_BACK_SPACE){
            evt.consume();
        }
    }//GEN-LAST:event_JTFIDKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGSubtitulada;
    private javax.swing.ButtonGroup BGes3D;
    private javax.swing.JDesktopPane EscritorioProyeccion;
    private javax.swing.JButton JBBuscar;
    private javax.swing.JButton JBEliminar;
    private javax.swing.JButton JBGuardar;
    private javax.swing.JButton JBLimpiar;
    private javax.swing.JButton JBModificar;
    private javax.swing.JComboBox<String> JCBHoraInicio;
    private com.toedter.calendar.JDateChooser JDCCalendario;
    private javax.swing.JTextField JTFHoraFin;
    private javax.swing.JTextField JTFID;
    private javax.swing.JTextField JTFLugaresDispo;
    private javax.swing.JTextField JTFPrecioAsiento;
    private javax.swing.JComboBox<String> jCBIdioma;
    private javax.swing.JComboBox<Pelicula> jCBPelicula;
    private javax.swing.JComboBox<Sala> jCBSala;
    private javax.swing.JCheckBox jCBSubNO;
    private javax.swing.JCheckBox jCBSubSI;
    private javax.swing.JCheckBox jCheckBox3DNO;
    private javax.swing.JCheckBox jCheckBox3DSI;
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
    private javax.swing.JLabel jLabelID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProyecciones;
    private javax.swing.JPanel jpBuscarProyeccion;
    private javax.swing.JPanel jpGuardarProyeccion;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
