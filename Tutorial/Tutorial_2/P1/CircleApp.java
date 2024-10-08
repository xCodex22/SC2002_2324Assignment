package Tutorial.Tutorial_2.P1;

import java.util.*;

public class CircleApp {
    public static void main(String[] args) {
        int choice = 0;
        Circle circle = null; // initialise circle with Circle Class

        Scanner scanner = new Scanner(System.in);
        System.out.println("==== Circle Computation =====");
        System.out.println("|1. Create a new circle     |");
        System.out.println("|2. Print Area              |");
        System.out.println("|3. Print circumference     |");
        System.out.println("|4. Quit                    |");
        System.out.println("=============================");

        do {
            System.out.println("Choose option (1-3): ");
            choice = scanner.nextInt();
            
            switch(choice) {
                case 1:
                    System.out.println("Enter the radius to compute the area and circumference");
                    circle = new Circle(scanner.nextInt()); // Create circle
                    System.out.println("A new circle is created");
                    break;
                case 2:
                    System.out.println("Area of circle");
                    System.out.println("Radius: " + circle.getRadius());
                    circle.printArea();
                    break;
                case 3:
                    System.out.println("Circumference of circle");
                    System.out.println("Radius: " + circle.getRadius());
                    circle.printCircumference();
                    break;
                case 4:
                    System.out.println("Thank you!!");
                    break;

            }
        } while (choice != 4);

        scanner.close();
    }
}