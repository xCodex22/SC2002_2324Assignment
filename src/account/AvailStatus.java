package account;

/**
 * status option for the doctor's slot
 */
public enum AvailStatus {
  /**
   * make slot available
   */
  OPEN, 

  /**
   * make slot unavailable
   */
  CLOSE, 

  /**
   * patient is booking the slot
   */
  BOOK, 

  /**
   * patient or doctor is cancelling the slot
   */
  CANCEL, 

  /**
   * doctor confirms the slot
   */
  CONFIRM, 

  /**
   * doctor completes the appointment
   */
  COMPLETE
}
