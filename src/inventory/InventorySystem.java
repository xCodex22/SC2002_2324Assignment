package inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class InventorySystem implements IAddStock, IRemoveStock, IUpdateAlert{
  public InventorySystem() {
    List<Medicine> ans = new ArrayList<>();
    try {
      String path = "../data/InventoryDB/medicine.csv";
      File file = new File(path);
      Scanner sc = new Scanner(file);
      sc.nextLine();
      while (sc.hasNextLine()) {
        String[] line = sc.nextLine().split(",");
        Medicine med = new Medicine(line[0]);
        ans.add(med);
      } 
      this.medList = ans;
    }catch(Exception e) {
      System.out.println(e.getMessage());
      System.out.println("[-] System initialisation failed");
    }
  }

  public void printAllMedName() {
    for (Medicine i : medList) {
      System.out.println(i.name);
    }
  }

  public void printAllDetail() {
    try {
      System.out.println("\nColumns: Medicine Name, Unit, Max Stock, Current Stock, Low Stock Benchmark");
      String path = "../data/InventoryDB/medicine.csv";
      File file = new File(path);
      Scanner sc = new Scanner(file);
      sc.nextLine();
      while (sc.hasNextLine()) {
        String[] line = sc.nextLine().split(",");
        for (int i = 0; i < line.length; i++) {
          System.out.print(line[i] + " "); 
        }
        System.out.println();
      } 
    }catch(Exception e) {
      System.out.println(e.getMessage());
    }

  }

  public boolean addStock(Medicine med, String offset) {
    if (Integer.valueOf(offset) + Integer.valueOf(med.currentStock) > Integer.valueOf(med.initialStock)) {
      med.initialStock = String.valueOf(Integer.valueOf(offset) + Integer.valueOf(med.currentStock));
      med.currentStock = med.initialStock;
    }
    else 
      med.currentStock = String.valueOf(Integer.valueOf(offset) + Integer.valueOf(med.currentStock));

    if (!med.update()) return false;
    
    return true;
  }

  public boolean removeStock(Medicine med, String offset) {
    if (Integer.valueOf(med.currentStock) < Integer.valueOf(offset)) {
      System.out.println("[-] Failed to remove stock from medicine. Not enough stock"); 
      return false;
    }
    else 
      med.currentStock = String.valueOf(Integer.valueOf(med.currentStock) - Integer.valueOf(offset));

    if (!med.update()) return false;
    
    return true;
  }

  public boolean updateAlert(Medicine med, String newLevel) {
    try {
      med.lowStockThreshold = newLevel; 
      if(!med.update()) return false;
      return true;
    } catch(Exception e) {
      System.out.println(e.getMessage());
      System.out.println("[-] Failed to update alert level");
      return false;
    }
  }

  private List<Medicine> medList;
}
