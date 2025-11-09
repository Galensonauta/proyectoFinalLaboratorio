
package vistas;

import java.awt.HeadlessException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.*;
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
        listaPelis = peliData.listarTodasLasPeliculas();
        listaSalas = salaData.obtenerTodasLasSalas();
        listaProyeccion = proData.listarTodasLasProyecciones();
        cargarComboPelis();
        cargarComboSalas();
        cargarComboIdiomas();
        cargarHorarios();
        cabecera();
        cargarTabla();
    }

    private void cargarComboPelis() {
        for (Pelicula aux : listaPelis) {
            jComboBoxPelicula.addItem(aux);
        }
        jComboBoxPelicula.setSelectedIndex(-1);
    }

    private void cargarComboSalas() {
        for (Sala aux : listaSalas) {
            jComboBoxSala.addItem(aux);
        }
        jComboBoxSala.setSelectedIndex(-1);
    }

    private void cargarComboIdiomas() {
        String[] idiomas = {"Español", "Inglés", "Portugués", "Italiano", "Frances"};

        jComboBoxIdioma.removeAllItems();
        for (String aux : idiomas) {
            jComboBoxIdioma.addItem(aux);
        }
        jComboBoxIdioma.setSelectedIndex(-1);
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
        JCBHoraFin.removeAllItems();

        for (String aux : horarios) {
            JCBHoraInicio.addItem(aux);
            JCBHoraFin.addItem(aux);
        }

        JCBHoraInicio.setSelectedIndex(-1);
        JCBHoraFin.setSelectedIndex(-1);
    }

    private void actualizarHoraFin() {
        String horaInicioStr = (String) JCBHoraInicio.getSelectedItem();

        if (horaInicioStr == null) {
            JCBHoraFin.setSelectedIndex(-1);
            return;
        }

        try {
            LocalTime inicio = LocalTime.parse(horaInicioStr);
            LocalTime fin = inicio.plusHours(2);

            String horaFinStr = fin.format(DateTimeFormatter.ofPattern("HH:mm"));
            JCBHoraFin.setSelectedItem(horaFinStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al calcular hora de finalización" + e.getMessage());
            JCBHoraFin.setSelectedIndex(-1);
        }
    }
    
    private void limpiarCampos(){
        jComboBoxPelicula.setSelectedIndex(-1);
        jComboBoxSala.setSelectedIndex(-1);
        jComboBoxIdioma.setSelectedIndex(-1);
        BGes3D.clearSelection();
        JDCCalendario.setDate(null);
        JCBHoraInicio.setSelectedIndex(-1);
        JCBHoraFin.setSelectedIndex(-1);
        BGSubtitulada.clearSelection();
        jTextFieldPrecioAsiento.setText("");
        jTextFieldLugaresDispo.setText("");
    }
    private void asignarPrecioSegunSala() {

        final int PRECIO_2D = 5500;
        final int PRECIO_3D = 8000;

        Sala salaSeleccionada = (Sala) jComboBoxSala.getSelectedItem();

        if (salaSeleccionada != null) {

            if (salaSeleccionada.isApta3D()) {
                jTextFieldPrecioAsiento.setText(String.valueOf(PRECIO_3D));
                jCheckBox3DSI.setEnabled(true);
                jCheckBox3DNO.setEnabled(true);
            } else {
                jTextFieldPrecioAsiento.setText(String.valueOf(PRECIO_2D));
                jCheckBox3DSI.setSelected(false);
                jCheckBox3DNO.setSelected(true);
                jCheckBox3DSI.setEnabled(false);
            }

        } else {
            jTextFieldPrecioAsiento.setText("");
            jTextFieldLugaresDispo.setText("");
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

        BGes3D = new javax.swing.ButtonGroup();
        BGSubtitulada = new javax.swing.ButtonGroup();
        EscritorioProyeccion = new javax.swing.JDesktopPane();
        jLabel11 = new javax.swing.JLabel();
        titulo = new javax.swing.JLabel();
        jpGuardarProyeccion = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxPelicula = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxSala = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxIdioma = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox3DSI = new javax.swing.JCheckBox();
        jCheckBox3DNO = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jCheckBoxSubSI = new javax.swing.JCheckBox();
        jCheckBoxSubNO = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldPrecioAsiento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldLugaresDispo = new javax.swing.JTextField();
        JDCCalendario = new com.toedter.calendar.JDateChooser();
        JCBHoraInicio = new javax.swing.JComboBox<>();
        JCBHoraFin = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jpBuscarProyeccion = new javax.swing.JPanel();
        jLabelID = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        JBBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProyecciones = new javax.swing.JTable();
        JBModificar = new javax.swing.JButton();
        JBEliminar = new javax.swing.JButton();
        JBLimpiar = new javax.swing.JButton();
        JBGuardar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Gestión de Proyección");

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

        BGSubtitulada.add(jCheckBoxSubSI);
        jCheckBoxSubSI.setText("SI");

        BGSubtitulada.add(jCheckBoxSubNO);
        jCheckBoxSubNO.setText("NO");

        jLabel8.setText("Precio Butaca:");

        jLabel9.setText("Lugares Disponibles: ");

        JDCCalendario.setDateFormatString("yyyy-MM-dd");
        JDCCalendario.setDoubleBuffered(false);
        JDCCalendario.setMaxSelectableDate(new java.util.Date(1767236399000L));
        JDCCalendario.setMinSelectableDate(Date.valueOf(LocalDate.now(ZoneId.systemDefault())));

        JCBHoraInicio.setSelectedItem(-1);
        JCBHoraInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBHoraInicioActionPerformed(evt);
            }
        });

        JCBHoraFin.setSelectedItem(-1);

        jLabel12.setText("Fecha de Proyección:");

        javax.swing.GroupLayout jpGuardarProyeccionLayout = new javax.swing.GroupLayout(jpGuardarProyeccion);
        jpGuardarProyeccion.setLayout(jpGuardarProyeccionLayout);
        jpGuardarProyeccionLayout.setHorizontalGroup(
            jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)))
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxSala, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxPelicula, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxIdioma, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpGuardarProyeccionLayout.createSequentialGroup()
                                .addComponent(jCheckBox3DSI)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox3DNO))
                            .addComponent(JDCCalendario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JCBHoraInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JCBHoraFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 70, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldLugaresDispo, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(jTextFieldPrecioAsiento))
                        .addGap(31, 176, Short.MAX_VALUE))
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(60, 60, 60)
                        .addComponent(jCheckBoxSubSI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxSubNO)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jpGuardarProyeccionLayout.setVerticalGroup(
            jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCheckBox3DSI)
                    .addComponent(jCheckBox3DNO))
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel12))
                    .addGroup(jpGuardarProyeccionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(JDCCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(JCBHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JCBHoraFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBoxSubSI)
                        .addComponent(jCheckBoxSubNO)))
                .addGap(28, 28, 28)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldPrecioAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpGuardarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLugaresDispo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        jpBuscarProyeccion.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(153, 153, 153)));
        jpBuscarProyeccion.setToolTipText("");

        jLabelID.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabelID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelID.setText("ID: ");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBuscarProyeccionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JBLimpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JBModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JBEliminar)
                .addContainerGap())
            .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                .addGap(329, 329, 329)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1036, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                        .addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JBBuscar)))
                .addGap(0, 49, Short.MAX_VALUE))
        );
        jpBuscarProyeccionLayout.setVerticalGroup(
            jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarProyeccionLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jpBuscarProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBBuscar)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGap(23, 23, 23)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jpGuardarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(JBGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpBuscarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EscritorioProyeccionLayout.setVerticalGroup(
            EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                .addGroup(EscritorioProyeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                        .addComponent(titulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpGuardarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(JBGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EscritorioProyeccionLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jpBuscarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(EscritorioProyeccion)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JCBHoraInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBHoraInicioActionPerformed
        actualizarHoraFin();
    }//GEN-LAST:event_JCBHoraInicioActionPerformed

    private void JBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBGuardarActionPerformed
        try {
            LocalDate fecha = JDCCalendario.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String horaInicioStr = JCBHoraInicio.getSelectedItem() + "";
            String horaFinStr = JCBHoraFin.getSelectedItem() + "";

            if (horaInicioStr == null || horaFinStr == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un horario de inicio y fin válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            LocalTime inicio = LocalTime.parse(horaInicioStr);
            LocalTime fin = LocalTime.parse(horaFinStr);

            LocalDateTime horaInicio = LocalDateTime.of(fecha, inicio);
            LocalDateTime horaFin = LocalDateTime.of(fecha, fin);

            Pelicula peli = (Pelicula) jComboBoxPelicula.getSelectedItem();
            Sala salaa = (Sala) jComboBoxSala.getSelectedItem();
            String idioma = jComboBoxIdioma.getSelectedItem() + "";
            boolean es3D = jCheckBox3DSI.isSelected();

            if (!jCheckBox3DSI.isSelected() & !jCheckBox3DNO.isSelected()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una opción para el campo 'es3D'");
                jCheckBox3DSI.requestFocus();
            }

            int precioButaca = Integer.parseInt(jTextFieldPrecioAsiento.getText());
            boolean subtitulada = jCheckBoxSubSI.isSelected();
            int lugarDispo = Integer.parseInt(jTextFieldLugaresDispo.getText());

            proyeccion = new Proyeccion(peli, salaa, idioma, subtitulada, horaInicio, horaFin, lugarDispo, es3D, precioButaca);
            proData.guardarProyeccion(proyeccion);

            JOptionPane.showMessageDialog(this, "Proyección guardada correctamente!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al tratar de convertir Horarios");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en boton Guardar" + e.getMessage());
        }
        cargarTabla();
        limpiarCampos();
    }//GEN-LAST:event_JBGuardarActionPerformed

    private void JBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBBuscarActionPerformed
        modeloTabla.setRowCount(0);
        try {
            int id = Integer.valueOf(jTextFieldID.getText());
            Proyeccion aux = proData.buscarProyeccionPorID(id);

            if (aux != null) {

                String tituloPeli;
                if (aux.getPelicula() != null) {
                    tituloPeli = aux.getPelicula().getTitulo();
                } else {
                    tituloPeli = "NULL";
                }

                int nroSala = 0;
                if (aux.getSala() != null) {
                    aux.getSala().getNroSala();
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

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna proyección con el ID ingresado");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número de ID válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        }
        jTextFieldID.setText("");
    }//GEN-LAST:event_JBBuscarActionPerformed

    private void JBLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBLimpiarActionPerformed
        modeloTabla.setRowCount(0);
        cargarTabla();
    }//GEN-LAST:event_JBLimpiarActionPerformed

    private void JBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBModificarActionPerformed
        int filaSelec = jTableProyecciones.getSelectedRow();

        if (filaSelec == -1) {
            JOptionPane.showMessageDialog(this, "Debe Seleccionar una Proyección de la Tabla para modificar.");
            return;
        }

        try {
            //Object idObj = modeloTabla.getValueAt(filaSelec, 0);
            //int id = Integer.parseInt(idObj.toString());
            int id = (Integer) modeloTabla.getValueAt(filaSelec, 0);
            Proyeccion p = proData.buscarProyeccionPorID(id); //la proyeccion traída de la base de datos

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Error, no se pudo cargar la proyección");
            }else {
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
            EscritorioProyeccion.add(mpv);
            mpv.setVisible(true);
            mpv.moveToFront();
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "El ID de la tabla no es numérico", "ERROR", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error al abrir nueva ventana. " + e.getMessage());
        }


    }//GEN-LAST:event_JBModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGSubtitulada;
    private javax.swing.ButtonGroup BGes3D;
    private javax.swing.JDesktopPane EscritorioProyeccion;
    private javax.swing.JButton JBBuscar;
    private javax.swing.JButton JBEliminar;
    private javax.swing.JButton JBGuardar;
    private javax.swing.JButton JBLimpiar;
    private javax.swing.JButton JBModificar;
    private javax.swing.JComboBox<String> JCBHoraFin;
    private javax.swing.JComboBox<String> JCBHoraInicio;
    private com.toedter.calendar.JDateChooser JDCCalendario;
    private javax.swing.JCheckBox jCheckBox3DNO;
    private javax.swing.JCheckBox jCheckBox3DSI;
    private javax.swing.JCheckBox jCheckBoxSubNO;
    private javax.swing.JCheckBox jCheckBoxSubSI;
    private javax.swing.JComboBox<String> jComboBoxIdioma;
    private javax.swing.JComboBox<Pelicula> jComboBoxPelicula;
    private javax.swing.JComboBox<Sala> jComboBoxSala;
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
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldLugaresDispo;
    private javax.swing.JTextField jTextFieldPrecioAsiento;
    private javax.swing.JPanel jpBuscarProyeccion;
    private javax.swing.JPanel jpGuardarProyeccion;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
