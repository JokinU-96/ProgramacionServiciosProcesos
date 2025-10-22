package Synchronized;

public class Compartido {
    private int n;

    public Compartido(int n) {
        this.n = n;
    }

    synchronized void entrarJardin() {
        this.n++;
        System.out.println(Thread.currentThread().getName() + " entra al jardín.\n" +
                "Quedan: " + n + "\n");
    }

    synchronized void salirJardin() {
        this.n--;
        System.out.println(Thread.currentThread().getName() + " sale del jardín.\n" +
                "Quedan: " + n + "\n");
    }

    synchronized void entrarAlmacen(int m){
        this.n = this.n + m;
        System.out.println(Thread.currentThread().getName() + "| " + m + " productos entran al almacén.\n" +
                "Quedan: " + this.n + "\n");
    }

    synchronized void salirAlmacen(int m){
        if ((this.n - m) > 0){
            this.n = this.n - m;
            System.out.println(Thread.currentThread().getName() + "| " + m + " productos salen del almacén.\n" +
                    "Quedan: " + this.n + "\n");
        }else {
            System.out.println("\nNo es posible extraer productos del almacén porque no hay suficiente inventario.\n");
        }
    }
}
