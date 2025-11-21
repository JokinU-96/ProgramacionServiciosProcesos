package Ejercicio6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Cliente extends Thread {
    Socket socket;
    private static int contador;

    private Semaphore mutex = new Semaphore(1);

    public Cliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream entrada = null;
        try {
            entrada = socket.getInputStream();
            DataInputStream lectura = new DataInputStream(entrada);

            OutputStream salida = null;
            DataOutputStream escritura = null;

            int n = 0;
            while(n > -1){
                n = lectura.readInt();

                mutex.acquire();
                contador += n;
                mutex.release();

                salida = socket.getOutputStream();
                escritura = new DataOutputStream(salida);

                escritura.writeInt(contador);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(Thread.currentThread().getName() + " Socket cerrado");
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
