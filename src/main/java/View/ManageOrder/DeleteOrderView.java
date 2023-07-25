package View.ManageOrder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrderView extends JFrame {
    public JPanel contentPane;
    private JButton btnDelete;
    private JButton btnOrderDetails;
    private JTextField txtOrderID;

    public DeleteOrderView() {

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Connect to the database
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    System.out.println("DataBase Connection Success");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share_app", "root", "");

                    // Prompt the user for the record ID to delete
                    String OrderID = txtOrderID.getText();

                    // Prepare the delete query
                    String deleteQuery = "DELETE FROM customer_order WHERE Order_ID = ?";
                    PreparedStatement statement = con.prepareStatement(deleteQuery);
                    statement.setString(1, OrderID);

                    // Execute the delete query
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Order deleted successfully.");
                        JOptionPane.showMessageDialog(contentPane,"Customer Order deleted successfully", "Success", 1);
                    } else {
                        System.out.println("No record found with the specified ID.");
                        JOptionPane.showMessageDialog(contentPane,"No record found with the specified ID", "Error", 2);
                    }

                    // Close the resources
                    statement.close();
                    con.close();
                    txtOrderID.setText("");

                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnOrderDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayOrderDetailsView v1= new DisplayOrderDetailsView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Order Details");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

    }

    public static void main(String[] args){
        DeleteOrderView ui=new DeleteOrderView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Delete Order");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
