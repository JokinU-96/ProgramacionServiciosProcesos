public class Ejer7 {

    static class contador implements Runnable{

        private int aumento;
        static int n;//Solo puede ser accedida por un único elemento a la vez.

        public contador(int aumento) {
            this.aumento = aumento;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            for (int i = 0; i < aumento; i++){
                sumar();
                System.out.println(n + "\t" +  Thread.currentThread().getName() + " \uF52A");
            }
        }

        public synchronized void sumar() {
            setN(getN() + 1);
        }
    }

    public static void main(String[] args) {
        //Implementar un programa que lance cuatro threads, cada uno
        //incrementará una variable contador de tipo entero, compartida por
        //todos, 5000 veces y luego saldrá. ¿Se obtiene el resultado correcto?

        contador contador1 = new contador(1700);
        contador contador2 = new contador(700);
        contador contador3 = new contador(700);
        contador contador4 = new contador(1900);

        Thread t1 = new Thread(contador1);
        Thread t2 = new Thread(contador2);
        Thread t3 = new Thread(contador3);
        Thread t4 = new Thread(contador4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        System.out.println(contador1.getN());
        System.out.println(contador2.getN());
        System.out.println(contador3.getN());
        System.out.println(contador4.getN());
    }
}
