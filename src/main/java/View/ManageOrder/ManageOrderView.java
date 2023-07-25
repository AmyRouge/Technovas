package View.ManageOrder;

import View.HomeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageOrderView extends JFrame {
    public JPanel contentPane;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnRemove;
    private JButton btnOrderDetails;
    private JButton btnHome;

    public ManageOrderView() {

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeView v1= new HomeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Home");
                v1.setSize(350,500);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrderView v1= new AddOrderView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Add Order");
                v1.setSize(350,300);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateOrderView v1= new UpdateOrderView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Update Order");
                v1.setSize(350,300);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOrderView v1= new DeleteOrderView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Delete Order");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
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
        ManageOrderView ui=new ManageOrderView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Manage Order");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
