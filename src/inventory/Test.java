package inventory;

public class Test {
  public static void main(String args[]) {
    InventorySystem ins = new InventorySystem();
    ins.printAllMedName();
    try {
      Medicine med = new Medicine("Paracetamol");
      if(!ins.updateAlert(med, "0")) System.out.println("failed");
      System.out.println("pass");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
