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

    public synchronized String pintaEstado() {
    StringBuilder sb = new StringBuilder();
    sb.append("| ");
    for (int i = 0; i < CAPACIDAD_BUFFER; i++) {
        if (i < posicionBuffer) {
            sb.append("[").append(arrayBuffer[i]).append("]");
        } else {
            sb.append("[ ]");
        }
    }
    sb.append(" |");
    return sb.toString();
}


}
