public class HiloContador extends Thread {
    /*El HiloContador debe del 1
    imprimir al 5 con una pausa.*/
    public void run() {
        System.out.println(Thread.currentThread().getName() + " -> Ha comenzado.");
        for(int i=1;i<=10;i++) {
            System.out.println(i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Hilo Contador FIN.");
    }
}
