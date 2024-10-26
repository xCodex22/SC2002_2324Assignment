public class StaffManagementSystem {

  public void display(FilterOption option) {
    switch(option.getValue()) {
    }
  }

  public void filter(FilterOption option) {
    // filtering by age does not require dynamically changing data
    // rather, just calculate offset using the statically stored date of birth
    /*
     * - Manage hospital staff (doctors and pharmacists) by adding, updating, or 
      removing staff members. 
      - Display a list of staff filtered by role, gender, age, etc.
    */
  }

  public void removeStaff(int hospitalID, string role) throws Exception {
    // 1. remove basic info
    // 2. remove account 
    switch(role) {
      case "DOCTOR":
        break;
      case "PHARMACIST":
        break;
      default:
        throw new Exception("[-] in removeStaff(): Unknown Role"); 
        return;
    }
  }

}
