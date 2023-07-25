package View.ManageEmployee;

import View.HomeView;
import View.ManageOrder.DisplayOrderDetailsView;

import javax.swing.*;
import java.awt.event.*;

public class ManageEmployeeView extends JFrame {
    public JPanel contentPane;
    private JButton btnAdd;
    private JButton btnUpadate;
    private JButton btnDelete;
    private JButton btnEmployeeDetails;
    private JButton btnHome;

    public ManageEmployeeView() {


        btnEmployeeDetails.addActionListener(new ActionListener() {
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

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeView v1= new AddEmployeeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Add Employee");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnUpadate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateEmployeeView v1= new UpdateEmployeeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Update Employee");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteEmployeeView v1= new DeleteEmployeeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Delete Employee");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });


        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeView v1= new HomeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Home");
                v1.setSize(600,600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });


    }
    public static void main(String[] args){
        ManageEmployeeView ui=new ManageEmployeeView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Manage Order");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
