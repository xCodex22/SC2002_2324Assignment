package MobilePhone;

import java.util.*;

public class MobilePhoneApplication {
    public static void main(String[] args) {

        // Constructor - to create/ initialise an object with SET DEFAULT/ INITIAL Value
        MobilePhone johnPhone; // 1. Declaration
        johnPhone = new MobilePhone(); // 2. Creation

        // Declare + Create App
        App appTom = new App("Tribe", 1.00);
        
        // Creating more applications
        App app1 = new App("Facebook", 0);
        App app2 = new App("Angry Bird", 2.00);

        // Declare + Create Array of app Class; appStore
        ArrayList<App> appStore = new ArrayList<App>();
        // Adding element to Array, appStore
        appStore.add(app1);
        appStore.add(app2);

        // Create a new object with GIVEN Value
        MobilePhone tomPhone = new MobilePhone("blue", 7.0, "Tom", appTom, appStore);

        // Show App
        tomPhone.getApp().print();

        tomPhone.setSize(9.0);
        System.out.println("The screensize of tom phone is " + tomPhone.getSize());

        johnPhone.sendSMS("Hi Tom.");
        tomPhone.sendSMS("Hi John.");

        MobilePhone copiedPhone = tomPhone.copyPhone();
        copiedPhone.sendSMS("Hi John. This is a copy!");
        tomPhone.setName("Jane");
        tomPhone.sendSMS("Hi John. This is actually Jane!");
        System.out.println(tomPhone.color); // mobile phone attribute of owner is public
        System.out.println(tomPhone.getName()); // assessor get() Methods

        
        System.out.println(tomPhone.getNumOfSMS()); // 2
        System.out.println(johnPhone.getNumOfSMS());
        //System.out.println(tomPhone.totalNumOfSMS()); // 4
        System.out.println(MobilePhone.totalNumOfSMS()); // can call class method directly: 4

        // Class: tomPhone > appStore(1) > app Angry bird> print app info
        tomPhone.getAppStore().get(1).print();

        // 5G Phone Class inherited from MobilePhone
        FiveGPhone iPhone14 = new FiveGPhone("Green", 7.0, "Michael", appTom, appStore, 3.1);

        System.out.println("The color is: " + iPhone14.getColor()); // inherited from superclass of MobilePhone

        iPhone14.sendSMS("I want to eat");
        iPhone14.ring();
    }
}
