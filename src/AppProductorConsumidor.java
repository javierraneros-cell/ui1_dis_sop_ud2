import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AppProductorConsumidor {

    private static int numHilosProductores;
    private static int numHilosConsumidores;

    public static int MIN_RITMO_PRODUCTOR = 500;
    public static int MAX_RITMO_PRODUCTOR = 900;
    
    public static int MIN_RITMO_CONSUMIDOR = 500;
    public static int MAX_RITMO_CONSUMIDOR = 900;
    
    public static final int INT_ANCHO_TEXTO = 15;

    private static List<Productor>  arrayHilosProductores   = new ArrayList<>();
    private static List<Consumidor> arrayHilosConsumidores  = new ArrayList<>();

    public static void main (String[] args){

        numHilosProductores = 10;
        numHilosConsumidores = 2;

        //Creamos el buffer a inyectar en los hilos consumidores y productores:
        Buffer buffer = new Buffer();

        for (int i = 0; i < numHilosProductores; i++) {
            Productor hiloProductor = new Productor(i, buffer, getRitmoProduccion());
            arrayHilosProductores.add(hiloProductor);
        }

        for (int i = 0; i < numHilosConsumidores; i++) {
            Consumidor hiloConsumidor = new Consumidor(i, buffer, getRitmoConsumicion());
            arrayHilosConsumidores.add(hiloConsumidor);
        }

        //Comenzamos todos los hilos Productores:
        for(Productor hiloProductor : arrayHilosProductores){
            System.out.println(String.format("Hilo Productor[%s] iniciado ...", hiloProductor.getIdHilo()));
            hiloProductor.start();
        }

        //Comenzamos todos los hilos Consumidores:
        for(Consumidor hiloConsumidor : arrayHilosConsumidores){
            System.out.println(String.format("Hilo Consumidor[%s] iniciado ...", hiloConsumidor.getIdHilo()));
            hiloConsumidor.start();
        }
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
        if (texto.length() >= INT_ANCHO_TEXTO) {
            return texto.substring(0, INT_ANCHO_TEXTO);
        }
        return String.format("%-" + INT_ANCHO_TEXTO + "s", texto);
    }

}
