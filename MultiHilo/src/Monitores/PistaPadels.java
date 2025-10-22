package Monitores;

import java.util.Random;

// Monitor: Gestiona el estado y el acceso concurrente a la pista.
public class PistaPadels {

    private boolean ocupada = false;
    private boolean limpiando = false;
    private int idUsuarioActual = -1;

    // Constantes para los tiempos de simulación
    private static final int TIEMPO_USO_MAX = 3000;
    private static final int TIEMPO_LIMPIEZA_MAX = 1500;

    /**
     * Lógica para que un usuario solicite y use la pista.
     * @param id ID del usuario.
     */
    public synchronized void usarPista(int id) {

        // 4. Si la pista NO está disponible, el usuario debe esperar.
        while (ocupada || limpiando) {
            try {
                System.out.println("🚶 Usuario " + id + " espera. Estado: Ocupada=" + ocupada + ", Limpiando=" + limpiando);
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // 1. Un solo usuario accede a la vez.
        // La sincronización garantiza que solo un hilo llegue a este punto a la vez.
        ocupada = true;
        idUsuarioActual = id;
        System.out.println("\n🟢 Pista ASIGNADA: Usuario " + id + " comienza a jugar.");

        // Simulación del tiempo de uso
        int tiempoUso = new Random().nextInt(TIEMPO_USO_MAX) + 500;
        try {
            Thread.sleep(tiempoUso);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Finaliza el uso y libera la pista
        System.out.println("🔴 Pista LIBRE: Usuario " + id + " ha terminado después de " + tiempoUso + "ms.");
        ocupada = false;
        idUsuarioActual = -1;

        // Al finalizar el uso, notificamos al Limpiador que debe comenzar su trabajo.
        // El limpiador también está esperando, y el notifyAll garantiza que los usuarios
        // que esperan también comprueben el estado.
        notifyAll();
    }

    /**
     * Lógica para que el servicio de limpieza comience y termine su trabajo.
     */
    public synchronized void limpiarPista() {
        for(;;) {
            // Espera hasta que la pista esté libre y no esté en proceso de limpieza
            // Esto asegura que el Limpiador solo actúa DESPUÉS de un uso.
            while (ocupada || limpiando) {
                try {
                    // Esperamos una señal, generalmente del metodo usarPista().
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // 2. Después de cada uso, la pista debe ser limpiada.
            // 3. Mientras se limpia la pista, los usuarios deben esperar.
            limpiando = true;
            System.out.println("\n🧼 LIMPIEZA INICIA: Servicio de limpieza comienza el trabajo.");

            // Simulación del tiempo de limpieza
            int tiempoLimpieza = new Random().nextInt(TIEMPO_LIMPIEZA_MAX) + 500;
            try {
                Thread.sleep(tiempoLimpieza);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            limpiando = false;
            System.out.println("✨ LIMPIEZA TERMINADA: Pista lista después de " + tiempoLimpieza + "ms.");

            // Notificamos a todos los usuarios esperando que la pista está disponible.
            notify();
        }
    }
}

// Clase Hilo: Representa a un usuario que solicita la pista.
class Usuario implements Runnable {
    private PistaPadels pista;
    private int id;

    public Usuario(PistaPadels pista, int id) {
        this.pista = pista;
        this.id = id;
    }

    @Override
    public void run() {
        // Un usuario intenta usar la pista varias veces para simular actividad.
        for (int i = 0; i < 2; i++) {
            System.out.println("➡️ Usuario " + id + " solicita la pista por " + (i + 1) + "a vez.");
            pista.usarPista(id);
            // Pequeña pausa entre solicitudes
            try {
                Thread.sleep(new Random().nextInt(500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Clase Hilo: Representa al servicio de limpieza.
class Limpiador implements Runnable {
    private PistaPadels pista;

    public Limpiador(PistaPadels pista) {
        this.pista = pista;
    }

    @Override
    public void run() {
        pista.limpiarPista();
    }
}


// Clase Principal para la simulación
class EjercicioPadel {

    public static void main(String[] args) {
        PistaPadels pista = new PistaPadels();
        int numUsuarios = 10;
        // Crear e iniciar varios hilos de usuarios
        Thread[] hilosUsuarios = new Thread[numUsuarios];
        for (int i = 0; i < numUsuarios; i++) {
            hilosUsuarios[i] = new Thread(new Usuario(pista, i + 1), "Hilo-Usuario-" + (i + 1));
            hilosUsuarios[i].start();
        }

        // Iniciar el hilo del limpiador (debe ser un hilo de larga duración)
        Thread hiloLimpiador = new Thread(new Limpiador(pista), "Hilo-Limpiador");
        hiloLimpiador.setDaemon(true); // Opcional: Permite que el programa termine si solo queda este hilo.
        hiloLimpiador.start();

        // Esperar a que todos los usuarios terminen sus dos usos
        for (int i = 0; i < numUsuarios; i++) {
            try {
                hilosUsuarios[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n✅ SIMULACIÓN TERMINADA. Todos los usuarios han usado la pista.");
    }
}
