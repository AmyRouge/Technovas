package View.EMPemail;

import DBLayer.DataBaseConnection;
import ServiceLayer.ManageInventoryService;
import View.HomeView;
import net.proteanit.sql.DbUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class empEmail extends JFrame {
    private JTextField txtEmailSubject; // Text field for email subject
    private JTextField txtReciverEmail; // Text field for recipient's email address
    private JTextArea txtEmailMsg; // Text area for email content
    private JButton HOMEButton; // Button to go back to the home screen
    private JButton SENDButton; // Button to send the email
    public JPanel contentPane; // Main content panel for the frame
    private JTable tblEmpNotfy;
    DataBaseConnection dataBaseConnection;
    PreparedStatement pst;
    Connection con;

    public empEmail() {
        dataBaseConnection = DataBaseConnection.getSingleInstance();
        con = dataBaseConnection.getConnection();
        tableLoad();
        HOMEButton.addActionListener(new ActionListener() {
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

        SENDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipient = txtReciverEmail.getText();
                String subject = txtEmailSubject.getText();
                String content = txtEmailMsg.getText();

                if (recipient.isEmpty() || subject.isEmpty() || content.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Send the email
                if (sendEmail(recipient, subject, content)) {
                    JOptionPane.showMessageDialog(contentPane, "Email sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Failed to send the email.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        tblEmpNotfy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = tblEmpNotfy.getSelectedRow();


                if (selectedRow >= 0) {
                    String empEmail = tblEmpNotfy.getValueAt(selectedRow, 0).toString();
                    String orderID = tblEmpNotfy.getValueAt(selectedRow, 1).toString();
                    String cusName = tblEmpNotfy.getValueAt(selectedRow, 2).toString();
                    String cusNumber = tblEmpNotfy.getValueAt(selectedRow, 3).toString();
                    String orderCat = tblEmpNotfy.getValueAt(selectedRow, 4).toString();


                    txtReciverEmail.setText(empEmail);
                    txtEmailSubject.setText("Complete Order Soon");
                    txtEmailMsg.setText("Your Order ID = "+orderID+"\nYour Customer Name = "+cusName+
                            "\nYour Customer's Contact Number = "+cusNumber+
                            "\nYour Order Catogeory = "+orderCat);
                }
            }
        });
    }

    // Method to send the email using JavaMail API
    private boolean sendEmail(String recipient, String subject, String content) {
        // Replace these with your email account's SMTP settings
        final String username = "sharemy2002@gmail.com";
        final String password = "bnrlwiasmeztbmuk";

        // SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server address
        props.put("mail.smtp.port", "587"); // Replace with your SMTP server port

        // Create a session with the SMTP server
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Set the email subject and content
            message.setSubject(subject);
            message.setText(content);

            // Send the email
            Transport.send(message);
            return true; // Email sent successfully
        } catch (MessagingException ex) {
           ex.printStackTrace();
            System.out.println("massage faild");
            return false; // Failed to send the email
        }
    }
    void tableLoad() {
        try {
            pst = con.prepareStatement("SELECT emp.EmpEmail, cus.Order_ID, cus.CustomerName, cus.CustomerNumber, cus.OrderCategory FROM allocation alc JOIN customer_order cus ON alc.Order_ID = cus.Order_ID JOIN employee emp ON alc.EmpID = emp.EmpID WHERE cus.Status = 'Allocated'");
            ResultSet rs = pst.executeQuery();
            tblEmpNotfy.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e1) {
            System.out.println("SQL ISSUE");
        }
    }
    public static void main(String[] args){
        empEmail ui=new empEmail();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Email to Employee");
        ui.pack();
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }


}
