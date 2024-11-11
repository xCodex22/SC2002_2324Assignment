package staffmanagement;

import java.time.Year;
import users.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import account.AccountSystem;
import account.BasicInfo;
import menu.Sanitise;

/**
 * class for staff management
 */
public class StaffManagementSystem {

  /**
   * Constructor that initialises all pharmacists and doctors into array lists
   */
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

  /**
   * Display the list of staffs based on filter options
   *
   * @param option the filter option 
   *
   */
  public void display(FilterOption option) {
    switch(option) {
      case ALL:
        System.out.println("\n==================[ List of Doctors ]======================\n");
        for (User i : doctorArray) { i.getBasicInfo().displayInfo(); }
        System.out.println("\n================[ List of Pharmacists ]====================\n");
        for (User i : pharmacistArray) { i.getBasicInfo().displayInfo(); }
        break;
      case GENDER:
        String gender = Sanitise.readGender();
        System.out.println("\n==================[ Filter: Gender  ]======================\n");
        System.out.println("\n==================[ List of Doctors ]======================\n");
          for (User i : doctorArray) {
            if (i.getBasicInfo().getGender().equals(gender))
              i.getBasicInfo().displayInfo();
          }
        System.out.println("\n==================[ List of Pharmacists ]======================\n");
          for (User i : pharmacistArray) {
            if (i.getBasicInfo().getGender().equals(gender))
              i.getBasicInfo().displayInfo();
          }
        break;
      case ROLE:
        String role = Sanitise.readRole();
        if (role == "DOCTOR") {
          System.out.println("\n==================[ List of Doctors ]======================\n");
          for (User i : doctorArray) { i.getBasicInfo().displayInfo(); }
        }
        else {
          System.out.println("\n================[ List of Pharmacists ]====================\n");
          for (User i : pharmacistArray) { i.getBasicInfo().displayInfo(); }
        }
        break;
      case AGE:
        System.out.print("\n[!] Enter the age to be filtered: ");
        String age = null;
        int year = Year.now().getValue();
        try {
          age = Sanitise.readAge();
          System.out.println("\n==================[ List of Doctors ]======================\n");
          for (User i : doctorArray) { 
            if(year - Integer.valueOf(i.getBasicInfo().getDOB().substring(6, 10)) == Integer.valueOf(age))
              i.getBasicInfo().displayInfo();
          }
          System.out.println("\n==================[ List of Pharmacists ]======================\n");
          for (User i : pharmacistArray) { 
            if(year - Integer.valueOf(i.getBasicInfo().getDOB().substring(6, 10)) == Integer.valueOf(age))
              i.getBasicInfo().displayInfo();
          }
        } catch(Exception e) {
          e.getMessage();
        }
        break;
      case ID:
        System.out.print("\n[!] Enter the ID number to be filtered: ");
        String id = null;
        try {
          id = Sanitise.readID(); 
          User ans = findStaff(Integer.valueOf(id));
          if (ans == null)
            System.out.println("[-] Staff is not found in the data base.");
          else
            ans.getBasicInfo().displayInfo();
        } catch(Exception e) {
          e.getMessage();
        }
        break;
      default:
        System.out.println("[-] In staffmanagement.display(): unknown FilterOption");
        break;
    }
  }

  /**
   * Finding of staff
   *
   * @param hospitalID the integer value of hospital ID
   * 
   * @return the staff user class
   */
  public User findStaff(int hospitalID) {
        User res = null;
        res = binSearch(doctorArray, hospitalID); 
        if (res != null) return res;
        else return binSearch(pharmacistArray, hospitalID);
  }

  /**
   * Binary search of staff 
   *
   * @param A the array A for list of users
   * @param target the target hospital ID
   *
   * @return the staff user object 
   *
   */
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

  /**
   * removes staff from the data base via their hospital ID
   *
   * @param hospitalID the id of staff
   *
   * @return whether the operation is successful
   */
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
    if(!acc.deleteAccount(hospitalID)) 
      return false;
    try {
      BasicInfo basicInfo = new BasicInfo(hospitalID, role); 
      if (!basicInfo.delete()) 
        return false;
    } catch(Exception e) { 
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private List<User> doctorArray;
  private List<User> pharmacistArray;
}
