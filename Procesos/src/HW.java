import java.io.IOException;

public class HW {
    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "echo ¡Hola Mundo!");
        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
