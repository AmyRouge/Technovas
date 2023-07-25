package Controllers;

import Models.Employee;
import ServiceLayer.EmployeeService;


public class AddEmployeeController extends Employee{

    Employee Employee;
    EmployeeService Service;

    public AddEmployeeController(){
        Service = new EmployeeService();
    }

    public Employee AddEmployee(String EmpID, String EmpName, String EmpNumber,
                                String EmpEmail, String JobRole,int NoOfOrders) {
        Employee = new Employee (EmpID,EmpName,EmpNumber,EmpEmail,JobRole,NoOfOrders);

        return Employee;
    }

    public boolean AddEmployeeToDataBase() {
        return Service.AddEmployee(Employee);
    }
}

