package Repaso;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Gasolinera {
    Semaphore controlSurtidor;
    static int tanqueGasolinera;

    Gasolinera(Semaphore controlSurtidor, int tanqueGasolinera) {
        this.controlSurtidor = controlSurtidor;
        Gasolinera.tanqueGasolinera = tanqueGasolinera;
    }

    void llenarVehiculo(int tanque) {
        // Lleno el tanque de cada vehículo de uno en uno.
        System.out.println("El vehículo " + Thread.currentThread().getId() + " entra a la gasolinera. --- " + tanqueGasolinera + "\n");
        try {
            controlSurtidor.acquire();
            System.out.println("El vehículo " + Thread.currentThread().getId() + " ha entrado a la gasolinera. --- " + tanqueGasolinera);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Tengo que llenar el depósito del vehículo, independientemente de los litros que tenga.
        tanqueGasolinera = tanqueGasolinera - tanque;
        System.out.println("El vehículo " + Thread.currentThread().getId() + " ha llenado su tanque. --- " + tanqueGasolinera);

        controlSurtidor.release();

    }

    void llenarTanqueGasolinera(int tanque) {
        // Lleno el tanque de la gasolinera hasta el tope cuando no quede suficiente como para llenar un vehículo.
        System.out.println("El camión " + Thread.currentThread().getId() + " entra a la gasolinera. --- " + tanqueGasolinera + "\n");
        try {
            controlSurtidor.acquire();
            System.out.println("El camión " + Thread.currentThread().getId() + " ha entrado a la gasolinera. --- " + tanqueGasolinera);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (tanqueGasolinera < 1000) {
            tanqueGasolinera = 1000;
        }
        System.out.println("El camión " + Thread.currentThread().getId() + " ha llenado el tanque de la gasolinera. --- " + tanqueGasolinera);
        controlSurtidor.release();
    }
}

class Vehiculo extends Thread {
    Gasolinera gasolinera;
    int tanqueVehiculo;

    public Vehiculo(Gasolinera gasolinera, int tanqueVehiculo) {
        this.gasolinera = gasolinera;
        this.tanqueVehiculo = tanqueVehiculo;
    }

    @Override
    public void run() {
        gasolinera.llenarVehiculo(tanqueVehiculo);
    }
}

class Camion extends Thread {
    Gasolinera gasolinera;
    int tanqueCamion;

    public Camion(Gasolinera gasolinera, int tanqueCamion) {
        this.gasolinera = gasolinera;
        this.tanqueCamion = tanqueCamion;
    }

    @Override
    public void run() {
        gasolinera.llenarTanqueGasolinera(tanqueCamion);
    }
}

//Implementa una aplicación concurrente para solucionar el siguiente problema:

//Una gasolinera dispone de un solo surtidor de gasolina con una capacidad
//para almacenar 1000 litros de combustible. A dicha gasolinera pueden acudir
//vehículos para repostar y camiones cisterna para llenar el depósito del surtidor.

//El funcionamiento de la gasolinera y el comportamiento de los vehículos y
//camiones cisterna es el siguiente:

//• En cualquier instante de tiempo, el surtidor solo puede ser utilizado por
//  un vehículo repostando o por un camión cisterna llenando el depósito
//  del surtidor.

//• Cuando un vehículo se detiene para repostar, lo hace por una cantidad
//  de litros que varía según el vehículo. Si el surtidor está libre y
//  posee suficiente gasolina, entonces reposta. Si el surtidor está siendo
//  utilizado por otro vehículo o no posee una cantidad suficiente para
//  satisfacer la cantidad requerida, entonces el vehículo se pone en
//  una cola de espera.

//• Al llegar un camión cisterna la gasolinera, se detiene para llenar el
//  depósito del surtidor, entonces comprueba que el surtidor no está
//  siendo utilizado por un vehículo. Si está siendo utilizado, entonces
//  espera a que quede libre. Si no está siendo utilizado, entonces llena el
//  depósito del surtidor hasta completar su capacidad y abandona la
//  gasolinera.

//Lanza varios hilos de vehículos y uno de camión cisterna, haz que el programa
//sea infinito. Implementa el tiempo en el que los vehículos están repostando y
//el camión rellenando de forma aleatoria.

//Fuerza al máximo la concurrencia.
public class EjRepaso {
    public static void main(String[] args) {

        Gasolinera gs = new Gasolinera(new Semaphore(1), 1000);

        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int l = r.nextInt(100);
            new Vehiculo(gs, l).start();
        }

        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int l = r.nextInt(1000);
            new Camion(gs, l).start();
        }
    }

}
