import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class cripto {
    public static void main(String[] args) {
        String texto = "Esto es un texto plano.";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(texto.getBytes());
            byte[] digest = md.digest(); // genera un array de bytes.
            System.out.println(Arrays.toString(digest));
            System.out.println("Resumen (hex): " + toHexadecimal(digest));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHexadecimal(byte[] hash) {

        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            String h = Integer.toHexString(b & 0xFF);
            if (h.length() == 1) {
                hex.append("0");
            }
            hex.append(h);
        }
        return hex.toString().toUpperCase();
    }
}
