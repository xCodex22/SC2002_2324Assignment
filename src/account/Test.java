package account;

public class Test {
  public static void main(String[] args) {
    try {
      MedicalInfo mi = new MedicalInfo("12345");
      mi.displayInfo();
    } catch (Exception e) {
      System.out.println("[-] Patient not found");
    }
  }
}
