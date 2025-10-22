package Monitores;

import java.util.Random;

public class Ej3 {

    public static class Museo{
        private static int n; //cantidad de personas
        private static int nMax; //aforo de la sala
        private static int t; //temperatura

        public Museo(int n, int nMax, int t) {
            this.n = n;
            this.nMax = nMax;
            this.t = t;
        }

        public synchronized void salirDeSala(){
            n--;
            System.out.println("La persona ha salido de la sala.\nAforo: " + n + " de " + nMax + "\n");
            notify();
        }

        public void entrarASala(){
            while(n > nMax){
                try{
                    System.out.println("La persona debe esperar fuera de la sala hasta que el aforo sea menor a " + nMax);
                    wait();
                }catch(InterruptedException e){}
            }
            n++; //Aumento el aforo en +1.
            System.out.println("\nUna nueva persona ha entrado a la sala.\nAforo: " + n + " de " + nMax + "\n");
            tiempoEnSala();
            salirDeSala();
        }

        public void cambiarTemperatura(){
            for(;;){
                Random r = new Random();
                int tr = r.nextInt(4000);
                try {
                    Thread.sleep(tr);//Espero un tiempo random para que los visitantes vean las obras.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                t = r.nextInt(40);
                System.out.println("La temperatura ha cambiado a " + t);
                actualizarAforoTemperatura();
                notify();
            }
        }

        private void actualizarAforoTemperatura(){
            if(t > 30 && n < 35){
                System.out.println("La temperatura es mayor de 30 grados.\nEl aforo es menor que 35 personas.\nSe desaloja la sala.");
                nMax = 35;
            } else {
                nMax = 50;
            }
        }

        private void tiempoEnSala(){
            Random r = new Random();
            int tr = r.nextInt(400);
            try {
                Thread.sleep(tr);//Espero un tiempo random para que los visitantes vean las obras.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("La persona va ha salir de la sala.");
        }
    }

    static class Persona implements Runnable {
        private Museo m;

        public Persona(Museo m) {
            this.m = m;
        }

        @Override
        public void run() {
            m.entrarASala();
        }
    }

    static class Temperatura implements Runnable {
        private Museo m;

        public Temperatura(Museo m) {
            this.m = m;
        }

        @Override
        public void run() {
            m.cambiarTemperatura();
        }
    }

    //En un museo muy famoso han optado por mejorar su sistema de control de
    //visitas. Para ello se desea desarrollar un sistema para controlar la
    //temperatura y el número de personas que se encuentran en una sala del
    //museo.

    //En condiciones normales se permiten 50 personas en la sala. Si la
    //temperatura sube por encima de un umbral(tUmbral=30), se limita el
    //número de personas a 35. Si cuando se detecta este suceso el número de
    //personas en la sala es mayor que 35, no es necesario desalojarlas.
    //Cada persona se representa por un hilo. Además, hay un hilo que mide
    //periódicamente la temperatura de la sala y notifica su valor al sistema.

    public static void main(String[] args) {
        Museo m = new Museo(3,50, 17);


        for (int i = 0; i < 60; i++){
            new Thread(new Persona(m)).start();
        }

        Temperatura temp = new Temperatura(m);
        Thread  temp1 = new Thread(temp);
        temp1.start();

    }
}
