package appointment;

public class Test {
  public static void main(String args[]) {
    AppointmentSystem.printAvailSlot("01-01");
    AppointmentSystem.printScheduledAppointment("12345");
    if(AppointmentSystem.scheduleAppointment("01-01-2024", "1300-1400", "12346", "12345", ScheduleOption.NEW))
      System.out.println("success");
    else
      System.out.println("failed");
  }
}
