package inventory;

public interface IRemoveStock extends IInventory {
  public boolean removeStock(Medicine med, String offset);
}
