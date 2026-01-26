public class Productor extends Thread {

    private final Buffer buffer;
    private final int idHilo;
    private int ritmoProduccion;

    private static int producto = 0;

    public Productor(int idHilo, Buffer buffer, int ritmoProduccion){
        this.idHilo = idHilo + 1;
        this.buffer = buffer;
        this.ritmoProduccion = ritmoProduccion;
    }

    @Override
    public void run() {
        while(true){
            try {
                int valorProducido = producto++;
                buffer.ejecutaProducir(valorProducido);
                System.out.println( String.format("%s >> %s", AppProductorConsumidor.ajustarAncho( String.format("PROD[%s]: %s", idHilo, valorProducido)), buffer.pintaEstado()));
                Thread.sleep(ritmoProduccion);
            } catch (InterruptedException e) {
                System.out.println(String.format("Se ha producido un error en el hilo Productor[%s] al pausar: %s", idHilo, e.getMessage()));
            }
        }
    }

    public int getIdHilo() {
        return idHilo;
    }

    public int getRitmoProduccion(){
        return ritmoProduccion;
    }

}
