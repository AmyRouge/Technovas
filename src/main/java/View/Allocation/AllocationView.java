package View.Allocation;

import Controllers.AllocateController;
import Models.Allocation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class AllocationView extends JFrame {
    public JPanel contentPane;
    private JButton btnAllocate;
    private JButton btnAllocationDetails;
    private JComboBox comboBoxOrderID;
    private JButton btnGetEmp;

    public JPanel root;
    private JTextField txtOrderID;
    private JComboBox comboBoxEmpID;

    AllocateController AllocateC;
    Allocation[] AllocateArray;
    ArrayList<Allocation> AllocateList;
    int AllocateCount;

    public AllocationView() {

        AllocateC=new AllocateController();
        AllocateArray=new Allocation[100];
        AllocateCount=0;
        AllocateList=new ArrayList<>();

        JTable table;
        DefaultTableModel tableModel;

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DataBase Connection Success");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");
            Statement st = con.createStatement();

            String query = "SELECT *  FROM customer_order";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int cols=rsmd.getColumnCount();
            String[] colName = new String[2];
            colName[0]="Order ID";
            colName[1]="Order Category";


            tableModel.setColumnIdentifiers(colName);

            while (rs.next()) {
                Object[] rowData = new Object[cols];
                for (int i = 1; i <= cols; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                String[] RawData = new String[cols];
                RawData[0] = (String) rowData[0];
                RawData[1]= (String) rowData[4];

                if(((String) rowData[5]).equalsIgnoreCase("Incomplete")){
                    tableModel.addRow(RawData);
                }
            }

            // Close the resources
            rs.close();
            st.close();
            con.close();

            root.setLayout(new BorderLayout());
            root.add(table.getTableHeader(), BorderLayout.PAGE_START);
            root.add(scrollPane, BorderLayout.CENTER);;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        btnGetEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    System.out.println("DataBase Connection Success");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");

                    Statement st = con.createStatement();
                    String query = "SELECT EmpID, Job_Role, NoOfOrders  FROM employee";
                    ResultSet rs = st.executeQuery(query);

                    comboBoxEmpID.removeAllItems();
                    while (rs.next()) {
                        String JobROLE = rs.getString("Job_Role");
                        String id = rs.getString("EmpID");
                        int NoOfOrders = rs.getInt("NoOfOrders");
                        if(JobROLE.equalsIgnoreCase((String)comboBoxOrderID.getSelectedItem())
                                && NoOfOrders<5){
                            comboBoxEmpID.addItem(id);
                            }

                    }

                    rs.close();
                    st.close();
                    con.close();

                } catch (SQLException ex) {
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String AllocationID=generateAllocationID();
                String OrderID = txtOrderID.getText();
                String EmpID = (String) comboBoxEmpID.getSelectedItem();


                if (OrderID.isEmpty() || EmpID==null) {
                    JOptionPane.showMessageDialog(contentPane,"Fill the Blanks", "Fail", 1);

                }else {

                    try {
                        // Connect to the database
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        System.out.println("DataBase Connection Success");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");


                        Statement st = con.createStatement();
                        String query = "SELECT EmpID,NoOfOrders  FROM employee";
                        ResultSet rs = st.executeQuery(query);


                        while (rs.next()) {
                            String id = rs.getString("EmpID");

                            if(id.equalsIgnoreCase(EmpID)) {
                                int NoOfOrders = rs.getInt("NoOfOrders");
                                ++NoOfOrders;

                                String updateQuery1 = "UPDATE employee SET NoOfOrders = ? WHERE EmpID = ?";
                                PreparedStatement statement1 = con.prepareStatement(updateQuery1);
                                statement1.setInt(1,NoOfOrders);
                                statement1.setString(2, EmpID);


                                // Execute the update statement
                                int rowsAffected1 = statement1.executeUpdate();
                                if (rowsAffected1 > 0 ) {
                                    System.out.println("NoOfOrders updated successfully.");
                                    JOptionPane.showMessageDialog(contentPane,"NoOfOrders updated successfully", "Success", 1);
                                } else {
                                    System.out.println("Failed to update NoOfOrders details.");
                                }

                                // Close the resources
                                statement1.close();

                            }

                        }


                        // Prepare the Update query

                        String updateQuery = "UPDATE customer_order SET Status = ? WHERE Order_ID = ?";
                        PreparedStatement statement = con.prepareStatement(updateQuery);
                        statement.setString(1, "Allocated");
                        statement.setString(2, OrderID);


                        Allocation AllocateB = AllocateC.AddAllocation(AllocationID,OrderID,EmpID);
                        AllocateArray[AllocateCount] = AllocateB;
                        AllocateList.add(AllocateB);
                        AllocateCount++;


                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Order Status Update successfully.");
                            JOptionPane.showMessageDialog(contentPane,"Update Status from the Order list", "Success", 1);

                            if(AllocateC.AddAllocationToDataBase()){
                                JOptionPane.showMessageDialog(contentPane,"Allocation Successfully Added to the DataBase", "Success", 1);


                                AllocationView v1= new AllocationView();
                                setVisible(false);
                                v1.setContentPane(v1.contentPane);
                                v1.setTitle("Allocation Details");
                                v1.setSize(300,350);
                                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                v1.setVisible(true);

                                txtOrderID.setText("");

                            }else {
                                JOptionPane.showMessageDialog(contentPane,"Cannot insert to the DB", "Error", 1);
                            }

                        } else {
                            System.out.println("No record found with the specified ID.");
                            JOptionPane.showMessageDialog(contentPane,"No record found with the specified ID", "Error", 2);
                        }

                        // Close the resources
                        statement.close();
                        con.close();

                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                }


            }
        });



        btnAllocationDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AlloactionDetailsView v1= new AlloactionDetailsView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Manage Allocation");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });



    }
    public String generateAllocationID(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DataBase Connection Success");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from allocation");
            int count;
            int noOfRaw=0;
            String LastAllocationID = null;

            while (rs.next()) {
                noOfRaw++;
                String AllocationID = rs.getString("AllocationID");
                LastAllocationID = AllocationID;
            }
            if(noOfRaw<1){
                count=1000;
            }else {
                count=Integer.parseInt(LastAllocationID.substring(1,5));
                ++count;
            }

            return "A"+count;
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args){

        AllocationView ui=new AllocationView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Manage Allocation");
        ui.setSize(300,350);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }

}
