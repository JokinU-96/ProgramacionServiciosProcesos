import java.io.IOException;

public class Ejercicio7_Lanzador {
    public static void main(String[] args) {
        /*Desarrollar una clase Java llamada Lanzador, cuya función sea ejecutar otras
        clases Java del mismo proyecto, pero usando ProcessBuilder para lanzar
        procesos externos.
        Puedes probar a crear una clase que imprima el típico hola mundo y otra con
        los primeros 10 números.
                Ayuda lanzamos el comando java para invocar el interprete de Java para
        ejecutar archivos .java o .class.*/
        System.out.println("Iniciando el Lanzador de procesos...");

        ProcessBuilder holaMundo = new ProcessBuilder("java", "src/HW.java");
        ProcessBuilder contador = new ProcessBuilder("java", "src/Contador.java");

        try {

            holaMundo.inheritIO();
            Process hm = holaMundo.start();
            hm.waitFor();

            contador.inheritIO();
            Process c =  contador.start();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
