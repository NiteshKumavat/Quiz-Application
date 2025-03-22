import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;

public class MainWindow extends JFrame {
    private final List<Question> questions;
    private final String email;
    private final int rollNo;
    private final String userName;
    private final String subject = "Java";

    private int currentQuestion = 0;
    private int score = 0;
    private static final String url = "jdbc:mysql://localhost:3306/EXAMS";
    private static final String username = "root";
    private static final String dbPassword = "admin123";

    private JLabel titleLabel;
    private JLabel questionLabel;
    private JButton[] optionButtons;

    public MainWindow(List<Question> questions, String email, int rollNo, String userName) {
        this.questions = questions;
        this.email = email;
        this.rollNo = rollNo;
        this.userName = userName;
        initializeUI();
        showNextQuestion();
    }

    private void initializeUI() {
        setTitle("Quiz Exam");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Label
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setPreferredSize(new Dimension(700, 500));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cardPanel.add(questionLabel, BorderLayout.CENTER);

        add(cardPanel, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setBackground(new Color(173, 216, 230));
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionButtons[i].addActionListener(this::checkAnswer);
            optionsPanel.add(optionButtons[i]);
        }

        add(optionsPanel, BorderLayout.SOUTH);
    }

    private void showNextQuestion() {
        if (currentQuestion >= questions.size()) {
            savePerformance();
            JOptionPane.showMessageDialog(this, "Quiz Complete! Score: " + score + "/" + questions.size());

            this.dispose();
            return;
        }

        Question q = questions.get(currentQuestion);
        titleLabel.setText("Question " + (currentQuestion + 1));
        questionLabel.setForeground(Color.BLACK);
        questionLabel.setText("<html><div style='text-align: center;'>" + q.getQuestion() + "</div></html>");

        List<String> options = q.getAllOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText((i + 1) + ". " + options.get(i));
        }
    }

    private void savePerformance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, dbPassword);

            String checkQuery = "SELECT score FROM performance WHERE email = ? AND roll_no = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            checkStmt.setInt(2, rollNo);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                int previousScore = resultSet.getInt("score");
                int newScore = Math.max(previousScore, score);
                String updateQuery = "UPDATE performance SET score = ? WHERE email = ? AND roll_no = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setInt(1, newScore);
                updateStmt.setString(2, email);
                updateStmt.setInt(3, rollNo);
                updateStmt.executeUpdate();
                updateStmt.close();
            } else {

                String insertQuery = "INSERT INTO performance (name, email, roll_no, subject, score) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, userName);
                insertStmt.setString(2, email);
                insertStmt.setInt(3, rollNo);
                insertStmt.setString(4, subject);
                insertStmt.setInt(5, score);
                insertStmt.executeUpdate();
                insertStmt.close();
            }

            resultSet.close();
            checkStmt.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkAnswer(ActionEvent e) {
        Question current = questions.get(currentQuestion);
        JButton clicked = (JButton) e.getSource();
        String answer = clicked.getText().substring(3); // Extract the answer text

        if (answer.equals(current.getCorrectAnswer())) {
            score++;
            showFeedback("You got it right!", Color.GREEN);
        } else {
            showFeedback("Correct answer: " + current.getCorrectAnswer(), Color.RED);
        }

        currentQuestion++;
        Timer timer = new Timer(2000, evt -> showNextQuestion());
        timer.setRepeats(false);
        timer.start();
    }

    private void showFeedback(String message, Color color) {
        titleLabel.setText("Score: " + score + "/" + questions.size());
        questionLabel.setText("<html><div style='text-align: center;'>" + message + "</div></html>");
        questionLabel.setForeground(color);
    }
}
