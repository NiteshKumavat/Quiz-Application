import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeacherCreateAccount extends JFrame implements ActionListener {

    private static final String url = "jdbc:mysql://localhost:3306/EXAMS";
    private static final String username = "root";
    private static final String dbPassword = "admin123";

    JButton Gologin;

    JButton submit;
    JTextField textName;
    JTextField emailField;
    JPasswordField passwordField;
    JTextField collegeField;
    TeacherCreateAccount(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Teacher Login Form");
        this.setLayout(null);

        JLabel title = new JLabel("Create Your Teacher Account");
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));
        title.setBounds(90, 2, 500, 100);

        //Submit
        submit = new JButton("Submit");
        submit.setFocusable(false);
        submit.setBackground(new Color(101, 140, 60));
        submit.setBounds(200, 350, 200, 50);
        submit.addActionListener(this);


        //login page
        Gologin = new JButton("Already have an Account ?");
        Gologin.setFocusable(false);
        Gologin.setBounds(500, 350, 200, 50);
        Gologin.addActionListener(this);


        //Labels
        JLabel name = new JLabel("Name : ");
        name.setFont(new Font("Consolas", Font.PLAIN, 20));
        name.setBounds(100,100, 140, 35);


        JLabel email = new JLabel("E-mail : ");
        email.setFont(new Font("Consolas", Font.PLAIN, 20));
        email.setBounds(100,150, 140, 35);


        JLabel password = new JLabel("Password : ");
        password.setFont(new Font("Consolas", Font.PLAIN, 20));
        password.setBounds(100,200, 140, 35);


        JLabel collegeName = new JLabel("College name : ");
        collegeName.setFont(new Font("Consolas", Font.PLAIN, 20));
        collegeName.setBounds(100,250, 175, 35);



        // Fields
        textName = new JTextField(20);
        textName.setBounds(300,100, 300, 25);


        emailField = new JTextField(50);
        emailField.setBounds(300,150, 300, 25);


        passwordField = new JPasswordField(50);
        passwordField.setBounds(300,200, 300, 25);


        collegeField = new JTextField(40);
        collegeField.setBounds(300,250, 300, 25);




        this.add(title);

        this.add(name);
        this.add(textName);
        this.add(email);
        this.add(emailField);
        this.add(password);
        this.add(passwordField);
        this.add(collegeName);
        this.add(collegeField);

        this.add(submit);
        this.add(Gologin);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String name = textName.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String college = collegeField.getText();


            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || college.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {



                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, dbPassword);


                String checkQuery = "SELECT * FROM teacherAccount WHERE email = ?";
                PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
                checkStmt.setString(1, email);
                ResultSet resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Email already exists! Please use a different email or Use Login Button.", "Error", JOptionPane.ERROR_MESSAGE);
                    checkStmt.close();
                    connection.close();
                    return;
                }
                checkStmt.close();

                // Insert Data
                String insertQuery = "INSERT INTO teacherAccount (name, email, password, college) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, name);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);
                insertStmt.setString(4, college);


                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    this.dispose();
                    new TeacherDashboard(email);
                } else {
                    JOptionPane.showMessageDialog(this, "Error creating account!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                insertStmt.close();
                connection.close();

            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
            }
        } else if (e.getSource() == Gologin) {
            this.dispose();
            new TeacherLogin();
        }
    }


}
