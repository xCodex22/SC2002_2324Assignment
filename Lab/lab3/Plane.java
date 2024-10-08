package Lab.lab3;

public class Plane {
    // Attributes
    private PlaneSeat[] seats = new PlaneSeat[12];
    private int numEmptySeat;

    // Constructor
    public Plane() {
        for (int i = 0; i < seats.length; i++) {
            seats[i] = new PlaneSeat(i + 1);
        }
        numEmptySeat = 12; 
    }

    // Methods:
    // 1. sortSeats()
    private PlaneSeat[] sortSeats() {
        PlaneSeat[] sortedCustId = seats;
        // InsertionSort
        for (int i = 1; i < sortedCustId.length; i++) {
            PlaneSeat value = sortedCustId[i];
            int currentPos = i;

            while (sortedCustId[currentPos - 1].getCustomerID() > value.getCustomerID() && currentPos > 0) {
                sortedCustId[currentPos] = sortedCustId[currentPos - 1];
                currentPos--;
            }
            sortedCustId[currentPos] = value;
        }
        return sortedCustId;
    }

    // 2. showNumEmptySeats():
    public void showNumEmptySeats() {
        System.out.println("There are " + numEmptySeat + " empty seats");
    }

    // 3. showEmptySeats()
    public void showEmptySeats() {
        System.out.println("The following seats are empty:");
        for (int i = 0; i < 12; i++) {
            if (seats[i].isOccupied() == false) {
                System.out.println("SeatID " + seats[i].getSeatID());
            }
        }
    }

    // 4. showAssignedSeats():
    public void showAssignedSeats(boolean bySeatId) {
        System.out.println("The seat assignments are as follow:");
        if (bySeatId == true) {
            for (int i = 0; i < seats.length; i++) {
                if (seats[i].isOccupied() == true) {
                    System.out.println("SeatID " + seats[i].getSeatID() + " assigned to CustomerID " + seats[i].getCustomerID() + ".");
                }
            }        
        } else {
            PlaneSeat[] sortedSeats = sortSeats();
            for (int i = 0; i < sortedSeats.length; i++) {
                if (sortedSeats[i].isOccupied() == true) {
                    System.out.println("SeatID " + sortedSeats[i].getSeatID() + " assigned to CustomerID " + sortedSeats[i].getCustomerID() + ".");
                }
            }  
        }
    }

    // 5. assignSeat():
    public void assignSeat(int seatId, int cust_id) {
        // Check if existing CustomerID, and SeatID
        for (int i = 0; i < seats.length; i++) {
            if (seats[i].isOccupied() && seats[i].getCustomerID() == cust_id) {
                System.out.println("Customer ID " + cust_id + " already has a seat assigned.");
                return; // Exit Function (avoid further running of method)
            }
        }
        // Check if existing SeatID
        for (int i = 0; i < seats.length; i++) {
            if (seats[i].getSeatID() == seatId) {
                if (seats[i].isOccupied() == true) {
                    System.out.println("Seat already assigned to a customer.");
                    break;
                } else {
                    seats[i].assign(cust_id);
                    numEmptySeat--;
                    System.out.println("Seat Assigned!");
                }
            }
        }
    }

    // 6. unAssignSeat():
    public void unAssignSeat(int seatId) {
        for (int i = 0; i < seats.length; i++) {
            if (seats[i].getSeatID() == seatId) {
                seats[i].unAssign();
                numEmptySeat++;
                System.out.println("Seat Unassigned!");
            }
        }
    }
}
