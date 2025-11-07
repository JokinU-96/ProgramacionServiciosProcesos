package Ejercicio8_TCP_Objetos;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Conexion {
    public static void main(String[] args) {
        Socket conexion = null;

        try {
            conexion = new Socket("localhost", 7777);
            ObjectOutputStream oos = new ObjectOutputStream(conexion.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(conexion.getInputStream());

            while (true) {
                System.out.print("Operador uno: ");
                int operador1 = Integer.parseInt(System.console().readLine());
                System.out.print("Operador dos: ");
                int operador2 = Integer.parseInt(System.console().readLine());

                Datos datos = new Datos(operador1, operador2);


                oos.writeObject(datos);
                oos.flush();

                // Respuesta del servidor
                Datos respuesta = (Datos) ois.readObject();

                System.out.println("Resultado: " + respuesta.getOperador1() + " x " + respuesta.getOperador2() + " = " + respuesta.getResultado() + "\n");


            }

            //conexion.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
