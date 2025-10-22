
public class Main {
    public static void main(String[] args) {
        ClaseHil c1 = new ClaseHil();
        ClaseHil c2 = new ClaseHil();
        c1.setName("Hilo 1 numeros");
        c2.setName("Hilo 2 numeros");

        HiloRunnable hr =  new HiloRunnable();
        Thread hilo = new Thread(hr);
        hilo.setName("Hilo 1 carácteres");

        hilo.start();

        c1.start();//El metodo run actúa con concurrencia.
        c2.start();//En programación multihilo hay que usar el metodo start.

    }
}