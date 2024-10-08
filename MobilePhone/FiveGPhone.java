package MobilePhone;

import java.util.*;

// implements a Interface: IntPhone
public class FiveGPhone extends MobilePhone implements IntPhone {
    // its own attribute
    private double dataRate;

    // Constructor
    // 1. default
    public FiveGPhone() {
        //super(); // 1. super inherit MobilePhone class
        // 2. another way of inheriting:
        super.color = "white";
        super.screenSize = 5.0; // protected; can inherit because same package
        super.owner = "john"; // protected
        dataRate = 0.0;
    }
    // 2. parameter
    public FiveGPhone(String color, double screenSize, String owner,App app, ArrayList<App> appStore, double dataRate) {
        super(color, screenSize, owner, app, appStore);
        this.dataRate = dataRate;
    }

    // Assesor
    public double getDataRate() {
        return dataRate;
    }

    // Mutator
    public void setDataRate(double dataRate) {
        this.dataRate = dataRate;
    }

    public String sendSMS(String message) {
        //super.sendSMS(message); // superclass method
        System.out.println(message + " This message was sent by 5G phone");
        return "send successfully";
    }

    // Implementing IntPhone
    public void ring() {
        System.out.println("Play Music; this is a 5G Phone.");
    }

}
