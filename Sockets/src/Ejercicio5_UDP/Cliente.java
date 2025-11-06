package Ejercicio5_UDP;

import java.io.IOException;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            DatagramSocket cliente = new DatagramSocket();
            System.out.print("Introduce el mensaje: ");
            byte[] mensaje = ((String) System.console().readLine()).getBytes();

            InetAddress direccion = InetAddress.getByName("localhost");
            DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, direccion, 7777);

            cliente.send(envio);

            // lectura desde Cliente
            byte[] lectura = new byte[1024];
            DatagramPacket respuestaServidor = new DatagramPacket(lectura, lectura.length);

            cliente.receive(respuestaServidor);

            String contenido = new String(respuestaServidor.getData()).trim();

            System.out.println("Respuesta: " + contenido + "\n\t- IP: " + respuestaServidor.getAddress());

            cliente.send(envio);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
