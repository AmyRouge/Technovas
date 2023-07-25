package View.ManageEmployee;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateEmployeeView extends JFrame {
    public JPanel contentPane;
    private JTextField txtEmpID;
    private JTextField txtEmpName;
    private JTextField txtEmpEmail;
    private JTextField txtEmpNo;
    private JComboBox comboBox;
    private JButton btnUpdate;
    private JButton btnEmpDetails;

    public UpdateEmployeeView() {

        btnEmpDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayEmployeeDetailsView v1 = new DisplayEmployeeDetailsView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Employee Details");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String EmpID = txtEmpID.getText();
                String EmpName = txtEmpName.getText();
                String EmpNumber = txtEmpNo.getText();
                String EmpEmail = txtEmpEmail.getText();
                String JobRole = (String) comboBox.getSelectedItem();
                if (EmpName.isEmpty() || EmpNumber.isEmpty() || EmpEmail.isEmpty()|| EmpID.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane,"Fill the Blanks", "Fail", 1);

                }else{
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        System.out.println("DataBase Connection Success");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");


                        String updateQuery1 = "UPDATE employee" +
                                " SET EmpName = ?,EmpEmail = ?,EmpNumber = ?,Job_Role = ?" +
                                "WHERE EmpID = ?";
                        PreparedStatement statement1 = con.prepareStatement(updateQuery1);
                        statement1.setString(1, EmpName);
                        statement1.setString(2, EmpEmail);
                        statement1.setString(3, EmpNumber);
                        statement1.setString(4, JobRole);
                        statement1.setString(5, EmpID);


                        // Execute the update statement
                        int rowsAffected1 = statement1.executeUpdate();

                        if (rowsAffected1 > 0) {
                            System.out.println("Employee details updated successfully.");
                            JOptionPane.showMessageDialog(contentPane,"Employee updated successfully", "Success", 1);
                        } else {
                            System.out.println("Failed to update Employee details.");
                            JOptionPane.showMessageDialog(contentPane,"Failed to update Employee details", "Fail", 2);
                        }

                        // Close the resources
                        statement1.close();
                        con.close();


                        txtEmpID.setText("");
                        txtEmpName.setText("");
                        txtEmpNo.setText("");
                        txtEmpEmail.setText("");

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });

    }

    public static void main(String[] args){
        UpdateEmployeeView ui=new UpdateEmployeeView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Update Employee");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }

}
