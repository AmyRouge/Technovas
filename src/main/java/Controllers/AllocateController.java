package Controllers;

import Models.Allocation;
import ServiceLayer.AllocationService;

public class AllocateController extends Allocation{

    Allocation Allocation;
    AllocationService Service;
    public AllocateController(){
        Service = new AllocationService();
    }
    public Allocation AddAllocation(String AllocationID, String OrderID, String EmpID) {
        Allocation = new Allocation(AllocationID,OrderID,EmpID);

        return Allocation;
    }

    public boolean AddAllocationToDataBase(){
        return Service.AddAllocation(Allocation);
    }

}
