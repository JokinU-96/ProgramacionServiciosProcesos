package Ejercicio1;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Cliente {
    public static void main(String[] args) {

        Socket conexion = null;

        try {
            conexion = new Socket("0.0.0.0", 4999);

            //Entrada
            InputStream entrada = conexion.getInputStream();
            DataInputStream lectura = new DataInputStream(entrada);

            //Salida
            OutputStream salida = conexion.getOutputStream();
            DataOutputStream escritura = new DataOutputStream(salida);


            //Lógica
            escritura.writeUTF(LocalDateTime.now().toString());

            String respuesta = lectura.readUTF();
            System.out.println(respuesta + " <- la respuesta recibida en el cliente.");

            //Cerrar conexiones.
            entrada.close();
            lectura.close();
            salida.close();
            escritura.close();
            conexion.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
