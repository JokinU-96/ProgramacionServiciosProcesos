package Ejercicio3;

import java.io.*;
import java.net.Socket;

public class Consulta {
    public static void main(String[] args) {
        Socket conexion = null;
        try {
            conexion = new Socket("localhost", 5500);

            InputStream is = conexion.getInputStream();
            DataInputStream lectura = new DataInputStream(is);

            OutputStream os = conexion.getOutputStream();
            DataOutputStream escritura = new DataOutputStream(os);
            String mensaje = "...";

            while (!mensaje.equalsIgnoreCase("Exit")) {
                System.out.print("Mensaje: ");
                mensaje = System.console().readLine();

                if (!mensaje.isEmpty()){
                    escritura.writeUTF(mensaje);
                }

                String respuesta = lectura.readUTF();
                System.out.println(respuesta);
            }

            //Cerrar conexiones.
            escritura.close();
            os.close();
            lectura.close();
            is.close();
            conexion.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
