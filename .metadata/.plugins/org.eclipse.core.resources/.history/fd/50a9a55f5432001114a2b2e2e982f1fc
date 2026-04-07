package QuizApplication.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HomePage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                HomePage frame = new HomePage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Open Teacher Login Page
     */
    private void openTeacherLoginPage() {
        EventQueue.invokeLater(() -> {
            TeacherLoginPage teacherLoginPage = new TeacherLoginPage();
            teacherLoginPage.setVisible(true);
        });
        dispose();
    }

    /**
     * Safe image loader
     */
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        URL imageUrl = getClass().getResource(path);

        if (imageUrl == null) {
            System.out.println("Image not found: " + path);
            return null;
        }

        ImageIcon originalIcon = new ImageIcon(imageUrl);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Create rounded button
     */
    private JButton createRoundedButton(String text, ImageIcon icon, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text, icon) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };

        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setBackground(normalColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });

        return button;
    }

    /**
     * Create the frame.
     */
    public HomePage() {
        setTitle("Quiz Education System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Quiz Education System", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(30, 60, 90));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(450, 60));

        contentPane.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        centerPanel.setBackground(new Color(230, 230, 230));

        ImageIcon teacherIcon = loadScaledIcon("/images/teacher.jpg", 100, 100);
        ImageIcon studentIcon = loadScaledIcon("/images/student.jpg", 100, 100);

        JButton teacherBtn = createRoundedButton(
                "Teacher mode",
                teacherIcon,
                new Color(255, 140, 80),
                new Color(255, 120, 60)
        );

        teacherBtn.addActionListener(e -> openTeacherLoginPage());

        JButton studentBtn = createRoundedButton(
                "Student mode",
                studentIcon,
                new Color(50, 150, 140),
                new Color(50, 150, 120)
        );

        studentBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Student mode selected")
        );

        centerPanel.add(teacherBtn);
        centerPanel.add(studentBtn);

        contentPane.add(centerPanel, BorderLayout.CENTER);
    }
}