package inventory;

/**
 * interface class for dispensing medicine
 */
public interface IDispense extends IInventory {
  /**
   * this method should dispense medicine based on the appoint outcome request 
   * @param med the medicine to be dispensed
   * @param offset the amount of medicine to be dispensed
   * @param entry the request for dispense
   * @return whether the operation is successful
   */
  public boolean dispense(Medicine med, String offset, String entry);
}
