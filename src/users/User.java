package users;

import account.BasicInfo;

public class User {
  public User() {}
  public User(String hospitalID, String role) {
    this.hospitalID = hospitalID;
    this.role = role;
    try {
      basicInfo = new BasicInfo(hospitalID, role);
    } catch(Exception e) {
      basicInfo = null;
      System.out.println(e.getMessage()); 
    }
  }

  public BasicInfo getBasicInfo() {
    return basicInfo;
  }

  private void setBasicInfo(BasicInfo other) {
    basicInfo = other;
  }

  public User copy() {
    User copy = new User(); 
    copy.setBasicInfo(this.basicInfo.copy()); 
    return copy;
  }

  private String hospitalID = null;
  private String role = null;
  private BasicInfo basicInfo;
}
