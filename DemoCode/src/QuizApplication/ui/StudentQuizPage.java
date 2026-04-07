package QuizApplication.ui;

import QuizApplication.model.Student;
import QuizApplication.model.StudentQuizDashboardItem;
import QuizApplication.model.StudentQuizQuestionItem;
import QuizApplication.model.StudentQuizResultSummary;
import QuizApplication.service.StudentQuizService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentQuizPage extends JFrame {

    private final Student currentStudent;
    private final StudentQuizDashboardItem dashboardItem;
    private final StudentQuizService studentQuizService;

    private int attemptId;
    private List<StudentQuizQuestionItem> questions;
    private int currentIndex = 0;

    private JLabel lblProgress;
    private JLabel lblTimeLeft;
    private JLabel lblQuestionNumber;
    private JTextArea txtQuestionContent;

    private JRadioButton rbA;
    private JRadioButton rbB;
    private JRadioButton rbC;
    private JRadioButton rbD;
    private ButtonGroup optionGroup;

    private JButton btnBack;
    private JButton btnNext;
    private JButton btnSubmit;

    private Timer countdownTimer;
    private int remainingSeconds;

    public StudentQuizPage(Student currentStudent, StudentQuizDashboardItem dashboardItem) {
        this.currentStudent = currentStudent;
        this.dashboardItem = dashboardItem;
        this.studentQuizService = new StudentQuizService();
        this.questions = new ArrayList<>();

        initializeAttempt();
        initComponents();
        loadQuestions();
        startCountdown();
    }

    private void initializeAttempt() {
        attemptId = studentQuizService.startQuiz(currentStudent, dashboardItem);
    }

    private void initComponents() {
        setTitle("Start Quiz");
        setSize(980, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        StudentQuizPage.this,
                        "Do you want to submit this quiz now?",
                        "Submit Quiz",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    submitQuiz(false);
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(229, 232, 248));
        setContentPane(mainPanel);

        JLabel titleLabel = new JLabel("Start Quiz", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(45, 31, 112));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        titleLabel.setPreferredSize(new Dimension(980, 88));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(new Color(229, 232, 248));
        bodyPanel.setBorder(new EmptyBorder(22, 34, 22, 34));

        JPanel topInfoPanel = new JPanel(new BorderLayout());
        topInfoPanel.setBackground(new Color(229, 232, 248));
        topInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        lblProgress = new JLabel("Completed questions: 0/0");
        lblProgress.setFont(new Font("SansSerif", Font.PLAIN, 16));

        lblTimeLeft = new JLabel("Time left: 00:00");
        lblTimeLeft.setFont(new Font("SansSerif", Font.PLAIN, 16));

        topInfoPanel.add(lblProgress, BorderLayout.WEST);
        topInfoPanel.add(lblTimeLeft, BorderLayout.EAST);

        lblQuestionNumber = new JLabel("Question 1");
        lblQuestionNumber.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblQuestionNumber.setBorder(new EmptyBorder(16, 0, 16, 0));

        JPanel questionCard = new JPanel();
        questionCard.setLayout(new BoxLayout(questionCard, BoxLayout.Y_AXIS));
        questionCard.setBackground(new Color(157, 164, 228));
        questionCard.setBorder(new EmptyBorder(18, 18, 18, 18));
        questionCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        txtQuestionContent = new JTextArea(4, 20);
        txtQuestionContent.setLineWrap(true);
        txtQuestionContent.setWrapStyleWord(true);
        txtQuestionContent.setEditable(false);
        txtQuestionContent.setFont(new Font("SansSerif", Font.PLAIN, 18));
        txtQuestionContent.setBackground(Color.WHITE);
        txtQuestionContent.setBorder(new EmptyBorder(12, 12, 12, 12));

        questionCard.add(txtQuestionContent);
        questionCard.add(Box.createVerticalStrut(14));

        rbA = createOptionRadio();
        rbB = createOptionRadio();
        rbC = createOptionRadio();
        rbD = createOptionRadio();

        optionGroup = new ButtonGroup();
        optionGroup.add(rbA);
        optionGroup.add(rbB);
        optionGroup.add(rbC);
        optionGroup.add(rbD);

        questionCard.add(buildOptionRow("A", rbA));
        questionCard.add(Box.createVerticalStrut(10));
        questionCard.add(buildOptionRow("B", rbB));
        questionCard.add(Box.createVerticalStrut(10));
        questionCard.add(buildOptionRow("C", rbC));
        questionCard.add(Box.createVerticalStrut(10));
        questionCard.add(buildOptionRow("D", rbD));

        JLabel noteLabel = new JLabel("Note: A check mark indicates your chosen answer.");
        noteLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        noteLabel.setForeground(new Color(60, 60, 60));
        noteLabel.setBorder(new EmptyBorder(10, 2, 0, 2));

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 36, 0));
        bottomButtonPanel.setBackground(new Color(229, 232, 248));
        bottomButtonPanel.setBorder(new EmptyBorder(24, 0, 0, 0));

        btnBack = createActionButton("Back");
        btnNext = createActionButton("Next");
        btnSubmit = createActionButton("Submit");

        bottomButtonPanel.add(btnBack);
        bottomButtonPanel.add(btnNext);
        bottomButtonPanel.add(btnSubmit);

        bodyPanel.add(topInfoPanel);
        bodyPanel.add(lblQuestionNumber);
        bodyPanel.add(questionCard);
        bodyPanel.add(noteLabel);
        bodyPanel.add(bottomButtonPanel);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        btnBack.addActionListener(e -> goBack());
        btnNext.addActionListener(e -> goNext());
        btnSubmit.addActionListener(e -> submitQuiz(false));

        rbA.addActionListener(e -> saveCurrentAnswer("A"));
        rbB.addActionListener(e -> saveCurrentAnswer("B"));
        rbC.addActionListener(e -> saveCurrentAnswer("C"));
        rbD.addActionListener(e -> saveCurrentAnswer("D"));
    }

    private JRadioButton createOptionRadio() {
        JRadioButton radioButton = new JRadioButton();
        radioButton.setOpaque(false);
        radioButton.setFocusPainted(false);
        return radioButton;
    }

    private JPanel buildOptionRow(String optionCode, JRadioButton radioButton) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(new EmptyBorder(8, 10, 8, 10));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        JLabel lblOptionCode = new JLabel("Option " + optionCode);
        lblOptionCode.setFont(new Font("SansSerif", Font.PLAIN, 15));
        row.add(lblOptionCode, BorderLayout.WEST);
        row.add(radioButton, BorderLayout.EAST);

        return row;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(new Color(32, 19, 198));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadQuestions() {
        questions = studentQuizService.loadQuizQuestions(attemptId);

        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No questions found for this quiz.",
                    "Quiz Error",
                    JOptionPane.ERROR_MESSAGE
            );
            backToDashboard();
            return;
        }

        int durationMinutes = studentQuizService.getAttemptDurationMinutes(attemptId);
        remainingSeconds = Math.max(0, durationMinutes * 60);

        renderQuestion();
    }

    private void renderQuestion() {
        if (questions == null || questions.isEmpty()) {
            return;
        }

        StudentQuizQuestionItem item = questions.get(currentIndex);

        lblQuestionNumber.setText("Question " + (currentIndex + 1));
        txtQuestionContent.setText(item.getQuestionContent());

        optionGroup.clearSelection();

        if ("A".equalsIgnoreCase(item.getSelectedOption())) rbA.setSelected(true);
        if ("B".equalsIgnoreCase(item.getSelectedOption())) rbB.setSelected(true);
        if ("C".equalsIgnoreCase(item.getSelectedOption())) rbC.setSelected(true);
        if ("D".equalsIgnoreCase(item.getSelectedOption())) rbD.setSelected(true);

        updateProgressLabel();
        updateNavButtons();
    }

    private void updateProgressLabel() {
        int answered = 0;
        for (StudentQuizQuestionItem item : questions) {
            if (item.isAnswered()) {
                answered++;
            }
        }
        lblProgress.setText("Completed questions: " + answered + "/" + questions.size());
    }

    private void updateNavButtons() {
        btnBack.setEnabled(currentIndex > 0);
        btnNext.setEnabled(currentIndex < questions.size() - 1);
    }

    private void saveCurrentAnswer(String option) {
        StudentQuizQuestionItem item = questions.get(currentIndex);
        item.setSelectedOption(option);
        studentQuizService.saveAnswer(attemptId, item.getQuestionId(), option);
        updateProgressLabel();
    }

    private void goBack() {
        if (currentIndex > 0) {
            currentIndex--;
            renderQuestion();
        }
    }

    private void goNext() {
        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            renderQuestion();
        }
    }

    private void startCountdown() {
        updateTimeLabel();

        countdownTimer = new Timer(1000, e -> {
            remainingSeconds--;
            updateTimeLabel();

            if (remainingSeconds <= 0) {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(
                        this,
                        "Time is up. The quiz will be submitted automatically.",
                        "Time Over",
                        JOptionPane.WARNING_MESSAGE
                );
                submitQuiz(true);
            }
        });

        countdownTimer.start();
    }

    private void updateTimeLabel() {
        int mins = Math.max(0, remainingSeconds) / 60;
        int secs = Math.max(0, remainingSeconds) % 60;
        lblTimeLeft.setText("Time left: " + String.format("%02d:%02d", mins, secs));
    }

    private void submitQuiz(boolean forcedOverdue) {
        try {
            if (!studentQuizService.isAttemptStillInProgress(attemptId)) {
                StudentQuizResultSummary summary = studentQuizService.getQuizSummary(attemptId);
                openResultPage(summary);
                return;
            }

            if (countdownTimer != null) {
                countdownTimer.stop();
            }

            StudentQuizResultSummary summary = forcedOverdue
                    ? studentQuizService.markQuizAsOverdue(attemptId)
                    : studentQuizService.submitQuiz(attemptId);

            openResultPage(summary);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Submit Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openResultPage(StudentQuizResultSummary summary) {
        SwingUtilities.invokeLater(() -> {
            new StudentQuizResultPage(currentStudent, dashboardItem, summary).setVisible(true);
        });
        dispose();
    }

    private void backToDashboard() {
        SwingUtilities.invokeLater(() -> {
            new StudentDashboard(currentStudent).setVisible(true);
        });
        dispose();
    }
}