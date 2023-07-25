package View.ManageEmployee;

import View.ManageOrder.DeleteOrderView;
import View.ManageOrder.DisplayOrderDetailsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteEmployeeView extends JFrame {
    public JPanel contentPane;
    private JTextField txtEmpID;
    private JButton btnDelete;
    private JButton btnEmpDetails;

    public DeleteEmployeeView() {
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Connect to the database
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    System.out.println("DataBase Connection Success");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");

                    // Prompt the user for the record ID to delete
                    String EmpID = txtEmpID.getText();


                    // Prepare the delete query
                    String deleteQuery = "DELETE FROM employee WHERE EmpID = ?";
                    PreparedStatement statement = con.prepareStatement(deleteQuery);
                    statement.setString(1, EmpID);

                    // Execute the delete query
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Employee deleted successfully.");
                        JOptionPane.showMessageDialog(contentPane,"Employee deleted successfully", "Success", 1);
                    } else {
                        System.out.println("No record found with the specified ID.");
                        JOptionPane.showMessageDialog(contentPane,"No record found with the specified ID", "Error", 2);
                    }

                    // Close the resources
                    statement.close();
                    con.close();

                    txtEmpID.setText("");

                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnEmpDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayEmployeeDetailsView v1= new DisplayEmployeeDetailsView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Employee Details");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });
    }
    public static void main(String[] args){
        DeleteEmployeeView ui=new DeleteEmployeeView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Remove Employee");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }

}
