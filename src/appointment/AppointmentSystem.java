package appointment;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.Console;
import java.util.*;
import java.util.stream.Stream;
import users.*;
import account.ScheduleInfo;
import account.AvailStatus;
import java.util.regex.Pattern;

public class AppointmentSystem {
  private AppointmentSystem(){};
   
  public static void printAvailSlot(String date) {
    loadAvailSlot(date);
    // doctorID --> [availability for the day]  
    for (Map.Entry<String, boolean[]> entry : docAvailForTheDay.entrySet()) {
      String docID = entry.getKey(); 
      boolean[] slots = entry.getValue();        
      Doctor doc = new Doctor(docID);
      String docName = "Dr. " + doc.getBasicInfo().getFirstName() + " " + doc.getBasicInfo().getLastName();

      for (int i = 0; i < slots.length; i++) {
        if (slots[i])
          System.out.println(ScheduleInfo.getSlotFromIndex(i) + ": " + docName + " ID: " + docID);
      }
    }

  }

  private static void loadAvailSlot(String date) {
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


  private static boolean updatePatientAppointment(String patID, HashMap<List<String>, List<String>> scheduledApt) {
    try {
      String path = "../data/AppointmentDB/" + patID + "request.csv";
      String tmp = "../data/AppointmentDB/" + patID + "request.csv~";
      List<String> content = scheduleMapToFileContent(patID, scheduledApt);
      Files.write(Paths.get(tmp), content, StandardCharsets.UTF_8);
      Files.copy(Paths.get(tmp), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
      Files.delete(Paths.get(tmp));
      return true;
    } catch(IOException e) {
      e.printStackTrace();
      System.out.println("[-] No permission to read / write file");
      return false;
    }
  }

  private static List<String> scheduleMapToFileContent(String patID, HashMap<List<String>, List<String>> map) {
    // key: [Date, TimeSlot] value: [status, drName, drID]
    List<String> ans = new ArrayList<>();
    ans.add(REQUEST_HEADER);

    for (Map.Entry<List<String>, List<String>> entry : map.entrySet()) { 
      List<String> k = entry.getKey();
      List<String> v = entry.getValue();
      String newLine = v.get(0) + "," + patID + ",";

      if (k.get(0).length() > 5)
        newLine += k.get(0) + ",";
      else
        newLine += k.get(0) + "-2024,";

      newLine += k.get(1) + ",";
      newLine += (v.get(2) + "," + v.get(1)); 
      ans.add(newLine);  
    }
    
    return ans;
  }

  public static boolean scheduleAppointment(String date, String slot, String docID, String patID, ScheduleOption opt) {
        // need to first check if there is even any avail schedule slot
    loadAvailSlot(date); 
    int index = 0; 

    if (opt != ScheduleOption.CANCEL) {
      try {
        index = ScheduleInfo.getIndexFromSlot(slot);
      } catch(Exception e) {
        System.out.println("\n"+e.getMessage());
        System.out.println("[-] Failed to book appointment");
        return false;
      }
      int noSlotCount = 0;
      int totalCount = docAvailForTheDay.size();

      for (Map.Entry<String, boolean[]> entry : docAvailForTheDay.entrySet()) {
        String key = entry.getKey();
        boolean[] value = entry.getValue();
        if (!value[index]) 
        noSlotCount++;
      }

      if (noSlotCount == totalCount) {
        System.out.println("\n[-] Failed to book appointment. No slots are available");
        return false;
      }
    }

    HashMap<List<String>, List<String>> patSchedule = getScheduledAppointment(patID);
    List<String> key = new ArrayList<>();
    key.add(date); key.add(slot);
    ScheduleInfo si = new ScheduleInfo(docID);
    
    switch(opt) {
      case NEW:

      if (patSchedule.containsKey(key)) {
        if (!patSchedule.get(key).get(0).equals("cancelled")) {
          System.out.println("\n[-] There is an existing appointment: ");
          System.out.println("[1] Scheduled Date [2] Scheduled Slot [3] Appointment Status [4] Doctor Name [5] Doctor ID");
          System.out.println(key.get(0) + " " + key.get(1) + " " + patSchedule.get(key).get(0) + " " + patSchedule.get(key).get(1) + " " + patSchedule.get(key).get(2));
          return false;
        }
      }
        List<String> newVal = new ArrayList<>();
        Doctor doc = new Doctor(docID);
        newVal.add("pending"); 
        newVal.add(doc.getBasicInfo().getFirstName() + " " + doc.getBasicInfo().getLastName());
        newVal.add(docID);
        patSchedule.put(key, newVal);
        if(!updatePatientAppointment(patID, patSchedule)) return false; 
        if(!si.setAvailability(date,slot,patID,AvailStatus.BOOK)) return false;

      break;

      case CANCEL:
      // if the slot wasn't in scheduled list, then error
      if(!patSchedule.containsKey(key)) {
        System.out.println("[-] You have not booked this slot yet");
        return false;
      }
      List<String> val = patSchedule.get(key);
      val.set(0,"cancelled");
      patSchedule.put(key, val);
      if(!updatePatientAppointment(patID, patSchedule)) return false; 
      if(!si.setAvailability(date,slot,patID,AvailStatus.CANCEL)) return false;
      // otherwise 1) set the status to cancelled
      // 2) set the doctor schedule to available
      break;

      default:
        break;
    }
    return true;     
  }

  public static void acceptAppointment(String date, String slot, String docID, String patID) {
    // error handling will be done by other classes
    // all this does is add to the appointment database
    HashMap<List<String>, List<String>> patSchedule = getScheduledAppointment(patID); // pat schedule
    List<String> key = new ArrayList<>();
    key.add(date); key.add(slot);
    List<String> val = patSchedule.get(key);
    val.set(0, "confirmed");
    patSchedule.put(key, val); 
    // need to update appointment
    updatePatientAppointment(patID, patSchedule);
    // private static boolean updatePatientAppointment(String patID, HashMap<List<String>, List<String>> scheduledApt) 
  }

  public static void declineAppointment(String date, String slot, String docID, String patID) {
    // error handling will be done by other classes
     HashMap<List<String>, List<String>> patSchedule = getScheduledAppointment(patID); // pat schedule
    List<String> key = new ArrayList<>();
    key.add(date); key.add(slot);
    List<String> val = patSchedule.get(key);
    val.set(0, "cancelled");
    patSchedule.put(key, val); 
    // need to update appointment
    updatePatientAppointment(patID, patSchedule);
   
  }

  /* @param patient's ID
   * @return the hash map for current appointment of patient, key: [Date, TimeSlot] value: [status, drName, drID]
   */
  private static HashMap<List<String>, List<String>> getScheduledAppointment(String patID) {
    // pending, completed, confirmed, cancelled
    try {
      HashMap<List<String>, List<String>> ans = new HashMap<List<String>, List<String>>();
      String pathName = "../data/AppointmentDB/" + patID + "request.csv";
      File file = new File(pathName);
      Scanner sc = new Scanner(file);
      sc.nextLine();
      String[] line;
      while (sc.hasNextLine()) {
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        line = sc.nextLine().split(",");
        key.add(line[2]); key.add(line[3]);
        value.add(line[0]); value.add(line[5]); value.add(line[4]);
        ans.put(key, value);
      }
      return ans;
    } catch(FileNotFoundException e) {
      System.out.println("[-] User not found");
      return null;
    }
  }

  public static void printScheduledAppointment(String patID) {
    HashMap<List<String>, List<String>> ans = getScheduledAppointment(patID);
    System.out.println("[1] Scheduled Date [2] Scheduled Slot [3] Appointment Status [4] Doctor Name [5] Doctor ID");
    for (Map.Entry<List<String>, List<String>> entry : ans.entrySet()) { 
        List<String> k = entry.getKey();
        List<String> v = entry.getValue();
        for (String i : k) 
          System.out.print(i + " ");
        for (String j : v)
          System.out.print(j + " ");
        System.out.println();
      }
  }

  
  public static boolean recordOutcome(String docID, String patID, String date, String slot, String entry) {
    // patient request entry is deleted
    // add new entry to outcome
    // doctor schedule info set to X
    
    Doctor doc = new Doctor(docID);
    doc.getScheduleInfo().setAvailability(date, slot, patID, AvailStatus.COMPLETE);
    HashMap<List<String>, List<String>> patSchedule = getScheduledAppointment(patID);
    List<String> key = new ArrayList<>();
    key.add(date); key.add(slot);
    patSchedule.remove(key);
    updatePatientAppointment(patID, patSchedule);
    try {
      String path = "../data/AppointmentDB/" + patID + "outcome.csv";
      FileWriter writer = new FileWriter(path, true);
      writer.write(entry + "\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }


  public static void printAllAppointment() {
    // read all request files
    Pattern pattern = Pattern.compile("^\\d+request\\.csv$");     
    String dir = "../data/AppointmentDB/";
    // see Files.walk documentation
    System.out.println("\n[!] Colums: Status, Patient ID, Appointment Date, Time Slot, Doctor ID, Doctor Name\n");
    try (Stream<Path> filePathStream = Files.walk(Paths.get(dir))) {
      filePathStream
        .filter(Files::isRegularFile) 
        .map(Path::getFileName) 
        .map(Path::toString) 
        .filter(fileName -> pattern.matcher(fileName).matches()) 
        .forEach(fileName -> {
          try {
          List<String> allRows = Files.readAllLines(Paths.get(dir, fileName));
          for (int i = 1; i < allRows.size(); i++) {
            System.out.println("[" + i + "]" + " " + allRows.get(i));
          }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }); 
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void printAllOutcome() {
    System.out.println("\n[!] Colums: Patient ID, Date, Service, Doctor ID, Diagnosis, Medication, Qty, Status, Treatment, Memo\n");
    Pattern pattern = Pattern.compile("^\\d+outcome\\.csv$");     
    String dir = "../data/AppointmentDB/";
    // see Files.walk documentation
    try (Stream<Path> filePathStream = Files.walk(Paths.get(dir))) {
      filePathStream
        .filter(Files::isRegularFile) 
        .map(Path::getFileName) 
        .map(Path::toString) 
        .filter(fileName -> pattern.matcher(fileName).matches()) 
        .forEach(fileName -> {
          try {
          List<String> allRows = Files.readAllLines(Paths.get(dir, fileName));
          for (int i = 1; i < allRows.size(); i++) {
            System.out.println("[" + i + "]" + " " + allRows.get(i));
          }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }); 
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static HashMap<String, boolean[]> docAvailForTheDay;
  private static String REQUEST_HEADER = "status,patientID,appointmentDate,timeSlot,drID,drName";
  private static String OUTCOME_HEADER = "patientID,serviceDate,serviceName,drID,diagnosis,medicationPrescribed,medicationAmount,medicationStatus,treatmentPlan,remarks";
}
