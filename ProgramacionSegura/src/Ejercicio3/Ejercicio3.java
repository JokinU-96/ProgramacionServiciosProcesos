package Ejercicio3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Ejercicio3 {
    public static String hashContrasenya(){
        return "";
    }
    public static void main(String[] args) {

        System.out.print("Sistema de Gestión de Contraseñas: \n1. Registrarse\n2. Iniciar Sesión\n3. Salir\nEscribir una opción: ");
        Scanner scanner = new Scanner(System.in);
        int opcion;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            while (!scanner.hasNextInt()) {
                System.out.println("Por favor escriba un valor numérico.");
                scanner.next();
            }

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: //Registrarse
                    System.out.print("Nombre de usuario: ");
                    String usuario = scanner.nextLine();

                    System.out.println("Contraseña: ");
                    md.update(scanner.nextLine().getBytes());
                    byte[] digest = md.digest();
                    System.out.println(Arrays.toString(digest));
                    System.out.println("Resumen (hex): " + toHexadecimal(digest));

                    System.out.println("La cuenta de " + usuario + " ha sido creada.");

                case 2: //Iniciar sesión
                    System.out.println("Usuario: ");
                    String usuarioRegistrado = scanner.nextLine();
                    System.out.println("Contraseña: ");
                    String contrasenyaRegistrada = scanner.nextLine();

                case 3:

                default:

            }

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
