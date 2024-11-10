package users;

/**
 * Represents the pharmacist user, extends the {@link User} class
 */
public class Pharmacist extends User {
  /**
   * Constructs pharmacist object using the hospital ID
   *
   * @param hospitalID the id number that identifies the pharmacist
   */
  public Pharmacist(String hospitalID) {
    super(hospitalID, "PHARMACIST");
  }
}
