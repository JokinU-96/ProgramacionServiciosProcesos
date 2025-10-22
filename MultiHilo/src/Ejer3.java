public class Ejer3 {

    static class contadorImpares implements Runnable {

        @Override
        public void run() {
            for(int i=1;i<=100;i++) {
                if(i%2!=0) {
                    System.out.println(Thread.currentThread().getName() + ": Impares -> " + i);
                }
            }
        }
    }

    static class contadorPares implements Runnable {
        @Override
        public void run() {
            for(int i=1;i<=100;i++) {
                if(i%2==0) {
                    System.out.println(Thread.currentThread().getName() + ": Pares -> " + i);
                }
            }
        }
    }

    public static void main(String[] args) {
        //Crea una aplicación en la que se lancen dos hilos uno de ellos que sea un
        //contador de números pares y otro de impares(del 1 al 100).Visualiza la
        //prioridad de tus hilos. Cambia las prioridades de los hilos,¿que ocurre?

        contadorImpares contadorI = new contadorImpares();
        contadorPares contadorP = new contadorPares();

        Thread threadI = new Thread(contadorI);
        Thread threadP = new Thread(contadorP);

        threadI.start();
        threadP.start();

        threadI.setPriority(5);
        threadP.setPriority(10);

        System.out.println("Prioridad para los Impares: " + threadI.getPriority());
        System.out.println("Prioridad para los Pares: " + threadP.getPriority());
    }
}
