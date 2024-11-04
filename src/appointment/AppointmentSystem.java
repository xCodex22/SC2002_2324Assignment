package appointment;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.Console;
import java.util.*;
import users.*;

public class AppointmentSystem {
  public String[][][] getSchedule(Doctor dr) {
    String[][][] res = new String[13][32][7];
    
    for (int i = 0; i < 13; i++) res[i][0] = null; 

    String id = dr.getBasicInfo().getID();
    String path1 = "../data/ScheduleDB/";
    String path2 = "/2024/";
    Scanner sc;
    File file;

    for (int i = 1; i <= 12; i++) {
      String month; String date;

      if (i < 10) month = "0" + String.valueOf(i) + "/";
      else month = String.valueOf(i) + "/";

      for (int j = 1; j <= 31; j++) {
        if (j < 10) date = "0" + String.valueOf(j) + ".csv";
        else date = String.valueOf(j) + ".csv";

        try {
          file = new File(path1+id+path2+month+date);
          sc = new Scanner(file);
          sc.nextLine();
          String[] line = sc.nextLine().split(",");
          res[i][j] = line;
        } catch(FileNotFoundException e) {
          while(j <= 31) {
            res[i][j] = null;
            j++;
          }
          break;
        }

      }
    }
  }
}
