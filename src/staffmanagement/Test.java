package staffmanagement;

public class Test {
  public static void main(String[] args) {
   StaffManagementSystem sms = new StaffManagementSystem();
    sms.display();

    if (sms.findStaff(12345) == null) System.out.println("\nNot found");
    else sms.findStaff(12345).getBasicInfo().displayInfo();

    if (sms.findStaff(12346) == null) System.out.println("\nNot found");
    else sms.findStaff(12346).getBasicInfo().displayInfo();

    if (sms.findStaff(12347) == null) System.out.println("\nNot found");
    else sms.findStaff(12347).getBasicInfo().displayInfo();
  }
}
