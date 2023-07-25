package View.ManageEmployee;

import javax.swing.*;
import Controllers.AddEmployeeController;
import Models.Employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AddEmployeeView extends JFrame {
    public JPanel contentPane;
    private JTextField txtempName;
    private JTextField txtEmpEmail;
    private JTextField txtEmpNumber;
    private JComboBox comboBox;
    private JButton btnAdd;
    private JButton btnEmployeeDetails;

    AddEmployeeController EmpC;
    Employee[] EmpArray;
    ArrayList<Employee> EmpList;
    int EmpCount;

    public AddEmployeeView() {
        EmpC=new AddEmployeeController();
        EmpArray=new Employee[100];
        EmpCount=0;
        EmpList=new ArrayList<>();

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String EmpID = generateEmpID();
                String EmpName = txtempName.getText();
                String EmpEmail = txtEmpEmail.getText();
                String EmpNumber = txtEmpNumber.getText();
                String JobRole = (String) comboBox.getSelectedItem();
                int NoOfOrders=0;
                if (EmpName.isEmpty() || EmpNumber.isEmpty() || EmpEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane,"Fill the Blanks", "Fail", 1);

                }else {
                    Employee EmpB = EmpC.AddEmployee(EmpID,EmpName,EmpNumber, EmpEmail,JobRole,NoOfOrders);
                    EmpArray[EmpCount] = EmpB;
                    EmpList.add(EmpB);
                    EmpCount++;
                    if(EmpC.AddEmployeeToDataBase()){
                        JOptionPane.showMessageDialog(contentPane,"Employee Successfully Added to the DataBase", "Success", 1);

                        txtempName.setText("");
                        txtEmpEmail.setText("");
                        txtEmpNumber.setText("");

                    }else {
                        JOptionPane.showMessageDialog(contentPane,"Cannot insert to the DB", "Error", 1);
                    }



                }
            }
        });

        btnEmployeeDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayEmployeeDetailsView v1= new DisplayEmployeeDetailsView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Employee Details");
                v1.setSize(600,300);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });


    }
    public String generateEmpID(){
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DataBase Connection Success");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from employee");

            int count;
            int noOfRaw=0;
            String LastEmpID = null;

            while (rs.next()) {
                noOfRaw++;
                String EmpID = rs.getString("EmpID");
                LastEmpID = EmpID;
            }
            if(noOfRaw<1){
                count=1000;
            }else {
                count=Integer.parseInt(LastEmpID.substring(2,6));
                ++count;
            }


            return "EM"+count;
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args){
        AddEmployeeView ui=new AddEmployeeView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Add Employee");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }

}
