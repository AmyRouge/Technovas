package View.ManageOrder;

import Controllers.AddOrderController;
import Models.CustomerOrder;
import ServiceLayer.OrderService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class AddOrderView extends JFrame {
    public JPanel contentPane;
    private JTextField txtCusName;
    private JTextField txtCusEmail;
    private JTextField txtCusNo;
    private JComboBox comboBoxOrder;
    private JButton btnAdd;
    private JButton btnOrderDetails;




    AddOrderController OrderC;
    CustomerOrder[] orderArray;
    ArrayList<CustomerOrder> OrderList;
    int orderCount;

    public AddOrderView() {
        OrderC=new AddOrderController();
        orderArray=new CustomerOrder[100];
        orderCount=0;
        OrderList=new ArrayList<>();


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderService or = new OrderService();

                    String OrderID = or.generateOrderID();
                    String CustomerName = txtCusName.getText();
                    String CustomerEmail = txtCusEmail.getText();
                    String CustomerNumber = txtCusNo.getText();
                    String CustomerCategory = (String) comboBoxOrder.getSelectedItem();
                    String Status = "Incomplete";

                    if (CustomerName.isEmpty() || CustomerNumber.isEmpty() || CustomerEmail.isEmpty()) {
                        JOptionPane.showMessageDialog(contentPane,"Fill the Blanks", "Fail", 1);

                    }else {
                        CustomerOrder OrderB = OrderC.AddOrder(OrderID,CustomerName,CustomerNumber, CustomerEmail,CustomerCategory,Status);
                        orderArray[orderCount] = OrderB;
                        OrderList.add(OrderB);
                        orderCount++;
                        if(OrderC.AddOrderToDataBase()){
                            JOptionPane.showMessageDialog(contentPane,"Order Successfully Added to the DataBase", "Success", 1);

                            txtCusName.setText("");
                            txtCusEmail.setText("");
                            txtCusNo.setText("");

                        }else {
                            JOptionPane.showMessageDialog(contentPane,"Cannot insert to the DB", "Error", 1);
                        }



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
                v1.setSize(600,300);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

    }



    public static void main(String[] args){
        AddOrderView ui=new AddOrderView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Add Order");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
