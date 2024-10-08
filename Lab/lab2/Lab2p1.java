package Lab.lab2;

import java.util.Scanner;

public class Lab2p1 {
    // Declaring a class-level static variable
    // (static - where all methods within Lab2p1 class can access)
    // (private - only the methods within Lab2p1 class can access)
    // (Scanner - object, scanner - name)
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("Perform the following methods:");
            System.out.println("1: miltiplication test");
            System.out.println("2: quotient using division by subtraction");
            System.out.println("3: remainder using division by subtraction");
            System.out.println("4: count the number of digits");
            System.out.println("5: position of a digit");
            System.out.println("6: extract all odd digits");
            System.out.println("7: quit");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    mulTest();
                    System.out.println();
                    break;
                case 2:
                    System.out.println("m = ");
                    int num1 = scanner.nextInt();
                    System.out.println("n = ");
                    int num2 = scanner.nextInt();
                    System.out.println(num1 + " / " + num2 + " = " + divide(num1, num2));
                    System.out.println();
                    break;
                case 3:
                    System.out.println("m = ");
                    int num3 = scanner.nextInt();
                    System.out.println("n = ");
                    int num4 = scanner.nextInt();
                    System.out.println(num3 + " % " + num4 + " = " + modulus(num3, num4));
                    System.out.println();
                    break;
                case 4: /* add countDigits() call */
                    System.out.println("n : ");
                    int num5 = scanner.nextInt();
                    if (num5 < 0) {
                        System.out.println("n : " + num5 + " - Error input!!");
                    } else {
                        System.out.println("n : " + num5 + " - count = " + countDigits(num5));
                    }
                    break;
                case 5:
                    System.out.println("n : ");
                    int num6 = scanner.nextInt();
                    System.out.println("digit : ");
                    int num7 = scanner.nextInt();
                    System.out.println("position = " + position(num6, num7));
                    break;
                case 6:
                    System.out.println("n : ");
                    int num8 = scanner.nextInt();
                    if (num8 < 0) {
                        System.out.println("oddDigits = Error input!!");
                    } else {
                        System.out.println("oddDigits = " + extractOddDigits(num8));
                    }
                    break;
                case 7: 
                    System.out.println("Program terminating ….");
            }
        } while (choice < 7);

        scanner.close();
    }

    /* add method code here */
    // 1
    public static void mulTest() {
        int score = 0;
        for (int i = 1; i <= 5; i++) {
            int num1 = (int)(Math.random() * 9) + 1;
            int num2 = (int)(Math.random() * 9) + 1;

            System.out.println("How much is " + num1 + " times " + num2 + "?");

            int guess = scanner.nextInt();
            if (guess == num1 * num2) {
                score++;
            }
        }
        System.out.println("........");
        System.out.println(score + " answers out of 5 are correct.");
    }

    // 2
    public static int divide(int m, int n) {
        int sub = 0;
        while (m >= n) {
            m = m - n;
            sub++;
        }
        return sub;
    }

    // 3
    public static int modulus(int m, int n) {
        while (m >= n) {
            m = m - n;
        }
        return m;
    }

    // 4
    public static int countDigits(int n) {
        int digits = 0;
        if (n == 0) {
            return 1;
        } else {
            while (n > 0) {
                n  /= 10;
                digits++;
            }
        }
        return digits;
    }

    // 5
    public static int position(int n, int digit) {
        int pos = 0;
        int mod;
        while (n > 0) {
            mod = n % 10;
            pos++;
            if (mod == digit) {
                return pos;
            }
            n /= 10;
        }
        return -1;
    }

    // 6
    public static long extractOddDigits(long n) {
        long num = 0;
        long multiplier = 0;
        long mod;
        while (n > 0) {
            mod = n % 10;
            if (mod % 2 != 0) {
                num += (long) (mod * (Math.pow(10, multiplier)));
                multiplier++;
            }
            n /= 10;
        }
        if (num == 0) {
            return -1;
        } else {
            return num;
        }
    }
}