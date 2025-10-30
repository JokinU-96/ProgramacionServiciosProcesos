package Ejercicio3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(5500);
            System.out.println("Servidor levantado.");

            Socket cliente = null;

            for (;;){
                cliente = servidor.accept();
                new Cliente(cliente, servidor).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void manipularDatos(Socket cliente, ServerSocket servidor) throws IOException {
        //Flujo de entrada en servidor.
        InputStream entrada = cliente.getInputStream();
        DataInputStream lectura = new DataInputStream(entrada);
        OutputStream salida = null;
        DataOutputStream escritura = null;


        String peticion = "";


        while (!peticion.equals("Exit")){

            peticion = lectura.readUTF();

            System.out.println( peticion + " <- un mensaje recibido." );

            salida = cliente.getOutputStream();
            escritura = new DataOutputStream(salida);

            if (peticion.equals("Exit")){
                escritura.writeUTF(peticion + " <- El servidor se apagará en breves.");
                servidor.close();
                entrada.close();
                escritura.close();
                salida.close();
                cliente.close();
            } else {
                escritura.writeUTF(peticion);
            }
        }
    }
}
