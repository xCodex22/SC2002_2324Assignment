package inventory;

public interface IDispense extends IInventory {
  public boolean dispense(Medicine med, String offset, String entry);
}
