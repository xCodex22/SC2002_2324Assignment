package account;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Class for managing basic personal information of users
 */
public class BasicInfo {
  /**
   * Default constructor for creating an empty BasicInfo object.
   */
  public BasicInfo(){}
  
  /**
   * Constructs a BasicInfo object for a user with the specified hospital ID and role.
   * 
   * @param hospitalID The ID of the hospital account.
   * @param role The role of the user (e.g., patient, doctor, pharmacist).
   * @throws Exception if the hospital ID is not found.
   */
  public BasicInfo(String hospitalID, String role) throws Exception {
    this.hospitalID = hospitalID;
    this.role = role;
    boolean found = false;
    File file = null;
    String[] line = null;
      try {
        switch(role) {
          case "PATIENT":
            file = new File("../../data/BasicInfoDB/patient.csv");
            break;
          case "DOCTOR":
            file = new File("../../data/BasicInfoDB/doctor.csv");
            break;
          case "PHARMACIST":
            file = new File("../../data/BasicInfoDB/pharmacist.csv");
            break;
          default:
            break;
        }
        Scanner read = new Scanner(file);
        while(read.hasNextLine()) {
          line = read.nextLine().split(",");
          if (line[0].equals(hospitalID)) { found = true; break; }	
        }
        read.close();
        if (!found)
          throw new Exception("[-] in BasicInfo(): No such hospital ID exists");
        summary = line;
        firstName = line[1];
        lastName = line[2];
        gender = line[3];
        dateOfBirth = line[4];
        phoneNumber = line[5];
        emailAddress = line[6];
      } catch(FileNotFoundException e) {
       System.out.println("[-] No such data exist in data base"); 
    }
  }

  /**
   * Make a copy of the current user by duplicating the member fields
   * @return a copy of the BasicInfo
   */
  public BasicInfo copy() {
    BasicInfo copy = new BasicInfo();
    copy.setID(hospitalID);
    copy.setRole(role);
    copy.setFirstName(firstName); 
    copy.setLastName(lastName);
    copy.setGender(gender);
    copy.setDOB(dateOfBirth);
    copy.setPhoneNumber(phoneNumber);
    copy.setEmailAddress(emailAddress);
    return copy;
  }

  /**
   * Displays the personal information of the user
   */
  public void displayInfo() {
    System.out.println("===========[ Personal Information ]============");
    System.out.println("[-] ID Number: " + summary[0]);
    System.out.println("[-] Account Type: " + this.role);
    System.out.println("[1] First Name: " + summary[1]);
    System.out.println("[2] Last Name: " + summary[2]);
    System.out.println("[3] Gender: " + summary[3]);
    System.out.println("[4] Date of Birth: " + summary[4]);
    System.out.println("[5] Phone Number: " + summary[5]);
    System.out.println("[6] Email Address: " + summary[6]);
  }

