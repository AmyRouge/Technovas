package ServiceLayer;

import DBLayer.DataBaseConnection;
import Models.Allocation;

public class AllocationService {

    Allocation Allocation= new Allocation();
    private DataBaseConnection singleCon;
    public AllocationService(){
        singleCon = DataBaseConnection.getSingleInstance();
    }
    public boolean AddAllocation(Allocation Order){
        try {
            String querry = "insert into allocation values('"+Allocation.getAllocationID()+"','"+
                    Allocation.getOrderID()+"','"+Allocation.getEmpID()+"')";
            boolean result = singleCon.ExecuteQuery(querry);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot insert Allocation");
            return false;
        }
    }
}
