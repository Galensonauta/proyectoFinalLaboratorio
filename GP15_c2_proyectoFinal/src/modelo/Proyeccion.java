package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class Proyeccion {
    private int idProyeccion;
    private Pelicula pelicula;
    private Sala sala;
    private String idioma;
    private boolean subtitulada;
    private LocalDateTime horaInicio, horaFin;
    private int lugaresDisponibles;
    private boolean es3D;
    private int precioLugar;

    public Proyeccion() {}

    public Proyeccion(int idProyeccion, Pelicula pelicula, Sala sala, String idioma, boolean subtitulada, LocalDateTime horaInicio, LocalDateTime horaFin, int lugaresDisponibles, boolean es3D, int precioLugar) {
        this.idProyeccion = idProyeccion;
        this.pelicula = pelicula;
        this.sala = sala;
        this.idioma = idioma;
        this.subtitulada = subtitulada;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.lugaresDisponibles = lugaresDisponibles;
        this.es3D = es3D;
        this.precioLugar = precioLugar;
    }

    public Proyeccion(Pelicula pelicula, Sala sala, String idioma, boolean subtitulada, LocalDateTime horaInicio, LocalDateTime horaFin, int lugaresDisponibles, boolean es3D, int precioLugar) {
        this.pelicula = pelicula;
        this.sala = sala;
        this.idioma = idioma;
        this.subtitulada = subtitulada;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.lugaresDisponibles = lugaresDisponibles;
        this.es3D = es3D;
        this.precioLugar = precioLugar;
    }
    

    public int getIdProyeccion() {
        return idProyeccion;
    }

    public void setIdProyeccion(int idProyeccion) {
        this.idProyeccion = idProyeccion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public boolean isSubtitulada() {
        return subtitulada;
    }

    public void setSubtitulada(boolean subtitulada) {
        this.subtitulada = subtitulada;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getLugaresDisponibles() {
        return lugaresDisponibles;
    }

    public void setLugaresDisponibles(int lugaresDisponibles) {
        this.lugaresDisponibles = lugaresDisponibles;
    }

    public boolean isEs3D() {
        return es3D;
    }

    public void setEs3D(boolean es3D) {
        this.es3D = es3D;
    }

    public int getPrecioLugar() {
        return precioLugar;
    }

    public void setPrecioLugar(int precioLugar) {
        this.precioLugar = precioLugar;
    }
    
    
 

    @Override
    public String toString() {
        return "Pelicula: " + pelicula.getTitulo()+ "\nIdioma: "  + idioma + "\nSubtitulada: " + subtitulada +  "\nHora de Inicio: " + horaInicio + "\nHora de Finalización: " + horaFin + "\nSala: " + sala.getNroSala();
    }
    
    
    
    
    
    
    
    
 }
