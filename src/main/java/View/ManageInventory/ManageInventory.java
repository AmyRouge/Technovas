package View.ManageInventory;



import DBLayer.DataBaseConnection;
import ServiceLayer.ManageInventoryService;
import View.HomeView;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class ManageInventory extends JFrame{
    private JTextField txtSrch;
    private JButton btnSrch;
    private JButton btnShowAll;
    private JTextField txtPID;
    private JTextField txtPName;
    private JTextField txtPPrice;
    private JComboBox PcatBox;
    private JButton ADDButton;
    private JButton btnHome;
    private JButton DELETEButton;
    private JButton UPDATEButton;
    public JPanel contentPane;
    private JLabel lblError;
    private JTable tblP;
    DataBaseConnection dataBaseConnection;
    PreparedStatement pst;
    Connection con;


    public ManageInventory() {
        dataBaseConnection = DataBaseConnection.getSingleInstance();
        con = dataBaseConnection.getConnection();
        ManageInventoryService mng=new ManageInventoryService();
        tableLoad();
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
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ProductID = txtPID.getText();
                String ProductName = txtPName.getText();
                String ProductPrice=txtPPrice.getText();
                String ProductCategory = (String) PcatBox.getSelectedItem();

                try {
                    double price = 0;
                } catch (NumberFormatException ex) {
                    lblError.setText("Please Enter Only Numbers For Price");
                    return; // Stop further execution of the action listener
                }

                if (ProductName.isEmpty() || ProductPrice.isEmpty() || ProductCategory.equals("......")) {
                    if(ProductCategory.equals("")){
                        lblError.setText("Please Select Catagory");
                    }
                    else{
                        lblError.setText("Fill The Blanks");}

                }

                else {
                    try {
                        double price = Double.parseDouble(ProductPrice);
                    } catch (NumberFormatException ex) {
                        lblError.setText("Please Enter Only Numbers For Price");
                        return; // Stop further execution of the action listener
                    }
                    if(!ProductID.isEmpty())
                    {
                        JOptionPane.showMessageDialog(contentPane,"The ID You Entered Is Replaced With An Auto Generated ID", "Alert", 1);
                    }
                    lblError.setText("");

                    if(mng.addProduct(ProductName,ProductPrice,ProductCategory)){
                        JOptionPane.showMessageDialog(contentPane,"Product Successfully Added to the DataBase", "Success", 1);
                        tableLoad();
                        clear();

                    }else {
                        JOptionPane.showMessageDialog(contentPane,"Cannot insert to the DB", "Error", 1);
                    }
                }
            }
        });
        //table eke row select krnn
        tblP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = tblP.getSelectedRow();


                if (selectedRow >= 0) {
                    String productID = tblP.getValueAt(selectedRow, 0).toString();
                    String productName = tblP.getValueAt(selectedRow, 1).toString();
                    String productPrice = tblP.getValueAt(selectedRow, 2).toString();
                    String productCategory = tblP.getValueAt(selectedRow, 3).toString();


                    txtPID.setText(productID);
                    txtPName.setText(productName);
                    txtPPrice.setText(productPrice);
                    PcatBox.setSelectedItem(productCategory);
                }
            }
        });



        btnShowAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableLoad();
                clear();
            }
        });


        btnSrch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword=txtSrch.getText();

                if (!keyword.equals("")) {
                    try {
                        pst = con.prepareStatement("select Product_Name,Price,Category,Product_ID from products where Product_ID = '"+keyword+"'  OR Product_Name = '"+keyword+"'");
                        ResultSet rs = pst.executeQuery();

                        if (rs.next()) {

                            String Name = rs.getString(1);
                            String Price = rs.getString(2);
                            String Cat = rs.getString(3);
                            String id = rs.getString(4);
                            tableserch(keyword);
                            txtPID.setText(id);
                            lblError.setText("");
                            txtPName.setText(Name);
                            txtPPrice.setText(Price);
                            PcatBox.setSelectedItem(Cat);
                        } else {

                            tableLoad();
                            clear();
                            lblError.setText(keyword + " is Invalid Keyword");

                        }
                    } catch (SQLException e1) {
                        System.out.println("SQL ISSE");
                    }
                } else {
                    tableLoad();
                    clear();
                    lblError.setText("Please Enter ID Or Product Name");
                }
            }
        });


        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, price, cat, id;

                name = txtPName.getText();
                price = txtPPrice.getText();
                cat = (String)PcatBox.getSelectedItem();
                id = txtPID.getText();
                try {
                    double Checprice = Double.parseDouble(price);
                } catch (NumberFormatException ex) {
                    lblError.setText("Please Enter Only Numbers For Price");
                    return; // Stop further execution of the action listener
                }
                if(mng.UpdateProduct(id,name,price,cat)){
                    JOptionPane.showMessageDialog(contentPane, "Update succesful","Success",JOptionPane.INFORMATION_MESSAGE);
                    tableLoad();
                    clear();
                }
                else{
                    JOptionPane.showMessageDialog(contentPane,"Cannot UPDATE DB", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=txtPID.getText();
                int confirmation = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this product?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );if (confirmation == JOptionPane.YES_OPTION) {
                    if(mng.DeleteProduct(id)) {
                        JOptionPane.showMessageDialog(null, "Delete Succsessfull","Success",JOptionPane.INFORMATION_MESSAGE);
                        tableLoad();
                        clear();
                    }
                    else {
                        JOptionPane.showMessageDialog(contentPane,"Cannot DELETE ITEM", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


    }




    void tableLoad() {
        try {
            pst = con.prepareStatement("select * from products");
            ResultSet rs = pst.executeQuery();
            tblP.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e1) {
            System.out.println("SQL ISSUE");
        }
    }
    void tableserch(String keyword) {
        try {
            pst = con.prepareStatement("select * from products where Product_ID = ?  OR Product_Name = ?");
            pst.setString(1, keyword);
            pst.setString(2, keyword);
            ResultSet rs = pst.executeQuery();
            tblP.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e1) {
            System.out.println("SQL ISSUE");
        }
    }
    void clear(){
        txtPName.setText("");
        txtPPrice.setText("");
        PcatBox.setSelectedItem("");
        txtPID.setText("");
        txtSrch.setText("");
        lblError.setText("");
    }

    public static void main(String[] args){

        ManageInventory ui=new ManageInventory();

        ui.setContentPane(ui.contentPane);
        ui.setTitle("Manage Inventory");
        ui.pack();
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);

    }

}
