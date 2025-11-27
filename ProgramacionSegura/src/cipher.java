import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class cipher {
    public static void main(String[] args) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            Cipher cipher1 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Cipher cipher2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            Cipher cipherConProveedor = Cipher.getInstance("AES", "BC");

            // Cipher.ENCRYPT_MODE // para cifrar

            // Cipher.DECRYPT_MODE // para descifrar

            //Ejemplo simétrico con AES (recomendado), DES, 3DES, BLOWFISH:
            // cipher.init(Cipher.ENCRYPT_MODE, claveSimetrica);
            //Ejemplo asimétrico con RSA, ECC:
            // cipher.init(Cipher.ENCRYPT_MODE, clavePublica);

            //Ejecutar la operación:
            // byte[] resultado = cipher.doFinal(datos);


        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }
}
