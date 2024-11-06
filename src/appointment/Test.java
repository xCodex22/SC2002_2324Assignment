package appointment;

public class Test {
  public static void main(String args[]) {
    AppointmentSystem.printAvailSlot("01-01");
    AppointmentSystem.printScheduledAppointment("12345");
    AppointmentSystem.scheduleAppointment("01-01-2024", "1500-1600", "12356", "12345", ScheduleOption.NEW);
  }
}
