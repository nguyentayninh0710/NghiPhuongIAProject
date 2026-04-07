package QuizApplication.ui;

import QuizApplication.model.Student;
import QuizApplication.model.StudentQuizDashboardItem;
import QuizApplication.model.StudentQuizResultSummary;
import QuizApplication.model.StudentQuizReviewItem;
import QuizApplication.service.StudentQuizService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentQuizReviewPage extends JFrame {

    private static final int PAGE_SIZE = 2;

    private final Student currentStudent;
    private final StudentQuizDashboardItem dashboardItem;
    private final StudentQuizResultSummary summary;
    private final StudentQuizService studentQuizService;

    private List<StudentQuizReviewItem> reviewItems;
    private int currentPage = 1;

    private JPanel reviewListPanel;
    private JLabel lblPageInfo;
    private JButton btnPrev;
    private JButton btnNext;

    public StudentQuizReviewPage(Student currentStudent,
                                 StudentQuizDashboardItem dashboardItem,
                                 StudentQuizResultSummary summary) {
        this.currentStudent = currentStudent;
        this.dashboardItem = dashboardItem;
        this.summary = summary;
        this.studentQuizService = new StudentQuizService();
        this.reviewItems = new ArrayList<>();

        initComponents();
        loadReviewData();
    }

    private void initComponents() {
        setTitle("Detailed Results");
        setSize(960, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(229, 232, 248));
        setContentPane(mainPanel);

        JLabel titleLabel = new JLabel("[" + safe(summary.getQuizTitle()) + "] Detailed Results", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(45, 31, 112));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        titleLabel.setPreferredSize(new Dimension(960, 88));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        reviewListPanel = new JPanel();
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));
        reviewListPanel.setBackground(new Color(229, 232, 248));
        reviewListPanel.setBorder(new EmptyBorder(18, 28, 18, 28));

        JScrollPane scrollPane = new JScrollPane(reviewListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(229, 232, 248));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(229, 232, 248));
        bottomPanel.setBorder(new EmptyBorder(12, 20, 16, 20));

        JPanel pagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pagePanel.setBackground(new Color(229, 232, 248));

        btnPrev = createPageButton("←");
        btnNext = createPageButton("→");
        lblPageInfo = new JLabel("1 / 1");
        lblPageInfo.setFont(new Font("SansSerif", Font.BOLD, 18));

        pagePanel.add(btnPrev);
        pagePanel.add(lblPageInfo);
        pagePanel.add(btnNext);

        JButton btnFinish = new JButton("Finish");
        btnFinish.setPreferredSize(new Dimension(120, 38));
        btnFinish.setBackground(new Color(32, 19, 198));
        btnFinish.setForeground(Color.WHITE);
        btnFinish.setFocusPainted(false);
        btnFinish.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnFinish.setBorderPainted(false);
        btnFinish.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(pagePanel, BorderLayout.CENTER);
        bottomPanel.add(btnFinish, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnPrev.addActionListener(e -> previousPage());
        btnNext.addActionListener(e -> nextPage());
        btnFinish.addActionListener(e -> {
            new StudentDashboard(currentStudent).setVisible(true);
            dispose();
        });
    }

    private JButton createPageButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(52, 34));
        button.setBackground(new Color(108, 169, 224));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadReviewData() {
        reviewItems = studentQuizService.getQuizReviewItems(summary.getAttemptId());
        refreshReviewPanel();
    }

    private void refreshReviewPanel() {
        reviewListPanel.removeAll();

        List<StudentQuizReviewItem> pageItems = getCurrentPageItems();
        if (pageItems.isEmpty()) {
            JLabel lblEmpty = new JLabel("No review data found.", SwingConstants.CENTER);
            lblEmpty.setFont(new Font("SansSerif", Font.PLAIN, 18));
            reviewListPanel.add(lblEmpty);
        } else {
            for (StudentQuizReviewItem item : pageItems) {
                reviewListPanel.add(buildReviewCard(item));
                reviewListPanel.add(Box.createVerticalStrut(12));
            }
        }

        lblPageInfo.setText(currentPage + " / " + getTotalPages());
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < getTotalPages());

        reviewListPanel.revalidate();
        reviewListPanel.repaint();
    }

    private JPanel buildReviewCard(StudentQuizReviewItem item) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(157, 164, 228));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(130, 139, 214)),
                new EmptyBorder(14, 14, 14, 14)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JLabel lblTitle = new JLabel("Question " + item.getQuestionNumber());
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        JTextArea txtQuestion = new JTextArea(item.getQuestionContent());
        txtQuestion.setEditable(false);
        txtQuestion.setLineWrap(true);
        txtQuestion.setWrapStyleWord(true);
        txtQuestion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtQuestion.setBackground(Color.WHITE);
        txtQuestion.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel answerPanel = new JPanel(new GridLayout(2, 2, 12, 8));
        answerPanel.setBackground(new Color(157, 164, 228));
        answerPanel.setBorder(new EmptyBorder(12, 0, 0, 0));

        JLabel lblYourAnswer = new JLabel((item.isCorrect() ? "✅ " : "❌ ") + "Your Answer: " + safeOption(item.getSelectedOption()));
        lblYourAnswer.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JLabel lblCorrectAnswer = new JLabel("✅ Correct Answer: " + safeOption(item.getCorrectOption()));
        lblCorrectAnswer.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JTextArea txtExplanation = new JTextArea(item.getCorrectExplanation());
        txtExplanation.setEditable(false);
        txtExplanation.setLineWrap(true);
        txtExplanation.setWrapStyleWord(true);
        txtExplanation.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtExplanation.setBackground(Color.WHITE);
        txtExplanation.setBorder(new EmptyBorder(8, 8, 8, 8));

        answerPanel.add(lblYourAnswer);
        answerPanel.add(new JLabel());
        answerPanel.add(lblCorrectAnswer);
        answerPanel.add(txtExplanation);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(157, 164, 228));
        content.add(lblTitle);
        content.add(txtQuestion);
        content.add(answerPanel);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private int getTotalPages() {
        if (reviewItems == null || reviewItems.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) reviewItems.size() / PAGE_SIZE);
    }

    private List<StudentQuizReviewItem> getCurrentPageItems() {
        if (reviewItems == null || reviewItems.isEmpty()) {
            return new ArrayList<>();
        }

        int fromIndex = (currentPage - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, reviewItems.size());

        if (fromIndex >= reviewItems.size()) {
            return new ArrayList<>();
        }

        return reviewItems.subList(fromIndex, toIndex);
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            refreshReviewPanel();
        }
    }

    private void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            refreshReviewPanel();
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String safeOption(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "No answer";
        }
        return value;
    }
}