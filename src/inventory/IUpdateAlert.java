package inventory;

/**
 * interface class that updates the threshold for when the stock is at low level
 */
public interface IUpdateAlert extends IInventory {
  /**
   * method to update the low level threshold
   *
   * @param med the medicine to be updated
   * @param newLevel the new level for low aler
   * @return whether the operation is successful
   */
  public boolean updateAlert(Medicine med, String newLevel);
}
