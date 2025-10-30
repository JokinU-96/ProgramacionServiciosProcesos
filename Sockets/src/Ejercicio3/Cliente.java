package Ejercicio3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente extends Thread{
    Socket cliente;
    ServerSocket servidor;

    public Cliente(Socket cliente, ServerSocket servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            Servidor.manipularDatos(cliente, servidor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
