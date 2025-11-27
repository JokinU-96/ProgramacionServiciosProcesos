import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CifradoSimetrico {
    public static byte[] cifrar(String texto, SecretKey clave) {
        try {

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, clave);
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

    public static void main(String[] args) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey clave = kg.generateKey();

            String original = "Mensaje secreto";
            byte[] cifrado = cifrar(original, clave);
            String descifrado = descrifrar(cifrado, clave);

            System.out.println("Descifrado: " + descifrado);
            System.out.println("Original: " + original);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
