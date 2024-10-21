package menu;

import account.*;
import users.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.Console;
import java.util.InputMismatchException;

public class Menu {
  public Menu() { acc = new AccountSystem(); }

  public void start() {
    int choice = 3;
    clearScreen();	
    Scanner sc = new Scanner(System.in);

    do {
      try { 
        Scanner banner = new Scanner(new File("menu/main_banner.txt")); 
        while (banner.hasNextLine()) { System.out.println(banner.nextLine()); }
      } catch (FileNotFoundException e) { e.printStackTrace(); }

      System.out.println("1. Log in");
      System.out.println("2. Register");
      System.out.println("3. Exit");
      System.out.print("Enter option (1-3): ");	

      choice = Sanitise.readInt(1, 3, 4);

      switch (choice) {
        case 1:
        login_menu();	
        break;
        case 2:
        register_menu();
        break;
        case 3:
        break;
        default:
        clearScreen();
        System.out.println("=================================================================");
        System.out.println("                    [-] Invalid Option. Try Again.               ");
        System.out.println("=================================================================");
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
      uname = cnsl.readLine("Enter your ID number: ");
      passwd = new String(cnsl.readPassword("Enter your password: "));

      switch(acc.login(uname, passwd)) {
        case LoginStatus.SUCCESS:
        select_menu(acc.getRole());
        break;
        case LoginStatus.WRONG_PASSWORD:
        clearScreen();
        System.out.println("=================================================================");
        System.out.println("                   [!!] Wrong Password. Try Again.               ");
        System.out.println("=================================================================");
        break;
        case LoginStatus.USER_NOT_FOUND:
        clearScreen();
        System.out.println("==================================================================");
        System.out.println("                   [!!] User Not Found. Try Again.                ");
        System.out.println("==================================================================");
        break;
        case LoginStatus.ERROR:
        throw new Exception("[-] Unexpected error has Occurred");
      }
    } catch(Exception e) { e.printStackTrace(); }
  }

  public void register_menu() {
    clearScreen();
    final String menu = "=======[ Registration Menu ]=========\n" +
    "[!!] This registration is only for patients. For staff, please contact your admin directly.\n" +
    "1. Register\n" +
    "2. Exit"; 
    System.out.println(menu);
    Scanner sc = new Scanner(System.in);
    int choice = 3;
    do {
      System.out.print("Enter option (1-2): ");
      choice = Sanitise.readInt(1, 2, 3);
      switch(choice) {
        case 1:
        String[] info = new String[7];
        info[0] = null;
        System.out.println("\n[!] Set Up Your Account"); 

        do {
          try {
            System.out.print("Enter your first name: ");
            info[1] = Sanitise.readName(); 
            break;
          } catch(Exception e) {
            System.out.println(e.getMessage());
          }
        } while(true);

        do {
          try {
            System.out.print("Enter your last name: ");
            info[2] = Sanitise.readName(); 
            break;
          } catch(Exception e) {
            System.out.println(e.getMessage());
          }
        } while(true);


        int option = 4;
        do {
          System.out.println("\nSelect your gender: "); 
          System.out.println("1. Male");
          System.out.println("2. Female");
          System.out.println("3. Others");
          System.out.print("Choose option (1-3): ");
          option = Sanitise.readInt(1, 3, 4);
          switch(option) {
            case 1:
            info[3] = "MALE";
            break;
            case 2:
            info[3] = "FEMALE";
            break;
            case 3:
            info[3] = "OTHERS";
            break;
            case 4:
            System.out.println("[-] Invalid option. Try again");
            break;
          }
        } while(option==4);

        final String dobExample = "[!] Example \n" + 
        "[1] Birthday is on 9th June 1942 \n" + 
        "[2] Enter \"09-06-1942\"\n";
        System.out.println("\nEnter your date of birth in DD-MM-YYYY format."); 
        System.out.println(dobExample);
        do {
          try {
            System.out.print("Enter your date of birth: ");
            info[4] = Sanitise.readDOB();
            break;
          } catch(Exception e){
            System.out.println(e.getMessage());
          }
        } while(true);

        do {
          try {
            System.out.print("\nEnter your phone Number: ");
            info[5] = Sanitise.readPhoneNumber();
            break;
          } catch(Exception e){
            System.out.println(e.getMessage());
          }
        } while(true);

        do {
          try{
            System.out.print("\nEnter your email address: ");
            info[6] = Sanitise.readEmailAddress();
            break;
          } catch(Exception e) {
            System.out.println(e.getMessage());
          }
        } while(true);

        if(acc.register(info, "PATIENT")) {
          final String message = 	"\n[+] Account Created Successfully\n\n" +
          "[!] Please Save the Following Information\n" +
          "[1] Your unique Hospital ID: " + acc.getUname() + "\n" +
          "[2] Your default password is: password\n" +
          "[3] Please change your password after logging in\n\n" +
          "You may exit this page and log in now.";
          System.out.println(message);
        }
        else {
          System.out.println("\n[-] Failed to Create Account. Try again.");
        }
        break;
        case 2:
        clearScreen();
        break;
        default:
        System.out.println("[-] Invalid Option");
        break;
      }
    } while(choice != 2);
  }

  public void patient_menu(){
    clearScreen();	
    user = new Patient(acc.getUname());
    String gender = user.getBasicInfo().getGender(); 
    String pronoun = null;
    switch(gender) {
      case "MALE":
        pronoun = "Mr. ";
        break;
      case "FEMALE":
        pronoun = "Miss. ";
        break;
      case "OTHERS":
        pronoun = " ";
        break;
    }
    final String menu = "=====[ Patient Menu ]=====\n" +
                       "|| Welcome Back, " + pronoun + user.getBasicInfo().getLastName() + " ||\n\n" +
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
      choice = Sanitise.readInt(1, 9, 10);
      switch (choice) {
        case 1:
        break;
        case 2:
          updatePersonalInfo_menu();
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
      choice = Sanitise.readInt(1, 9, 10);
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
      choice = Sanitise.readInt(1, 6, 7);
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
    int choice = 7;
    do {
      System.out.print("Enter option (1-6): ");
      choice = Sanitise.readInt(1, 6, 7);	
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
      choice = Sanitise.readInt(1, 2, 3);
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

  public void updatePersonalInfo_menu() {
    clearScreen();
    user.getBasicInfo().displayInfo();
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  private AccountSystem acc;
  private User user;
}
