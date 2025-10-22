package Monitores;

public class Ej1 {

    public static class Q {
        private static int n;

        public Q(int n) {
            Q.n = n;
        }

        //Métodos para disminuir o aumentar el dinero de la cuenta.

        public synchronized void sumar(int cantidad){

            System.out.println("\n-- " + n + '\n');
            //Tiene que aumentar el saldo en 10€ siempre y cuando haya menos de 250€ en la cuenta.
            while(n >= 250){
                try {
                    System.out.println("-- BLCK --\t" + Thread.currentThread().getName());
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            n += cantidad;
            System.out.println("\nSe han ahorrado " + cantidad +" euros,\n nuevo sueldo: " + n);
            notifyAll();
        }

        public synchronized void gastar(int cantidad){

            System.out.println("\n-- " + n + '\n');
            //Mientras haya dinero en la cuenta, se puede gastar.
            while(n <= 0){
                try {
                    System.out.println("-- BLCK --\t" + Thread.currentThread().getName());
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            n -= cantidad;
            System.out.println("\nSe han gastado " + cantidad + " euros,\n nuevo sueldo: " + n);
            notifyAll();
        }
    }

    static class Ahorra implements Runnable {

        private Q q;

        public Ahorra(Q q) {
            this.q = q;
        }

        @Override
        public void run() {
            System.out.println("\n-- " + Thread.currentThread().getName() + '\n');

            q.sumar(20);
        }
    }

    static class Gasta implements Runnable {
        private Q q;

        public Gasta(Q q) {
            this.q = q;
        }

        @Override
        public void run() {
            System.out.println("\n-- " + Thread.currentThread().getName() + '\n');

            q.gastar(20);
        }
    }

    //Este programa crea una cuenta y arranca un número de hilos ahorradores y
    //un número equivalente de hilos gastadores que ahorran y gastan 10€ en un
    //número equivalente de veces. Espera a que los hilos terminen y muestra el
    //saldo final de la cuenta.

    //Se pone como condición que si no hay saldo no puede gastar y debe
    //esperar a que haya saldo.

    //Los ahorradores controlarán también el saldo de la cuenta, cuando el saldo
    //sea 250€ debe esperar a que baje para seguir ahorrando.

    //Solucionarlo con monitores.

    public static void main(String[] args) {
        Q q = new Q(100);

        //Creo 10 hilos que ahorran.
        for (int i = 0; i < 15; i++) {
            new Thread(new Ahorra(q)).start();
        }

        //Creo 10 hilos que gastan.
        for (int i = 0; i < 15; i++) {
            new Thread(new Gasta(q)).start();
        }
    }
}
