import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class AppProductorConsumidor {

    private static int numHilosProductores;
    private static int numHilosConsumidores;

    public static final int CAPACIDAD_BUFFER = 40;

    public static int MIN_RITMO_PRODUCTOR = 100;
    public static int MAX_RITMO_PRODUCTOR = 900;
    
    public static int MIN_RITMO_CONSUMIDOR = 100;
    public static int MAX_RITMO_CONSUMIDOR = 900;

    private static List<Productor>  arrayHilosProductores   = new ArrayList<>();
    private static List<Consumidor> arrayHilosConsumidores  = new ArrayList<>();

    private static final Scanner scanner = new Scanner(System.in);

    private static final String MENSAJE_NUM_HILOS_PRODUCTOR = "Introduzca el numero de hilos PRODUCTORES: ";
    private static final String MENSAJE_NUM_HILOS_CONSUMIDOR = "Introduzca el numero de hilos CONSUMIDORES ";

    public static final int INT_ANCHO_TEXTO_SALIDA = 14;

    public static void main (String[] args){

        numHilosProductores = solicitaNumHilos(MENSAJE_NUM_HILOS_PRODUCTOR);
        numHilosConsumidores = solicitaNumHilos(MENSAJE_NUM_HILOS_CONSUMIDOR);

        //Creamos el buffer a inyectar en los hilos consumidores y productores:
        Buffer buffer = new Buffer(CAPACIDAD_BUFFER);

        for (int i = 0; i < numHilosProductores; i++) {
            Productor hiloProductor = new Productor(i, buffer, getRitmoProduccion());
            arrayHilosProductores.add(hiloProductor);
        }

        for (int i = 0; i < numHilosConsumidores; i++) {
            Consumidor hiloConsumidor = new Consumidor(i, buffer, getRitmoConsumicion());
            arrayHilosConsumidores.add(hiloConsumidor);
        }

        //Comenzamos todos los hilos Consumidores:
        for(Consumidor hiloConsumidor : arrayHilosConsumidores){
            System.out.println(String.format("Hilo Consumidor[%s] iniciado con ritmo %s ...", hiloConsumidor.getIdHilo(), hiloConsumidor.getRitmoConsumo()));
            hiloConsumidor.start();
        }
        System.out.println("Todos los hilos CONSUMIDORES han sido iniciados ...");

        //Comenzamos todos los hilos Productores:
        for(Productor hiloProductor : arrayHilosProductores){
            System.out.println(String.format("Hilo Productor[%s] iniciado con ritmo %s...", hiloProductor.getIdHilo(), hiloProductor.getRitmoProduccion()));
            hiloProductor.start();
        }
        System.out.println("Todos los hilos PRODUCTORES han sido iniciados ...");

    }

    private static int getRitmoProduccion(){
        return ThreadLocalRandom.current().nextInt( AppProductorConsumidor.MIN_RITMO_PRODUCTOR, 
                                                    AppProductorConsumidor.MAX_RITMO_PRODUCTOR );
    }
    
    private static int getRitmoConsumicion(){
        return ThreadLocalRandom.current().nextInt( AppProductorConsumidor.MIN_RITMO_CONSUMIDOR, 
                                                    AppProductorConsumidor.MAX_RITMO_CONSUMIDOR );
    }

    public static String ajustarAncho(String texto) {
        if (texto.length() >= INT_ANCHO_TEXTO_SALIDA) {
            return texto.substring(0, INT_ANCHO_TEXTO_SALIDA);
        }
        return String.format("%-" + INT_ANCHO_TEXTO_SALIDA + "s", texto);
    }

    private static int solicitaNumHilos(String mensaje){
        String numHilos;
        do{
            System.out.print(String.format("\n\t%14s", mensaje ));
            numHilos = scanner.nextLine();
            if ( numHilos.isEmpty() ) {
                System.err.println("Introduzca un numero de hilos correcto...");
            }else {
                try {
                    Integer.parseInt(numHilos);
                } catch (Exception e) {
                    System.err.println("Introduzca un numero de hilos correcto...");
                    numHilos = "";
                }
            }
        }while(numHilos.isEmpty() );
        return Integer.parseInt(numHilos);
    }

}
