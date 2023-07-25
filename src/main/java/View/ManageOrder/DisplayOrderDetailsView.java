package View.ManageOrder;

import View.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DisplayOrderDetailsView extends JFrame {
    public int count;
    public JPanel contentPane;
    private JButton btnAdd;
    private JButton btnUpadte;
    private JButton btnDelete;
    private JButton btnHome;
    JPanel root;
    private JTable table1;

    public DisplayOrderDetailsView() {

        DefaultTableModel tableModel;

        tableModel = new DefaultTableModel();
        table1 = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table1);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DataBase Connection Success");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/OOPproducts", "root", "");
            Statement st = con.createStatement();

            String query = "SELECT *  FROM customer_order";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int cols=rsmd.getColumnCount();
            String[] colName = new String[cols];
            colName[0]="Order ID";
            colName[1]="Customer Name";
            colName[2]="Email";
            colName[3]="Number";
            colName[4]="Order Category";
            colName[5]="Order Status";
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
            root.add(table1.getTableHeader(), BorderLayout.PAGE_START);
            root.add(scrollPane, BorderLayout.CENTER);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Driver Class Error" + ex.getMessage());
            ex.printStackTrace();
        }


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrderView v1 = new AddOrderView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Add Order");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnUpadte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateOrderView v1 = new UpdateOrderView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Update Order");
                v1.setSize(600, 600);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOrderView v1 = new DeleteOrderView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Delete Order");
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
        DisplayOrderDetailsView ui=new DisplayOrderDetailsView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Order Details");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
