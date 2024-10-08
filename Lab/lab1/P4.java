package Lab.lab1;

import java.util.Scanner;

public class P4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("height = ");
        int height = scanner.nextInt();

        if (height == 0) {
            System.out.println("Error input!!");
        } else {
            for (int i = 1; i <= height; i++) {
                if (i % 2 == 0) {
                    for (int j = 1; j <= i; j++) {
                        if (j % 2 == 0) {
                            System.out.print("AA");
                        }
                        else {
                            System.out.print("BB");
                        }
                    }
                } else {
                    for (int j = 1; j <= i; j++) {
                        if (j % 2 == 0) {
                            System.out.print("BB");
                        }
                        else {
                            System.out.print("AA");
                        }
                    }
                }
                System.out.println();
            }
            
        }
        scanner.close();
    }
}
