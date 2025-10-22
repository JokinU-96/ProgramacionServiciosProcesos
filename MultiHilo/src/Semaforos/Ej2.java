package Semaforos;
//Supongamos que tenemos en una aula de informática tres portátiles
//para que puedan usarlos los alumnos. Pero tenemos cinco alumnos
//que los quieren usar. Queremos asegurarnos de que solo tres
//estudiantes puedan usar los portátiles al mismo tiempo. Cuando uno
//quede libre otro alumno lo puede utilizar.

import java.util.Random;
import java.util.concurrent.Semaphore;

class Alumna extends Thread {
    Portatil portatil;

    Alumna(Portatil portatil) {
        this.portatil = portatil;
    }

    @Override
    public void run() {
        portatil.usarPortatil();
    }
}

class Portatil{
    Semaphore semaforo;

    public Portatil(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public void usarPortatil(){
        permisos();
        try {
            Random r = new Random();
            int t = r.nextInt(4000);
            Thread.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("El estudiante " + Thread.currentThread().getName() + " ha terminado con su sesión.");
        semaforo.release();
        System.out.println(semaforo.availablePermits() + "/3 sitios libres.\n");
    }

    private synchronized void permisos() {
        try {
            System.out.println("\n" + semaforo.availablePermits() + "/3 sitios libres.");
            Thread.sleep(500);
            System.out.println("El estudiante " + Thread.currentThread().getName() + " se dispone a usar el portátil...");
            semaforo.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
public class Ej2 {
    public static void main(String[] args) {

        Semaphore semaforo = new Semaphore(3);

        Portatil portatil = new Portatil(semaforo);

        Alumna al = new Alumna(portatil);
        Alumna al1 = new Alumna(portatil);
        Alumna al2 = new Alumna(portatil);
        Alumna al3 = new Alumna(portatil);
        Alumna al4 = new Alumna(portatil);
        Alumna al5 = new Alumna(portatil);
        Alumna al6 = new Alumna(portatil);
        Alumna al7 = new Alumna(portatil);
        Alumna al8 = new Alumna(portatil);

        al.start();
        al1.start();
        al2.start();
        al3.start();
        al4.start();
        al5.start();
        al6.start();
        al7.start();
        al8.start();

    }
}
