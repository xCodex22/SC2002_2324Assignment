package account;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * class representing the schedule information of a doctor
 */
public class ScheduleInfo{
  /**
   * constructor that initialises the schedule info of a doctor
   *
   * @param hospitalID the id number of the doctor
   */
  public ScheduleInfo(String hospitalID) {
    this.id = hospitalID;
    load(hospitalID);    
  }

  /**
   * loads the information based on the ID
   *
   * @param hospitalID the id number of the doctor
   */
  private void load(String hospitalID) {
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

    String id = hospitalID;
    String path1 = "../../data/ScheduleDB/";
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


  /**
   * gets the first day of the month, for the year 2024
   *
   * @param month the month we are interested in
   * @param year the year, but we only support 2024
   * @return the first day of the month as an offset of the days in the week
   */
  private int getFirstDayOfMonth(String month, String year) {
    // we assume that there is only one year for simplicity of implementation
    // direct look up method
    // Su Mo Tu We Th Fr Sa
    // 0  1  2  3  4  5  6
    int[] res = {-1,1,4,5,1,3,6,1,1,0,2,5,0};
    return res[Integer.valueOf(month)];
  }

  /**
   * gets the number of the days in the month for the year 2024
   *
   * @param month the month of interest
   * @param year the year of interest, however we only support 2024
   * @return the number of days in the month
   */
  private int getNumberOfDaysOfMonth(String month, String year) {
    int count = 0;
    for (int i = 1; i <= 31; i++) {
      if (scheduleArray[Integer.valueOf(month)][i] == null)
        break;
      count++;
    }
    return count;
  }

  /**
   * gets the schedule status of the day
   *
   * @param month month of interest
   * @param date date of month
   * @param year we only support 2024
   *
   * @return X for unavailable, O for available ! for pending
   */
  private String getStatusOfDay(String month, String date, String year) {
    String[] slots = scheduleArray[Integer.valueOf(month)][Integer.valueOf(date)];
    boolean hasPending = false;
    boolean hasAvailable = false;

    for (String i : slots) {
      if (!i.equals("O") && !i.equals("X"))
        hasPending = true;
      else if (i.equals("O"))
        hasAvailable = true;
    }

    if (hasPending) return "!!";
    if (hasAvailable) return "OO";
    return "XX"; 
  }

  /**
   * prints the schedule information as a ASCII calendar for the month
   *
   * @param month the month of the schedule
   */
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
    System.out.println("---------------------");
    int j = getFirstDayOfMonth(month, "2024");  
    int tmp = j;

    int numberOfDays = getNumberOfDaysOfMonth(month, "2024");
    int i = 1;
   
    while(tmp > 0) {
      System.out.print("   ");
      tmp--;
    }

    tmp = j;

    StringBuilder status = new StringBuilder();
    while (i <= numberOfDays) {
      String day;
      if ((i+j-1)%7 == 0) {
        if (status != null) {
          System.out.println();
          while(tmp > 0) {
            System.out.print("   ");
            tmp--;
          }
          System.out.println(status);
          status = new StringBuilder();
          System.out.println("---------------------");
        }
        else
          System.out.println("\n---------------------");
      }
      
      if (i < 10) 
        day = "0" + String.valueOf(i);
      else 
        day = String.valueOf(i);
      System.out.print(day + " ");
      
      // the following line should print after all dates in the line has been printed
      // for example 
      // 01 02 03
      // XX XX XX
      status.append(getStatusOfDay(month, day, "2024"));
      status.append(" ");
      i++;
    }

    if (status != null) {
        System.out.println(); 
          System.out.println(status);
          status = new StringBuilder();
          System.out.println("---------------------");
    }

    System.out.println("\nLegends: ");
    System.out.println("[XX] Date marked unavailable");
    System.out.println("[OO] Date marked available");
    System.out.println("[!!] Date with pending appointment request\n");

  }

  /**
   * displays the availability of slots for a particular day 
   *
   * @param date of the schedule
   */
  public void displayDay(String date) {
    try {
      List<String> status = getSlotStatus(date);
      System.out.println("\n=====[ Schedule for " + date + " ]=====");
      System.out.println("[1] 0900-1000: " + status.get(0));
      System.out.println("[2] 1000-1100: " + status.get(1));
      System.out.println("[3] 1100-1200: " + status.get(2));
      System.out.println("[4] 1300-1400: " + status.get(3));
      System.out.println("[5] 1400-1500: " + status.get(4));
      System.out.println("[6] 1500-1600: " + status.get(5));
      System.out.println("[7] 1600-1700: " + status.get(6));
    } catch (Exception e) {
      System.out.println(e.getMessage());      
    }
  }

