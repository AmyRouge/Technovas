package Models;

public class CustomerOrder {

    private static String OrderID;
    private static String CustomerName;
    private static String CustomerNumber;
    private static String CustomerEmail;
    private static String OrderCategory;
    private static String Status;

    public CustomerOrder(String OrderID, String CustomerName, String CustomerNumber,
                         String CustomerEmail, String OrderCategory, String Status){
        this.OrderID = OrderID;
        this.CustomerName = CustomerName;
        this.CustomerNumber = CustomerNumber;
        this.CustomerEmail = CustomerEmail;
        this.OrderCategory = OrderCategory;
        this.Status = Status;
    }

    public CustomerOrder() {

    }
    public static String getOrderID() {
        return OrderID;
    }
    public static String getCustomerName() {
        return CustomerName;
    }
    public static String getCustomerNumber() {
        return CustomerNumber;
    }
    public static String getCustomerEmail() {
        return CustomerEmail;
    }
    public static String getOrderCategory() {
        return OrderCategory;
    }

    public static String getStatus() {
        return Status;
    }
}
