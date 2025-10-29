package modelo;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo Fornes, Santiago Girardi)
 */
public class Sala {
    private int nroSala;
    private boolean apta3D;
    private int capacidad;
    private boolean estado;

    public Sala() {
    }

    public Sala(int nroSala, boolean apta3D, int capacidad, boolean estado) {
        this.nroSala = nroSala;
        this.apta3D = apta3D;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public Sala(Sala sala) {
        this.nroSala = sala.getNroSala();
        this.apta3D = sala.getApta3D();
        this.capacidad = sala.getCapacidad();
        this.estado = sala.isEstado();
    }

    public int getNroSala() {
        return nroSala;
    }

    public void setNroSala(int nroSala) {
        this.nroSala = nroSala;
    }

    public boolean getApta3D() {
        return apta3D;
    }

    public void setApta3D(boolean apta3D) {
        this.apta3D = apta3D;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Sala[" + "Nro. Sala: " + nroSala + ", Apta 3D: " + apta3D + ", Capacidad: " + capacidad +']';
    }
    
    
    
    
}
