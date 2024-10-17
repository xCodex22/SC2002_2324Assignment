package HospitalManagementSystem.src.users;

import java.util.List;

import HospitalManagementSystem.src.appointments.Appointment;

import java.util.ArrayList;

public class Doctor extends User {
    // Attributes
    private String staffID;
    private List<Appointment> appointments;
    private List<Patient> patientsUnderCare;

    // Constructor
    public Doctor(int hospitalID, String staffID, String specialization) {
        super(hospitalID);
        this.staffID = staffID;
        this.appointments = new ArrayList<>();
        this.patientsUnderCare = new ArrayList<>();
    }

    // Methods:
    // 1. View Patient Medical Records
    public void viewPatientRecords(Patient patient) {
        System.out.println("Medical Record for: " + patient.getName());
        System.out.println("PatientID: " + patient.getPatientID());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Contact Number: " + patient.getContactNum());
        System.out.println("Blood Type: " + patient.getBloodType());
        System.out.println("Medical Record: " + patient.getMedicalReport());
    }

    // 2. Update Patient Medical Records:

    // 3. View Personal Appointment

    // 4. Set availbility for Appointments

    // Setters/ Getters
    // Getters/ Setters
    public String getStaffID() {
        return staffID;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Patient> getPatientsUnderCare() {
        return patientsUnderCare;
    }
}
