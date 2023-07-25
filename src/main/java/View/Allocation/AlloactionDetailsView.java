package View.Allocation;

import View.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AlloactionDetailsView extends JFrame {
    public JPanel contentPane;
    private JButton btnHome;
    private JButton btnAllocate;
    private JPanel root;

    public AlloactionDetailsView() {

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

            String query = "SELECT *  FROM allocation";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int cols=rsmd.getColumnCount();
            String[] colName = new String[cols];
            colName[0]="Allocation ID";
            colName[1]="Order ID";
            colName[2]="Employee ID";
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

        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllocationView v1= new AllocationView();
                setVisible(false);
                v1.setContentPane(v1.contentPane);
                v1.setTitle("Allocation");
                v1.setSize(300,350);
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            }
        });

    }

    public static void main(String[] args){
        AlloactionDetailsView ui=new AlloactionDetailsView();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Allocation Details");
        ui.setSize(600,600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}

