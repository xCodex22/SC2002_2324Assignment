package users;

import account.MedicalInfo;

/**
 * Represents the Patient class, extends {@link User} class
 */
public class Patient extends User{
  /**
   * Constructor for Patient, initalises basic info and medical info
   *
   * @param hospitalID the id number tagged to patient
   */
  public Patient(String hospitalID) {
    super(hospitalID, "PATIENT");
    try {
      medicalInfo = new MedicalInfo(hospitalID);
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
    }
  }

  /**
   * @return the medical information of Patient
   */
  public MedicalInfo getMedicalInfo() { return medicalInfo; }

  private MedicalInfo medicalInfo;
}
