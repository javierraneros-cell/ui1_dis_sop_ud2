public class Consumidor extends Thread {

    private final Buffer buffer;
    private final int idHilo;
    private int ritmoConsumo;

    public Consumidor(int idHilo, Buffer buffer, int ritmoConsumo){
        this.idHilo = idHilo + 1;
        this.buffer = buffer;
        this.ritmoConsumo = ritmoConsumo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int valorConsumido = buffer.ejecutaConsumir();
                System.out.println( String.format("%s \t%s", AppProductorConsumidor.ajustarAncho( String.format("CONS[%s]: %s", idHilo, valorConsumido)), buffer.pintaEstado()));
                Thread.sleep(ritmoConsumo);
            } catch (InterruptedException e) {
                System.out.println(String.format("Se ha producido un error en el hilo Consumidor[%s] al pausar: %s", idHilo, e.getMessage()));
            }            
        }
    }

    public int getIdHilo() {
        return idHilo;
    }

    
}
