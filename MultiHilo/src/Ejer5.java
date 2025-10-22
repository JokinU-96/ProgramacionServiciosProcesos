import java.util.Random;

public class Ejer5{

    static class Hilo implements Runnable{

        private int[] n;
        private double media;
        private int porcion;
        private int porciones;

        public Hilo(int[] n, int porcion, int porciones) {
            this.n = n;
            this.porcion = porcion;
            this.porciones = porciones;
        }

        public double getMedia() {
            return media;
        }

        @Override
        public void run() {
            int suma = 0;
            int cantidadPorHilo = (int) (double) (n.length / porciones);
            int inicioBucle = cantidadPorHilo * porcion;
            for (int i = inicioBucle; i < inicioBucle + cantidadPorHilo; i++){
                suma += n[i];
                media = (double) suma /i;
            }
        }


    }
    public static void main(String[] args) {
        //Genera una aplicación en la que utilizaremos las cualidades de los hilos
        //para obtener la media total de una array de 2000 posiciones. Utiliza los
        //hilos para aprovechar las cualidades de la concurrencia y obtener el
        //resultado mas rápido.

        //Completo el array con números enteros aleatorios.
        int[] n = new int[2000];
        Random rand = new Random();
        for (int i = 0; i < n.length; i++) {
            n[i] = rand.nextInt(100);
        }

        //Le paso el array al hilo.
        Hilo r1 = new Hilo(n, 3, 4);
        Hilo r2 = new Hilo(n, 2, 4);
        Hilo r3 = new Hilo(n, 1, 4);
        Hilo r4 = new Hilo(n, 0, 4);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);
        Thread t4= new Thread(r4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        System.out.printf("Media R1: %,.2f\n", r1.getMedia());
        System.out.printf("Media R2: %,.2f\n", r2.getMedia());
        System.out.printf("Media R3: %,.2f\n", r3.getMedia());
        System.out.printf("Media R4: %,.2f\n", r4.getMedia());

        double media = (r1.getMedia() + r2.getMedia() + r3.getMedia() + r4.getMedia()) / 4;

        System.out.printf("Media total: %,.2f", media);
    }
}
