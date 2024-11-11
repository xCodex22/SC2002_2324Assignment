package inventory;

/**
 * interface class for stock removal
 */
public interface IRemoveStock extends IInventory {
  /**
   * method for removing stock
   * 
   * @param med the Medicine to be removed
   * @param offset the amount of medicine to be removed
   * @return whether the operation is successful
   */
  public boolean removeStock(Medicine med, String offset);
}
