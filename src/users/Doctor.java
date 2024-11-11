package users;

import account.ScheduleInfo;

/**
 * Represents Doctor, extends {@link User} class
 *
 */
public class Doctor extends User {

  /**
   * Constructor for doctor, also intialise schedule info
   *
   * @param hospitalID of the doctor
   */
  public Doctor(String hospitalID) {
    super(hospitalID, "DOCTOR");
    si = new ScheduleInfo(hospitalID);
  }

  /**
   * gets the schedule information of doctor
   * @return the schedule information of doctor
   */
  public ScheduleInfo getScheduleInfo() { return si; }

  private ScheduleInfo si;
}
