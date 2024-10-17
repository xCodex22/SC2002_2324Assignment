package Menu;

import AccountSystem.AccountSystem;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.Console;
import java.util.InputMismatchException;

public class Menu{
	public Menu() { acc = new AccountSystem(); }

	public void start() {
		int choice = 3;
		clearScreen();	
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("====[ Hospital Management System ]====");
			System.out.println("1. Log in");
			System.out.println("2. Register");
			System.out.println("3. Exit");

			System.out.print("Enter option (1-3): ");	

			try {
				choice = sc.nextInt();
			} catch(InputMismatchException e) { 
				choice = 4; 
				sc.nextLine();
			}

			switch (choice) {
				case 1:
					login_menu();	
					break;
				case 2:
					break;
				case 3:
					break;
				default:
					System.out.println("Invalid option!");
					break;
			}
		} while(choice != 3);
	}
	
	public void select_menu(String role) {
		switch (role) {
			case "PATIENT":
				patient_menu();
				break;
			case "DOCTOR":
				doctor_menu();
				break;
			case "PHARMACIST":
				pharma_menu();
				break;
			case "ADMIN":
				admin_menu();
					break;
			default:
				throw new Error("[-] in select_menu(): unknown role");
		}
	}

	public void login_menu() {
		Console cnsl = null;
		try {	
			String uname, passwd;
			cnsl = System.console();	
			uname = cnsl.readLine("Enter your username: ");
			passwd = new String(cnsl.readPassword("Enter your password: "));
			if (acc.login(uname, passwd)) 	
				select_menu(acc.getRole());	
		} catch(Exception e) { e.printStackTrace(); }
	}

	public void patient_menu(){
		clearScreen();	
		Scanner sc = new Scanner(System.in);
		System.out.println("=====[ Patient Menu ]=====");
		System.out.println("1. View Medical Record");	
		System.out.println("2. Update Personal Information");
		System.out.println("3. View Available Appointment Slots");
		System.out.println("4. Schedule an Appointment");
		System.out.println("5. Reschedule an Appointment");
		System.out.println("6. Cancel an Appointment");
		System.out.println("7. View Scheduled Appointments");
		System.out.println("8. Change password");
		System.out.println("9. Logout");

		int choice = 9;
		do {
			System.out.print("Enter option (1-9): ");
			try {	
				choice = sc.nextInt();
			} catch(InputMismatchException e) { 
				choice = 10; 
				sc.nextLine();	
			}
			switch (choice) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					System.out.println("Logging out..");
					break;
				default:
					System.out.println("Invalid Choice!");
					break;
			}
		} while(choice != 9);
		clearScreen();	
	}

	public void doctor_menu(){
		clearScreen();	
		Scanner sc = new Scanner(System.in);
		System.out.println("=====[ Doctor Menu ]=====");	
		System.out.println("1. View Patient Medical Records");
		System.out.println("2. Update Patient Medical Records");
		System.out.println("3. View Personal Schedule");
		System.out.println("4. Set Availability for Appointments");
		System.out.println("5. Accept or Decline Appointment Requests");
		System.out.println("6. View Upcoming Appointments");
		System.out.println("7. Record Appointment Outcome");
		System.out.println("8. Change Password");
		System.out.println("9. Logout");

		int choice = 9;
		do {
			System.out.print("Enter option (1-9): ");
			try {
				choice = sc.nextInt();
			} catch(InputMismatchException e) { 
				choice = 10; 
				sc.nextLine();
			}
			switch (choice) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					System.out.println("Logging out..");
					break;
				default:
					System.out.println("Invalid Choice!");
					break;
			}
		} while(choice != 9);
		clearScreen();	
	}

	public void pharma_menu(){
		clearScreen();	
		Scanner sc = new Scanner(System.in);
		System.out.println("====[ Pharmacist Menu]===");
		System.out.println("1. View Appointment Outcome Record");
		System.out.println("2. Update Prescription Status");
		System.out.println("3. View Medication Inventory");
		System.out.println("4. Submit Replenishment Request");
		System.out.println("5. Change Passowrd");
		System.out.println("6. Logout");

		int choice = 6;
		do {
			System.out.print("Enter option (1-6): ");
			try {
				choice = sc.nextInt();
			} catch(InputMismatchException e) { 
				choice = 7; 
				sc.nextLine();
			}
			switch (choice) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					System.out.println("Logging out..");
					break;
				default:
					System.out.println("Invalid Choice!");
					break;
			}
		} while(choice != 6);
		clearScreen();	
	}

	public void admin_menu(){ 
		clearScreen();	
		Scanner sc = new Scanner(System.in);
		System.out.println("====[ Administrator Menu ]=====");
		System.out.println("1. View and Manage Hospital Staff");
		System.out.println("2. View Appointmnet details");
		System.out.println("3. View and Manage Medication Inventory");
		System.out.println("4. Approve Replenishment Requests");
		System.out.println("5. Change Password");
		System.out.println("6. Logout");

		int choice = 6;
		do {
			System.out.print("Enter option (1-6): ");
			try {
				choice = sc.nextInt();
			} catch(InputMismatchException e) { 
				choice = 7;
				sc.nextLine();
		   	}
			switch (choice) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					System.out.println("Logging out..");
					break;
				default:
					System.out.println("Invalid Choice!");
					break;
			}
		} while(choice != 6);
		clearScreen();	
	}

	public void password_menu() {
		clearScreen();			
		String oldpass, newpass1, newpass2;
		Scanner sc = new Scanner(System.in);
		System.out.println("===[ Change Your Password ]===");	
		System.out.println("1. Change Passowrd");
		System.out.println("2. Exit");
		int choice = 2;
		do {
			System.out.print("Enter option (1-2): ");	
			try {	
				choice = sc.nextInt();
			} catch(InputMismatchException e) {
				choice = 3;
				sc.nextLine();
			}
			switch (choice) {
				case 1:
					try {
						Console cnsl = System.console();
						oldpass = new String(cnsl.readPassword("Enter your old password: "));
						newpass1 = new String(cnsl.readPassword("Enter your new password: "));
						newpass2 = new String(cnsl.readPassword("Confirm your new password: "));
						//if (acc.changePassword(oldpass, newpass1, newpass2)) 
						System.out.println("Password has been changed successfully");	
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					break;
				default:
					System.out.println("Invalid Option!");
					break;
			}
		} while(choice != 2);
		clearScreen();	
		select_menu(acc.getRole());
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private AccountSystem acc;
}
