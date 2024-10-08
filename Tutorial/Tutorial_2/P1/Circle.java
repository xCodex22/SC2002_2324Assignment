package Tutorial.Tutorial_2.P1;

public class Circle {
    private double radius; // radius of circle

    private static final double PI = 3.14159;
    // constructor
    public Circle(double rad) {
        this.radius = rad;
    }
    // mutator method – set radius
    public void setRadius(double rad) {
        this.radius = rad;
    }
    // accessor method – get radius
    public double getRadius() {
        return radius;
    }
    // calculate area
    public double area() {
        return PI * radius * radius;
    }
    // calculate circumference
    public double circumference() {
        return 2 * PI * radius;
    }
    // print area
    public void printArea() {
        System.out.println("Area: " + this.area());
    }
    // print circumference
    public void printCircumference() {
        System.out.println("Circumference: " + this.circumference());
    }
}
