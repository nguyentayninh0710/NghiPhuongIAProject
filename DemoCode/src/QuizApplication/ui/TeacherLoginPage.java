package QuizApplication.ui;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import QuizApplication.auth.SessionManager;
import QuizApplication.model.LoginResult;
import QuizApplication.model.Teacher;
import QuizApplication.service.TeacherService;

import java.awt.*;

public class TeacherLoginPage extends JFrame {

    private RoundedTextField emailField;
    private RoundedPasswordField passField;
    private RoundedButton loginBtn;

    private final TeacherService teacherService = new TeacherService();

    public TeacherLoginPage() {
        setTitle("Teacher Sign-In");
        setSize(980, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // ===== HEADER =====
        JLabel titleLabel = new JLabel("Teacher Sign-In", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(19, 55, 91));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setPreferredSize(new Dimension(980, 110));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ===== BODY =====
        JPanel bodyPanel = new JPanel(null);
        bodyPanel.setBackground(new Color(207, 128, 93));

        // ===== IMAGE / FALLBACK =====
        JLabel iconLabel = createTeacherImageLabel();
        iconLabel.setBounds(75, 165, 160, 220);
        bodyPanel.add(iconLabel);

        // ===== LABELS =====
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

        // ===== INPUTS =====
        emailField = new RoundedTextField(24);
        emailField.setBounds(310, 175, 610, 54);
        bodyPanel.add(emailField);

        passField = new RoundedPasswordField(24);
        passField.setBounds(310, 315, 610, 54);
        bodyPanel.add(passField);

        // ===== BUTTON =====
        loginBtn = new RoundedButton("Log In", 24);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 20));
        loginBtn.setForeground(new Color(150, 90, 60));
        loginBtn.setBounds(705, 435, 185, 62);
        loginBtn.addActionListener(e -> handleLogin());
        bodyPanel.add(loginBtn);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JLabel createTeacherImageLabel() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/teacher.jpg"));
            Image scaled = icon.getImage().getScaledInstance(160, 220, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            JLabel fallback = new JLabel("Teacher", JLabel.CENTER);
            fallback.setOpaque(true);
            fallback.setBackground(new Color(240, 230, 220));
            fallback.setForeground(new Color(90, 90, 90));
            fallback.setFont(new Font("Arial", Font.BOLD, 20));
            fallback.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            return fallback;
        }
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());

        resetFieldState();
        setLoginButtonEnabled(false);

        try {
            LoginResult result = teacherService.loginTeacher(email, password);

            if (result.isSuccess()) {
                Teacher teacher = result.getTeacher();
                SessionManager.setCurrentTeacher(teacher);

                JOptionPane.showMessageDialog(
                        this,
                        "Login success!\nHello, " + teacher.getTeacherName(),
                        "Notice",
                        JOptionPane.INFORMATION_MESSAGE
                );

                openTeacherDashboard(teacher);
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

    private void openTeacherDashboard(Teacher teacher) {
        SwingUtilities.invokeLater(() -> {
            TeacherDashboard dashboard = new TeacherDashboard(teacher);
            dashboard.setVisible(true);
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
        SwingUtilities.invokeLater(() -> new TeacherLoginPage().setVisible(true));
    }

    // =========================================================
    // Rounded Text Field
    // =========================================================
    static class RoundedTextField extends JTextField {
        private final int radius;
        private Color fillColor = Color.WHITE;

        public RoundedTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(new EmptyBorder(0, 18, 0, 18));
            setFont(new Font("Arial", Font.PLAIN, 22));
            setForeground(Color.BLACK);
            setCaretColor(Color.BLACK);
        }

        public void setNormalBackground() {
            fillColor = Color.WHITE;
            repaint();
        }

        public void setErrorBackground() {
            fillColor = new Color(255, 220, 220);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(fillColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(230, 230, 230));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    // =========================================================
    // Rounded Password Field
    // =========================================================
    static class RoundedPasswordField extends JPasswordField {
        private final int radius;
        private Color fillColor = Color.WHITE;

        public RoundedPasswordField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(new EmptyBorder(0, 18, 0, 18));
            setFont(new Font("Arial", Font.PLAIN, 22));
            setForeground(Color.BLACK);
            setCaretColor(Color.BLACK);
        }

        public void setNormalBackground() {
            fillColor = Color.WHITE;
            repaint();
        }

        public void setErrorBackground() {
            fillColor = new Color(255, 220, 220);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(fillColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(230, 230, 230));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    // =========================================================
    // Rounded Button
    // =========================================================
    static class RoundedButton extends JButton {
        private final int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int shadowX = 6;
            int shadowY = 10;
            int boxWidth = getWidth() - 8;
            int boxHeight = getHeight() - 12;

            if (isEnabled()) {
                g2.setColor(new Color(120, 75, 55, 90));
                g2.fillRoundRect(shadowX, shadowY, boxWidth, boxHeight, radius, radius);

                g2.setColor(new Color(241, 214, 200));
                g2.fillRoundRect(0, 0, boxWidth, boxHeight, radius, radius);
            } else {
                g2.setColor(new Color(160, 160, 160, 70));
                g2.fillRoundRect(shadowX, shadowY, boxWidth, boxHeight, radius, radius);

                g2.setColor(new Color(220, 220, 220));
                g2.fillRoundRect(0, 0, boxWidth, boxHeight, radius, radius);
            }

            g2.setFont(getFont());
            g2.setColor(isEnabled() ? getForeground() : new Color(130, 130, 130));
            FontMetrics fm = g2.getFontMetrics();

            String text = getText();
            int textX = (boxWidth - fm.stringWidth(text)) / 2;
            int textY = ((boxHeight - fm.getHeight()) / 2) + fm.getAscent();

            g2.drawString(text, textX, textY);
            g2.dispose();
        }
    }
}