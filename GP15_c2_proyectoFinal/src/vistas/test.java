package vistas;

import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import modelo.Comprador;
import modelo.LugarAsiento;
import modelo.Pelicula;
import modelo.Proyeccion;
import modelo.Sala;
import persistencia.CompradorData;
import persistencia.LugarAsientoData;
import persistencia.PeliculaData;
import persistencia.ProyeccionData;
import persistencia.SalaData;

/**
 *
 * @author Grupo 15 (Evelyn Cetera, Tomas Puw Zirulnik, Matias Correa, Enzo
 * Fornes, Santiago Girardi)
 */
public class test {

    public static void main(String[] args) {

        /*
        SalaData salaData = new SalaData();

    Sala s = new Sala(1, true,170,true);
    salaData.guardarSala(s);
    salaData.buscarSala(1); 
    salaData.actualizarSala(1, true, 175, true);    
    salaData.borrarSala(1);
    salaData.altaSala(1);
    salaData.bajaSala(1);
    
    CompradorData cd1 = new CompradorData();
    Comprador c1 = new Comprador(123456789, "Matias Correa", "12345", "Debito",LocalDate.of(1990, 07, 23));
    Comprador c2 = new Comprador(123456788, "Enzo Fornes", "12345", "Debito",LocalDate.of(1990, 07, 23));
    Comprador c3 = new Comprador(123456787, "Santiago Girardi Correa", "12345", "Debito",LocalDate.of(1990, 07, 23));
    Comprador c4 = new Comprador(123456786, "Evelyn Cetera", "12345", "Debito",LocalDate.of(1990, 07, 23));
    Comprador c5 = new Comprador(123456785, "Tomás Puw", "12345", "Debito",LocalDate.of(1990, 07, 23));
    cd1.guardarComprador(c1);
    cd1.guardarComprador(c2);
    cd1.guardarComprador(c3);
    cd1.guardarComprador(c4);
    cd1.guardarComprador(c5);
    
        CompradorData compradorData = new CompradorData();

        compradorData.actualizarComprador(123456789, 123456789, "Matias Correa", "12345677","efectivo" , LocalDate.of(1990, 07, 23));
    Comprador test = new Comprador(1234567812, "test comprador", "12345", "Debito",LocalDate.of(1990, 07, 23));
    compradorData.guardarComprador(test);
    compradorData.borrarComprador(1234567812);
        compradorData.buscarComprador(123456787);

         */
        
        
        
        
    
//----------------PRUEBAS CLASES  PELICULADATA, PROYECCIONDATA, LUGARASIENTODATA----------------------------
        SalaData salaData = new SalaData();
        CompradorData cd1 = new CompradorData();
        ProyeccionData proyecData = new ProyeccionData();
        PeliculaData pd = new PeliculaData();
        LugarAsientoData lad = new LugarAsientoData();
        
System.out.println("\n------------------------PELICULA------------------\n");
        
        String tituloStr1 = "Pelicula de prueba 111111";
        String tituloStr2 = "Pelicula de prueba 222222";
        String tituloNuevo = "Nuevo Titulo";
    
        
        /*
    Pelicula peliTest1 = new Pelicula(tituloStr1, "Juanito", "Varios", "AR", "Terror", LocalDate.of(2025, Month.MARCH, 20), true);
    Pelicula peliTest2 = new Pelicula(tituloStr2, "Pepito", "Varios", "USA", "Terror", LocalDate.of(2025, Month.DECEMBER, 15), false);
   
    pd.guardarPelicula(peliTest1);
    pd.guardarPelicula(peliTest2);
         */
        
        
    /*
        Pelicula encontrada1 = pd.obtenerPeliculaPorTitulo(tituloStr1);
        Pelicula encontrada2 = pd.obtenerPeliculaPorTitulo(tituloStr2);//buscamos la peli

        if (encontrada1 != null) {
            System.out.println("La peli encontrada es: " + encontrada1.getTitulo());
        }
        if (encontrada1 != null) { //la modificamos
            String tituloViejo = encontrada1.getTitulo();
            encontrada1.setGenero("Accion");
            pd.modificarPelicula(encontrada1, tituloViejo);
        }
        //baja 
        pd.eliminarPelicula(tituloStr1);

        Sala sala1 = salaData.buscarSala(1);
        

System.out.println("\n------------------------PROYECCION------------------\n");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime horaInicio = LocalDateTime.parse("2025-10-30 20:00:00", formatter);
        LocalDateTime horaFin = LocalDateTime.parse("2025-10-30 22:30:00", formatter);

        Proyeccion proyec = new Proyeccion(0, encontrada2, sala1, "ES", false, horaInicio, horaFin, sala1.getCapacidad(), true, 12000);

        proyecData.guardarProyeccion(proyec);
        int idP = proyec.getIdProyeccion();

        Proyeccion proEncontrada1 = proyecData.buscarProyeccionPorID(idP);
        if (proEncontrada1 != null) {
            System.out.println("Proyección: " + idP + " - Pelicula: " + proEncontrada1.getPelicula() + " - Sala: " + proEncontrada1.getSala());
        }

        if (proEncontrada1 != null) {
            proEncontrada1.setEs3D(false);
            proEncontrada1.setPrecioLugar(8000);
            proyecData.actualizarProyeccion(proEncontrada1);
        }
        
        
System.out.println("\n------------------------LUGAR/ASIENTO------------------\n");
        
        

        LugarAsiento lugar = new LugarAsiento(8, "A", 33, true, proEncontrada1);

        lad.guardarAsiento(lugar);
        int codLugar = lugar.getCodLugar();

        LugarAsiento lugarEncontrado = lad.obtenerAsientoPorCod(codLugar);

        if (lugarEncontrado != null) {
            String estadoStr;
            if (lugarEncontrado.isEstado()) {
                estadoStr = "Ocupado";
            } else {
                estadoStr = "Libre";
            }
            System.out.println("Asiento: " + lugarEncontrado.getFila() + lugarEncontrado.getNumeroAsiento()
                    + " - Estado: " + estadoStr);
        }

        lad.actualizarEstadoAsiento(codLugar, true); //actualizar estado de ocupado a libre o viceversa
        System.out.println("Estado actualizado: " + lugarEncontrado.toString());
    */    
    
    }

}
