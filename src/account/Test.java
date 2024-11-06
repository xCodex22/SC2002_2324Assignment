package account;

public class Test {
  public static void main(String[] args) {
    try {
      System.out.println("");
      MedicalInfo mi = new MedicalInfo("12345");
      mi.displayInfo();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println("");
      MedicalInfo mi = new MedicalInfo("12355");
      mi.displayInfo();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println();
      MedicalInfo mi = new MedicalInfo("12356");
      mi.displayInfo();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }
}
