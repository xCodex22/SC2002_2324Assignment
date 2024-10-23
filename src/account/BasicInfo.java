package account;

import menu.Sanitise;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BasicInfo {
  public BasicInfo(){}
  public BasicInfo(String hospitalID, String role) throws Exception {
    this.hospitalID = hospitalID;
    this.role = role;
    boolean found = false;
    File file = null;
    String[] line = null;
      try {
        switch(role) {
          case "PATIENT":
            file = new File("../data/BasicInfoDB/patient.csv");
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
          throw new Exception("[-] in BasicInfo(): Unexpected Error");
        summary = line;
        hospitalID = line[0];
        firstName = line[1];
        lastName = line[2];
        gender = line[3];
        dateOfBirth = line[4];
        phoneNumber = line[5];
        emailAddress = line[6];
      } catch(FileNotFoundException e) {
        e.printStackTrace();
    }
  }

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

  public void displayInfo() {
    System.out.println("===========[ Your Personal Information ]============");
    System.out.println("[-] ID Number: " + summary[0]);
    System.out.println("[1] First Name: " + summary[1]);
    System.out.println("[2] Last Name: " + summary[2]);
    System.out.println("[3] Gender: " + summary[3]);
    System.out.println("[4] Date of Birth: " + summary[4]);
    System.out.println("[5] Phone Number: " + summary[5]);
    System.out.println("[6] Email Address: " + summary[6]);
  }

  public void setFirstName(String firstName) { this.firstName = firstName; summary[1] = firstName;}
  public void setLastName(String lastName) { this.lastName = lastName; summary[2] = lastName; }
  public void setGender(String gender) { this.gender = gender; summary[3] = gender; }
  public void setDOB(String dob) { dateOfBirth = dob; summary[4] = dob; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; summary[5] = phoneNumber; }
  public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; summary[6] = emailAddress; }
  private void setID(String hospitalID) { this.hospitalID = hospitalID; summary[0] = hospitalID; }
  private void setRole(String role) { this.role = role; }
  
  public String[] getSummary() { return summary; }
  public String getID() { return hospitalID; }
  public String getGender() { return gender; }
  public String getRole() { return role; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getDOB() { return dateOfBirth; }
  public String getPhoneNumber() { return phoneNumber; }
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
