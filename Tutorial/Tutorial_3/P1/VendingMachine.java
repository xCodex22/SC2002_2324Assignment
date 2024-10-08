package Tutorial.Tutorial_3.P1;

public class VendingMachine {
    // constructor
    public VendingMachine() {

    }
    // get the drink selection, and return the cost of the drink
    public double selectDrink() {
        
    }
    // insert the coins and returns the amount inserted
    public double insertCoins(double drinkCost) {
        
    }
    // check the change and print the change on screen
    public void checkChange(double amount, double drinkCost) {
        double change = amount - drinkCost;
        System.out.println("Change: $ " + change);
    }
    // print the receipt and collect the drink
    public void printReceipt() {
        System.out.println("Please collect your drink");
        System.out.println("Thank you !!");
    }
}
