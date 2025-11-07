package Ejercicio8_TCP_Objetos;

import java.io.IOException;
import java.net.Socket;

public class Cliente extends Thread {
    Socket socket;

    public Cliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Servidor.iniciarLogica(socket);
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
