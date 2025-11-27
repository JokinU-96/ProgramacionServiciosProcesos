package Ejercicio7;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Servidor {
    static int PORT = 6000;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "/home/149fa02@arriagainfo.local/Documentos/AlmacenSSLRSA.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "12345Abcde");

        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket servidorSSL = null;

        try {
//            ServerSocket servidor = new ServerSocket(PORT);
            servidorSSL = (SSLServerSocket) sfact.createServerSocket(PORT);

            DataInputStream flujoEntrada = null;
            System.out.println("Servidor multicliente levantado. Este con certificado SSL");

//          Socket cliente = null;
            SSLSocket cliente = null;
            int i = 0;
            for(;;) {
                i++;
                cliente = (SSLSocket) servidorSSL.accept();
                Socket clienteFinal = cliente;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String mensajeDescifrado = "";

                            KeyPair clavesServidor = generarClaves();
                            Cipher descifrador = configurarDescifrador(clavesServidor.getPrivate());
                            Cipher cifrador = configurarCifrador(clavesServidor.getPublic());

                            Signature signatureServer = Signature.getInstance("SHA256withRSA");
                            signatureServer.initSign(clavesServidor.getPrivate());

                            ObjectOutputStream oos = new ObjectOutputStream(clienteFinal.getOutputStream());
                            ObjectInputStream ois = new ObjectInputStream(clienteFinal.getInputStream());

                            oos.writeObject(clavesServidor.getPublic()); // Clave pública enviada al cliente.

                            Signature signatureClient = Signature.getInstance("SHA256withRSA");

                            while (!mensajeDescifrado.equals("Salir")) {
                                PublicKey clavePublicaCliente = (PublicKey) ois.readObject();
                                byte[] mensajeCifrado = (byte[]) ois.readObject();
                                byte[] certificadoCliente = (byte[]) ois.readObject();

                                signatureClient.initVerify(clavePublicaCliente);
                                signatureClient.update(mensajeCifrado);
                                boolean verificada = signatureClient.verify(certificadoCliente); // verifica la firma digital.

                                if (verificada){
                                    mensajeDescifrado = new String(descifrador.doFinal(mensajeCifrado));
                                    System.out.println("Firma verificada con clave pública.");
                                    System.out.println("El texto enviado por el cliente " + Thread.currentThread().getName() + " y descifrado por el servidor es :\n" + mensajeDescifrado);

                                    Cipher cifradorRespuesta = configurarCifrador(clavePublicaCliente);
                                    mensajeCifrado = cifradorRespuesta.doFinal("Gracias por tu mensaje.".getBytes());

                                    signatureServer.update(mensajeCifrado);
                                    byte[] certificadoServidor = signatureServer.sign();

                                    oos.writeObject(mensajeCifrado);
                                    oos.writeObject(certificadoServidor);
                                } else {
                                    System.out.println("El mensaje enviado no contiene un certificado válido.");
                                }
                            }

                            ois.close();
                            oos.close();
                            clienteFinal.close();

                            System.out.println("Fin de la conexión");
                        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | SignatureException |
                                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, "Cliente " + i).start();
            }
        } catch (IOException e) {
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

    private static Cipher configurarDescifrador(PrivateKey clave) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, clave);
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
