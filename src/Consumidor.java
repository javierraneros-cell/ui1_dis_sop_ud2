import java.util.concurrent.ThreadLocalRandom;

public class Consumidor extends Thread {

    private final Buffer buffer;
    private final int idHilo;
    private int ritmo;

    public Consumidor(int idHilo, Buffer buffer){
        this.idHilo = idHilo;
        this.buffer = buffer;
        ritmo = ThreadLocalRandom.current().nextInt(AppProductorConsumidor.MIN_RITMO_CONSUMIDOR, 
                                                    AppProductorConsumidor.MAX_RITMO_CONSUMIDOR);
    }

    @Override
    public void run() {
        while (true) {
            try {
                int valorConsumido = buffer.ejecutaConsumir();
                System.out.println(String.format("Producto consumido por hilo Consumidor[%s]: %s", idHilo, valorConsumido));
                Thread.sleep(ritmo);
            } catch (InterruptedException e) {
                System.out.println(String.format("Se ha producido un error en el hilo Consumidor[%s] al pausar: %s", idHilo, e.getMessage()));
            }            
        }
    }

    public int getIdHilo() {
        return idHilo;
    }

    
}
