package users;

import account.BasicInfo;

public class User {
  public User(String hospitalID, String role) {
    try {
      basicInfo = new BasicInfo(hospitalID, role);
    } catch(Exception e) {
      basicInfo = null;
      e.printStackTrace();
    }
  }

  public BasicInfo getBasicInfo() {
    return basicInfo;
  }

  private BasicInfo basicInfo;
}
