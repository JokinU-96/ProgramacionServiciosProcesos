package Semaforos;
// En una biblioteca hay una única impresora compartida que puede ser
// usada por los estudiantes para imprimir sus trabajos.

// Varios estudiantes desean imprimir al mismo tiempo, pero la
// impresora solo puede ser utilizada por una persona cada vez.

import java.util.concurrent.Semaphore;

class HiloImpresor extends Thread {
    Impresora impresora;

    public HiloImpresor(Impresora impresora) {
        this.impresora = impresora;
    }

    @Override
    public void run() {
        impresora.imprimir();
    }
}

class Impresora{
    Semaphore semaforo;

    public Impresora(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public void imprimir(){
        try {
            semaforo.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Imprimiendo " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finalizado para " + Thread.currentThread().getName() + "\n");
        semaforo.release();
    }

}
public class Ej1 {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(1);
        Impresora impresora = new Impresora(semaforo);

        HiloImpresor h1 = new HiloImpresor(impresora);
        HiloImpresor h2 = new HiloImpresor(impresora);
        HiloImpresor h3 = new HiloImpresor(impresora);
        HiloImpresor h4 = new HiloImpresor(impresora);

        h1.start();
        h2.start();
        h3.start();
        h4.start();
    }
}
