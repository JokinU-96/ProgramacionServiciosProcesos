package Monitores;

import java.util.Random;

public class Ej5 {
    private int pesoMax;
    private int aforoMax;

    private static int pesoAcumulado;
    private static int aforoAcumulado;

    public Ej5(int pesoMax, int aforoMax) {
        this.pesoMax = pesoMax;
        this.aforoMax = aforoMax;
    }

    public synchronized void usarPuente(int pesoVehiculo) {
        System.out.println("\nUn vehículo quiere usar el puente...\n");
        while (pesoAcumulado > pesoMax || aforoAcumulado > aforoMax) {
            try {
                System.out.println("El vehículo " + Thread.currentThread().getName() + " está esperando a entrar.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pesoAcumulado = pesoVehiculo + pesoAcumulado;
        System.out.println("Nuevo peso acumulado: " + pesoAcumulado);
        aforoAcumulado++;
        System.out.println("Nuevo aforo acumulado: " + aforoAcumulado);
    }

    public synchronized void salirPuente(int pesoVehiculo) {
        aforoAcumulado--;
        System.out.println("El vehículo " + Thread.currentThread().getName() + " ha salido del puente.");
        pesoAcumulado = pesoAcumulado - pesoVehiculo;
        System.out.println("Nuevo peso acumulado: " + pesoAcumulado + "\n");
        notifyAll();
    }

}

class Vehiculo implements Runnable {

    Ej5 ej5;
    private int pesoVehiculo;

    public Vehiculo(Ej5 ej5, int pesoVehiculo) {
        this.ej5 = ej5;
        this.pesoVehiculo = pesoVehiculo;
    }

    @Override
    public void run() {
        ej5.usarPuente(pesoVehiculo);
        Random r = new Random();
        int tr = r.nextInt(400);
        try {
            Thread.sleep(tr);//Espero un tiempo random para que los visitantes vean las obras.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("El vehículo va ha salir del puente.");
        ej5.salirPuente(pesoVehiculo);
    }
}

class EjercicioPuente {

    public static void main(String[] args) {
        //Se quiere desarrollar un sistema de seguridad que impida que un puente
        //cargue más de 15.000 Kg y no haya simultáneamente más de 5 vehículos
        //atravesándolo.

        //Un vehículo no recibirá permiso para entrar en el puente si dadas sus
        //características o el estado del mismo, se incumplen los requisitos de
        //seguridad. Es interesante conocer el peso que está soportando el puente
        //cuando se deniega la entrada de un coche.

        //Se debe desarrollar un monitor que garantice exclusión mutua en el acceso
        //a los datos compartidos. Lanza suficientes vehículos para comprobar el
        //buen funcionamiento del monitor.

        Ej5 ej5 = new Ej5(15000, 5);
        Vehiculo[] v = new Vehiculo[10];
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int peso = r.nextInt(6000);
            v[i] = new Vehiculo(ej5, peso);
            Thread t = new Thread(v[i]);
            t.start();
        }

    }
}
