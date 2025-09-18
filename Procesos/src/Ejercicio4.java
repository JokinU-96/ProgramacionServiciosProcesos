import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio4 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("bash","-c", "ls -l");
        Process proceso = null;

        Runtime rt = Runtime.getRuntime();

        try {

            //pb.redirectOutput(new File("salida.txt"));
            //pb.redirectError(new File("error.txt"));

            proceso = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(line);
                //sb.append(System.getProperty("line.separator"));
            }

            System.out.println(sb);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
