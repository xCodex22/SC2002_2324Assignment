package inventory;

import users.*;

public class Test {
  public static void main(String args[]) {
    InventorySystem ins = new InventorySystem();
    ins.printAllMedName();
    try {
      Medicine med = new Medicine("Paracetamol");
      Pharmacist ph = new Pharmacist("12347"); 
      ins.requestReplenishment(med, "10", ph);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
