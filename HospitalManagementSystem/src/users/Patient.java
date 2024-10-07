package HospitalManagementSystem.src.users;

import java.time.LocalDate;

public class Patient extends User{
    // Attributes
    private int patientID;
    private String name;
    private LocalDate dateOfBirth;
    private boolean gender;
    private int contactNum;
    private String contactEmail;
    private String bloodType;
    private String medicalRecord;

    // Constructor
    public Patient(int hospitalID, String password, int patientID, String name,
    LocalDate dateOfBirth,
    boolean gender,
    int contactNum,
    String contactEmail,
    String bloodType,
    String medicalRecord) {
        super(hospitalID, password);
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNum = contactNum;
        this.contactEmail = contactEmail;
        this.bloodType = bloodType;
        this.medicalRecord = medicalRecord;
    }
}
