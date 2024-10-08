package Tutorial.Tutorial_2.P2;

import java.util.Scanner;

public class DiceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();

        System.out.println("Press <key> to roll the first dice");
        try
        {
            System.in.read();
            scanner.nextLine();
        }  
        catch(Exception e)
        {}  
        dice1.setDiceValue();
        dice1.printDiceValue();

        System.out.println("Press <key> to roll the second dice");
        try
        {
            System.in.read();
            scanner.nextLine();
        }  
        catch(Exception e)
        {}  
        dice2.setDiceValue();
        dice2.printDiceValue();

        System.out.println("Your total number is: " + (dice1.getDiceValue() + dice2.getDiceValue()));

        scanner.close();
    }
}
