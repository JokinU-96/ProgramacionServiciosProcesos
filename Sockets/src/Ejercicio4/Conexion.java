package Ejercicio4;

import java.io.*;
import java.net.Socket;

public class Conexion {
    public static void main(String[] args) {
        Socket conexion = null;
        try {
            conexion = new Socket("localhost", 5500);

            InputStream is = conexion.getInputStream();
            DataInputStream lectura = new DataInputStream(is);

            OutputStream os = conexion.getOutputStream();
            DataOutputStream escritura = new DataOutputStream(os);
            int opcion = 1;

            while (opcion != 0) {
                System.out.print("Operación: \nSuma -> 1\nResta -> 2\nDvision -> 3\nMultiplicación -> 4");
                opcion = Integer.parseInt(System.console().readLine());

                switch (opcion) {
                    case 1:
                        escritura.writeInt(1);
                    case 2:
                        escritura.writeInt(2);
                    case 3:
                        escritura.writeInt(3);
                    case 4:
                        escritura.writeInt(4);
                    default:
                        escritura.writeInt(0);
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
