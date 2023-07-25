package ServiceLayer;

import DBLayer.DataBaseConnection;


public class ManageInventoryService
{
    private DataBaseConnection singleCon;
    public ManageInventoryService(){
        singleCon = DataBaseConnection.getSingleInstance();
    }
    public boolean addProduct(String ProductName,String Price,String Category){
        try {
            String querry = "insert into Products (Product_ID,Product_Name, Price, Category) values(NULL,'"+ProductName+"','"+Price+"','"+Category+"')";

            boolean result = singleCon.ExecuteQuery(querry);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot Add This Product");
            return false;
        }
    }

    public boolean UpdateProduct(String ProductID,String ProductName,String Price,String Category){
        try {
            String querry = "update products set Product_Name='"+ProductName+"',Price='"+Price+"',Category='"+Category+"' where Product_ID='"+ProductID+"'";

            boolean result = singleCon.ExecuteQuery(querry);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot Update This Product");
            return false;
        }
    }

    public boolean DeleteProduct(String ProductID){
        try {
            String querry = "delete from products where Product_ID='"+ProductID+"'";

            boolean result = singleCon.ExecuteQuery(querry);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot Delete This Product");
            return false;
        }
    }

}
