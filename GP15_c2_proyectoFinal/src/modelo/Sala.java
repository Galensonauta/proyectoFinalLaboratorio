/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author y
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
    
}
