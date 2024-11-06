package menu;
import account.*;
import users.*;
import appointment.*;
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
        register_menu("PATIENT");
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

  public void register_menu(String inputRole) {
    clearScreen();
    final String menu = "=======[ Registration Menu ]=========\n" +
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

        if(acc.register(info, inputRole)) {
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
          clearScreen();
          if (user instanceof Patient) {
            Patient patient = (Patient) user;
            patient.getBasicInfo().displayInfo();
            patient.getMedicalInfo().displayInfo(user);
          }
          confirm();
          clearScreen();
          break;
        case 2:
          updatePersonalInfo_menu(user);
          break;
        case 3: //view available slots
          clearScreen();
          System.out.println("=======[ View Available Slots ]=======");
          String d = null;
          do {
            try {
              d = Sanitise.readDate();
              break;
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
          } while(true);
          System.out.println("\n[!] The available slots for " + d + " are: ");
          AppointmentSystem.printAvailSlot(d);
          confirm();
          clearScreen();
          break;
        case 4:
          break;
        case 5:
          break;
        case 6:
          break;
        case 7: // view scheduled appointment
          clearScreen();
          System.out.println("======[ All Scheduled Appointments ]=======\n");
          AppointmentSystem.printScheduledAppointment(user.getBasicInfo().getID());
          confirm();
          clearScreen();
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
    Doctor doc;
    if (user instanceof Doctor) {
      doc = (Doctor) user;
    }
    else {
        System.out.println("[-] Unexpected Error in doctor_menu()");
        return;
    }

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
        case 1: // view patient medical records 
          clearScreen();
          System.out.println("======[ View Patient Medical Record ]======");
          System.out.print("Enter patient's ID number: ");
          String pid = null;
          try {
            pid = Sanitise.readID(); 
          } catch(Exception e) { 
            System.out.println("[-] Invalid ID number format");
            confirm();
            clearScreen();
            System.out.println(menu);
            break;
          }
          
          try {
            Patient p = new Patient(pid);
            p.getMedicalInfo().displayInfo(user);
          } catch(Exception e) {
            System.out.println("[-] Patient not found. ");
            confirm();
            clearScreen();
            System.out.println(menu);
            break;
          }
          
          confirm(); 
          clearScreen();
          System.out.println(menu);
          break;

        case 2: // update patient medical records
          clearScreen();
          System.out.println("======[ Update Patient Medical Record ]======");
          System.out.print("Enter patient's ID number: ");
          pid = null;
        try {
          pid = Sanitise.readID(); 
        } catch(Exception e) { 
          System.out.println("[-] Invalid ID number format");
          confirm();
          clearScreen();
          System.out.println(menu);
          break;
        }

        System.out.println("\n[!] You are attempting to update the medical record of the following user:\n");
        Patient p = new Patient(pid);
        p.getBasicInfo().displayInfo();
        System.out.println();
        String[] newEntry = new String[11]; // directly map
        // 0. hospitalID of patient
        // 1. bloodType of patient
        // 2. service date
        // 3. service name
        // 4. doctor ID
        // 5. doctor name
        // 6. diagnosis
        // 7. medication prescribed
        // 8. medication amount
        // 9. treatment plan
        // 10. remarks
        newEntry[0] = p.getBasicInfo().getID();
        newEntry[1] = p.getMedicalInfo().getBloodType();
        newEntry[3] = "Medical Record Update";
        newEntry[4] = user.getBasicInfo().getID();
        newEntry[5] = user.getBasicInfo().getFirstName() + " " + user.getBasicInfo().getLastName(); 
        newEntry[10] = "NIL";

        String newDate, newDiag, newMed, newQty, newPlan;
        do {
          try {
            System.out.println("Enter the date of update: ");
            newDate = Sanitise.readDate();
            newEntry[2] = newDate + "-2024";
            break;
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        } while (true);

        System.out.println("Enter new diagnosis: ");
        newDiag = sc.nextLine(); 
        System.out.println("Enter new prescription: "); 
        newMed= sc.nextLine();
        System.out.println("Enter new prescribed quantity: ");
        newQty = sc.nextLine();
        System.out.println("Enter new treatment plan: ");
        newPlan = sc.nextLine();

        newEntry[6] = newDiag;
        newEntry[7] = newMed;
        newEntry[8] = newQty;
        newEntry[9] = newPlan;

        for (String i : newEntry) {
          System.out.println(i);
        }
 
        // need dependency from inventory system
        // TODO: local save

        confirm(); 
        clearScreen();
        System.out.println(menu);
        break;

        case 3: // view personal schedule
          int in1 = 4;
          clearScreen(); 
        String month = "01";

        doc.getScheduleInfo().printSchedule(month); 
        do {
          System.out.println("[1] Next");
          System.out.println("[2] Previous");
          System.out.println("[3] Exit");
          System.out.print("Enter option (1-3): ");
          in1 = Sanitise.readInt(1, 3, 4);
          switch (in1) {
            case 1:
            if (Integer.valueOf(month) + 1 == 13) 
            System.out.println("[-] Already at end of year"); 
            else {
              int tmp = Integer.valueOf(month) + 1;
              if (tmp < 10)
              month = "0" + String.valueOf(tmp);
              else 
              month = String.valueOf(tmp);
              clearScreen();
              doc.getScheduleInfo().printSchedule(month);
            }
            break;

            case 2:
            if (Integer.valueOf(month) - 1 == 0)
            System.out.println("[-] Already at start of year");
            else {
              int tmp = Integer.valueOf(month) - 1;
              if (tmp < 10)
              month = "0" + String.valueOf(tmp);
              else 
              month = String.valueOf(tmp);
              clearScreen();
              doc.getScheduleInfo().printSchedule(month);
            }
            break;
            case 3:
            break;
            default:
            System.out.println("[-] Invalid option. Try again.");
            break;
          }
        } while(in1 != 3);
          
          clearScreen();
          System.out.println(menu);
          break;

        case 4: // set availability
          clearScreen();
          System.out.println("========[ Update Schedule ]========");
          String date = null;
          do {
            System.out.print("Enter the date to modify: ");
            try {
              date = Sanitise.readDate(); 
              break;
            } catch (Exception e) {
              System.out.println("[-] Invalid date. Try again");
            }
          } while (true);
           
          doc.getScheduleInfo().displayDay(date);
        
          AvailStatus option;
          int in4 = 3;
          
          do { 
            System.out.println("\n[!] Choose option:");
            System.out.println("[1] Mark a slot as available");
            System.out.println("[2] Mark a slot as unavailable");
            System.out.print("Enter option (1-2): ");
            in4 = Sanitise.readInt(1,2,3);
            if (in4 == 3)
              System.out.println("[-] Invalid option. Try again");
          } while (in4 == 3);
          
          switch (in4) {
            case 1:
              option = AvailStatus.OPEN;
              break;
            case 2:
              option = AvailStatus.CLOSE;
              break;
            default:
              System.out.println("[-] in updateSchedule(): unkown option");
              return;
          }

          do { 
            System.out.println("\n[!] Choose time slot base on the numbering given");
            System.out.print("Enter option (1-7): ");
            in4 = Sanitise.readInt(1,7,8);
            if (in4 == 8)
              System.out.println("[-] Invalid option. Try again");
          } while (in4 == 8);

          String slot;

          switch (in4) {
            case 1:
              slot = "0900-1000";
              break;
            case 2:
              slot = "1000-1100";
              break;
            case 3:
              slot = "1100-1200";
              break;
            case 4:
              slot = "1300-1400";
              break;
            case 5:
              slot = "1400-1500";
              break;
            case 6:
              slot = "1500-1600";
              break;
            case 7:
              slot = "1600-1700";
              break;
            default:
              System.out.println("[-] in updateSchedule(): unknown slot");
              return;
          }

          if(doc.getScheduleInfo().setAvailability(date, slot, null, option)) {
            clearScreen();
            doc.getScheduleInfo().displayDay(date);
            System.out.println("\n[+] Schedule updated successfully");
          }
          else {
            System.out.println("\n[-] Schedule update failed");
          }

          confirm();
          clearScreen();
          System.out.println(menu);
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
    System.out.println(menu);
    do {
      System.out.print("Enter option (1-6): ");
      choice = Sanitise.readInt(1, 6, 7);	
      switch (choice) {
        case 1:
          staffManagement_menu();
          System.out.println(menu);
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

  public void updatePersonalInfo_menu(User inputUser) {
    clearScreen();
    inputUser.getBasicInfo().displayInfo();
    User backup = inputUser.copy();
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
              inputUser.getBasicInfo().setFirstName(newFirstName);
              System.out.println("[+] Changed first name to: " + inputUser.getBasicInfo().getFirstName());
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
              inputUser.getBasicInfo().setLastName(newLastName);
              System.out.println("[+] Changed last name to: " + inputUser.getBasicInfo().getLastName());
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
          inputUser.getBasicInfo().setGender(newGender);
          System.out.println("[+] Changed gender to: " + inputUser.getBasicInfo().getGender());
          changed = true;
          break;

        case 4:
          String newDOB = null;
          do {
            try {
              System.out.println("[!] Change your date of birth: ");
              newDOB = Sanitise.readDOB();
              inputUser.getBasicInfo().setDOB(newDOB);
              System.out.println("[+] Changed date of birth to: " + inputUser.getBasicInfo().getDOB());
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
              inputUser.getBasicInfo().setPhoneNumber(newPhoneNumber);
              System.out.print("[+] Changed phone number to: " + inputUser.getBasicInfo().getPhoneNumber());
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
              inputUser.getBasicInfo().setEmailAddress(newEmail);
              System.out.println("[+] Changed email address to: " + inputUser.getBasicInfo().getEmailAddress());
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
          inputUser.getBasicInfo().displayInfo(); 
          do {
            System.out.print("Enter \"1\" to quit: "); 
            in = Sanitise.readInt(1, 1, 2);
          } while(in != 1);
          choice = 9;
          changed = false;
          inputUser.getBasicInfo().update();
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
            inputUser.getBasicInfo().displayInfo(); 
            boolean done = false;
            do {
              System.out.println("\n1. Confirm");
              System.out.println("2. Cancel");
              System.out.print("Enter option (1-2): ");
              confirm = Sanitise.readInt(1, 2, 3);
              switch(confirm) {
                case 1:
                  inputUser.getBasicInfo().update();
                  System.out.println("\n[+] Changes Updated.");
                  do {
                    System.out.print("Enter \"1\" to quit: "); 
                    in = Sanitise.readInt(1, 1, 2);
                  } while(in != 1);
                  done = true;
                  break;
                case 2:
                  inputUser = backup;
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
    final String menu = 
      "================[ Staff Management Menu ]==================\n" +
      "[1] View Current Staff\n" +
      "[2] Edit Staff Information\n" +
      "[3] Add Staff\n" +
      "[4] Remove Staff\n" +
      "[5] Exit this page\n"; 
    System.out.println(menu);
    do {
      System.out.print("Enter option (1-5): ");  
      choice = Sanitise.readInt(1, 5, 6); 
      switch(choice) {
        case 1:
          clearScreen();
        int option1 = 6;
        final String prompt1 = 
        "================[ Filter Options ]==================\n" +
        "[1] Filter by ID Number\n" +
        "[2] Filter by Role\n" +
        "[3] Filter by Gender\n" +
        "[4] Filter by Age\n" +
        "[5] Display All Staff\n" +
        "[6] Exit";
        System.out.println(prompt1);
          do{
          System.out.print("\nEnter filter option (1-6): ");
          option1 = Sanitise.readInt(1,6,7);
          switch(option1) {
            case 1:
              clearScreen();
              sms.display(FilterOption.ID);
              confirm();
              clearScreen();
              System.out.println(prompt1); 
              break;
            case 2:
              clearScreen();
              sms.display(FilterOption.ROLE);
              confirm();
              clearScreen();
              System.out.println(prompt1);
              break;
            case 3:
              clearScreen();
              sms.display(FilterOption.GENDER);
              confirm();
              clearScreen();
              System.out.println(prompt1);
              break;
            case 4:
              clearScreen();
              sms.display(FilterOption.AGE);
              confirm();
              clearScreen();
              System.out.println(prompt1);
              break;
            case 5:
              clearScreen();
              sms.display(FilterOption.ALL);
              confirm();
              clearScreen();
              System.out.println(prompt1);
              break;
            case 6:
              break;
            default:
              System.out.println("[-] Invalid option. Try agiain");
              break;
            }
          } while (option1 != 6);
          clearScreen();
          System.out.print(menu);
          break;
        case 2:
          final String prompt2 = "============================[ Edit Staff Information ]=============================\n" +
          "[!] You will be editing staff members' personal information \n" +
          "[!] Follow the steps by entering the staff's information (NOT your information)\n";

          String id= null;  
          User inputUser;
          System.out.println(prompt2);
          System.out.print("Enter staff's ID number: "); 
          try {
            id = Sanitise.readID();
            inputUser = sms.findStaff(Integer.valueOf(id));
            if (inputUser == null) {
              System.out.println("[-] Failed to edit staff information. Staff does not exist.");
              confirm();
            }
            else {
              updatePersonalInfo_menu(inputUser); 
            }
          } catch (Exception e) {
            e.getMessage();
            System.out.println("[-] Failed to edit staff information. ID number contains illegal characters. Only numerals allowed");
            confirm();
          }
          clearScreen();
          System.out.print(menu);
          break;
        case 3:
          final String prompt3 = "============================[ Staff Registration ]=============================\n" +
          "[!] You will be adding staff members to the database\n" +
          "[!] Follow the steps by entering the staff's information (NOT your information)\n" +
          "[1] Register a doctor\n" +
          "[2] Register a pharmacist\n";
          int choice3 = 3;
          String inputRole = null;
          System.out.println(prompt3);
          do {
            System.out.print("Enter option (1-2): ");
            choice3 = Sanitise.readInt(1,2,3);
            if (choice3 == 1) { inputRole = "DOCTOR"; break; }
            else if (choice3 == 2) { inputRole = "PHARMACIST"; break; }
            else System.out.println("[-] Invalid option. Try again");
          } while (true);
          register_menu(inputRole);
          clearScreen();
          System.out.print(menu);
          break;
        case 4:
          do {
            try {
              System.out.print("Enter the hospital ID of the staff to be removed: "); 
              idEntered = Integer.valueOf(Sanitise.readID());
              break;
            } catch(Exception e){ 
              System.out.println("[-] ID contains illegal characters. Only numerals allowed.");
            }
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
          confirm(); 
          clearScreen();
          System.out.print(menu);
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

  public void confirm() {
    System.out.print("\n[!] Enter \"1\" to continue: ");
    int ans = 2;
    do {
      ans = Sanitise.readInt(1, 1, 2);
      if (ans == 1) break;
      else System.out.print("[!] Enter \"1\" to continue: ");
    } while(true);
  }

  private AccountSystem acc;
  private User user;
}
