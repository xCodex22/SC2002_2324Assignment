package users;

/**
 * Represents the administrator, extends {@link User} class
 */
public class Administrator extends User {
  /**
   * constructor that constructs the administrator class using its ID
   * @param hospitalID the id number tagged to administrator
   */
  public Administrator(String hospitalID) {
    super(hospitalID, "ADMIN");
  }
}
