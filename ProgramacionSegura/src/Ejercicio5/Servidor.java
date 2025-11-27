package Ejercicio5;

import Ejercicio4.Cliente;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(7777);
            System.out.println("Servidor multicliente levantado.");

            Socket cliente = null;
            int i = 0;
            for (; ; ) {
                i++;
                cliente = servidor.accept();
                Socket finalCliente = cliente;
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        HashMap<PublicKey, byte[]> mensaje = null;
                        String mensajeDescifrado = "";
                        KeyPair clavesServidor = generarClaves();
                        // Para descifrar, con la clave privada.
                        Cipher descifrador = configurarDescifrador(clavesServidor.getPrivate());
                        Cipher cifrador;
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(finalCliente.getOutputStream());
                            ObjectInputStream ois = new ObjectInputStream(finalCliente.getInputStream());

                            oos.writeObject(clavesServidor.getPublic()); // claves mandadas al cliente.

                            while (!mensajeDescifrado.equals("salir")) {
                                mensaje = (HashMap<PublicKey, byte[]>) ois.readObject();
                                byte[] mensajeCifrado = mensaje.values().iterator().next();
                                mensajeDescifrado = new String(descifrador.doFinal(mensajeCifrado));
                                System.out.println("El texto enviado por el cliente y descifrado por el servidor es : " + mensajeDescifrado);
                            }

                            ois.close();
                            oos.close();
                            finalCliente.close();

                            System.out.println("Fin de la conexión.");
                        } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            Logger.getLogger("Servidor").log(Level.SEVERE, null, e); // creando un logger.
                        }


                    }
                }, "Cliente " + i).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher configurarDescifrador(PrivateKey clave) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, clave);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher configurarCifrador(PublicKey clave) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, clave);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generarClaves() {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            return gen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
