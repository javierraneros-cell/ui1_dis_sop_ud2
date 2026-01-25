public class Buffer {

    public static final int CAPACIDAD_BUFFER = 10;

    private final int[] arrayBuffer;
    private int posicionBuffer;
    
    public Buffer(){
        arrayBuffer = new int[CAPACIDAD_BUFFER];
        posicionBuffer = 0;
    }

    public synchronized void ejecutaProducir(int numeroProducido) throws InterruptedException{
        while(posicionBuffer == CAPACIDAD_BUFFER){
           wait();
        }
        arrayBuffer[posicionBuffer++] = numeroProducido;
        notify();   //Notificar a algún consumidor
    }

    public synchronized int ejecutaConsumir() throws InterruptedException{
        while(posicionBuffer == 0 ){
            wait();
        }
        int producto = arrayBuffer[--posicionBuffer];
        notify();   //Notificar a algún productor
        return producto;
    }

}
