import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentCreateAccount extends JFrame implements ActionListener {
    private static final String url = "jdbc:mysql://localhost:3306/EXAMS";
    private static final String username = "root";
    private static final String dbPassword = "admin123"; // Renamed to avoid conflict with GUI password field

    JButton Gologin, submit;
    JTextField textName, emailField, collegeField, yearField, divField, rollnoField;
    JPasswordField passwordField;

    StudentCreateAccount() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Student Registration");
        this.setLayout(null);

        JLabel title = new JLabel("Create Your Student Account");
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setBounds(90, 2, 500, 100);

        // Submit
        submit = new JButton("Submit");
        submit.setFocusable(false);
        submit.setBackground(new Color(101, 140, 60));
        submit.setBounds(200, 550, 100, 50);
        submit.addActionListener(this);

        // Login
        Gologin = new JButton("Already have an Account?");
        Gologin.setFocusable(false);
        Gologin.setBounds(500, 550, 200, 50);
        Gologin.addActionListener(this);

        // Labels & Fields
        JLabel name = new JLabel("Name: ");
        name.setFont(new Font("Consolas", Font.PLAIN, 20));
        name.setBounds(100, 100, 140, 35);
        textName = new JTextField(20);
        textName.setBounds(300, 100, 300, 25);

        JLabel email = new JLabel("E-mail: ");
        email.setFont(new Font("Consolas", Font.PLAIN, 20));
        email.setBounds(100, 150, 140, 35);
        emailField = new JTextField(50);
        emailField.setBounds(300, 150, 300, 25);

        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Consolas", Font.PLAIN, 20));
        password.setBounds(100, 200, 140, 35);
        passwordField = new JPasswordField(50);
        passwordField.setBounds(300, 200, 300, 25);

        JLabel collegeName = new JLabel("College Name: ");
        collegeName.setFont(new Font("Consolas", Font.PLAIN, 20));
        collegeName.setBounds(100, 250, 175, 35);
        collegeField = new JTextField(40);
        collegeField.setBounds(300, 250, 300, 25);

        JLabel year = new JLabel("Year: ");
        year.setFont(new Font("Consolas", Font.PLAIN, 20));
        year.setBounds(100, 300, 140, 35);
        yearField = new JTextField(40);
        yearField.setBounds(300, 300, 300, 25);

        JLabel div = new JLabel("Division: ");
        div.setFont(new Font("Consolas", Font.PLAIN, 20));
        div.setBounds(100, 350, 140, 35);
        divField = new JTextField(40);
        divField.setBounds(300, 350, 300, 25);

        JLabel rollNo = new JLabel("Roll No.: ");
        rollNo.setFont(new Font("Consolas", Font.PLAIN, 20));
        rollNo.setBounds(100, 400, 140, 35);
        rollnoField = new JTextField(40);
        rollnoField.setBounds(300, 400, 300, 25);

        this.add(title);
        this.add(name); this.add(textName);
        this.add(email); this.add(emailField);
        this.add(password); this.add(passwordField);
        this.add(collegeName); this.add(collegeField);
        this.add(year); this.add(yearField);
        this.add(div); this.add(divField);
        this.add(rollNo); this.add(rollnoField);

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
            String year = yearField.getText();
            String division = divField.getText();
            String rollNo = rollnoField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || college.isEmpty() || year.isEmpty() || division.isEmpty() || rollNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int yearInt = Integer.parseInt(year);
                int rollNoInt = Integer.parseInt(rollNo);


                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, dbPassword);


                String checkQuery = "SELECT * FROM studentAccount WHERE email = ?";
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
                String insertQuery = "INSERT INTO studentAccount (name, email, password, college, year, division, roll_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, name);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);
                insertStmt.setString(4, college);
                insertStmt.setInt(5, yearInt);
                insertStmt.setString(6, division);
                insertStmt.setInt(7, rollNoInt);

                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    new StudentDashboard(email, rollNoInt, name);
                    this.dispose();
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
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Year or Roll No!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == Gologin) {
            this.dispose();
            new StudentLogin();
        }
    }
}
