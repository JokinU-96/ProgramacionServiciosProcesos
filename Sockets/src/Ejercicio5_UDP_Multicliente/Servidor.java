package Ejercicio5_UDP_Multicliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

class HiloUDP extends Thread {
    DatagramSocket servidor;
    DatagramPacket contenedor;

    public HiloUDP(DatagramSocket servidor, DatagramPacket contenedor) {
        this.servidor = servidor;
        this.contenedor = contenedor;
    }

    @Override
    public void run() {
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

            try {
                servidor.send(respuestaServidor);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            byte[] mensaje = "".getBytes();
            DatagramPacket respuestaServidor = new DatagramPacket(mensaje, mensaje.length, contenedor.getAddress(), contenedor.getPort());
            try {
                servidor.send(respuestaServidor);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Servidor {
    public static void main(String[] args) {
        try {
            DatagramSocket servidor = new DatagramSocket(7777);

            System.out.println("Servidor levantado.");
            byte[] buffer = new byte[1024];
            for (;;) {
                DatagramPacket contenedor = new DatagramPacket(buffer, buffer.length);
                servidor.receive(contenedor);

                HiloUDP hilo = new HiloUDP(servidor, contenedor);
                hilo.start();
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
