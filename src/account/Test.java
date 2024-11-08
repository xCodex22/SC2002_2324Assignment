package account;

public class Test {
  public static void main(String[] args) {
    ScheduleInfo si = new ScheduleInfo("12346");
    si.getUpcomingAppointment();
    if(!si.setAvailability("01-01-2024", "1300-1400", "12345", AvailStatus.CONFIRM))
      System.out.println("failed");
    else
      System.out.println("success");
  }
}
