package menu;
import account.*;
import users.*;
import staffmanagement.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.Console;
import java.util.InputMismatchException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class Menu {
  public Menu() { acc = new AccountSystem(); }

  public void start() {
    int choice = 3;
    clearScreen();	
    Scanner sc = new Scanner(System.in);
    String bannerArt = null;
    try {
      bannerArt = Files.readString(Paths.get("menu/main_banner.txt"), StandardCharsets.UTF_8);
    } catch(IOException e) {
      e.printStackTrace();
    }
    do {
      System.out.println(bannerArt);
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

        info[3] = Sanitise.readGender();

        do {
          try {
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
          "[1] Your unique Hospital ID: " + acc.getID() + "\n" +
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
    user = new Patient(acc.getID());
    String gender = user.getBasicInfo().getGender(); 
    String pronoun = null;
    switch(gender) {
      case "MALE":
        pronoun = "Mr ";
        break;
      case "FEMALE":
        pronoun = "Miss ";
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
          break;
        case 9:
          break;
        default:
          System.out.println("[-] Invalid Option");
          break;
      }
      System.out.println(menu);
    } while(choice != 9);
    clearScreen();	
  }

  public void doctor_menu(){
    clearScreen();	
    user = new Doctor(acc.getID());
    String surname = user.getBasicInfo().getLastName();
    final String menu = "=====[ Doctor Menu ]=====\n" +
    "||  Welcome back, Dr. " + surname + " ||\n\n" +
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
    Scanner sc = new Scanner(System.in);
    int choice = 7;
    do {
      System.out.println(menu);
      System.out.print("Enter option (1-6): ");
      choice = Sanitise.readInt(1, 6, 7);	
      switch (choice) {
        case 1:
          staffManagement_menu();
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
    User backup = user.copy();
    boolean changed = false;
    int choice = 8; int confirm = 3; int in = 2;

    System.out.println("\n========= Select the field to edit by their numbering ==============");
    do {
      System.out.println("\n[!] Enter (1-6) to edit the corresponding details");
      System.out.println("[!] Enter \"7\" to save changes");
      System.out.println("[!] Enter \"8\" to display help");
      System.out.println("[!] Enter \"9\" to exit this page");
      System.out.print("Enter option (1-9): ");
      choice = Sanitise.readInt(1, 9, 10);
      switch(choice) {
        case 1:
          do {
            try {
              System.out.print("[!] Change your first name: ");
              String newFirstName = Sanitise.readName(); 
              user.getBasicInfo().setFirstName(newFirstName);
              System.out.println("[+] Changed first name to: " + user.getBasicInfo().getFirstName());
              changed = true;
            } catch(Exception e) {
              System.out.println(e.getMessage());
            }
            break;
          } while(true);
          break;

        case 2:
          do {
          try {
              System.out.print("[!] Change your last name: ");
              String newLastName = Sanitise.readName(); 
              user.getBasicInfo().setLastName(newLastName);
              System.out.println("[+] Changed last name to: " + user.getBasicInfo().getLastName());
              changed = true;
            } catch(Exception e) {
              System.out.println(e.getMessage());
            }
            break;
          } while(true);
          break;

        case 3:
          String newGender = "OTHERS";
          System.out.println("[!] Change your gender: ");
          newGender = Sanitise.readGender();
          user.getBasicInfo().setGender(newGender);
          System.out.println("[+] Changed gender to: " + user.getBasicInfo().getGender());
          changed = true;
          break;

        case 4:
          String newDOB = null;
          do {
            try {
              System.out.println("[!] Change your date of birth: ");
              newDOB = Sanitise.readDOB();
              user.getBasicInfo().setDOB(newDOB);
              System.out.println("[+] Changed date of birth to: " + user.getBasicInfo().getDOB());
              changed = true;
            } catch(Exception e) {
              System.out.println(e.getMessage());  
            } 
            break;
          } while(true);
          break;
        case 5:
          String newPhoneNumber = null;
          do {
            try {
              System.out.print("[!] Change your phone number: ");
              newPhoneNumber = Sanitise.readPhoneNumber();
              user.getBasicInfo().setPhoneNumber(newPhoneNumber);
              System.out.print("[+] Changed phone number to: " + user.getBasicInfo().getPhoneNumber());
              changed = true;
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
            break;
          } while (true);
          break;
        case 6:
          String newEmail = null;
          do {
            try {
              System.out.print("[!] Change your email address: ");
              newEmail = Sanitise.readEmailAddress();
              user.getBasicInfo().setEmailAddress(newEmail);
              System.out.println("[+] Changed email address to: " + user.getBasicInfo().getEmailAddress());
              changed = true;
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
            break;
          } while (true);
          break;
        
        case 7:
          clearScreen();
          System.out.println("\n========= Your Information has been Updated ==============");
          user.getBasicInfo().displayInfo(); 
          do {
            System.out.print("Enter \"1\" to quit: "); 
            in = Sanitise.readInt(1, 1, 2);
          } while(in != 1);
          choice = 9;
          changed = false;
          user.getBasicInfo().update();
          clearScreen();
          break;
        
        case 8:
          System.out.println("\n=========[ Usage Menu ]==============");
          System.out.println("[1] Select \"1\" to edit first name.");
          System.out.println("[2] Select \"2\" to edit last name.");
          System.out.println("[3] Select \"3\" to edit gender .");
          System.out.println("[4] Select \"4\" to edit date of birth.");
          System.out.println("[5] Select \"5\" to edit phone number.");
          System.out.println("[6] Select \"6\" to edit email address."); 
          break;

        case 9:
          if (changed) {
            clearScreen();
            System.out.println("\n=========[ !! WARNING: You have Unsaved Changes ]==============");
            user.getBasicInfo().displayInfo(); 
            boolean done = false;
            do {
              System.out.println("\n1. Confirm");
              System.out.println("2. Cancel");
              System.out.print("Enter option (1-2): ");
              confirm = Sanitise.readInt(1, 2, 3);
              switch(confirm) {
                case 1:
                  user.getBasicInfo().update();
                  System.out.println("\n[+] Changes Updated.");
                  do {
                    System.out.print("Enter \"1\" to quit: "); 
                    in = Sanitise.readInt(1, 1, 2);
                  } while(in != 1);
                  done = true;
                  break;
                case 2:
                  user = backup;
                  System.out.println("\n[-] Changes Reverted.");
                  do {
                    System.out.print("Enter \"1\" to quit: "); 
                    in = Sanitise.readInt(1, 1, 2);
                  } while(in != 1);
                  done = true;
                  break;
                default:
                  System.out.println("[-] Invalid option. Try again");
                  break;
              }
            } while(!done);
          } 
          clearScreen();
          break;
        default:
          System.out.println("[-] Invalid option. Try again.");
          break;
      }
    } while(choice != 9);
  }

  public void staffManagement_menu() {
    clearScreen();
    int choice = 5;
    int idEntered = 0;
    StaffManagementSystem sms = new StaffManagementSystem();
    do {
      System.out.println("================[ Staff Management Menu ]==================");
      System.out.println("[1] View Current Staff");
      System.out.println("[2] Edit Staff Information");
      System.out.println("[3] Add Staff"); 
      System.out.println("[4] Remove Staff");
      System.out.println("[5] Exit this page");
      System.out.print("Enter option (1-5): ");
      choice = Sanitise.readInt(1, 5, 6); 
      switch(choice) {
        case 1:
          sms.display(FilterOption.ALL);
          break;
        case 2:
          // this will be the same as 
          break;
        case 3:
          // this will just be the same as account register but we will switch the role to the respective Doctor / Pharmacist
          break;
        case 4:
          System.out.print("Enter the hospital ID of the staff to be removed: "); 
          do {
            try {
              idEntered = Integer.valueOf(Sanitise.readID());
              break;
            } catch(Exception e){}
          } while(true);
          
          User tmp = sms.findStaff(idEntered);  
          if (tmp == null) System.out.println("\n[-] Unable to remove staff. Staff not found");
          else { 
            clearScreen();
            System.out.println("\n============================[ Warning!! ]=============================");
            System.out.println("[!] You are about to remove the following personnel from the data base: \n");
            tmp.getBasicInfo().displayInfo();
            System.out.println("\n[!] Do you wish to proceed?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            System.out.print("Enter option (1-2): ");
            int option = Sanitise.readInt(1, 2, 3);
            switch(option) {
              case 1:
                if(sms.removeStaff(String.valueOf(idEntered)))
                  System.out.println("[+] Staff has been removed from data base");
                else
                  System.out.println("[-] Failed to remove staff. Unknown error has occurred");
                break;
              case 2:
                System.out.println("[!] Procedure terminated by user");
                break;
              default:
                System.out.println("[-] Invalid option. Aborted");
                break;
            }
          }
          break;
        case 5:
          break;
        default:
          System.out.println("[-] Invalid Option. Try Again.");
          break;
      }
    } while(choice != 5);
    clearScreen();
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  private AccountSystem acc;
  private User user;
}
