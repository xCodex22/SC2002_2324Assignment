package MobilePhone;

// Important Imports; ArrayList
import java.util.*;
//import java.util.ArrayList;

public class MobilePhone {

    // Define Class
    public static final double MAX_SCREEN_SIZE = 8.0;

    public String color; // public so can be accessed in main()
    protected double screenSize;
    protected String owner; // belongs to name of owner (protected)
    
    // Instance variable (NO static)
    private int numOfSMS;
    // Static/ Class Variable (Static)
    private static int totalNumOfSMS;

    // Class as an attribute
    private App app;
    // Array as an attribute where elements are class
    private ArrayList<App> appStore;

    // Constructor 1: initial/ default value
    public MobilePhone() {
        color = "white";
        screenSize = 5.0;
        owner = "John";
        //this("white", 5.0, "Default");
    }

    // Constructor 2: if parameter is given
    public MobilePhone(String col, double size, String ownerName, App app, ArrayList<App> appStore) {
        // this refers to the constructor directed at the object created
        this.color = col; 
        this.screenSize = size;
        this.owner = ownerName;
        this.app = app;
        this.appStore = appStore;
    }   

    // sendSMS method
    public String sendSMS(String message) {
        numOfSMS++;
        totalNumOfSMS++;
        //this.setName("unknown");
        System.out.println(message + " This is " + this.owner);
        return "sent successfully";
    }

    // copyingObjects
    public MobilePhone copyPhone() {
        MobilePhone phone = new MobilePhone(color, screenSize, owner, app, appStore);
        return phone;
    }

    // changing attribute values / Mutate / set() Method
    public void setName(String ownerName) {
        this.owner = ownerName;
    }

    public void setColor(String col) {
        this.color = col;
    }

    public void setSize(double size) {
        if (size >= MAX_SCREEN_SIZE) {
            this.screenSize = MAX_SCREEN_SIZE;
        } else {
            this.screenSize = size;
        }
    }

    // to access appstore an app Class attribute in MobilePhone
    public void setApp(App app) {
        this.app = app;
    }

    // to access appstore array of app Class in MobilePhone
    public void setAppStore(ArrayList<App> appStore) {
        this.appStore = appStore;
    }

    // Assess get() Method
    public String getColor() {
        return color;
    }

    public double getSize() {
        return screenSize;
    }

    public String getName() {
        return owner;
    }

    public int getNumOfSMS() {
        return numOfSMS;
    }

    public static int totalNumOfSMS() {
        return totalNumOfSMS;
    }

    // to access app Class methods in MobilePhone
    public App getApp() {
        return app;
    }

    // to access array of app Class in MobilePhone
    public ArrayList<App> getAppStore() {
        return appStore;
    }
}
