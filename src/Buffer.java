public class Buffer {

    public static final int CAPACIDAD_BUFFER = AppProductorConsumidor.CAPACIDAD_BUFFER;

    private final int[] arrayBuffer;
    private int posicionBuffer;
    
    public Buffer(int capacidadBuffer){
        arrayBuffer = new int[capacidadBuffer];
        posicionBuffer = 0;
    }

    /**
     * Medodo que sera invocado por la clase Productora que llene este buffer.
     * 
     * Se tiene que sincronizar para no escribir si el buffer esta lleno, tanto con los hilos
     * consumidores como con los hilos productores
     * 
     * @param numeroProducido
     * @throws InterruptedException
     */
    public synchronized void ejecutaProducir(int numeroProducido) throws InterruptedException{
        //Si la posición a escribir sobrepasa la capacidad del Buffer esperar
        while(posicionBuffer == CAPACIDAD_BUFFER){
           wait();
        }
        arrayBuffer[posicionBuffer++] = numeroProducido;
        notify();   //Notificar a algún consumidor
    }

    /**
     * Metodo que sera invocado por la clase Consumidora que vacia este buffer
     * 
     * Se tiene que sincronizar tanto con los hilos productores como consumidores y NO consumir
     * si el buffer se encuentra vacio
     * @return
     * @throws InterruptedException
     */
    public synchronized int ejecutaConsumir() throws InterruptedException{
        //si la posicion a escribir en el buffer es 0, osea, esta vacio entonces esperar
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
