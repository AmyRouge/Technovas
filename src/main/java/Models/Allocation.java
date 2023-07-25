package Models;

public class Allocation {

    private static String AllocationID;
    private static String OrderID;
    private static String EmpID;

    public Allocation(String AllocationID, String OrderID, String EmpID) {

        this.AllocationID=AllocationID;
        this.OrderID=OrderID;
        this.EmpID=EmpID;
    }

    public Allocation() {

    }

    public static String getAllocationID() {
        return AllocationID;
    }

    public static String getOrderID() {
        return OrderID;
    }

    public static String getEmpID() {
        return EmpID;
    }
}
