package account;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.Console;
import java.util.*;
import users.*;

public class ScheduleInfo{
  public ScheduleInfo(Doctor dr) {
    String[][][] res = new String[13][32][7];
    // String[month][date][timeslots]
    // for simplicity assume only for the year 2024
    // time slots have 7 slots, status denoted by char
    // 'X': doctor is unavailable
    // 'O': doctor is available
    // '!': patient requesting to book --> pending status
    // if accept patient's request, then become 'X', doctor becomes unavaialble
    // if reject doctor becomes available 'O'

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
    scheduleArray = res; 
  }


  private int getFirstDayOfMonth(String month, String year) {
    // we assume that there is only one year for simplicity of implementation
    // direct look up method
    // Su Mo Tu We Th Fr Sa
    // 0  1  2  3  4  5  6
    int[] res = {-1,1,4,5,1,3,6,1,1,0,2,5,0};
    return res[Integer.valueOf(month)];
  }

  private int getNumberOfDaysOfMonth(String month, String year) {
    int count = 0;
    for (int i = 1; i <= 31; i++) {
      if (scheduleArray[Integer.valueOf(month)][i] == null)
        break;
      count++;
    }
    return count;
  }

  public void printSchedule(String month) {
    // should look something like the following
    // ====[ Name of the month ]====
    // Su Mo Tu We Th Fr Sa
    // 01 02 03 04 05 06 07
    // XX XX XX OO OO OO !!
    // -------------------
    // 08 09 10 11 12 13 14
    // to align with the 2 digit dates, 
    // make the status char i.e. "X", "O", "!" to repeat twice
    // condition to print:
    // so long as there is appearance of "!" in the array, mark the date "!"
    // otherwise, if there is "O", mark the date "O"
    // else if there is no "!" and no "O", mark the date "X"
    String msg = "[-] Error";
    switch(month) {
      case "01":
        msg = "January"; 
        break;
      case "02":
        msg = "Feburary";
        break;
      case "03":
        msg = "March";
        break;
      case "04":
        msg = "April";
        break;
      case "05":
        msg = "May";
        break;
      case "06":
        msg = "June";
        break;
      case "07":
        msg = "July";
        break;
      case "08":
        msg = "August"; 
        break;
      case "09":
        msg = "September";
        break;
      case "10":
        msg = "October";
        break;
      case "11":
        msg = "November";
        break;
      case "12":
        msg = "December";
        break;
      default:
        System.out.println("[-] Invalid month");
        break;
    }
    String banner = "======[ " + msg + " ]======";
    System.out.println(banner);
    System.out.println("Su Mo Tu We Th Fr Sa");
    int j = getFirstDayOfMonth(month, "2024");  
    int tmp = j;

    int numberOfDays = getNumberOfDaysOfMonth(month, "2024");
    int i = 1;
   
    while(tmp > 0) {
      System.out.print("   ");
      tmp--;
    }

    while (i <= numberOfDays) {
      if ((i+j-1)%7 == 0) {
        System.out.println("\n---------------------");
      }
      
      if (i < 10)
        System.out.print("0" + i + " ");
      else 
        System.out.print(i + " ");  

      i++;
    }

    System.out.println("\n---------------------");
  }


  private String[][][] scheduleArray;
}
