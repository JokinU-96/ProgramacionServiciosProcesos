import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Ejer4 {

    static class hiloA extends Thread{
        //El hiloA debe mostrar la tabla de multiplicar del 5.
        @Override
        public void run() {
            int n = 5;
            for(int i = 1; i <= 10; i++){
                int s = i*n;
                System.out.println(i + "X" + n + " = " + s);
            }
        }
    }

    static class hiloB extends Thread{
        //El hiloB debe resolver la suma de los números impares del 1 al 20.
        @Override
        public void run() {
            int suma = 0;
            for(int i = 1; i <= 20; i++){
                if(i % 2 != 0){
                    suma += i;
                }
            }
            System.out.println("Resuelto: " + suma);
        }
    }

    static class hiloC extends Thread{
        //el hiloC debe mostrar por pantalla la información almacenada en una lista de ciudades.
        @Override
        public void run() {
            List<String> ciudades = new ArrayList<>();

            ciudades.add("Badajoz");
            ciudades.add("Rafael");
            ciudades.add("Vitoria-Gasteiz");
            ciudades.add("Sevilla");
            ciudades.add("Bilbao");
            ciudades.add("Donostia-San Sebastian");
            for(String ciudad: ciudades){
                System.out.println("Ciudad: " + ciudad);
            }
        }
    }

    public static void main(String[] args) {
        //Crea un programa en Java que utilice varios hilos para simular tareas
        //que requieren ser ejecutadas de forma controlada, para ello utiliza el
        //metodo join(). En concreto, deberás crear tres hilos, cada uno con un
        //nombre distinto, y hacer que el hilo principal los gestione de manera que
        //el segundo hilo no comience hasta que el primero haya finalizado, y el
        //tercero no comience hasta que el segundo haya terminado.

        //El hilo principal debe mostrar claramente cuándo comienza y cuándo
        //termina su ejecución.

        hiloA ha = new hiloA();
        hiloB hb = new hiloB();
        hiloC hc = new hiloC();



        try {
            ha.start();
            ha.join();
            hb.start();
            hb.join();
            hc.start();
            hc.join();
            System.out.println("Hilo C que calcula la suma de todos los números pares hasta el 20 ha terminado.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
