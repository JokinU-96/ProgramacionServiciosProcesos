import javax.crypto.*;
import java.security.*;

public class CifradoAsimetrico {

    public static KeyPair generarClaves() {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            return gen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] cifrar (String msg, PublicKey clavePublica) {

        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, clavePublica);
            return cipher.doFinal(msg.getBytes());

        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String descrifrar(byte[] cifrado, PrivateKey clavePrivada) {

        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, clavePrivada);
            return new String(cipher.doFinal(cifrado));

        } catch (IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            KeyPair claves= generarClaves();
            String original = "Menaje secreto";

            byte[] cifrado = cifrar(original, claves.getPublic());
            String descrifrado = descrifrar(cifrado, claves.getPrivate());

            System.out.println("Descrifrado: " + descrifrado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
