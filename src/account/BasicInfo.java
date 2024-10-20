package account;

import java.io.File;

public class BasicInfo {
  public BasicInfo() {};
  public BasicInfo(String[] info) {
    summary = info;
    init(summary);
  }
  
  // public void retrieve(String role, String id) { 
    // init(summary);
  // }

  public void init(String[] summary) {
    hospitalID = summary[0];
    firstName = summary[1];
    lastName = summary[2];
    gender = summary[3];
    dateOfBirth = summary[4];
    phoneNumber = summary[5];
    emailAddress = summary[6];
  }

  public String[] getSummary() { return summary; }
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
}
