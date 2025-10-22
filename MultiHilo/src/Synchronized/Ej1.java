package Synchronized;

public class Ej1 {

    static class Entra implements Runnable {

        private Compartido comp;

        public Entra(Compartido comp) {
            this.comp = comp;
        }

        @Override
        public void run() {
            comp.entrarJardin();
        }
    }

    static class Sale implements Runnable {

        private Compartido comp;

        public Sale(Compartido comp) {
            this.comp = comp;
        }

        @Override
        public void run() {
            comp.salirJardin();
        }
    }

    public static void main(String[] args) {
        //1. Un jardín público permite la entrada y salida de visitantes a través de
        //una única puerta. Se desea llevar un registro exacto del número de
        //personas que se encuentran dentro del jardín en cada momento.

        //Para ello, se implementará una aplicación en Java que simule este
        //comportamiento utilizando hilos. Ya hay 20 personas dentro del jardín al
        //inicio. Posteriormente:

        //• 10 hilos simularán visitantes que desean entrar.
        //• 15 hilos simularán visitantes que desean salir.

        //Cada hilo deberá acceder a un contador compartido que representa el
        //número actual de visitantes dentro del jardín.
        //La aplicación debe mostrar por consola las acciones realizadas por cada
        //hilo (por ejemplo, “hilo h1----- Entra en Jardín”, “hilo h1----- Entra en
        //Jardín”) y el número actual de visitantes después de cada operación.

        Compartido compartido = new Compartido(20);

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
