import java.util.ArrayList;
import java.util.List;

public class AppProductorConsumidor {

    private static int numHilosProductores;
    private static int numHilosConsumidores;

    public static int MIN_RITMO_PRODUCTOR = 500;
    public static int MAX_RITMO_PRODUCTOR = 900;
    
    public static int MIN_RITMO_CONSUMIDOR = 500;
    public static int MAX_RITMO_CONSUMIDOR = 900;
    

    private static List<Productor>  arrayHilosProductores   = new ArrayList<>();
    private static List<Consumidor> arrayHilosConsumidores  = new ArrayList<>();

    public static void main (String[] args){

        numHilosProductores = 10;
        numHilosConsumidores = 2;

        //Creamos el buffer a inyectar en los hilos consumidores y productores:
        Buffer buffer = new Buffer();

        for (int i = 0; i < numHilosProductores; i++) {
            Productor hiloProductor = new Productor(i, buffer);
            arrayHilosProductores.add(hiloProductor);
        }

        for (int i = 0; i < numHilosConsumidores; i++) {
            Consumidor hiloConsumidor = new Consumidor(i, buffer);
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
}
