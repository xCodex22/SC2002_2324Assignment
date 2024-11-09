package inventory;

public interface IUpdateAlert extends IInventory {
  public boolean updateAlert(Medicine med, String newLevel);
}
