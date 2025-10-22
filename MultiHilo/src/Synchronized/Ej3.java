package Synchronized;

import java.util.Random;

public class Ej3 {

    static class Entra implements Runnable {

        private Compartido comp;

        public Entra(Compartido comp) {
            this.comp = comp;
        }

        @Override
        public void run() {
            Random r = new Random();
            int m = r.nextInt(100); //Un número aleatorio entre 0 y 100.
            comp.entrarAlmacen(m);
        }
    }

    static class Sale implements Runnable {

        private Compartido comp;

        public Sale(Compartido comp) {
            this.comp = comp;
        }

        @Override
        public void run() {
            Random r = new Random();
            int m = r.nextInt(100); //Un número aleatorio entre 0 y 100.
            comp.salirAlmacen(m);
        }
    }

    //En un almacén, el inventario de un producto debe actualizarse cada vez
    //que llegan nuevas unidades (entrada) o se venden unidades (salida). Este
    //proceso se va a automatizar a través de hilos.

    //Hay que asegurar que las
    //operaciones de entrada y salida actualicen de forma correcta el inventario.

    //No se puede permitir que el inventario sea negativo, si se da el caso,
    //anulamos la operación de venta de unidades.

    //Partimos de la base de que en el almacén tenemos 100 unidades.
    //El número de unidades que cada hilo maneja son aleatorias.

    public static void main(String[] args) {
        Compartido compartido = new Compartido(100);//El almacén cuenta con 100 unidades de producto.

        Entra entra = new Entra(compartido);
        Sale sale = new Sale(compartido);

        for (int i = 0; i < 10; i++){
            new Thread(entra).start();
        }

        for (int i = 0; i < 15; i++){
            new Thread(sale).start();
        }
    }


}
