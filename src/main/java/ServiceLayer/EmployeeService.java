package ServiceLayer;

import DBLayer.DataBaseConnection;
import Models.Employee;
public class EmployeeService {

    Employee Employee= new Employee();
    private DataBaseConnection singleCon;
    public EmployeeService(){
        singleCon = DataBaseConnection.getSingleInstance();
    }

    public boolean AddEmployee(Employee Emp){
        try {
            String querry = "insert into employee values('"+Employee.getEmpID()+"','"+
                    Employee.getEmpName()+"','"+Employee.getEmpEmail()+"','"+
                    Employee.getEmpNumber()+"','"+Employee.getJobRole()+"','"+Employee.getNoOfOrders()+"')";
            boolean result = singleCon.ExecuteQuery(querry);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot insert Employee");
            return false;
        }
    }
}
