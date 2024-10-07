package HospitalManagementSystem.src.main;

import java.util.Scanner;

public class HMSApp {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean loggedIn = false;
        //User currentUser = null;

        while (loggedIn == false) {
            System.out.println("Welcome to Hospital Management System");
            System.out.println("Enter HospitalID: ");
            int hospitalID = scanner.nextInt();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
        }
    }
}
