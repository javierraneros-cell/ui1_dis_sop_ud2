import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private static BufferedWriter writer;

    static {
        try {
            writer = new BufferedWriter(new FileWriter("buffer_log.csv", false)); // append
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void log(String linea) {
        try {
            writer.write(linea);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
