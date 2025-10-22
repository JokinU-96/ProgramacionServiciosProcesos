package Semaforos;

import java.util.concurrent.Semaphore;

class HiloVehiculo extends Thread {
    Puente puente;

    HiloVehiculo(Puente puente) {
        this.puente = puente;
    }

    @Override
    public void run() {
        try {
            puente.entrarVehiculoEspecial();
            puente.entrar();
            puente.salir();
            puente.salirVehiculoEspecial();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Puente{
    Vehiculo vn;
    VehiculoEspecial vs;
    Semaphore limpieza;

    public Puente(Vehiculo vn, VehiculoEspecial vs, Semaphore limpieza) {
        this.vn = vn;
        this.vs = vs;
        this.limpieza = limpieza;
    }

    public Puente(VehiculoEspecial vs) {
        this.vs = vs;
    }
    public Puente(Vehiculo vn) {
        this.vn = vn;
    }
    public Puente(Semaphore limpieza){
        this.limpieza = limpieza;
    }

    public void entrar() throws InterruptedException {
        vn.controlVehiculo.acquire();
        System.out.println("Ha entrado al puente.");
        Thread.sleep(2000);
    }

    public void salir() throws InterruptedException {
        System.out.println("Saliendo del puente...");
        Thread.sleep(1000);
        vn.controlVehiculo.release();
        System.out.println("Semáforo de nuevo en verde. El coche que espera puede entrar.");
    }

    public void entrarVehiculoEspecial() throws InterruptedException {
        vs.controlVehiculo.acquire(2);
        System.out.println("El vehículo especial ha entrado al puente.");
        Thread.sleep(4000);
    }

    public void salirVehiculoEspecial() throws InterruptedException {
        System.out.println("El vehículo especial está saliendo...");
        Thread.sleep(1000);
        vs.controlVehiculo.release(2);
        System.out.println("El vehículo especial ha salido del puente.");
    }

    public void limpiar() throws InterruptedException {
        limpieza.acquire();
    }
}

class Limpieza extends Thread {
    @Override
    public void run() {
        super.run();
    }
}

class Vehiculo {
    Semaphore controlVehiculo;

    public Vehiculo(Semaphore controlVehiculo) {
        this.controlVehiculo = controlVehiculo;
    }

    public Vehiculo(Parking parking, int i) {
    }
}

class VehiculoEspecial {
    Semaphore controlVehiculo;

    public VehiculoEspecial(Semaphore controlVehiculo) {
        this.controlVehiculo = controlVehiculo;
    }

}

public class Ejemplo {

    public static void main(String[] args) {
        Semaphore controlVehiculo = new Semaphore(2);
        Semaphore limpieza = new Semaphore(0);
        Vehiculo n = new Vehiculo(controlVehiculo);
        VehiculoEspecial s = new VehiculoEspecial(controlVehiculo);
//        Puente pn =  new Puente(n);
//        Puente ps = new Puente(s);
//        Puente pl = new Puente(limpieza);
        Puente puente = new Puente(n, s, limpieza);

        HiloVehiculo h = new HiloVehiculo(puente); //hilo con puente con vehículo normal.

        for(int i =0; i < 10; i++){
            h.start();
        }

    }
}
