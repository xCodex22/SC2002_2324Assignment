package users;

import account.BasicInfo;

/**
 * The super class that defines the default behaviour of users in the system
 */
public class User {
  /**
   * Default constructor that initialises null fields for User
   */
  public User() {}
  
  /**
   * Constructor to initialise the user with the corresponding basic information field
   *
   * @param hospitalID of the user
   * @param role of user
   */
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

  /**
   * Get the basicinfo class of the initialised user
   *
   * @return basicInfo class of the initialised user
   */
  public BasicInfo getBasicInfo() {
    return basicInfo;
  }


  /**
   * Set the basicInfo of the current user with basicInfo of another user
   *
   * @param other The basicInfo class of another user
   */
  private void setBasicInfo(BasicInfo other) {
    basicInfo = other;
  }

  /**
   * Copy one user to another user by copying the basic information field
   */
  public User copy() {
    User copy = new User(); 
    copy.setBasicInfo(this.basicInfo.copy()); 
    return copy;
  }

  private String hospitalID = null;
  private String role = null;
  private BasicInfo basicInfo;
}
