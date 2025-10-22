public class HiloSaludador extends Thread{
    /*El HiloSaludador debe imprimir el
    mensaje "Hola desde el hilo saludador" cinco veces, con un pequeño
    retardo entre cada impresión.*/
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " -> Ha comenzado.");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Hola desde el hilo saludador");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Hilo Saludador FIN.");
    }
}
