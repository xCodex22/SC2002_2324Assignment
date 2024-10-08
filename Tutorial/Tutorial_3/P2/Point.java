package Tutorial.Tutorial_3.P2;

public class Point {
    private int xcoor;
    private int ycoor;

    // Constructor
    public Point(int x, int y) {
        this.xcoor = x;
        this.ycoor = y;
    }

    // Methods
    public String toString() {
        return "[" + String.valueOf(this.xcoor) + ", " + String.valueOf(ycoor) + "]";
    }

    public void setPoint(int x, int y) {
        this.xcoor = x;
        this.ycoor = y;
    }

    public int getX() {
        return xcoor;
    }

    public int getY() {
        return ycoor;
    }
}
