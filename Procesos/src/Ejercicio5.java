import java.io.File;

public class Ejercicio5 {
    public static void main(String[] args) {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder();
            System.out.println("1. Directorio actual (después de crear ProcessBuilder): " + processBuilder.directory() + "\n" +
                    " si no se indica lo contrario el valor del proceso es nulo hasta que o bien lo modifiquemos nosotros o ejecutemos el proceso.\n");

            if (null == processBuilder.directory()){
                System.out.println("Toma el directorio del proceso padre, su valor es el de la variable del sistema user.dir"
                        + System.getProperty("user.dir") + " \n");
            }

            File src = new File("src");//guardo el directorio en el que quiero realizar las operaciones.
            processBuilder.directory(src);//configuro el processBuilder con un directorio específico.

            System.out.printf("3. Directorio de trabajo cambiado a: " + processBuilder.directory());

            processBuilder.command("bash", "-c", "ls -l");
            processBuilder.inheritIO();

            Process process = processBuilder.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
