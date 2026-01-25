import java.util.concurrent.ThreadLocalRandom;

public class Productor extends Thread {

    private final Buffer buffer;
    private final int idHilo;
    private int ritmo;

    private static int producto = 0;

    public Productor(int idHilo, Buffer buffer){
        this.idHilo = idHilo;
        this.buffer = buffer;
        ritmo = ThreadLocalRandom.current().nextInt(AppProductorConsumidor.MIN_RITMO_PRODUCTOR, 
                                                    AppProductorConsumidor.MAX_RITMO_PRODUCTOR);
    }

    @Override
    public void run() {
        while(true){
            try {
                int valorProducido = producto++;
                buffer.ejecutaProducir(valorProducido);
                System.out.println(String.format("Producto producido por hilo Productor[%s]: %s", idHilo, valorProducido));
                Thread.sleep(ritmo);
            } catch (InterruptedException e) {
                System.out.println(String.format("Se ha producido un error en el hilo Productor[%s] al pausar: %s", idHilo, e.getMessage()));
            }
        }
    }

    public int getIdHilo() {
        return idHilo;
    }

}
