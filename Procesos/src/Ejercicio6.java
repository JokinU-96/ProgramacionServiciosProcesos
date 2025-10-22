import java.io.File;
import java.io.IOException;

public class Ejercicio6 {
    public static void main(String[] args) {
        /*A la hora de lanzar un comando se puede dar la circunstancia de que
        escribamos mal el comando, no se pueda ejecutar, falten parámetros, es decir
        no se puede ejecutar y da un error.
        Desarrolla una aplicación donde fuerces un error en ejecución y recoga ese
        error en un archivo de errores.*/

        try{

            System.out.print("Escriba un comando de bash a continuación: ");
            String comando = System.console().readLine();

            ProcessBuilder processBuilder = new ProcessBuilder();
            System.out.println("1. Directorio actual (después de crear ProcessBuilder): " + processBuilder.directory() + "\n" +
                    " si no se indica lo contrario el valor del proceso es nulo hasta que o bien lo modifiquemos nosotros o ejecutemos el proceso.\n");

            if (null == processBuilder.directory()){
                System.out.println("Toma el directorio del proceso padre, su valor es el de la variable del sistema user.dir"
                        + System.getProperty("user.dir") + " \n");
            }

            File tmpDir = new File("src");
            processBuilder.directory(tmpDir);
            System.out.println("3. Directorio de trabajo cambiado a: " + processBuilder.directory());

            processBuilder.command("bash", "-c", comando);

            //processBuilder.inheritIO();

            processBuilder.redirectError(new File("errores.txt"));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0){
                System.out.println("Proceso terminado con código de salida: " + exitCode);
                System.out.println("El proceso ha generado errores, consulte el fichero errores.txt");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("La ejecución del programa ha dado algún error. Consulta el fichero errores.txt.");
            throw new RuntimeException(e);
        }

    }
}
