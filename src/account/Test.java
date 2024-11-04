package account;

import users.*;
import menu.Sanitise;

public class Test {
  public static void main(String[] args) {
    try {
      Doctor doc = new Doctor("12346");
      ScheduleInfo si = doc.getScheduleInfo();
      String date = Sanitise.readDate();
      si.displayDay(date);           
      si.setAvailability(date, "0900-1000", AvailStatus.OPEN);
      si.displayDay(date);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}

