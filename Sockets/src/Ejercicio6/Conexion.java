package Ejercicio6;

import java.io.*;
import java.net.Socket;

public class Conexion {
    public static void main(String[] args) {
        Socket conexion = null;
        try {
            conexion = new Socket("localhost", 7777);

            InputStream is = conexion.getInputStream();
            DataInputStream lectura = new DataInputStream(is);

            OutputStream os = conexion.getOutputStream();
            DataOutputStream escritura = new DataOutputStream(os);
            int mensaje = 0;

            while (mensaje > -1) {
                System.out.print("Número: ");
                mensaje = Integer.parseInt(System.console().readLine());

                escritura.writeInt(mensaje);

                int respuesta = lectura.readInt();
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
