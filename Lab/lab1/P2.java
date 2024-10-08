package Lab.lab1;

import java.util.Scanner;

public class P2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your salary:\n");
        int salary = scanner.nextInt();

        System.out.print("Enter your merit points:\n");
        int merit = scanner.nextInt();

        String grade = "Invalid";

        if (salary >= 700 && salary <= 899) {
            if (salary <= 799 && merit < 20) {
                grade = "Grade B";
            } else {
                grade = "Grade A";
            }
        } else if (salary >= 600 && salary <= 799) {
            if (salary <= 649 && merit < 10) {
                grade = "Grade C";
            } else {
                grade = "Grade B";
            }
        } else if (salary >= 500 && salary <= 649) {
            grade = "Grade C";
        }
            
        System.out.println("salary: $" + salary + ", merit: " + merit + " - Grade " + grade + ".");
        scanner.close();

        }
}
