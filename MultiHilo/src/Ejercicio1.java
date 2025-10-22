public class Ejercicio1 {
    public static void main(String[] args) {
        /*Crea un programa en Java que en el que generes dos hilos
        (HiloSaludador y HiloContador). El HiloSaludador debe imprimir el
        mensaje "Hola desde el hilo saludador" cinco veces, con un pequeño
        retardo entre cada impresión. Mientras que el HiloContador debe del 1
        imprimir al 5 con una pausa.

        Ambos hilos deben ejecutarse simultáneamente.

        Debe mostrarse por pantalla un mensaje de cuando empieza cada hilo,
        asi como un mensaje de comienzo y de fin del hilo principal.*/

        HiloContador hc = new HiloContador();
        HiloSaludador hs = new HiloSaludador();
        int i = 0;
        while(true){
            if (i == 1) {
                hc.start();
                hs.start();
                break;
            } else {
                System.out.println("Programa iniciado");
                i++;
            }
        }

        try {
            hs.join(); //Te aseguras de que el hilo ha acabado.
            System.out.println("Hilo Saludador FIN DEF.");
            hc.join(400);
            System.out.println("Hilo Contador FIN DEF.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
