package QuizApplication.ui;

import QuizApplication.auth.SessionManager;
import QuizApplication.model.Student;
import QuizApplication.model.StudentLoginResult;
import QuizApplication.service.StudentService;

import javax.swing.*;
import java.awt.*;

public class StudentLoginPage extends JFrame {

    private TeacherLoginPage.RoundedTextField emailField;
    private TeacherLoginPage.RoundedPasswordField passField;
    private TeacherLoginPage.RoundedButton loginBtn;

    private final StudentService studentService = new StudentService();

    public StudentLoginPage() {
        setTitle("Student Sign-In");
        setSize(980, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Student Sign-In", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(19, 55, 91));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setPreferredSize(new Dimension(980, 110));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(null);
        bodyPanel.setBackground(new Color(90, 145, 200));

        JLabel iconLabel = createStudentImageLabel();
        iconLabel.setBounds(60, 165, 190, 220);
        bodyPanel.add(iconLabel);

        JLabel emailLabel = new JLabel("E-mail");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        emailLabel.setBounds(310, 135, 180, 35);
        bodyPanel.add(emailLabel);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        passLabel.setBounds(310, 275, 180, 35);
        bodyPanel.add(passLabel);

        emailField = new TeacherLoginPage.RoundedTextField(24);
        emailField.setBounds(310, 175, 610, 54);
        bodyPanel.add(emailField);

        passField = new TeacherLoginPage.RoundedPasswordField(24);
        passField.setBounds(310, 315, 610, 54);
        bodyPanel.add(passField);

        loginBtn = new TeacherLoginPage.RoundedButton("Log In", 24);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 20));
        loginBtn.setForeground(new Color(60, 85, 120));
        loginBtn.setBounds(705, 435, 185, 62);
        loginBtn.addActionListener(e -> handleLogin());
        bodyPanel.add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(60, 470, 100, 38);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(225, 235, 245));
        backBtn.setForeground(new Color(19, 55, 91));
        backBtn.addActionListener(e -> backToHome());
        bodyPanel.add(backBtn);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JLabel createStudentImageLabel() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/student.jpg"));
            Image scaled = icon.getImage().getScaledInstance(190, 220, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            JLabel fallback = new JLabel("Student", JLabel.CENTER);
            fallback.setOpaque(true);
            fallback.setBackground(new Color(220, 235, 248));
            fallback.setForeground(new Color(50, 70, 90));
            fallback.setFont(new Font("Arial", Font.BOLD, 20));
            fallback.setBorder(BorderFactory.createLineBorder(new Color(190, 210, 225)));
            return fallback;
        }
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());

        resetFieldState();
        setLoginButtonEnabled(false);

        try {
            StudentLoginResult result = studentService.loginStudent(email, password);

            if (result.isSuccess()) {
                Student student = result.getStudent();
                SessionManager.setCurrentStudent(student);

                JOptionPane.showMessageDialog(
                        this,
                        "Login success!\nHello, " + student.getStudentName(),
                        "Notice",
                        JOptionPane.INFORMATION_MESSAGE
                );

                openStudentDashboard(student);
            } else {
                applyErrorState(email, password, result.getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Have error!\nDetail: " + ex.getMessage(),
                    "Error system",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            setLoginButtonEnabled(true);
        }
    }

    private void openStudentDashboard(Student student) {
        SwingUtilities.invokeLater(() -> {
            StudentDashboard dashboard = new StudentDashboard(student);
            dashboard.setVisible(true);
        });
        dispose();
    }

    private void backToHome() {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });
        dispose();
    }

    private void resetFieldState() {
        emailField.setNormalBackground();
        passField.setNormalBackground();
    }

    private void applyErrorState(String email, String password, String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Login fail",
                JOptionPane.WARNING_MESSAGE
        );

        if (email == null || email.trim().isEmpty() || !isLikelyValidEmail(email)) {
            emailField.setErrorBackground();
        }

        if (password == null || password.trim().isEmpty() || message.toLowerCase().contains("password")) {
            passField.setErrorBackground();
        }

        if (message.toLowerCase().contains("incorrect email or password")) {
            emailField.setErrorBackground();
            passField.setErrorBackground();
        }
    }

    private boolean isLikelyValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private void setLoginButtonEnabled(boolean enabled) {
        loginBtn.setEnabled(enabled);
        loginBtn.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentLoginPage().setVisible(true));
    }
}