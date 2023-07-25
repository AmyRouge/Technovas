package View.CustomerNotify;

import DBLayer.DataBaseConnection;
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

public class cusNotify extends JFrame{
    private JTextField txtCusEmail;
    private JTextField txtCusSub;
    private JTextArea txtCusMsg;
    private JButton HOMEButton;
    private JButton SENDButton;
    private JTable tblCus;
    public JPanel contentPane;

    DataBaseConnection dataBaseConnection;
    PreparedStatement pst;
    Connection con;
public cusNotify() {
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
            String recipient = txtCusEmail.getText();
            String subject = txtCusSub.getText();
            String content = txtCusMsg.getText();

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
    tblCus.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int selectedRow = tblCus.getSelectedRow();


            if (selectedRow >= 0) {
                String cusName = tblCus.getValueAt(selectedRow, 0).toString();
                String cusEmail = tblCus.getValueAt(selectedRow, 1).toString();

                txtCusEmail.setText(cusEmail);
                txtCusSub.setText("Your Order Is Ready");
                txtCusMsg.setText("Hello "+cusName+" Sir,"+
                        "\nYour Order Ready And You Can Take It"+
                        "\nThank You!!!");
            }

        }
    });
}

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
            pst = con.prepareStatement("SELECT CustomerName,CustomerEmail,Status FROM customer_order where Status='Allocated'");
            ResultSet rs = pst.executeQuery();
            tblCus.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e1) {
            System.out.println("SQL ISSUE");
        }
    }

    public static void main(String[] args){
        cusNotify ui=new cusNotify();
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Email to Customer");
        ui.pack();
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
