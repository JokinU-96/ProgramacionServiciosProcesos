package Ejemplo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        //Hay un puerto que escucha en el 5500.
        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket(5500);
            System.out.println("Servidor iniciado");
            Socket cliente = servidor.accept();

            //Flujo de entrada en servidor.
            InputStream lectura = cliente.getInputStream();
            DataInputStream entrada = new DataInputStream(lectura);

            String peticion = entrada.readUTF();

            System.out.println(peticion + " <- lo recibido desde el cliente.");


            OutputStream escritura = cliente.getOutputStream();
            DataOutputStream salida = new DataOutputStream(escritura);
            salida.writeUTF("Petición recibida. " + '\"' + peticion + '\"');
            salida.writeUTF("Otra respuesta hacia el cliente.");

            // Otro tipo de flujo datos.
            PrintWriter escritorio = new PrintWriter(salida);
            escritorio.println(); // Así se escribe con el print writer.

            //Cerrar conexión y flujos de datos.
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