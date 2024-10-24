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
			try { 
				Scanner banner = new Scanner(new File("Menu/main_banner.txt")); 
				while (banner.hasNextLine()) { System.out.println(banner.nextLine()); }
			} catch (FileNotFoundException e) { e.printStackTrace(); }

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
					System.out.println("Invalid Option");
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
		
		final String menu = "=====[ Patient Menu ]=====\n" +
                     "1. View Medical Record\n" +
                     "2. Update Personal Information\n" +
                     "3. View Available Appointment Slots\n" +
                     "4. Schedule an Appointment\n" +
                     "5. Reschedule an Appointment\n" +
                     "6. Cancel an Appointment\n" +
                     "7. View Scheduled Appointments\n" +
                     "8. Change password\n" +
                     "9. Logout";
		Scanner sc = new Scanner(System.in);
		System.out.println(menu);
		
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
					password_menu();
					System.out.println(menu);		
					break;
				case 9:
					break;
				default:
					System.out.println("[-] Invalid Option");
					break;
			}
		} while(choice != 9);
		clearScreen();	
	}

	public void doctor_menu(){
		clearScreen();	
		final String menu = "=====[ Doctor Menu ]=====\n" +
							"1. View Patient Medical Records\n" +
							"2. Update Patient Medical Records\n" +
							"3. View Personal Schedule\n" + 
							"4. Set Availability for Appointments\n" +
							"5. Accept or Decline Appointment Requests\n" +
							"6. View Upcoming Appointments\n" +
							"7. Record Appointment Outcome\n" +
							"8. Change Password\n" + 
							"9. Logout\n";
		System.out.println(menu);	
		Scanner sc = new Scanner(System.in);
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
					password_menu();
					System.out.println(menu);
					break;
				case 9:
					break;
				default:
					System.out.println("[-] Invalid Choice");
					break;
			}
		} while(choice != 9);
		clearScreen();	
	}

	public void pharma_menu(){
		clearScreen();	
		final String menu = "====[ Pharmacist Menu]===\n" +
							"1. View Appointment Outcome Record\n" +
							"2. Update Prescription Status\n" +
							"3. View Medication Inventory\n" +
							"4. Submit Replenishment Request\n" +
							"5. Change Password\n" +
							"6. Logout";

		System.out.println(menu);
		Scanner sc = new Scanner(System.in);
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
					password_menu();
					System.out.println(menu);
					break;
				case 6:
					break;
				default:
					System.out.println("[-] Invalid Choice");
					break;
			}
		} while(choice != 6);
		clearScreen();	
	}

	public void admin_menu(){ 
		clearScreen();	
		final String menu = "====[ Administrator Menu ]=====\n" + 
							"1. View and Manage Hospital Staff\n" +
							"2. View Appointmnet details\n" +
							"3. View and Manage Medication Inventory\n" +
							"4. Approve Replenishment Requests\n" +
							"5. Change Password\n" +
							"6. Logout";
		System.out.println(menu);
		Scanner sc = new Scanner(System.in);
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
					password_menu();
					System.out.println(menu);
					break;
				case 6:
					break;
				default:
					System.out.println("[-] Invalid Option");
					break;
			}
		} while(choice != 6);
		clearScreen();	
	}

	public void password_menu() {
		clearScreen();			
		String oldpass, newpass1, newpass2;
		final String menu = "===[ Change Your Password ]===\n" +
							"1. Change Password\n" + 
							"2. Exit";
		System.out.println(menu);
		Scanner sc = new Scanner(System.in);
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
						if (acc.changePassword(oldpass, newpass1, newpass2)) 
							System.out.println("[+] Password has been changed successfully");	
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					break;
				default:
					System.out.println("[-] Invalid Option");
					break;
			}
		} while(choice != 2);
		clearScreen();	
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private AccountSystem acc;
}