   /**
    * Updates the personal information of the user.
    * @return true if the update is successful, false otherwise.
    */
  public boolean update() {
    String newInfo = String.join(",", summary);
    String dir = "../../data/BasicInfoDB/";
    String loc = null;
    String tmp_loc = null;
    try {
      List<String> content = null;
      switch(role) {
        case "PATIENT":
          loc = "patient.csv";
          tmp_loc = "patient~.csv"; 
          break;
        case "DOCTOR":
          loc = "doctor.csv";
          tmp_loc = "doctor~.csv";
          break;
        case "PHARMACIST":
          loc = "pharmacist.csv";
          tmp_loc = "pharmacist~.csv";
          break;
        default:
          break;
        }
    
      content = new ArrayList<>(Files.readAllLines(Paths.get(dir+loc), StandardCharsets.UTF_8));

      for (int i = 0; i < content.size(); i++) {
        if(content.get(i).substring(0,hospitalID.length()).equals(hospitalID)) {
          content.set(i, newInfo);
          break;
        }
      }
      Files.write(Paths.get(dir+tmp_loc), content, StandardCharsets.UTF_8);
      Files.copy(Paths.get(dir+tmp_loc), Paths.get(dir+loc), StandardCopyOption.REPLACE_EXISTING);
      Files.delete(Paths.get(dir+tmp_loc));
    } catch(IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Deletes the user's personal information from the system.
   * 
   * @return true if the deletion is successful, false otherwise.
   */
  public boolean delete() {
    String dir = "../../data/BasicInfoDB/";
    String loc = null;
    String tmp_loc = null;
    try {
      List<String> content = null;
      switch(role) {
        case "PATIENT":
          loc = "patient.csv";
          tmp_loc = "patient~.csv"; 
          break;
        case "DOCTOR":
          loc = "doctor.csv";
          tmp_loc = "doctor~.csv"; 
          break;
        case "PHARMACIST":
          loc = "pharmacist.csv";
          tmp_loc = "pharmacist~.csv"; 
          break;
        default:
          break;
        }

      content = new ArrayList<>(Files.readAllLines(Paths.get(dir+loc), StandardCharsets.UTF_8));
      List<String> newContent = new ArrayList<>();

      for (int i = 0; i < content.size(); i++) {
        if(content.get(i).substring(0,hospitalID.length()).equals(hospitalID)) continue;
        newContent.add(content.get(i));
      }

      Files.write(Paths.get(dir+tmp_loc), newContent, StandardCharsets.UTF_8);
      Files.copy(Paths.get(dir+tmp_loc), Paths.get(dir+loc), StandardCopyOption.REPLACE_EXISTING);
      Files.delete(Paths.get(dir+tmp_loc));
    } catch(IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * sets the first name of user
   *
   * @param firstName new first name
   */
  public void setFirstName(String firstName) { this.firstName = firstName; summary[1] = firstName;}

  /**
   * sets the last name of user
   *
   * @param lastName new last name
   */
  public void setLastName(String lastName) { this.lastName = lastName; summary[2] = lastName; }

  /**
   * sets the gender of user
   *
   * @param gender new gender of user
   */
  public void setGender(String gender) { this.gender = gender; summary[3] = gender; }

  /**
   * sets the date of birth of user
   *
   * @param dob the new date of birth
   */
  public void setDOB(String dob) { dateOfBirth = dob; summary[4] = dob; }

  /**
   * sets the phone number of user
   *
   * @param phoneNumber the new phone number of user
   */
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; summary[5] = phoneNumber; }

  /**
   * sets the email address of user
   *
   * @param emailAddress the new email address
   */
  public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; summary[6] = emailAddress; }

  /**
   * sets the id of user
   *
   * @param hospitalID of the user
   */
  private void setID(String hospitalID) { this.hospitalID = hospitalID; summary[0] = hospitalID; }

  /**
   * sets the role of user
   *
   * @param role the new role of user
   */
  private void setRole(String role) { this.role = role; }
 
  /**
   * gets the basic information as a list of summary
   *
   * @return the summary
   */
  public String[] getSummary() { return summary; }

  /**
   * gets the id of user
   *
   * @return the id
   */
  public String getID() { return hospitalID; }

  /**
   * gets the gender of user
   *
   * @return the gender
   */
  public String getGender() { return gender; }

  /**
   * gets the role of user
   *
   * @return the role of user
   */
  public String getRole() { return role; }

  /**
   * gets the first name of user
   *
   * @return the first name of user
   */
  public String getFirstName() { return firstName; }
 
  /**
   * gets the last name of user
   *
   * @return the last name of user
   */
  public String getLastName() { return lastName; }


  /**
   * gets the date of birth of user
   *
   * @return the date of birth of user
   */
  public String getDOB() { return dateOfBirth; }


  /**
   * gets the phone number of user
   *
   * @return the phone number
   */
  public String getPhoneNumber() { return phoneNumber; }


  /**
   * gets the email address of user
   *
   * @return the email address
   */
  public String getEmailAddress() { return emailAddress; }

  private String[] summary = new String[7];
  private String hospitalID = null;
  private String firstName = null;
  private String lastName = null;
  private String gender = null;
  private String dateOfBirth = null;
  private String phoneNumber = null;
  private String emailAddress = null;
  private String role = null;
}
