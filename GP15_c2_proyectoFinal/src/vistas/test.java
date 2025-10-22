/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import modelo.Sala;
import persistencia.SalaData;

/**
 *
 * @author y
 */
public class test {
public static void main(String[] args){
    SalaData salaData = new SalaData();
    /*
    Sala s = new Sala(1, true,170,true);
    salaData.guardarSala(s);
    salaData.actualizarSala(1, true, 175, true);    
*/    
    salaData.borrarSala(1);

    
}
        }
