package Ejercicio4;

import java.net.Socket;

public class Cliente extends Thread{
    Socket socket;

    public Cliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Servidor.iniciarLogica(socket);
    }
}
