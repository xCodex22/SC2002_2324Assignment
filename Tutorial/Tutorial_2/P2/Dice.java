package Tutorial.Tutorial_2.P2;

public class Dice {
    private int valueOfDice;

    // Constructor
    public Dice() {
        valueOfDice = 0;
    }

    // Methods
    public int getDiceValue() {
        return valueOfDice;
    }

    public void setDiceValue() {
        this.valueOfDice = (int)(Math.random() * 5) + 1;
    }

    public void printDiceValue() {
        System.out.println("Current Value is " + this.valueOfDice);
    }

}
