package Semaforos;

import java.util.Random;
import java.util.concurrent.Semaphore;

class VehiculoParking extends Thread {
    Parking parking;
    int peso;

    public VehiculoParking(Parking parking, int peso) {
        this.parking = parking;
        this.peso = peso;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public void run() {
        parking.comprobarPeso(peso);
        parking.aparcar(peso);
    }
}

class Parking {
    Semaphore parking;
    static int n; //Número de vehículos que entran al parking.
    static int pesoTotal;

    public Parking(Semaphore parking) {
        this.parking = parking;
    }

    public void aparcar(int peso) {
        try {
            System.out.println("El hilo " + Thread.currentThread().getName() + " entra al parking.");
            parking.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        aumentarAforo(peso);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        parking.release();
        disminuirAforo(peso);
        System.out.println("El hilo " + Thread.currentThread().getName() + " sale del parking.\nAforo: " + n + "\n");

        System.out.println("Nuevo aforo diario: " + n + "\n" + "Nuevo peso: " + pesoTotal + "\n");
    }

    private void disminuirAforo(int peso) {
        Semaphore disminuirAforo = new Semaphore(1);
        try {
            disminuirAforo.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pesoTotal = pesoTotal - peso;
        System.out.println("Nuevo aforo diario: " + n + "\n" + "Nuevo peso: " + pesoTotal);
        disminuirAforo.release();
    }

    private void aumentarAforo(int peso) {
        Semaphore aumentarAforo = new Semaphore(1);
        try {
            aumentarAforo.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        n++;
        pesoTotal = pesoTotal + peso;
        System.out.println("Nuevo aforo diario: " + n + "\n" + "Nuevo peso: " + pesoTotal);
        aumentarAforo.release();
    }

    public void comprobarPeso(int peso) {
        Semaphore comprobarPeso = new Semaphore(1);
        try {
            comprobarPeso.acquire();
        } catch (InterruptedException e) {
            while (pesoTotal + peso > 20000) {
                // Mientras el peso total de los vehículos estacionados supere las 20 toneladas, el siguiente vehículo espera.
                System.out.println("El hilo " + Thread.currentThread().getName() + " espera a que baje el peso total.");
            }
            comprobarPeso.release();
        }
    }
}
//Una empresa administra un parking inteligente con capacidad limitada.

//El parking tiene espacio para un máximo de n vehículos al mismo
//tiempo. Cada vehículo tiene su peso.

//Cada vehículo que entra o sale es gestionado por un hilo en el programa.

//Además, el sistema mantiene una variable compartida totalEntradas,
//que registra el número total de vehículos que han entrado al parking a
//lo largo del día.

//Esta variable debe actualizarse de forma segura para
//evitar inconsistencias cuando varios vehículos (hilos) entren al mismo
//tiempo.
public class Ej4 {
    public static void main(String[] args) {
        Parking parking = new Parking(new Semaphore(10));

        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int p = random.nextInt(10000);
            new VehiculoParking(parking, p).start();
        }

    }
}
