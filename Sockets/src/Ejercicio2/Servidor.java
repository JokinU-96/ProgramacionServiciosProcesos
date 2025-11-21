package Ejercicio2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    // Desarrolla un programa que establezca una conexión a un servidor mediante sockets.
    // El servidor recibirá solicitudes del cliente, procesará la información y la devolverá en
    // mayúsculas. El intercambio de información finalizará cuando el cliente envíe una señal
    // de finalización.
    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(5500);
            System.out.println("Servidor levantado.");

            Socket cliente = servidor.accept();

            //Flujo de entrada en servidor.
            InputStream entrada = cliente.getInputStream();
            DataInputStream lectura = new DataInputStream(entrada);
            OutputStream salida = null;
            DataOutputStream escritura = null;


            String peticion = "";


            while (!peticion.equals("Exit")){

                peticion = lectura.readUTF();

                System.out.println(peticion + " <- Un mensaje recibido.");

                salida = cliente.getOutputStream();
                escritura = new DataOutputStream(salida);

                if (peticion.equals("Exit")){
                    escritura.writeUTF(peticion.toUpperCase() + " <- El servidor se apagará en breves.");
                } else {
                    escritura.writeUTF(peticion.toUpperCase());
                }
            }

            //cerrar conexión y flujos.
            entrada.close();
            escritura.close();
            salida.close();
            cliente.close();
            servidor.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
