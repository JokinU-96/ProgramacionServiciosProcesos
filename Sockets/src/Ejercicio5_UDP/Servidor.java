package Ejercicio5_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Date;

public class Servidor {
    public static void main(String[] args) {
        try {
            DatagramSocket servidor = new DatagramSocket(7777);
            byte[] buffer = new byte[1024];
            DatagramPacket contenedor = new DatagramPacket(buffer, buffer.length);

            servidor.receive(contenedor);

            String informacionRecibida = new String(contenedor.getData()).trim();
            int puerto = contenedor.getPort();
            String direccion = String.valueOf(contenedor.getAddress());

            System.out.println("El mensaje recibido es: " + informacionRecibida + "\n\t- Puerto: " + puerto + "\n\t- Direccion IP: " + direccion);

            // escritura desde el Servidor.
            if (informacionRecibida.equals("hora")){
                Date fecha = new Date();
                String respuesta = fecha.toString();

                byte[] mensaje = ("HORA DEL SERVIDOR " + respuesta).getBytes();
                DatagramPacket respuestaServidor = new DatagramPacket(mensaje, mensaje.length, contenedor.getAddress(), contenedor.getPort());

                servidor.send(respuestaServidor);
            }

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
