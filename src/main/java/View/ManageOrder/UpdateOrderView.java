package View.ManageOrder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateOrderView extends JFrame {
    public JPanel contentPane;
    private JTextField txtOrderID;
    private JTextField txtCustomerName;
    private JTextField txtEmail;
    private JComboBox comboBox1;
    private JButton btnupdate;
    private JButton btnOrderDetails;
    private JTextField txtNumber;

    public UpdateOrderView() {

        btnOrderDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayOrderDetailsView v1 = new DisplayOrderDetailsView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Order Details");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String OrderID = txtOrderID.getText();
                String CustomerName = txtCustomerName.getText();
                String CustomerNumber = txtNumber.getText();
                String CustomerEmail = txtEmail.getText();
                String CustomerCategory = (String) comboBox1.getSelectedItem();
                if (CustomerName.isEmpty() || CustomerNumber.isEmpty() || CustomerEmail.isEmpty()|| OrderID.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane,"Fill the Blanks", "Fail", 1);

                }else{
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        System.out.println("DataBase Connection Success");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");

                        //1---> Customer details
                        String updateQuery1 = "UPDATE customer_order " +
                                "SET Customer_Name = ?,Customer_Email = ?, Customer_Number = ?, Order_Category = ?" +
                                " WHERE Order_ID = ?";
                        PreparedStatement statement1 = con.prepareStatement(updateQuery1);
                        statement1.setString(1, CustomerName);
                        statement1.setString(2, CustomerEmail);
                        statement1.setString(3, CustomerNumber);
                        statement1.setString(4, CustomerCategory);
                        statement1.setString(5, OrderID);


                        // Execute the update statement
                        int rowsAffected1 = statement1.executeUpdate();
                        if (rowsAffected1 > 0 ) {
                            System.out.println("Order details updated successfully.");
                            JOptionPane.showMessageDialog(contentPane,"Customer Order updated successfully", "Success", 1);
                        } else {
                            System.out.println("Failed to update order details.");
                        }


                        // Close the resources
                        statement1.close();
                        con.close();


                        txtOrderID.setText("");
                        txtCustomerName.setText("");
                        txtEmail.setText("");
                        txtNumber.setText("");

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
        UpdateOrderView ui=new UpdateOrderView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Update Order");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }


}
