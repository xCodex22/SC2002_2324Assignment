package users;

import account.MedicalInfo;

public class Patient extends User{
  public Patient(String hospitalID) {
    super(hospitalID, "PATIENT");
    try {
      medicalInfo = new MedicalInfo(hospitalID);
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
    }
  }

  public MedicalInfo getMedicalInfo() { return medicalInfo; }

  private MedicalInfo medicalInfo;
}
