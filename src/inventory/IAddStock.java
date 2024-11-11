package inventory;

/**
 * Interface class for adding stock
 */
public interface IAddStock extends IInventory {
  /**
   * method for adding the stock to a medicine
   *
   * @param med add stock by providing the medicine field
   * @param offset the amount of stocks to be added
   * @return whether the operation is successful
   */
  public boolean addStock(Medicine med, String offset);
}