  /**
   * gets the status of the slot
   *
   * @param date of interest
   * @return list of status
   * @throws Exception if the slot is invalid
   */
  private List<String> getSlotStatus(String date) throws Exception {
    String day = date.substring(0, 2);
    String month = date.substring(3, 5);
    try {
      String path = "../../data/ScheduleDB/" + id + "/2024/" + month + "/" + day + ".csv";
      List<String> content = new ArrayList<>(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
      String[] line = content.get(1).split(",");
      List<String> ans = new ArrayList<>();
      for (String i : line) {
        if (i.equals("X"))
          ans.add("Unavailable");
        else if (i.equals("O"))
          ans.add("Available");
        else 
          ans.add("Pending Appointment by ID: " + i);
      }
      return ans;
    } catch (Exception e) {
      throw new Exception("[-] date does not exist"); 
    }
  }

  public boolean setAvailability(String date, String slot, String patID, AvailStatus status) {
    // input format will  be DD-MM (assume only one year)
    String day = date.substring(0, 2);
    String month = date.substring(3, 5);
    try {
      int index = getIndexFromSlot(slot);
      String path = "../../data/ScheduleDB/" + id + "/2024/" + month + "/" + day + ".csv";
      List<String> content = new ArrayList<>(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
      List<String> newContent = new ArrayList<>();
      newContent.add(content.get(0));
      String[] newLine = content.get(1).split(",");

      switch(status) {
        case CLOSE:
          if (newLine[index].equals("O"))
            newLine[index] = "X"; 
          else {
            System.out.println("[-] Unable to set date as unavailable. Time slot is reserved or already unavailable.");
            return false;
          }
          break;
        case OPEN:
          if (newLine[index].equals("X"))
            newLine[index] = "O"; 
          else {
            System.out.println("[-] Unable to set date as available. Time slot is reserved or already available.");
            return false;
          }
          break;
        case BOOK:
          if (!newLine[index].equals("O")) {
            System.out.println("[-] Unable to book slot. The time slot is unavailable.");
            return false;
          }
          newLine[index] = "P"+patID; 
          break;
        case CONFIRM:
         if (!newLine[index].startsWith("P")) {
            System.out.println("[-] Unable to confirm the time slot. There is no pending appointment");     
            return false;
          }
          newLine[index] = "C"+patID;
          break;
        case CANCEL:
          if (!newLine[index].startsWith("P")) {
            System.out.println("[-] Unable to cancel the time slot. There is no pending appointment");
            return false;
          }
          newLine[index] = "O";
          break;
        case COMPLETE:
          if (!newLine[index].startsWith("C")) {
            System.out.println("[-] Unable to complete appointment. There is no confirmed appointment");
            return false;
          }
          newLine[index] = "X";
          break;
        default:
          System.out.println("[-] in setAvailability(): unknown status");
          return false;
      }

      String newEntry = String.join(",", newLine);
      newContent.add(newEntry);

      String tmpPath = path + "~";
      Files.write(Paths.get(tmpPath), newContent, StandardCharsets.UTF_8);
			Files.copy(Paths.get(tmpPath), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
			Files.delete(Paths.get(tmpPath));

    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("[-] Failed to set availability");
      return false;
    }
       
    load(id);
    return true;
  }

  /**
   * converts intger month into string
   * @param i the integer value of month
   * @return 0 appended if month is 1 digit
   */
  private String getMonthFromIndex(int i) {
    String ans;
    if (i < 10) 
      ans = "0" + String.valueOf(i);
    else
      ans = String.valueOf(i);
    return ans;
  }

  /**
   * converts the integer date into string
   * @param i the integer value of month
   * @return 0 appended if date is 1 digit
   */
  private String getDateFromIndex(int i) {
    String ans;
    if (i < 10)
      ans = "0" + String.valueOf(i);
    else
      ans = String.valueOf(i);
    return ans;
  }

  /**
   * converts the index of the time slot array into the time slot
   *
   * @param i the index of the array
   *
   * @return the string value of the time slot
   */
  public static String getSlotFromIndex(int i) {
    String ans = null;
    switch(i) {
      case 0:
        ans = "0900-1000";
        break;
      case 1:
        ans = "1000-1100";
        break;
      case 2:
        ans = "1100-1200";
        break;
      case 3:
        ans = "1300-1400";
        break;
      case 4:
        ans = "1400-1500";
        break;
      case 5:
        ans = "1500-1600";
        break;
      case 6:
        ans = "1600-1700";
        break;
    }
    return ans;
  }

  /**
   * converts the string time slot into the index of the time slot array
   *
   * @param slot the string value of the slot
   * @return the int value of the index of the slot in the array
   * @throws Exception if the String value of slot is invalid, i.e. not in slot array
   */
  public static int getIndexFromSlot(String slot) throws Exception {
    int index;
     
    switch(slot) {
      case "0900-1000":
        index = 0;
        break;
      case "1000-1100":
        index = 1;
        break;
      case "1100-1200":
        index = 2;
        break;
      case "1300-1400":
        index = 3;
        break;
      case "1400-1500":
        index = 4;
        break;
      case "1500-1600":
        index = 5;
        break;
      case "1600-1700":
        index = 6;
        break;
      default:
        throw new Exception("[-] in getIndexFromSlot(): invalid slot"); 
    }
    
    return index;
  }

  
  /**
   * gets the upcoming appointment for the doctor
   * 
   * @return array of String with the upcoming appointment 
   */
  public List<String> getUpcomingAppointment() {
    // iterate through scheduleArray[month][date][timeslot]
    // P12345 (pending)
    // C12345 (confirmed)
    // X (done)
    // O (cancelled)
    // only read the ones that are subtring(0,).equals("C");
    // string format
    // Date Timeslot PatientID 

    List<String> ans = new ArrayList<>();

    for (int month = 1; month < scheduleArray.length; month++) {
      for (int date = 1; date < scheduleArray[month].length; date++) {
        if (scheduleArray[month][date] != null) { // num of days in mth is different
          for (int timeSlot = 0; timeSlot < scheduleArray[month][date].length; timeSlot++) {
            if (scheduleArray[month][date][timeSlot].startsWith("C")) {
              String m = getMonthFromIndex(month) + "-";
              String d = getDateFromIndex(date) + "-";
              String y = "2024";
              String s = getSlotFromIndex(timeSlot);
              String patID = scheduleArray[month][date][timeSlot].substring(1);
              String entry = d+m+y+" "+ s + " " +patID;
              ans.add(entry);
            }
          }
        }
      }
    }  
    return ans;
  }

/**
 * gets pending appointment
 *
 * @return list of pending appointment
 */
public List<String> getPendingAppointment() {
    // iterate through scheduleArray[month][date][timeslot]
    // P12345 (pending)
    // C12345 (confirmed)
    // X (done)
    // O (cancelled)
    // only read the ones that are subtring(0,).equals("C");
    // string format
    // Date Timeslot PatientID 

    List<String> ans = new ArrayList<>();

    for (int month = 1; month < scheduleArray.length; month++) {
      for (int date = 1; date < scheduleArray[month].length; date++) {
        if (scheduleArray[month][date] != null) { // num of days in mth is different
          for (int timeSlot = 0; timeSlot < scheduleArray[month][date].length; timeSlot++) {
            if (scheduleArray[month][date][timeSlot].startsWith("P")) {
              String m = getMonthFromIndex(month) + "-";
              String d = getDateFromIndex(date) + "-";
              String y = "2024";
              String s = getSlotFromIndex(timeSlot);
              String patID = scheduleArray[month][date][timeSlot].substring(1);
              String entry = d+m+y+" "+ s + " " +patID;
              ans.add(entry);
            }
          }
        }
      }
    }  
    return ans;
  }


  // accept or decling request is just setting availabiilty
  // use overwrite option
  // --> check that it must first be a String that is not (X) or (O)
  // --> if confirm, change to C12345
  // || also needs to update the patient side
  // || update this in request to their request, just make a new entry saying confirmed?
  // || this will work?  
  // --> if cancelled, change to O
  // || also need to update the patient side
  // || to cancell
  // so will just  
  //

  // record outcome has the following jsteps
  // 1. Update Medical Record
  // 2. availability is allready set 
  // 3. record into outcome.csv, so patients can view their outcome by indexing the file easily (outcome means completed)
  // 4. record into all .csv ?
  // 5. go and updat ehte all.csv 

  private String[][][] scheduleArray;
  private String id;
}
