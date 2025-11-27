package Ejercicio2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ejercicio2 {
    public static void main(String[] args) {
        Pattern email = null;
        Pattern usuario = null;
        Pattern fichero = null;
        Matcher mat = null;

        fichero = Pattern.compile("[A-Z]{8}.[a-z]{3}");
        // Ref: https://medium.com/@jamestjw/parsing-file-names-using-regular-expressions-3e85d64deb69
        //fichero = Pattern.compile("([^/]+)_\\d+.jpg}");
        // Ref: https://medium.com/@python-javascript-php-html-css/the-best-regular-expression-for-email-address-verification-42bf83ba2885
        email = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        usuario = Pattern.compile("");

        System.out.println("Nombre del fichero: ");
        String nombre = System.console().readLine();
        mat = fichero.matcher(nombre);

        if(mat.find()) {
            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
            try {
                FileHandler fh = new FileHandler("./" + nombre, true);
                SimpleFormatter formato = new SimpleFormatter();
                fh.setFormatter(formato);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.print("1. Usuario\n2. Email\n");
            for (int i = 1; i <= 2; i++) {
                switch (i) {
                    case 1:
                        System.out.println("Introduce el nombre de usuario: ");
                        mat = email.matcher(System.console().readLine());
                        if (mat.find()) {
                            System.out.println("Enhorabuena, el usuario está correctamente escrito.");
                        } else {
                            System.out.println("Intentalo de nuevo.");
                            logger.log(Level.SEVERE, "El nombre de usuario está mal");
                        }
                        break;
                    case 2:
                        System.out.println("Introduce el email: ");
                        mat = usuario.matcher(System.console().readLine());
                        if (mat.find()) {
                            System.out.println("Enhorabuena, el email está correctamente escrito.");
                        } else {
                            System.out.println("Intentalo de nuevo.");
                        }
                        break;
                }
            }
        } else {
            System.out.println("Intentalo de nuevo.");
        }


    }
}
