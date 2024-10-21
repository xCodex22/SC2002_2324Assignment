package account;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BasicInfo {
  public BasicInfo(String hospitalID, String role) throws Exception {
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
  };
  
  public String[] getSummary() { return summary; }
  public String getID() { return hospitalID; }
  public String getGender() { return gender; }
  public String getRole() { return role; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }

  private String[] summary = null;
  private String hospitalID = null;
  private String firstName = null;
  private String lastName = null;
  private String gender = null;
  private String dateOfBirth = null;
  private String phoneNumber = null;
  private String emailAddress = null;
  private String role = null;
}
