package Models;

public class Employee {
    private static String EmpID;
    private static String EmpName;
    private static String EmpNumber;
    private static String EmpEmail;
    private static String JobRole;

    private static int NoOfOrders;

    public Employee(String EmpID, String EmpName, String EmpNumber, String EmpEmail, String JobRole, int NoOfOrders){
        this.EmpID = EmpID;
        this.EmpName = EmpName;
        this.EmpNumber = EmpNumber;
        this.EmpEmail = EmpEmail;
        this.JobRole = JobRole;
        this.NoOfOrders = NoOfOrders;

    }

    public Employee() {

    }
    public static String getEmpID() {
        return EmpID;
    }
    public static String getEmpName() {
        return EmpName;
    }
    public static String getEmpNumber() {
        return EmpNumber;
    }
    public static String getEmpEmail() {
        return EmpEmail;
    }
    public static String getJobRole() {
        return JobRole;
    }
    public static int getNoOfOrders() {
        return NoOfOrders;
    }
}
