package account;

import users.*;

public class Test {
  public static void main(String[] args) {
    try {
      Doctor doc = new Doctor("12346");
      ScheduleInfo si = new ScheduleInfo(doc);
      for (int i = 1; i <= 12; i++) {
        String tmp;
        if (i < 10) 
          tmp = "0" + String.valueOf(i);
        else
          tmp = String.valueOf(i);
        si.printSchedule(tmp);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}

