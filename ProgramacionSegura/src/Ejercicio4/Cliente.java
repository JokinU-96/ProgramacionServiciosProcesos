package Ejercicio4;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Cliente implements Runnable {
    Servidor servidor;
    Socket cliente;

    public Cliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        kg.init(128);
        SecretKey clave = kg.generateKey();

        InputStream is = null;
        ObjectInputStream lectura;
        try {
            is = cliente.getInputStream();
            lectura = new ObjectInputStream(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OutputStream os = null;
        ObjectOutputStream escritura;
        try {
            os = cliente.getOutputStream();
            escritura = new ObjectOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Lectura del mensaje.
        try {
            Object mensajeCifrado = lectura.readObject();
            escritura.writeObject(descrifrar((byte[]) mensajeCifrado, clave));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String descrifrar(byte[] cifrado, SecretKey clave) {

        try {

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, clave);
            return new String(cipher.doFinal(cifrado));

        } catch (IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
