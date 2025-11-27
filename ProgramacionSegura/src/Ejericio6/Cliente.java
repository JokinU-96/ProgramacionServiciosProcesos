package Ejericio6;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.HashMap;
import java.util.Scanner;

public class Cliente {
    final int PUERTO = 6000;
    private Socket cliente;
    private String key;
    private Cipher descifrador;
    private Cipher cifrador;
    private String mensajeDescifrado = "";
    private byte[] mensajeCifrado;


    public static void main(String[] args) {

        Cliente c = new Cliente();
        c.initClient();

    }

    private void initClient() {
        try {
            KeyPair clavesCliente = generarClaves();
            cliente = new Socket("0.0.0.0", PUERTO);

            Signature signatureClient = Signature.getInstance("SHA256withRSA");
            signatureClient.initSign(clavesCliente.getPrivate());

            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            System.out.println("Esperando la clave pública del servidor...");
            PublicKey clavePublicaServidor = (PublicKey) ois.readObject();
            System.out.println("Clave pública del servidor recibida.\n" + clavePublicaServidor);

            Signature signatureServer = Signature.getInstance("SHA256withRSA");
            signatureServer.initVerify(clavePublicaServidor); // es la firma que manda el servidor, la clave es suya.

            descifrador = configurarDescrifrador(clavesCliente.getPrivate());
            cifrador = configurarCifrador(clavePublicaServidor);

            Scanner scanner = new Scanner(System.in);
            while (!mensajeDescifrado.equals("Salir")) {
                System.out.print("\t: ");

                mensajeDescifrado = scanner.nextLine();
                // se cifra con la clave pública del servidor.
                mensajeCifrado = cifrador.doFinal(mensajeDescifrado.getBytes());

                signatureClient.update(mensajeCifrado);
                byte[] certificado = signatureClient.sign();

                oos.writeObject(clavesCliente.getPublic());
                oos.writeObject(mensajeCifrado);
                oos.writeObject(certificado);

                /* lectura por parte del cliente */
                byte[] mensajeCifrado = (byte[]) ois.readObject();
                byte[] certificadoServidor = (byte[]) ois.readObject();

                signatureServer.update(mensajeCifrado);
                boolean verificada = signatureServer.verify(certificadoServidor); // verifica la firma digital.

                if (verificada) {
                    mensajeDescifrado = new String(descifrador.doFinal(mensajeCifrado));
                    System.out.println("\tR: " + mensajeDescifrado);
                } else {
                    System.out.println("El mensaje recibido no contiene un certificado válido.");
                }
            }

            oos.close();
            ois.close();
            System.out.println("Petición de salida en marcha...");

        } catch (NoSuchAlgorithmException | SignatureException | IOException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException | ClassNotFoundException e) {
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

    private Cipher configurarCifrador(PublicKey clave) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, clave);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private static KeyPair generarClaves() {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            return gen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
