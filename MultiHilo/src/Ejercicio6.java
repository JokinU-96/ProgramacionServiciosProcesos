public class Ejercicio6 {
    //Vamos a simular la final de 100 metros de las Olimpiadas. Lanzamos
    //varios atletas que van a disputar una carrera.
    //Primero hazlo con que todos los atletas avanzan la misma distancia.
    //Mejora el ejercicio haciendo que cada atleta vaya a “su ritmo”.

    public static void main(String[] args) {
        //Declaro los runnables.
        Atleta atleta1 = new Atleta();
        Atleta atleta2 = new Atleta();
        Atleta atleta3 = new Atleta();

        //Los convierto en hilos.
        Thread atleta1Thread = new Thread(atleta1);
        Thread atleta2Thread = new Thread(atleta2);
        Thread atleta3Thread = new Thread(atleta3);

        //Inicio los procesos.
        atleta1Thread.start();
        atleta2Thread.start();
        atleta3Thread.start();

        try {
            //Digo que los procesos esperen a terminar para empezar a correr.
            atleta2Thread.wait();
            atleta3Thread.wait();
        } catch (InterruptedException | IllegalMonitorStateException e) {
            throw new RuntimeException(e);
        }
    }
}
