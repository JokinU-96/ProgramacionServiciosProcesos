package Ejercicio1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    //Desarrollo de una aplicación cliente-servidor en la que el cliente sde conecta a un
    //servidor que informa la fecha y la hora del sistema, y el servidor le atiende
    //devolviendole la información.
    //La aplicación estará escuchando en el puerto 4999.
    //En el servidor:
    public static void main(String[] args) {
        try {
            ServerSocket servidor = null;

            servidor = new ServerSocket(4999);
            System.out.println("Servidor iniciado. -- " + "Ejercicio 1");

            Socket cliente = servidor.accept();

            //Flujo de entrada en servidor.
            InputStream entrada = cliente.getInputStream();
            DataInputStream lectura = new DataInputStream(entrada);

            String peticion = lectura.readUTF();

            System.out.println(peticion + " <- lo recibido desde el cliente.");

            OutputStream salida = cliente.getOutputStream();
            DataOutputStream escritura = new DataOutputStream(salida);
            escritura.writeUTF("Petición recibida: " + '\"' + peticion + '\"');

            //cerrar conexión y flujos.
            entrada.close();
            escritura.close();
            salida.close();
            cliente.close();
            servidor.close();

        } catch (IOException e){
            throw new RuntimeException();
        }
    }
}
