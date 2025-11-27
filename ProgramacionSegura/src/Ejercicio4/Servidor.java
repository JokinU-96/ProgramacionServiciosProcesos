package Ejercicio4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(7777);
            System.out.println("Servidor multicliente levantado.");

            Socket cliente = null;
            int i = 0;
            for (; ; ) {
                i++;
                cliente = servidor.accept();
                new Thread(new Cliente(cliente), "Cliente " + i ).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
