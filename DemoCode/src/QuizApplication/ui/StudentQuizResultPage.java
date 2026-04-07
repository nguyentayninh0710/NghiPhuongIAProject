package QuizApplication.ui;

import QuizApplication.model.Student;
import QuizApplication.model.StudentQuizDashboardItem;
import QuizApplication.model.StudentQuizResultSummary;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentQuizResultPage extends JFrame {

    private final Student currentStudent;
    private final StudentQuizDashboardItem dashboardItem;
    private final StudentQuizResultSummary summary;

    public StudentQuizResultPage(Student currentStudent,
                                 StudentQuizDashboardItem dashboardItem,
                                 StudentQuizResultSummary summary) {
        this.currentStudent = currentStudent;
        this.dashboardItem = dashboardItem;
        this.summary = summary;

        initComponents();
    }

    private void initComponents() {
        setTitle("Quiz Results");
        setSize(920, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(229, 232, 248));
        setContentPane(mainPanel);

        JLabel titleLabel = new JLabel("[" + safe(summary.getQuizTitle()) + "] Results!", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(45, 31, 112));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        titleLabel.setPreferredSize(new Dimension(920, 88));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(new Color(229, 232, 248));
        bodyPanel.setBorder(new EmptyBorder(26, 30, 26, 30));

        JLabel trophyLabel = new JLabel("🏆  🏆", SwingConstants.CENTER);
        trophyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        trophyLabel.setFont(new Font("SansSerif", Font.PLAIN, 70));

        JLabel congratsLabel = new JLabel(
                "Congratulations! You have completed [" + safe(summary.getQuizTitle()) + "]",
                SwingConstants.CENTER
        );
        congratsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        congratsLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 28, 8));
        scorePanel.setBackground(new Color(229, 232, 248));
        scorePanel.setBorder(new EmptyBorder(18, 0, 22, 0));

        JLabel lblScore = createInfoLabel("Score: " + summary.getScoreText());
        JLabel lblFinishedIn = createInfoLabel("Finished in: " + summary.getFinishedInText());

        scorePanel.add(lblScore);
        scorePanel.add(lblFinishedIn);

        JButton btnViewDetails = new JButton("View detailed results");
        btnViewDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnViewDetails.setPreferredSize(new Dimension(220, 42));
        btnViewDetails.setMaximumSize(new Dimension(220, 42));
        btnViewDetails.setBackground(new Color(32, 19, 198));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnViewDetails.setBorderPainted(false);
        btnViewDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnBackDashboard = new JButton("Back to Dashboard");
        btnBackDashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBackDashboard.setPreferredSize(new Dimension(220, 42));
        btnBackDashboard.setMaximumSize(new Dimension(220, 42));
        btnBackDashboard.setBackground(new Color(108, 169, 224));
        btnBackDashboard.setForeground(Color.BLACK);
        btnBackDashboard.setFocusPainted(false);
        btnBackDashboard.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnBackDashboard.setBorderPainted(false);
        btnBackDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bodyPanel.add(Box.createVerticalGlue());
        bodyPanel.add(trophyLabel);
        bodyPanel.add(Box.createVerticalStrut(16));
        bodyPanel.add(congratsLabel);
        bodyPanel.add(scorePanel);
        bodyPanel.add(btnViewDetails);
        bodyPanel.add(Box.createVerticalStrut(12));
        bodyPanel.add(btnBackDashboard);
        bodyPanel.add(Box.createVerticalGlue());

        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        btnViewDetails.addActionListener(e -> {
            new StudentQuizReviewPage(currentStudent, dashboardItem, summary).setVisible(true);
            dispose();
        });

        btnBackDashboard.addActionListener(e -> {
            new StudentDashboard(currentStudent).setVisible(true);
            dispose();
        });
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(new Color(206, 220, 239));
        label.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        return label;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}