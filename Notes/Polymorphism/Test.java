package Polymorphism;

public class Test {
    public static void main(String[] args) {
        Shark sharkie = new Shark("Sharkie", 3, 2);
        sharkie.move();
        call(sharkie);
    }

    public static void call(Shark shark) {
        shark.move();
    }
}
