import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PerformanceWindow extends JFrame {
    private static final String url = "jdbc:mysql://localhost:3306/EXAMS";
    private static final String username = "root";
    private static final String dbPassword = "admin123";

    String[] columns = {"Name", "E-mail", "Roll No", "Subject", "Score"};
    JTable table;
    DefaultTableModel model;

    public PerformanceWindow() {
        this.setTitle("Student's Performance");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane, BorderLayout.CENTER);
        databaseData();
        this.setVisible(true);
    }

    void databaseData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, dbPassword);

            String query = "SELECT * FROM performance";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String rollNo = resultSet.getString("roll_no");
                String subject = resultSet.getString("subject");
                int score = resultSet.getInt("score");

                model.addRow(new Object[]{name, email, rollNo, subject, score + "/" + 10});
                table.setRowHeight(50);
                table.setFont(new Font("Arial", Font.PLAIN, 20));
            }

            resultSet.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
