package Ejercicio4;

import Ejercicio3.Cliente;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        // Desarrollo de una aplicación distribuida en Java utilizando TCP. Cada cliente enviará un
        // mensaje que consistirá en una solicitud de cálculo (por ejemplo, sumar, restar o
        // multiplicar) junto con dos números. El servidor procesará la solicitud, realizará el
        // cálculo correspondiente y enviará de vuelta el resultado al cliente.
        // Además, el servidor gestionará múltiples conexiones de clientes simultáneamente. Y
        // llevará un registro de todas las operaciones realizadas.

        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(5500);
            System.out.println("Servidor levantado.");

            Socket cliente = null;

            for (; ; ) {
                cliente = servidor.accept();
                new Cliente(cliente, servidor).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void iniciarLogica(Socket socket) {
        try {
            //Flujo de entrada en servidor.
            InputStream entrada = socket.getInputStream();

            DataInputStream lecturaDatos = new DataInputStream(entrada);
            ObjectInputStream lecturaObjetos = new ObjectInputStream(entrada);

            OutputStream salida = null;
            DataOutputStream escritura = null;

            salida = socket.getOutputStream();
            escritura = new DataOutputStream(salida);

            int peticion = 0;
            peticion = lecturaDatos.readInt();

            System.out.println(peticion + " <- una petición recibida.");

            switch (peticion) {
                case 1:
                    escritura.writeInt(suma());
                case 2:
                    escritura.writeInt(resta());
                case 3:
                    escritura.writeInt(multiplicar());
                case 4:
                    escritura.writeInt(division());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int division() {
        return 0;
    }

    private static int multiplicar() {
        return 0;
    }

    private static int resta() {
        return 0;
    }

    private static int suma() {
        return 0;
    }
}
