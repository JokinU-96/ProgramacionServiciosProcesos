public class HiloRunnable implements Runnable {
    @Override
    public void run() {
        for (char i = 'a'; i <= 'z'; i++) {
            System.out.print(i + " - " + Thread.currentThread().getName() + " ");
            try {
                System.out.println("Estado del hilo de las letras "+Thread.currentThread().getState());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
