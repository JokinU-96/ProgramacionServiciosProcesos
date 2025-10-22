package Monitores;

import java.util.LinkedList;
import java.util.Random;

public class Ej2 {

    public static class Restaurante {
        LinkedList<Integer> mesas = new LinkedList<Integer>();
        LinkedList<String> ocupadas = new LinkedList<>();


        public Restaurante(LinkedList<Integer> mesas) {
            this.mesas = mesas;
        }

        public synchronized void ocuparMesa(int grupo, String codGrupo) {
            //Mientras haya una mesa con aforo suficiente se puede ocupar la mesa.
            //Mientras el grupo no esté en el listado de ocupadas se espera a que se libre.
            //Mientras una mesa tenga el código -1 no se puede ocupar.

            while (ocupadas.contains(codGrupo)) {
                try {
                    System.out.println("\nLa mesa no está disponible o no entran.\n");
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            for (int i : mesas) {
                System.out.print(i + " -- ");
                if (i >= grupo) {
                    ocupadas.add(codGrupo);
                    System.out.println("Mesa de " + i + " ocupada por " + codGrupo + ".");
                    break;
                }
            }
            System.lineSeparator();
            for (String ocupada : ocupadas) {
                System.out.println(ocupada + ",");
            }

            notifyAll(); //Se notifica el cambio
        }

        private void tiempoDeComer() {
            Random r = new Random();
            int t = r.nextInt(4000);
            try {
                Thread.sleep(t);//Espero un tiempo random para que los comensales coman.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Tiempo de comer agotado.");
        }

        public synchronized void librarMesa(String codGrupo) {
            //Si el tiempo de comer ha pasado, la mesa se libera.
            while (!ocupadas.contains(codGrupo)) {
                try {
                    wait();
                }catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            ocupadas.remove(codGrupo);
            notifyAll();
        }
    }


    static class reserva implements Runnable {
        private Restaurante restaurante;
        private int grupo;
        private String codGrupo;

        public reserva(Restaurante restaurante, int grupo, String codGrupo) {
            this.restaurante = restaurante;
            this.grupo = grupo;
            this.codGrupo = codGrupo;
        }

        @Override
        public void run() {
            restaurante.ocuparMesa(grupo, codGrupo);
            restaurante.tiempoDeComer();
            restaurante.librarMesa(codGrupo);
        }
    }



    public static void main(String[] args) {

        //Vamos a crear una aplicación que ayude a organizar un restaurante con
        //mesas limitadas.

        //Este restaurante en cuestión tiene 5 mesas. Los clientes (hilos) llegan y
        //esperan si no hay mesa disponible.

        //En caso de haber mesas disponibles las ocupan durante un tiempo aleatorio.

        //Cuando terminan de comer, liberan la mesa.

        //Solucionarlo con monitores.
        int[] m = new int[] {4, 4, 4, 3, 2, 2, 8};
        LinkedList<Integer> mesas = new LinkedList<>();
        for (int i : m) {
            mesas.add(i);
        }
        Restaurante kotxini = new Restaurante(mesas);

        int grupo1 = 8;
        int grupo2 = 2;
        int grupo3 = 3;
        int grupo4 = 4;
        int grupo5 = 4;
        int grupo6 = 3;
        int grupo7 = 7;

        Thread gr1 = new Thread(new reserva(kotxini, grupo1, "gr1"));
        Thread gr2 = new Thread(new reserva(kotxini, grupo2, "gr2"));
        Thread gr3 = new Thread(new reserva(kotxini, grupo3, "gr3"));
        Thread gr4 = new Thread(new reserva(kotxini, grupo4, "gr4"));
        Thread gr5 = new Thread(new reserva(kotxini, grupo5, "gr5"));
        Thread gr6 = new Thread(new reserva(kotxini, grupo6, "gr6"));
        Thread gr7 = new Thread(new reserva(kotxini, grupo7, "gr7"));

        gr1.start();
        gr2.start();
        gr3.start();
        gr4.start();
        gr5.start();
        gr6.start();
        gr7.start();

    }
}
