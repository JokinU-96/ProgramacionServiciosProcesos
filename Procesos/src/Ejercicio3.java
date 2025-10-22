import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Ejercicio3 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "whoami");
        Process proceso = null;

        String output ="";
        try {
            pb.inheritIO();
            //pb.redirectOutput(new File("salida.txt"));
            //pb.redirectError(new File("error.txt"));
            proceso = pb.start();

            BufferedReader br = new BufferedReader
                    (new InputStreamReader(proceso.getInputStream())
            );

            //StringBuilder sb = new StringBuilder();
            String variable = "";

            String linea;

            while ((linea = br.readLine()) != null) {
                //sb.append(linea);
                //sb.append(System.getProperty("line.separator"));
                variable += linea;
            }

            //output = sb.toString();
            System.out.println(variable);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
