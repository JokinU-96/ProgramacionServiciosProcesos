package Ejercicio8_TCP_Objetos;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(7777);
            System.out.println("Servidor multicliente levantado.");

            Socket cliente = null;

            for (;;) {
                cliente = servidor.accept();
                new Cliente(cliente).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void iniciarLogica(Socket socket) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Datos datos = (Datos) ois.readObject();
            int resultado = datos.getOperador1() * datos.getOperador2();

            datos.setResultado(resultado);
            Datos respuesta = datos;

            oos.writeObject(respuesta);
            oos.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
