package EjemploUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
    public static void main(String[] args) {

        try {
            DatagramSocket servidor = new DatagramSocket(7777);
            byte[] buffer = new byte[1024];
            DatagramPacket contenedor = new DatagramPacket(buffer, buffer.length); // espera recibir datos de máximo 1024 bytes.

            servidor.receive(contenedor); // espera a la conexión.

            String informacion = new String(contenedor.getData()).trim();
            int puerto = contenedor.getPort();

            System.out.println("El mensaje recibido es: " + informacion + "\n\t- Puerto: " + puerto);

            DatagramPacket contenedor2 = new DatagramPacket(buffer, buffer.length);

            servidor.receive(contenedor2);

            String informacion2 = new String(contenedor2.getData()).trim();
            int puerto2 = contenedor2.getPort();
            String direccionIp2 = String.valueOf(contenedor2.getAddress());

            System.out.println("El mensaje recibido es: " + informacion2 + "\n\t- Puerto: " + puerto2 + "\n\t- Direccion IP: " + direccionIp2);

            // escritura desde Servidor.
            byte[] mensaje = "Respuesta".getBytes();
            DatagramPacket respuesta = new DatagramPacket(mensaje, mensaje.length, contenedor2.getAddress(), contenedor2.getPort());

            servidor.send(respuesta);

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
