package staffmanagement;

import users.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import account.AccountSystem;
import account.BasicInfo;
import menu.Sanitise;

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

  public void display(FilterOption option) {
    switch(option) {
      case FilterOption.ALL:
        System.out.println("==================[ List of Doctors ]======================\n");
        for (User i : doctorArray) { i.getBasicInfo().displayInfo(); }
        System.out.println("\n================[ List of Pharmacists ]====================\n");
        for (User i : pharmacistArray) { i.getBasicInfo().displayInfo(); }
        break;
      case FilterOption.GENDER:
        String gender = Sanitise.readGender();
          for (User i : doctorArray) {
            if (i.getBasicInfo().getGender() == gender)
              i.getBasicInfo().displayInfo();
          }
          for (User i : pharmacistArray) {
            if (i.getBasicInfo().getGender() == gender)
              i.getBasicInfo().displayInfo();
          }
        break;
      case FilterOption.ROLE:
        break;
      default:
        System.out.println("[-] In staffmanagement.display(): unknown FilterOption");
        break;
    }
  }

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

  public boolean removeStaff(String hospitalID) {
    String role = null;
    for (User i : doctorArray) {
      if (i.getBasicInfo().getID().equals(hospitalID)) {
        doctorArray.remove(i); 
        role = "DOCTOR";
        break;
      }
    }

    for (User i : pharmacistArray) {
      if (i.getBasicInfo().getID().equals(hospitalID)) {
        pharmacistArray.remove(i); 
        role = "PHARMACIST";
        break;
      }
    }

    AccountSystem acc = new AccountSystem();
    if(!acc.deleteAccount(hospitalID)) {
      System.out.println("acc.deleteAccount() failed");
      return false;
    }

    System.out.println("Role detected is: " + role);
    
    try {
      BasicInfo basicInfo = new BasicInfo(hospitalID, role); 
      if (!basicInfo.delete()) {
        System.out.println("basicInfo.delete() failed");
        return false;
      }
    } catch(Exception e) { 
      e.printStackTrace();
      System.out.println("failed to create basicInfo object");
      return false;
    }
    return true;
  }

  public void addStaff(String role)  {}

  private List<User> doctorArray;
  private List<User> pharmacistArray;
}
