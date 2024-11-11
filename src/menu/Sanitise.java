package menu;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

/**
 * A class providing class methods for santising user input during menu interaction.
 */
public class Sanitise {

  private Sanitise(){};

  /**
   * Sanitise int values from user input.
   * @param start the starting value of the range of int allowed
   * @param end the ending value of the range of int allowed
   * @param sanitised the sanitised value when input int fell out of range
   * @return the resultant sanitised value
   */

  public static int readInt(int start, int end, int sanitised) {
    int res = sanitised;
    Scanner sc = new Scanner(System.in);
    try {
      res = sc.nextInt();
      if (res < start || res > end) res = sanitised;
    } catch(InputMismatchException e) {
      res = sanitised;
    }
    return res;
  }

  /**
   * Sanitise name fields of user input to Camel Case, strips space and throws error if invalid
   * @return the sanitised name
   */
  public static String readName() throws Exception {
    try {
      Scanner sc = new Scanner(System.in);
      String res = sc.nextLine().trim();
      String[] parts = res.split("\\s+");
      if(!VALID_NAME.matcher(res).matches()) 
        throw new Exception("[-] Invalid Names. Remove Special Characters and Numbers. Try Again.");
      StringBuilder sanitisedName = new StringBuilder();
      for (String i : parts) {
        if (i.length() > 0) {
          String sanitisedPart = i.substring(0, 1).toUpperCase() + i.substring(1).toLowerCase();
          sanitisedName.append(sanitisedPart).append(" ");
        }
      }
      return sanitisedName.toString().trim();
    } catch(InputMismatchException e) {
      throw new Exception("[-] Illegal Characters. Try Again."); 
    }
  }

  /**
   * Sanitise date of birth to check if it satisfies the form of DD-MM-YYYY; does not check leap year nor age
   *
   * @return the santisied date of birth
   */
  public static String readDOB() throws Exception {
    String dob = null;

   final String dobExample = "[!] Example \n" + 
        "[1] Birthday is on 9th June 1942 \n" + 
        "[2] Enter \"09-06-1942\"\n";
    System.out.println("\nEnter your date of birth in DD-MM-YYYY format: "); 
    System.out.print(dobExample); 
    try {
      System.out.print("Enter here: ");
      Scanner sc = new Scanner(System.in);
      dob = sc.nextLine().trim().replaceAll("\\s+","");
      if (!VALID_DOB.matcher(dob).matches()) 
        throw new Exception("[-] Invalid Format. Follow the Example and Try Again.");
    } catch(InputMismatchException e) {
      throw new Exception("[-] Illegal Characters. Try Again.");
    }
    return dob;
  }

  /**
   * Sanitise reader input of date, same as DOB except without year, this is because the assumption is only for current year
   *
   * @return the sanitised date
   */
  public static String readDate() throws Exception {
    String ans = null;
    System.out.println("\nEnter date in DD-MM format: "); 
    try {
      System.out.print("Enter here: ");
      Scanner sc = new Scanner(System.in);
      ans= sc.nextLine().trim().replaceAll("\\s+","");
      if (!VALID_DATE.matcher(ans).matches()) 
        throw new Exception("[-] Invalid Format. Try Again.");
    } catch(InputMismatchException e) {
      throw new Exception("[-] Illegal Characters. Try Again.");
    }
    return ans;
  }


  /**
   * Sanitise user input phone number and validate using regex; assume domestic phone number
   * @return the sanitised phone number
   */
  public static String readPhoneNumber() throws Exception {
    String res = null;
    try {
      Scanner sc = new Scanner(System.in);
      res = sc.nextLine().trim().replaceAll("\\s+","");
      if (!VALID_PHONE_NUMBER.matcher(res).matches()) throw new Exception("[-] Invalid Phone Number. Try Again.");
    } catch(InputMismatchException e) {
      throw new Exception("[-] Illegal Characters. Try Again.");
    }
    return res;
  }

  /**
   * Santise user input email address by validating format; does not check if email is real
   * @return the santised email address
   */
  public static String readEmailAddress() throws Exception {
    String res = null;
    try {
      Scanner sc = new Scanner(System.in);
      res = sc.nextLine().trim().replaceAll("\\s+","");
      if (!VALID_EMAIL_ADDRESS.matcher(res).matches()) throw new Exception("[-] Invalid Email Address. Try Again.");
    } catch(InputMismatchException e) {
      throw new Exception("[-] Illegal Characters. Try Again.");
    }
    return res.toLowerCase();
  }

  /**
   * Sanitise user input for gender by choosing discrete options
   * @return the sanitised gender field
   */
  public static String readGender() {
    int option = 4;
        do {
          System.out.println("\nEnter gender option: ");
          System.out.println("1. Male");
          System.out.println("2. Female");
          System.out.println("3. Others");
          System.out.print("Choose option (1-3): ");
          option = readInt(1, 3, 4);
          switch(option) {
            case 1:
              return "MALE";
            case 2:
              return "FEMALE";
            case 3:
              return "OTHERS";
            default :
              System.out.println("[-] Invalid option. Try again");
              break;
          }
        } while(option==4);
      return "OTHERS";
  }

  /**
   * Sanitise user input for role by choosing discrete options
   * @return the sanitised role field
   */
  public static String readRole() {
    int option = 4;
        do {
          System.out.println("\nEnter role option: ");
          System.out.println("1. Doctor");
          System.out.println("2. Pharmacist");
          System.out.print("Choose option (1-2): ");
          option = readInt(1, 2, 3);
          switch(option) {
            case 1:
              return "DOCTOR";
            case 2:
              return "PHARMACIST";
            default :
              System.out.println("[-] Invalid option. Try again");
          }
        } while(true);
  }


  /**
   * Sanitise user input for hospital ID entry by making sure it is numerals; doesn't check for existence 
   * @return the sanitised hospital ID number
   */
  public static String readID() throws Exception {
    String res = null;
    Scanner sc = new Scanner(System.in);
    res = sc.nextLine().trim().replaceAll("\\s+","");
    if (!VALID_ID.matcher(res).matches()) 
        throw new Exception("[-] Invalid ID Number. Remove Non-Numerals and Try Again.");
    return res;
  }

  /**
   * Sanitise user input for age, such that it is between 0-120 years old 
   * @return the sanitised age number
   */
  public static String readAge() throws Exception {
    String res = null;
    Scanner sc = new Scanner(System.in);
    res = sc.nextLine().trim().replaceAll("\\s+","");
    if (!VALID_AGE.matcher(res).matches()) 
        throw new Exception("[-] Invalid Age. Try Again.");
    return res;
  }
  
  public static final Pattern VALID_DATE = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])$");
  public static final Pattern VALID_AGE = Pattern.compile("^(1[01]?\\d|[1-9]?\\d)$"); 
  public static final Pattern VALID_ID = Pattern.compile("^[0-9]+");
  public static final Pattern VALID_NAME = Pattern.compile("^[A-Za-z\s]+$");
  public static final Pattern VALID_DOB = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$");
  public static final Pattern VALID_PHONE_NUMBER = Pattern.compile("^(8|9)\\d{7}$");
  public static final Pattern VALID_EMAIL_ADDRESS = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  public static final Pattern VALID_HOSPITAL_ID = Pattern.compile("^[0-9]$");
}
