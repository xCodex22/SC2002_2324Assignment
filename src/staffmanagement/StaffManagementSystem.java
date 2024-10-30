package staffmanagement;

import users.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class StaffManagementSystem {
  public StaffManagementSystem() {
    List<User> d = new ArrayList<User>();
    List<User> p = new ArrayList<User>();

    try { 
      File doctorData = new File("../data/BasicInfoDB/doctor.csv");
      Scanner read = new Scanner(doctorData);
      read.nextLine();
      while(read.hasNextLine()) {
        String[] line = read.nextLine().split(",");
        d.add(new Doctor(line[0])); 
      }
      read.close();
      File pharmacistData = new File("../data/BasicInfoDB/pharmacist.csv");
      read = new Scanner(pharmacistData);
      read.nextLine();
      while(read.hasNextLine()) {
        String[] line = read.nextLine().split(",");
        p.add(new Pharmacist(line[0])); 
      }
      read.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }

    doctorArray = d;
    pharmacistArray = p;
  }

  /* public void display(FilterOption option) {
    switch(option.getValue()) {
    }
  } */

  public void display() {
    System.out.println("==================[ List of Doctors ]======================\n");
    for (User i : doctorArray) { i.getBasicInfo().displayInfo(); }
    System.out.println("\n================[ List of Pharmacists ]====================\n");
    for (User i : pharmacistArray) { i.getBasicInfo().displayInfo(); }
  }

  /* public void filter(FilterOption option) {
    // filtering by age does not require dynamically changing data
    // rather, just calculate offset using the statically stored date of birth
   *
      - Manage hospital staff (doctors and pharmacists) by adding, updating, or 
      removing staff members. 
      - Display a list of staff filtered by role, gender, age, etc.
  }*/

  public User findStaff(int hospitalID) {
        User res = null;
        res = binSearch(doctorArray, hospitalID); 
        if (res != null) return res;
        else return binSearch(pharmacistArray, hospitalID);
  }

  private User binSearch(List<User> A, int target) {
    int hi = A.size() - 1; int lo = 0; int mi = 0;
    while (lo <= hi) {
      mi = (lo+hi)/2;
      if (Integer.valueOf(A.get(mi).getBasicInfo().getID()) == target) 
        return A.get(mi);
      else if (Integer.valueOf(A.get(mi).getBasicInfo().getID()) < target) 
        lo = mi+1;
      else
        hi = mi-1;
    }
    return null;
  }

  public void removeStaff(int hospitalID, String role) throws Exception {
    // 1. remove basic info
    // 2. remove account 
    // 3. also required to remove schedule etc
    switch(role) {
      case "DOCTOR":
        break;
      case "PHARMACIST":
        break;
      default:
        throw new Exception("[-] in removeStaff(): Unknown Role"); 
    }
  }

  public void addStaff(int hospitalID, String role) throws Exception {}

  private List<User> doctorArray;
  private List<User> pharmacistArray;
}
