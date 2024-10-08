package Lab.lab1;

import java.util.Scanner;

public class P3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("starting: ");
        int start = scanner.nextInt();

        System.out.println("ending: ");
        int end = scanner.nextInt();

        System.out.println("increment: ");
        int increment = scanner.nextInt();

        if (end == 0 || increment == 0) {
            System.out.println("Error input!!");
        } else {
            // For Loop Table
            System.out.println("\nFor Loop Table:\n");
            System.out.println("US$         S$");
            System.out.println("--------------");
            for (int i = start; i <= end; i += increment) {
                System.out.println(i + "           " + i*1.82);
            }
            // While Loop Table
            System.out.println("\nWhile Loop Table:\n");
            System.out.println("US$         S$");
            System.out.println("--------------");
            int n = start;
            while (n <= end) {
                System.out.println(n + "           " + n*1.82);
                n += increment;
            }

            // Do/While Loop Table
            System.out.println("\nDo/While Loop Table:\n");
            System.out.println("US$         S$");
            System.out.println("--------------");
            int x = start;
            do {
                System.out.println(x + "           " + x*1.82);
                x += increment;
            } while (x <= end);
        }

        scanner.close();
    }
    
}
