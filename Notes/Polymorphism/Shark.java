package Polymorphism;

public class Shark extends Animal {

    public Shark(String name, int age, int length) {
        super(name, age, length);
    }

    public void move() {
        super.move1();
        System.out.println("swimming");
    }
}
    

    

