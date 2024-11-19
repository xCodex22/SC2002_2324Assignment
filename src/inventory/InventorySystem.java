package inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import users.*;


/**
 * class for managing the medicine inventory
 */
public class InventorySystem implements IAddStock, IRemoveStock, IUpdateAlert, IDispense{
  /**
   * constructor to read all the medicine in the current database
   */
  public InventorySystem() {
    List<Medicine> ans = new ArrayList<>();
    List<String> sum = new ArrayList<>();
    try {
      String path = "../data/InventoryDB/medicine.csv";
      File file = new File(path);
      Scanner sc = new Scanner(file);
      sc.nextLine();
      while (sc.hasNextLine()) {
        String entry = sc.nextLine();
        String[] line = entry.split(",");
        Medicine med = new Medicine(line[0]);
        ans.add(med);
        sum.add(entry);
      } 
      this.medList = ans;
      this.summaryList = sum;
    }catch(Exception e) {
      System.out.println(e.getMessage());
      System.out.println("[-] System initialisation failed");
    }
  }

  /**
   * print all medicine name
   */
  public void printAllMedName() {
    for (Medicine i : medList) {
      System.out.println(i.name);
    }
  }

  /**
   * get the summary of the current inventory
   * @return the summary list 
   */
  public List<String> getSummary() { return summaryList; }

  /**
   * get the list of medicine in the current inventory
   * @return the list of medicine
   */
  public List<Medicine> getListMed() { return medList; }


  /**
   * gets the list of names of medicine
   *
   * @return the list of the names of medicine in the inventory
   */
  public List<String> getListMedName() {
    List<String> ans = new ArrayList<>();
    for (Medicine i : medList) 
      ans.add(i.name);
    return ans;
  }


  /**
   * prints all detailed information about the inventory
   *
   */
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
      sc.close();
    }catch(Exception e) {
      System.out.println(e.getMessage());
    }

  }


  /**
   * adds stock to medicine
   *
   * @param med the medicine to be updated
   * @param offset the amount of stock to be added
   * @return whether the operation is successful
   */
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


  /**
   * removes tock from medicine
   *
   * @param med the medicine to be updated
   * @param offset the quantity to be removed
   * @return whether the operation is successful
   */
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

  /**
   * sets new threshold for low alert
   *
   * @param med the medicine to be updated
   * @param newLevel the new threshold
   * @return whether the operation is successful
   */
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


  /**
   * request replenishment on medicine
   *
   * @param med the medicine to be replenished
   * @param requestQty the amount of quantity to be added
   * @param pharma the user information of the pharmacist
   * @return whether the operation is successful
   */
  public boolean requestReplenishment(Medicine med, String requestQty, User pharma) {
   List<String> ans = new ArrayList<>();
    ans.add(med.name);
    ans.add(med.unit);
    ans.add(requestQty);
    ans.add(pharma.getBasicInfo().getID());
    ans.add(pharma.getBasicInfo().getFirstName() + " " + pharma.getBasicInfo().getLastName());
    String entry = String.join(",", ans);
    try {
     String path = "../data/InventoryDB/request.csv"; 
      FileWriter writer = new FileWriter(path, true);
      writer.write(entry + "\n");
      writer.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * gets the current list of stocks that are low level
   *
   * @return list of information of the stocks that are at low level
   */
  public List<String> getLowStockList() {
    List<Medicine> med = getListMed();  
    List<String> ans = new ArrayList<>();
    for (Medicine i : med) {
      if (Integer.valueOf(i.currentStock) <= Integer.valueOf(i.lowStockThreshold)) {
        ans.add(i.name);
      }
    }
    return ans;
  }


  /**
   * dispense medicine from request
   *
   * @param med the information of the medicine
   * @param offset the quantity to be dispensed
   * @param entry the line entry of the request
   * @return whether the operation is successful
   */
  public boolean dispense(Medicine med, String offset, String entry) {
    try {
      String id = entry.split(",")[0];
      String path = "../data/AppointmentDB/" + id + "outcome.csv"; 
      String tmp = path + "~";

      if (!removeStock(med, offset)) return false;

      List<String> content = new ArrayList<>(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
				for (int i = 0; i < content.size(); i++) {
					if(content.get(i).equals(entry)) {
            // conte.get(i) is line, want to change content.get(i)[8] to string "dispensed"
            String[] field = content.get(i).split(",");
            field[8] = "dispensed";
            String newEntry = String.join(",", field);
            content.set(i, newEntry);
						break;
					}
				}

				Files.write(Paths.get(tmp), content, StandardCharsets.UTF_8);
				Files.copy(Paths.get(tmp), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
				Files.delete(Paths.get(tmp));

      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * gets all request for replenishment
   *
   * @return the list of reqeust
   */
  public List<String> getReplenRequest() {
    try {
      List<String> ans = new ArrayList<>();
      String path = "../data/InventoryDB/request.csv";  
      File file = new File(path);
      Scanner sc = new Scanner(file);
      sc.nextLine();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        ans.add(line); 
      }
     return ans; 
    } catch (FileNotFoundException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * approves the replenishment request, when approved update the stock level and remove request from database
   * @param the entry to be edited
   * @return whether the operation is successful
   */
  public boolean approveRequest(String entry) {
    //name,unit,requestQty,pharmaID,pharmaName
    try {
      String[] line = entry.split(",");
      Medicine med = new Medicine(line[0]);
      if(!addStock(med, line[2])) return false;
      if(!deleteRequest(entry)) return false;
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  /**
   * deletes the replenishment request from the data base
   *
   * @param the line entyr
   * @param whether the operation is successful
   */
  public boolean deleteRequest(String entry) {
    try {
      String path = "../data/InventoryDB/request.csv";
      String tmp = path + "~";
      // want to delete line that maches with String entry to the file
    List<String> content = new ArrayList<>(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
        List<String> newContent = new ArrayList<>();
				for (int i = 0; i < content.size(); i++) {
					if(content.get(i).equals(entry)) {
						continue;
					}  
          newContent.add(content.get(i));
				}
				Files.write(Paths.get(tmp), newContent, StandardCharsets.UTF_8);
				Files.copy(Paths.get(tmp), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
				Files.delete(Paths.get(tmp));
      return true;
    } catch (IOException e) {
      System.out.println("[-] Failed to update request database");
      e.printStackTrace();
      return false;
    }
  }

  private List<Medicine> medList;
  private List<String> summaryList;
}
