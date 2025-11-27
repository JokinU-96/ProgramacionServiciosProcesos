package Ejercicio5;

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    final int PUERTO = 7777;
    private Socket cliente;
    private String key;
    private Cipher descifrador;
    private Cipher cifrador;
    private String mensajeDescifrado = "";
    private byte[] mensajeCifrado;

    private HashMap<PublicKey, byte[]> paquete;

    public static void main(String[] args) {

        Cliente c = new Cliente();
        c.initClient();

    }

    public void initClient() {
        try {
            KeyPair clavesCliente = generarClaves();
            cliente = new Socket("0.0.0.0", PUERTO);

            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());

            PublicKey clavePublicaServidor = (PublicKey) ois.readObject();
            System.out.println("Clave pública del servidor recibida.\n" + clavePublicaServidor);

            descifrador = configurarDescrifrador(clavesCliente.getPrivate());
            cifrador = configurarCifrador(clavePublicaServidor);

            Scanner scanner = new Scanner(System.in);
            while (!mensajeDescifrado.equals("end")) {
                System.out.print("\t: ");

                mensajeDescifrado = scanner.nextLine();
                // se cifra con la clave públic del servidor.
                mensajeCifrado = cifrador.doFinal(mensajeDescifrado.getBytes());
                // Envío un paquete con la clave pública y el array de bytes cifrado.
                paquete = new HashMap<>();
                paquete.put(clavesCliente.getPublic(), mensajeCifrado);
                oos.writeObject(paquete);
                oos.flush();
            }
        } catch (IllegalBlockSizeException | BadPaddingException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Cipher configurarCifrador(PublicKey clave) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, clave);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private Cipher configurarDescrifrador(PrivateKey clave) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, clave);
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
