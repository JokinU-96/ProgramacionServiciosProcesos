import java.util.Scanner;

public class Contador {
    public static void main(String[] args) {
        System.out.print("Escribe un número: ");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        for (int i = 0; i <= num; i++) {
            System.out.println(i + "\n");
        }
    }
}
