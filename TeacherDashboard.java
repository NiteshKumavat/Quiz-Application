import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherDashboard extends JFrame implements ActionListener {
    private String email;
    private JButton viewResultsButton;

    public TeacherDashboard(String email) {
        this.email = email;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Teacher Dashboard - " + email);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Initialize "View Results" button
        viewResultsButton = createStyledButton("View Results", new Color(153, 51, 255));

        // Center the button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(viewResultsButton, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Set window properties
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 80));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewResultsButton) {
            JOptionPane.showMessageDialog(this, "Displaying Student Results...");
            new PerformanceWindow();
            this.dispose();
        }
    }
}
