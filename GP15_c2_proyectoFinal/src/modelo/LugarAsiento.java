package modelo;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class LugarAsiento {
    private int codLugar, numeroAsiento;
    private String fila;
    private boolean estado;
    private Proyeccion proyeccion;
    private int idProyeccion;
    
    public LugarAsiento() {}

    public LugarAsiento(int codLugar,String fila, int numeroAsiento, boolean estado, Proyeccion proyeccion) {
        this.codLugar = codLugar;
        this.numeroAsiento = numeroAsiento;
        this.fila = fila;
        this.estado = estado;
        this.proyeccion = proyeccion;
    }
    public LugarAsiento(String fila, int numeroAsiento, boolean estado, int idProyeccion) {
        
        this.numeroAsiento = numeroAsiento;
        this.fila = fila;
        this.estado = estado;
        this.idProyeccion = idProyeccion;
    }

    public int getCodLugar() {
        return codLugar;
    }

    public void setCodLugar(int codLugar) {
        this.codLugar = codLugar;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }

    

    @Override
    public String toString() {
        return "\nAsiento[" + "Código: " + codLugar + ", Fila: " + fila + ", Numero: " + numeroAsiento + ", Estado: " + estado + ']';
    }
    
    

}
