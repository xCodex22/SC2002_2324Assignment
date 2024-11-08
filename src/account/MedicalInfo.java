package account;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import users.*;

public class MedicalInfo {
  public MedicalInfo(String hospitalID) throws Exception {
    File file = null;
    String[] line = null;
    List<String[]> record = new ArrayList<>();
    String path = null;

    try {
      path = "../data/MedicalRecordsDB/" + hospitalID + ".csv";
      file = new File(path);
      Scanner read = new Scanner(file);
      read.nextLine();
      while(read.hasNextLine()) {
        line = read.nextLine().split(",");
        record.add(line);
      }
      if (line == null)
        this.bloodType = "undiagnosed";
      else 
        this.bloodType = line[1];
      this.ID = hospitalID;
      medicalRecords = record;
      this.filePath = path;
    } catch (FileNotFoundException e) {
     throw new Exception("[-] User does not exist"); 
    }
  }

  public void updateInfo(User doctor, String newEntry) {
    if (doctor instanceof Doctor) {
       try {
          FileWriter writer = new FileWriter(this.filePath, true);  
          writer.write(newEntry+"\n");
          writer.close();
       } catch (IOException e) {
        System.out.println("[-] Failed to update patient medical records. Record does not exist in data base");
      }
    }
    else {
      System.out.println("[-] Current user does not have permission to update medical info");
    }
  }

  public void displayInfo(User user) {
    System.out.println("===========[ Medical Information ]============");
    System.out.println("[-] Blood Type: " + this.bloodType);
    System.out.println("=============[ Medical Records ]==============");
    if (user instanceof Patient) {
      for (String[] i : medicalRecords) {
        System.out.println("[-] Service date: " + i[2]);
        System.out.println("[-] Doctor assigned: Dr. " + i[5]);
        System.out.println("[-] Service administered: " + i[3]);
        System.out.println("[-] Diagnosis result: " + i[6]);
        System.out.println("[-] Medication administered: " + i[7] + " " + i[8]);
        System.out.println("[-] Treatment plan administered: " + i[9]);
        System.out.println("----------------------------------------------");
      }
    } else if (user instanceof Doctor) {
       for (String[] i : medicalRecords) {
          System.out.println("[-] Service date: " + i[2]);
          System.out.println("[-] Service administered: " + i[3]);
          System.out.println("[-] Doctor assigned: Dr. " + i[5]);
          System.out.println("[-] Doctor ID: " + i[4]); 
          System.out.println("[-] Diagnosis result: " + i[6]);
          System.out.println("[-] Medication administered: " + i[7] + " " + i[8]);
          System.out.println("[-] Treatment plan administered: " + i[9]);
          System.out.println("[-] Memo: " + i[10]);
          System.out.println("----------------------------------------------");
      }
    } else {
      System.out.println("[-] Access Denied");
    }
  }

  public String getBloodType() { return bloodType; }
  public List<String[]> getMedicalRecords() { return medicalRecords; }

  private String bloodType = null;
  private String ID;
  private String filePath = null;
  private List<String[]> medicalRecords;
}
