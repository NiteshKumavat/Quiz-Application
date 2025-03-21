import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start extends JFrame implements ActionListener {
    JButton student;
    JButton teacher;
    ImageIcon teacherImage;
    ImageIcon studentImage;

    Start() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Teacher or Student");
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(123, 45, 234));

        studentImage = new ImageIcon("student.png");
        teacherImage = new ImageIcon("teacher.png");

        JLabel studentLabel = new JLabel(studentImage);
        JLabel teacherLabel = new JLabel(teacherImage);

        studentLabel.setBounds(500, 180, 180, 180);
        teacherLabel.setBounds(900, 470, 180, 180);


        student = new JButton("Student" );
        student.setBackground(new Color(23, 134, 12));
        student.setFont(new Font("Arial", Font.BOLD, 18));
        student.setFocusable(false);
        student.setBounds(700, 225, 170, 70);
        student.addActionListener(this);


        JLabel line = new JLabel("------------------------------------OR------------------------------------");
        line.setBounds(625, 400, 500, 20);



        teacher = new JButton("Teacher");
        teacher.setBackground(new Color(12, 81, 180));
        teacher.setFont(new Font("Arial", Font.BOLD, 18));
        teacher.setFocusable(false);
        teacher.setBounds(700, 525, 170, 70);
        teacher.addActionListener(this);

        this.add(studentLabel);
        this.add(teacherLabel);
        this.add(line);
        this.add(student);
        this.add(teacher);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == student){
            this.dispose();
            new StudentCreateAccount();

        } else if (e.getSource() == teacher) {
            this.dispose();
            new TeacherCreateAccount();
        }

    }
}

