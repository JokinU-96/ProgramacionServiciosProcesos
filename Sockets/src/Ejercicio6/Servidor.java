package Ejercicio6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(7777);
            System.out.println("Servidor multicliente levantado.");

            Socket cliente = null;

            for (; ; ) {
                cliente = servidor.accept();
                new Cliente(cliente).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
