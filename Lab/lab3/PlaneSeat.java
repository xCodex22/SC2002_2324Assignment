package Lab.lab3;

public class PlaneSeat {
    // Attributes
    private int seatId;
    private boolean assigned;
    private int customerId;

    // Constructor
    public PlaneSeat(int seat_id) {
        this.seatId = seat_id;
        this.assigned = false;
        this.customerId = -1;
    }

    // Methods:

    // 1. getSeatID
    public int getSeatID() {
        return seatId;
    }

    // 2. getCustomerID()
    public int getCustomerID() {
        return customerId;
    }

    // 3. isOccupied()
    public boolean isOccupied() {
        return assigned;
    }

    // 4. assign()
    public void assign(int cust_id) {
        this.customerId = cust_id;
        this.assigned = true;
    }

    // 5. unAssign()
    public void unAssign() {
        this.customerId = -1;
        this.assigned = false;
    }
}
