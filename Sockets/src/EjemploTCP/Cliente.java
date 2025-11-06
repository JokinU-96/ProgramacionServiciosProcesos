package EjemploTCP;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        // en localhost y puerto 5500
        Socket conexion = null;
        try {
            conexion = new Socket("0.0.0.0", 5500);

            //Entrada
            InputStream lec = conexion.getInputStream();
            DataInputStream entrada = new DataInputStream(lec);

            //Salida
            OutputStream es = conexion.getOutputStream();
            DataOutputStream salida = new DataOutputStream(es);


            //Lógica
            salida.writeUTF("Mi primera conexión.");

            String respuesta = entrada.readUTF();
            System.out.println(respuesta + " <- la respuesta recibida en el cliente.");

            //Cerrar conexiones.
            entrada.close();
            lec.close();
            salida.close();
            es.close();
            conexion.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
