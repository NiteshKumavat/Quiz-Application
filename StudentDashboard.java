import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class StudentDashboard extends JFrame implements ActionListener {
    private String email;
    private int rollNo;
    private String userName;

    private JButton takeQuizButton, logoutButton, category;

    public StudentDashboard(String email, int rollNo, String userName) {
        this.email = email;
        this.rollNo = rollNo;
        this.userName = userName;
        createUI();
    }

    private void createUI() {
        setTitle("Student Dashboard - " + email);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window


        JLayeredPane mainPane = new JLayeredPane();
        mainPane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());


        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(123, 13, 23));
        contentPanel.setOpaque(true);
        contentPanel.setBounds(0, 0,
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        takeQuizButton = new JButton("Take Quiz");
        category = new JButton("Categories");
        logoutButton = new JButton("Logout");

        styleButton(takeQuizButton, new Color(67, 133, 244));
        styleButton(category, new Color(23, 231, 45));
        styleButton(logoutButton, new Color(234, 67, 53));

        buttonPanel.add(takeQuizButton);
        buttonPanel.add(category);
        buttonPanel.add(logoutButton);

        // Add components to content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 50);
        contentPanel.add(buttonPanel, gbc);

        // Add panels to layered pane
        mainPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);

        // Add action listeners
        takeQuizButton.addActionListener(this);
        category.addActionListener(this);
        logoutButton.addActionListener(this);

        add(mainPane);
        validate();
        setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(300, 80));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == takeQuizButton) {
            Categories cat = new Categories();
            String answer = JOptionPane.showInputDialog(null, "Enter the Category id ");
            int categoryID = Integer.parseInt(answer.trim());
            cat.dispose();
            if ((categoryID == 0) || categoryID > 8 && categoryID < 33){
                String subject = cat.name(categoryID);
                cat.dispose();
                dispose();
                List<Question> questions = DataFetcher.fetchQuestions(categoryID);
                new MainWindow(questions, email, rollNo, userName, subject).setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this, "Inavalid number");
            }


        }else if(e.getSource() == category){
            new Categories();
        }else if (e.getSource() == logoutButton) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Start().setVisible(true);
            }
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            new Start();
            dispose();
        }
        super.processWindowEvent(e);
    }
}
