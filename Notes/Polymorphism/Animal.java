package Polymorphism;

public class Animal {
    // Attributes
    private String name;
    private int age;
    private int length;

    // Constructor by parameters
    public Animal(String name, int age, int length) {
        this.name = name;
        this.age = age;
        this.length = length;
    }

    // Methods
    public void move1() {
        System.out.println("Animal " + this.name + " moves by");
    }

}

