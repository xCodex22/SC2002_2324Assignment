package Lab.lab3;

import java.util.*;

public class PlaneApp {
    public static void main(String[] args) {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("(1) Show number of empty seats");
        System.out.println("(2) Show the list of empty seats");
        System.out.println("(3) Show the list of seat assignments by seat ID");
        System.out.println("(4) Show the list of seat assignments by customer ID");
        System.out.println("(5) Assign a customer to a seat");
        System.out.println("(6) Remove a seat assignment");
        System.out.println("(7) Exit");
        System.out.println();
        
        Plane plane = new Plane();
        
        do {
            System.out.println("Enter the number of your choice:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    plane.showNumEmptySeats();
                    System.out.println();
                    break;
                case 2:
                    plane.showEmptySeats();
                    System.out.println();
                    break;
                case 3:
                    plane.showAssignedSeats(true);
                    System.out.println();
                    break;
                case 4:
                    plane.showAssignedSeats(false);
                    System.out.println();
                    break;
                case 5:
                    int seatAssign;
                    int idAssign;
                    System.out.println("Assigning Seat .. ");
                    System.out.println("Please enter SeatID: ");
                    seatAssign = scanner.nextInt();
                    System.out.println("Please enter Customer ID:");
                    idAssign = scanner.nextInt();
                    plane.assignSeat(seatAssign, idAssign);
                    System.out.println();
                    break;
                case 6:
                    int seatUnassign;
                    System.out.println("Enter SeatID to unassign customer from:");
                    seatUnassign = scanner.nextInt();
                    plane.unAssignSeat(seatUnassign);
                    System.out.println();
                    break;
            } 
        } while (choice != 7);    

    scanner.close();        
    }
}
