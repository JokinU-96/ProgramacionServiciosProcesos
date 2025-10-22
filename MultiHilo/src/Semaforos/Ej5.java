package Semaforos;

import java.util.concurrent.Semaphore;

class MMangas extends Thread {
    Taller taller;

    MMangas(Taller taller) {
        this.taller = taller;
    }

    @Override
    public void run() {
        taller.nuevaManga();
    }
}

class MCuerpos extends Thread {
    Taller taller;

    MCuerpos(Taller taller) {
        this.taller = taller;
    }

    @Override
    public void run() {
        taller.nuevoCuerpo();
    }
}

class MJerseys extends Thread {
    Taller taller;

    MJerseys(Taller taller) {
        this.taller = taller;
    }

    @Override
    public void run() {
        taller.nuevoJersey();
    }
}

class Taller {
    Semaphore cestoMangas;
    Semaphore cestoCuerpos;

    Taller(Semaphore cestoMangas, Semaphore cestoCuerpos) {
        this.cestoMangas = cestoMangas;
        this.cestoCuerpos = cestoCuerpos;
    }

    void nuevaManga() {
        try {
            cestoMangas.acquire(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        };
        System.out.println(cestoMangas.availablePermits() + "\n--- Mangas restantes para llenar el cesto.");

    }

    void nuevoCuerpo() {
        try {
            cestoCuerpos.acquire(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cestoMangas.availablePermits() + "\n--- Cuerpos restantes para llenar el cesto.");

    }

    void nuevoJersey() {
        int mangasDisponibles = cestoMangas.availablePermits();
        int cuerposDisponibles = cestoCuerpos.availablePermits();
        if (mangasDisponibles > 2 && cuerposDisponibles > 1 && 0 == mangasDisponibles % 2) {
            System.out.println("Un nuevo jersey ha sido fabricado.");
            cestoMangas.release(2);
            cestoCuerpos.release(1);
        }
    }

}

//Tenemos un taller de costura, dedicado a hacer jerséis. En su interior
//tenemos a tres máquinas trabajando de sol a sol.

//Una máquina está continuamente fabricando mangas, que va
//depositando en un cesto.

//El cesto tiene una capacidad limitada:
//cuando se llena, la costurera deja de coser más mangas hasta que
//hay hueco libre. Otra máquina está continuamente fabricando los
//cuerpos de los jerséis, que también deposita en su correspondiente
//cesta de capacidad limitada.

//Una tercera máquina se encarga
//continuamente de ensamblar jerséis, cogiendo en cada caso dos
//mangas de la cesta de mangas y un cuerpo de la cesta de cuerpos.

//Se trata de escribir el código que sincronice a estas tres máquinas, de
//forma que las dos primeras máquinas no avancen si su cesta está
//llena, y que la tercera máquina no avance si le faltan piezas para
//hacer un nuevo jersey.

//Termina el programa pulsando return en el hilo principal.

public class Ej5 {
    public static void main(String[] args) {
        Semaphore semaphoreMangas = new Semaphore(50);
        Semaphore semaphoreCuerpos = new Semaphore(25);
        Taller taller = new Taller(semaphoreMangas, semaphoreCuerpos);

        for(int i = 0; i < 60; i++) {
            new Thread(new MMangas(taller)).start();
            new Thread(new MCuerpos(taller)).start();
            new Thread(new MJerseys(taller)).start();
        };
    }


}
