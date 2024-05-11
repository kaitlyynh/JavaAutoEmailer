package emailer;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
/**
 *
 * @author kaitlynhuynh
 */
public class Emailer {
    private static String department;
    private static int deptCount;
    private JButton[] deptButtons; 
    private DefaultTableModel model; 
    private JPanel rightPanel; 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Emailer e = new Emailer();
    }
    
    public Emailer() {
        setupUI(); 
        setupRightPanel(); 
    }


    private void setupUI(){
        JFrame jf  = new JFrame("Send Automated Shift");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); 
        JPanel leftPanel = new JPanel();
        rightPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.yellow); // might not show
        rightPanel.setBackground(Color.green);
        JPanel leftPanelUpper = new JPanel();
        JPanel leftPanelLower = new JPanel();
        leftPanel.setLayout(new GridLayout(2,1));  
        leftPanelUpper.setBackground(Color.lightGray);
        leftPanelLower.setBackground(Color.cyan);
        leftPanel.add(leftPanelUpper); 
        leftPanel.add(leftPanelLower); 
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(300, jf.getHeight()));
        jf.add(mainPanel);
        jf.setSize(1000, 1000);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creating a File object for directory
        File selectDept = new File("./src/emailer/Departments");
        System.out.println(selectDept.getAbsolutePath());
        //List of all files and directories
        String contents[] = selectDept.list();
       // System.out.println("List of files and directories in the specified directory:");
        for(int i=0; i<contents.length; i++) {
           deptCount++;
           System.out.println(contents[i] + deptCount);
        }
        deptButtons = new JButton[deptCount];
        leftPanelUpper.setLayout(new GridLayout(deptCount,1));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Departments"); 
        leftPanelUpper.setBorder(titledBorder);
        leftPanel.add(leftPanelUpper); 
        leftPanel.add(leftPanelLower);
        Color[] colors = {Color.LIGHT_GRAY.brighter(), Color.PINK.brighter(), Color.ORANGE};
        String[] symbols = {"Security","Healthcare", "Education"};
        
        for (int i = 0; i < deptCount; i++) {
            deptButtons[i] = new JButton(contents[i]);
            System.out.println("Department Button: " + contents[i]);
            deptButtons[i].setBackground(Color.lightGray);
            deptButtons[i].setForeground(Color.BLACK); 
            deptButtons[i].setOpaque(true);
            deptButtons[i].setBorderPainted(true);
            GraphicsButton button = new GraphicsButton(contents[i],symbols[i]);
           // deptButtons[i] = new graphics(contents[i], colors[i]);
            leftPanelUpper.add(deptButtons[i]);
            leftPanelUpper.add(button);
            deptButtons[i].addActionListener(new DepartmentButtonListener());
        }
        // Action toolbar
        TitledBorder titledBorder2 = BorderFactory.createTitledBorder("Action toolbar"); 
        leftPanelLower.setBorder(titledBorder2);
        JButton importButton = new JButton("Import Most Recent File");
        JButton sendEmail = new JButton("Send Emails");
        sendEmail.addActionListener(e -> sendEmails()); // action listern for box selected 
        leftPanelLower.add(importButton);
        leftPanelLower.add(sendEmail);
        jf.setVisible(true);
    }
    
    private void sendEmails() {
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 4); // check which ones are selected 
            if (Boolean.TRUE.equals(isSelected)) {
                String email = (String) model.getValueAt(i, 1); // Get the email address
                String name = (String) model.getValueAt(i, 0); // Get the name
                sendEmail(email); // Send the email 
                updateEmailStatus(email, "yes"); // Update status in the database
                model.setValueAt("yes", i, 3); // Update the table model
            }
        }
    }
    
    private void updateEmailStatus(String email, String status) {
        String url2 = "jdbc:mysql://localhost:3306/users?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=America/New_York";
        try {
          //  Connection connection = java.sql.DriverManager.getConnection(url, "root", "2003");
           Connection connection = DriverManager.getConnection(url2,"root","");
          // System.out.println("Connection to the database was successful.");
           String query =  "UPDATE users SET email_status = ? WHERE email = ?"; 
           PreparedStatement preparedS = connection.prepareStatement(query);
           preparedS.setString(1, status);
           preparedS.setString(2, email);
           preparedS.executeUpdate(); 
           connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void sendEmail(String email){
        System.out.println("Sending email to " + email);
    }
        
    
    private void setupRightPanel() {
        model = new DefaultTableModel(new Object[]{"Name", "Email", "Role", "Email Status", "Select"}, 0){ // creates a table 
        
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                    case 1:
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return Boolean.class; // to make sure to use checkboxes set emailstatus to boolean
                    default:
                        return Object.class;
                }
            }
        };
             
       JTable table = new JTable(model);
       JScrollPane scroll = new JScrollPane(table); 
       scroll.setBorder(BorderFactory.createTitledBorder("Select a Department"));
       rightPanel.add(scroll, BorderLayout.CENTER);
    }
                              
    
    
    private void getUserdata(String department){
        //   String url = "jdbc:mysql://localhost/user_info"; 
        String url2 = "jdbc:mysql://localhost:3306/users?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=America/New_York";
        try {
          //  Connection connection = java.sql.DriverManager.getConnection(url, "root", "2003");
           Connection connection = DriverManager.getConnection(url2,"root","");
          // System.out.println("Connection to the database was successful.");
           String query = "SELECT * FROM users WHERE Department = ?"; 
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1, department); 
           ResultSet resultSet = preparedStatement.executeQuery();
           model.setRowCount(0);// clears the previous results 
           
             // read all data from query 
           while (resultSet.next()) {
              String name = resultSet.getString("first") + " " + resultSet.getString("last");
              String email = resultSet.getString("email");
              String role = resultSet.getString("roles");
              String emailS = resultSet.getString("email_status"); 
              Boolean status = "yes".equalsIgnoreCase(emailS); // convert emailstatus to Boolean here
              model.addRow(new Object[]{name, email, role, emailS, status});  // new row to model
           }
           // update the border title "department" subscribers 
           TitledBorder border = (TitledBorder) ((JScrollPane) rightPanel.getComponent(0)).getBorder();
           border.setTitle(department + " Subscribers");
           rightPanel.revalidate();
           rightPanel.repaint();
            
           resultSet.close();
           preparedStatement.close();
           connection.close();
           
       }catch (SQLException e) {
           System.err.println("Connection failed: " + e.getMessage());
       }
    }
    
    class DepartmentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < deptCount; i++) {
                if (e.getSource() == deptButtons[i]) {
                    System.out.println(deptButtons[i].getText() + " was pressed.");
                    if (department == deptButtons[i].getText()) {
                        System.out.println(department + " unselected ");
                        department = null;         
                    } else {
                        department = deptButtons[i].getText();
                        getUserdata(department); 
                    }
                    
                }
            }
        }  
    }       
}
