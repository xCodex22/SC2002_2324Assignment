package inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;


/**
 * the class representing the medicine fields
 */
public class Medicine {
  /**
   * constructor for medicine
   *
   * @param name the name of the medicine to be constructed 
   * @throws Exception if the name of the medicine is not found in the data base
   */
  public Medicine(String name) throws Exception {
    boolean found = false;
    try {
      String path = "../../data/InventoryDB/medicine.csv";
      File file = new File(path);
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String[] line = sc.nextLine().split(",");
        if (line[0].equals(name)) {
          this.name = line[0];
          this.unit = line[1];
          this.initialStock = line[2];
          this.currentStock = line[3];
          this.lowStockThreshold = line[4];
          found = true;
          break;
        }
      }
      if (!found) throw new Exception("[-] Medicine not found");
    } catch(FileNotFoundException e) {
      System.out.println(e.getMessage());
    } 
  }  

  /**
   * updates the medicine information in the database
   *
   * @return whether the operation is successful
   */
  public boolean update() {
    String newEntry = getLineEntry();
    try {
      String path =  "../../data/InventoryDB/medicine.csv";
      String tmp = path + "~";
      List<String> content = new ArrayList<>(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
      for (int i = 0; i < content.size(); i++) {
        if(content.get(i).substring(0, name.length()).equals(newEntry.substring(0, name.length()))) {
          content.set(i, newEntry);
          break;
        }
      }
      Files.write(Paths.get(tmp), content, StandardCharsets.UTF_8);
      Files.copy(Paths.get(tmp), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
      Files.delete(Paths.get(tmp));
      return true;
    } catch(IOException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  
  /**
   * get the line entry to be written into the data base
   *
   * @return the line entry
   */
  private String getLineEntry() {
    List<String> ans = new ArrayList<>();
    ans.add(name);
    ans.add(unit);
    ans.add(initialStock);
    ans.add(currentStock);
    ans.add(lowStockThreshold);
    return String.join(",", ans);
  }

  protected String name;
  protected String unit;
  protected String initialStock;
  protected String currentStock;
  protected String lowStockThreshold;
}
