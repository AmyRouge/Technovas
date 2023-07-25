package ServiceLayer;

import DBLayer.DataBaseConnection;
import Models.CustomerOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderService {
    CustomerOrder CustomerOrder= new CustomerOrder();
    private DataBaseConnection singleCon;
    public OrderService(){
        singleCon = DataBaseConnection.getSingleInstance();
    }
    public boolean AddOrder(CustomerOrder Order){
        try {
            String querry = "insert into customer_order values('"+CustomerOrder.getOrderID()+"','"+
                    CustomerOrder.getCustomerName()+"','"+CustomerOrder.getCustomerEmail()+"','"+
                    CustomerOrder.getCustomerNumber()+"','"+CustomerOrder.getOrderCategory()+"'," +
                    "'"+CustomerOrder.getStatus()+"')";
            boolean result = singleCon.ExecuteQuery(querry);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot insert Order");
            return false;
        }
    }

    public String generateOrderID(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DataBase Connection Success");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/OOPproducts", "root", "");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from customer_order");
            int count;
            int noOfRaw=0;
            String LastOrderID = null;

            while (rs.next()) {
                noOfRaw++;
                String Order_ID = rs.getString("Order_ID");
                LastOrderID = Order_ID;
            }
            if(noOfRaw<1){
                count=1000;
            }else {
                count=Integer.parseInt(LastOrderID.substring(2,6));
                ++count;
            }
            return "OR"+count;
        } catch (Exception e) {
        }
        return null;
    }


}
