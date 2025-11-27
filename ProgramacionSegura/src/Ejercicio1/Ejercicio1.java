package Ejercicio1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ejercicio1 {
    public static void main(String[] args) {
        Pattern matricula = null;
        Pattern dni = null;
        Matcher mat = null;

        matricula = Pattern.compile("^[0-9]{4}-[A-Z]{3}$");
        dni = Pattern.compile("^\\d{8}-[A-Z]{1}$");
        Scanner scanner = new Scanner(System.in);
        String matriculaS;
        String dniS;

        System.out.print("1. Matrícula\n2. DNI\nOpción: ");
        try {
            int opcion;
            while(!scanner.hasNextInt()) {
                System.out.println("Por favor escriba un valor numérico.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Introduce una matrícula: ");
                    mat = matricula.matcher(System.console().readLine());
                    if(mat.find()){
                        System.out.println("Enhorabuena, la matrícula está correctamente escrita.");
                    } else {
                        System.out.println("Intentalo de nuevo. Ej. MJM-2345");
                    }
                    break;
                case 2:
                    System.out.println("Introduce un dni: ");
                    mat = dni.matcher(System.console().readLine());
                    if(mat.find()){
                        System.out.println("Enhorabuena, el dni está correctamente escrito.");
                    } else {
                        System.out.println("Intentalo de nuevo. Ej. 12345678Y");
                    }
                    break;
            }
            scanner.close();
        } catch (NumberFormatException ex) {
            System.out.println("Escribe un número por favor.");
        }
    }
}
