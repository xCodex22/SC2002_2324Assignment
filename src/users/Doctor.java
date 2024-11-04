package users;

import account.ScheduleInfo;

public class Doctor extends User {
  public Doctor(String hospitalID) {
    super(hospitalID, "DOCTOR");
    si = new ScheduleInfo(hospitalID);
  }

  public ScheduleInfo getScheduleInfo() { return si; }

  private ScheduleInfo si;
}
