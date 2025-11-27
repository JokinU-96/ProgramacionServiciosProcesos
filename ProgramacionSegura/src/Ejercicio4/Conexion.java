package Ejercicio4;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Conexion {
    public static void main(String[] args) {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        kg.init(128);
        SecretKey clave = kg.generateKey();

        Socket conexion;

        InputStream is = null;
        ObjectInputStream lectura;

        OutputStream os = null;
        ObjectOutputStream escritura;
        try {
            conexion = new Socket("localhost", 7777);

            is = conexion.getInputStream();
            lectura = new ObjectInputStream(is);
            os = conexion.getOutputStream();
            escritura = new ObjectOutputStream(os);

            String mensaje = "mensaje";

            while (!mensaje.equals("Salir")) {
                System.out.print("Mensaje: ");
                mensaje = System.console().readLine();

                byte[] mensajeArray = cifrar(mensaje, clave);
                try {
                    escritura.writeObject(mensajeArray);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    Object respuesta = lectura.readObject();
                    System.out.println(respuesta);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            escritura.close();
            os.close();
            lectura.close();
            is.close();
            conexion.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] cifrar(String texto, SecretKey clave) {
        try {

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, clave);
            return cipher.doFinal(texto.getBytes());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
