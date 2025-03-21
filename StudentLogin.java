import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentLogin extends JFrame implements ActionListener {
    private static final String url = "jdbc:mysql://localhost:3306/EXAMS";
    private static final String username = "root";
    private static final String dbPassword = "admin123"; // Renamed to avoid conflict with GUI password field

    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton, createAccountButton;

    StudentLogin() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Student Login");
        this.setLayout(null);

        JLabel title = new JLabel("Student Login");
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setBounds(90, 2, 300, 100);

        // Labels
        JLabel email = new JLabel("E-mail: ");
        email.setFont(new Font("Consolas", Font.PLAIN, 20));
        email.setBounds(100, 150, 140, 35);


        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Consolas", Font.PLAIN, 20));
        password.setBounds(100, 200, 140, 35);

        // Fields
        emailField = new JTextField(50);
        emailField.setBounds(300, 150, 300, 25);


        passwordField = new JPasswordField(50);
        passwordField.setBounds(300, 200, 300, 25);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        loginButton.setBackground(new Color(101, 140, 60));
        loginButton.setBounds(200, 300, 100, 50);
        loginButton.addActionListener(this);

        createAccountButton = new JButton("Create Account");
        createAccountButton.setFocusable(false);
        createAccountButton.setBounds(350, 300, 150, 50);
        createAccountButton.addActionListener(this);

        this.add(title);
        this.add(email);
        this.add(emailField);
        this.add(password);
        this.add(passwordField);
        this.add(loginButton);
        this.add(createAccountButton);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, dbPassword);


                String query = "SELECT * FROM studentAccount WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    int Rollno = resultSet.getInt("roll_no");
                    String username = resultSet.getString("name");

                    if (storedPassword.equals(password)) {
                        JOptionPane.showMessageDialog(this, "Login Successful!");
                        this.dispose();
                        new StudentDashboard(email, Rollno, username);
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect Password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Email not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                preparedStatement.close();
                connection.close();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == createAccountButton) {
            this.dispose();
            new StudentCreateAccount();
        }
    }


}
