package Semaforos;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Persona extends Thread {
    Espacio espacio;

    Persona(Espacio espacio) {
        this.espacio = espacio;
    }

    @Override
    public void run() {
        espacio.ocuparEspacio();
    }
}

class Espacio {
    Semaphore semaforo;

    public Espacio(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public void ocuparEspacio() {
        System.out.println("\n" + semaforo.availablePermits() + "/5 espacios libres.");
        try {
            Thread.sleep(500);
            semaforo.acquire();//Se les da permiso a las primeras 5 personas para que entren.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("La persona " + Thread.currentThread().getName() + " se dispone a ocupar un espacio...");
        permisosDeUnoEnUno();
        System.out.println("Los 5 espacios han sido ocupados.");
        try {
            Random r = new Random();
            int t = r.nextInt(3000); //El tiempo que tarda el ascensor en llegar al piso de cada uno.
            Thread.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("La persona " + Thread.currentThread().getName() + " ha llegado a su destino.");
        semaforo.release();
        System.out.println(semaforo.availablePermits() + "/5 sitios libres.\n");

    }

    private void permisosDeUnoEnUno() {
        Semaphore semaforoPermiso = new Semaphore(1);
        try {
            semaforoPermiso.acquire();// Las 5 personas con permiso de entrada entran de 1 en 1.
            Random r = new Random();
            int t = r.nextInt(500);
            Thread.sleep(t);// El tiempo que tarda cada persona en entrar al ascensor.
            System.out.println("La persona " + Thread.currentThread().getName() + " ha entrado.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        semaforoPermiso.release();
    }
}
// Se desea simular el uso concurrente de un ascensor en un edificio,
//enfocándose exclusivamente en el control del acceso de los pasajeros
//y en la capacidad máxima del ascensor.

//El edificio cuenta con un único ascensor que puede transportar un
//número limitado de personas al mismo tiempo. Cada pasajero intenta
//usar el ascensor para subir o bajar entre pisos, pero en esta
//simulación no se modela el movimiento entre pisos, sino únicamente
//la entrada y salida de pasajeros.

//El objetivo es garantizar que:
//  1. Nunca haya más pasajeros dentro del ascensor que su capacidad
//      máxima.
//  2. El acceso al ascensor esté correctamente sincronizado, evitando
//      que varios pasajeros entren o salgan al mismo tiempo y produzcan
//      condiciones de carrera.
public class Ej3 {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(5);
        Espacio espacio = new Espacio(semaforo);
        for (int i = 0; i < 10; i++) {
            new Persona(espacio).start();
        }
    }


}
