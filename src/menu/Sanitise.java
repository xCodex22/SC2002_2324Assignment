package menu;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

/**
 * A class providing class methods for santising user input during menu interaction.
 */
public class Sanitise {
  /**
   * Sanitise int values from user input.
   * @param start the starting value of the range of int allowed
   * @param end the ending value of the range of int allowed
   * @param sanitised the sanitised value when input int fell out of range
   * @return the resultant sanitised value
   */
  static int readInt(int start, int end, int sanitised) {
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
  static String readName() throws Exception {
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
   * @return the santisied date of birth
   */
  static String readDOB() throws Exception {
    String dob = null;
    try {
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
   * Sanitise user input phone number and validate using regex; assume domestic phone number
   * @return the sanitised phone number
   */
  static String readPhoneNumber() throws Exception {
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
  static String readEmailAddress() throws Exception {
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
  
  public static final Pattern VALID_NAME = Pattern.compile("^[A-Za-z\s]+$");
  public static final Pattern VALID_DOB = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$");
  public static final Pattern VALID_PHONE_NUMBER = Pattern.compile("^(8|9)\\d{7}$");
  public static final Pattern VALID_EMAIL_ADDRESS = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
}