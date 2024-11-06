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
import account.ScheduleInfo;

public class AppointmentSystem {
   
  public void printAvailSlot(String date) {
    loadAvailSlot(date);
    for (Map.Entry<String, boolean[]> entry : docAvailForTheDay.entrySet()) {
      String docID = entry.getKey(); 
      boolean[] slots = entry.getValue();        
      Doctor doc = new Doctor(docID);
      String docName = "Dr. " + doc.getBasicInfo().getFirstName() + " " + doc.getBasicInfo().getLastName();

      for (int i = 0; i < slots.length; i++) {
        if (slots[i]) {
          System.out.println(ScheduleInfo.getSlotFromIndex(i) + ": " + docName + " ID: " + docID);
        }
      }
    }
  }

  private void loadAvailSlot(String date) {
    HashMap<String, boolean[]> map = new HashMap<String,boolean[]>();

    try {
      String day = date.substring(0,2);
      String month = date.substring(3,5);
      File dir = new File("../data/ScheduleDB/");
      File init = new File("../data/ScheduleDB/init");
      File[] dirList = dir.listFiles();
      if (dirList != null) {
        for (File child : dirList) {
          if (!child.equals(init)) {
            String docID = child.getAbsolutePath().substring(child.getAbsolutePath().length()-5);
            File file = new File(child.getAbsolutePath() + "/" + "2024/" + month + "/" + day + ".csv");  
            Scanner sc = new Scanner(file);
            sc.nextLine();
            String[] line = null;
            boolean[] isSlotFree = new boolean[7];
            while(sc.hasNextLine()) {
              line = sc.nextLine().split(",");
            }
            for (int i = 0; i < line.length; i++) {
              if(line[i].equals("O")) 
               isSlotFree[i] = true;
              else
                isSlotFree[i] = false;
            }
            map.put(docID, isSlotFree);
          }
        }
      }
      docAvailForTheDay = map;
    } catch(Exception e) {
      System.out.println(e.getMessage()); 
    }
  }

  public boolean scheduleAppointment(String date, int slot, String docID, String patID) {
   return true; 
  }

  private HashMap<String, boolean[]> docAvailForTheDay;


  // print availble slot for appointment for the day
  // 1. search by day 
  // 2. it should display
  // ====[ Available Slots for DD-MM ]===
  // 9-10: Dr. First Name + Last Name
  // 9-10: Dr. First Name + Last Name
  //
  // ..
  // ..
  // f() to show available time slots for the DAY
  // To improve performance, should not read the entirety of the databse
  // must choose a day <-- returns an array of status for each of the time slot in the day
  // should use a map
  // doctor ID -> time slot that is AVAILABLE
  // read all ../data/ScheduleDB/numbers/mm/dd.csv

  // patients should have data fields for schedule appointment
  // Appointment Date
  // Doctor ID
  // Status: 1) Completed 2) Cancelled 3) Pending

  // 12345.csv
  // patientID,date,service,status,drID,medication,quantity
  // allow easy access through Patient 
  //
  // maybe should keep all.csv
  // for everything 
  //
  //separate completed.csv
  // appointment outcome 
  
  // appointment record
  // temporary db before writing to medical records?
  // so will keep recording lines
  
}
