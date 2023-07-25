package Controllers;

import Models.CustomerOrder;
import ServiceLayer.OrderService;

public class AddOrderController extends CustomerOrder{

    CustomerOrder CustomerOrder;
    OrderService Service;
    public AddOrderController(){
        Service = new OrderService();
    }
    public CustomerOrder AddOrder(String OrderID, String CustomerName, String CustomerNumber,
                              String CustomerEmail, String OrderCategory, String Status) {
        CustomerOrder = new CustomerOrder(OrderID,CustomerName,CustomerNumber, CustomerEmail,OrderCategory,Status);

        return CustomerOrder;
    }

    public boolean AddOrderToDataBase(){
        return Service.AddOrder(CustomerOrder);
    }

}
