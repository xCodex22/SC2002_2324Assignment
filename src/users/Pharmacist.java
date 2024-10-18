package HospitalManagementSystem.src.users;

import java.util.HashMap;
import java.util.Map;

public class Pharmacist extends User{
    // Attributes
    private String pharmacistID;
    private Map<String, Integer> medicationInventory;

    // Constructor
    public Pharmacist(int hospitalID, String pharmacistID) {
        super(hospitalID);
        this.pharmacistID = pharmacistID;
        this.medicationInventory = new HashMap<>();
    }

    // Getters/ Setters
    public String getPharmacistID() {
        return pharmacistID;
    }

    public Map<String, Integer> getMedicationInventory() {
        return medicationInventory;
    }

    public void addMedicationToInventory(String medication, int stockLevel) {
        medicationInventory.put(medication, stockLevel);
    }
}
