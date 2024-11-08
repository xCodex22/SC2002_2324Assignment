package account;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
 * Class for managing basic personal information of users
 */
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
          case "DOCTOR":
            file = new File("../data/BasicInfoDB/doctor.csv");
            break;
          case "PHARMACIST":
            file = new File("../data/BasicInfoDB/pharmacist.csv");
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

  /*
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

  public boolean update() {
    String newInfo = String.join(",", summary);
    String dir = "../data/BasicInfoDB/";
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

  public boolean delete() {
    String dir = "../data/BasicInfoDB/";
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
