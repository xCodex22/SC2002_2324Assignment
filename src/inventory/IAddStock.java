package inventory;

public interface IAddStock extends IInventory {
  public boolean addStock(Medicine med, String offset);
}
