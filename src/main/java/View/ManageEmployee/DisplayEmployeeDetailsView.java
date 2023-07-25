package View.ManageEmployee;

import View.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DisplayEmployeeDetailsView extends JFrame {
    public JPanel contentPane;
    private JPanel root;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnHome;

    public DisplayEmployeeDetailsView() {

        JTable table;
        DefaultTableModel tableModel;

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DataBase Connection Success");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/OOPproducts", "root", "");
            Statement st = con.createStatement();

            String query = "SELECT *  FROM employee";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int cols=rsmd.getColumnCount();
            String[] colName = new String[cols];
            colName[0]="Employee ID";
            colName[1]="Employee Name";
            colName[2]="Email";
            colName[3]="Number";
            colName[4]="Job Role";
            colName[5]="No Of Orders";
            tableModel.setColumnIdentifiers(colName);
            while (rs.next()) {
                Object[] rowData = new Object[cols];
                for (int i = 1; i <= cols; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(rowData);
            }

            // Close the resources
            rs.close();
            st.close();
            con.close();

            root.setLayout(new BorderLayout());
            root.add(table.getTableHeader(), BorderLayout.PAGE_START);
            root.add(scrollPane, BorderLayout.CENTER);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Driver Class Error" + ex.getMessage());
            ex.printStackTrace();
        }


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeView v1 = new AddEmployeeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Add Employee");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateEmployeeView v1 = new UpdateEmployeeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Update Employee");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteEmployeeView v1 = new DeleteEmployeeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Remove Employee");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeView v1 = new HomeView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Home");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

    }

    public static void main(String[] args){
        DisplayEmployeeDetailsView ui=new DisplayEmployeeDetailsView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Employee Details");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }

}
