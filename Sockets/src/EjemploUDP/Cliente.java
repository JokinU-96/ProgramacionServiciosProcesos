package EjemploUDP;

import java.io.IOException;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            DatagramSocket cliente = new DatagramSocket();
            byte[] mensaje = "hola y adios".getBytes();
            InetAddress direccion = InetAddress.getByName("localhost");
            DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, direccion, 7777);
            cliente.send(envio);

            mensaje = "segundo mensaje".getBytes();

            DatagramPacket segundo = new DatagramPacket(mensaje, mensaje.length, direccion, 7777);
            cliente.send(segundo);

            // lectura desde Cliente.
            byte[] lectura = new byte[1024];
            DatagramPacket respuestaServidor = new DatagramPacket(lectura, lectura.length);

            cliente.receive(respuestaServidor);

            String contenido = new String(respuestaServidor.getData()).trim();

            System.out.println("Desde servidor: " + contenido + "\n\t- IP: " + respuestaServidor.getAddress());

            cliente.close();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
