package QuizApplication.ui;

import QuizApplication.model.Teacher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddQuiz extends JFrame {

    private final Teacher currentTeacher;

    private final String quizName;
    private final String tags;
    private final String level;
    private final String startDate;
    private final String endDate;
    private final String duration;
    private final String assignedClass;

    private JLabel lblQuestionNumber;

    private JTextArea txtQuestionContent;
    private JTextField txtOptionA;
    private JTextField txtOptionB;
    private JTextField txtOptionC;
    private JTextField txtOptionD;
    private JTextField txtExplanation;

    private JCheckBox cbA;
    private JCheckBox cbB;
    private JCheckBox cbC;
    private JCheckBox cbD;

    private JButton btnAddQuestion;
    private JButton btnDeleteQuestion;
    private JButton btnBack;

    private int questionCount = 1;
    private final List<String> addedQuestions = new ArrayList<>();

    private final String quizId;

    public AddQuiz(Teacher teacher,
                   String quizId,
                   String quizName,
                   String tags,
                   String level,
                   String startDate,
                   String endDate,
                   String duration,
                   String assignedClass) {
        this.currentTeacher = teacher;
        this.quizId = quizId;
        this.quizName = quizName;
        this.tags = tags;
        this.level = level;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.assignedClass = assignedClass;

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Add Quiz Questions");
        setSize(920, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(220, 223, 237));
        setContentPane(mainPanel);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(920, 105));
        headerPanel.setBackground(new Color(43, 31, 115));

        JLabel lblTitle = new JLabel("[" + quizName + "]", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblTitle.setBorder(new EmptyBorder(18, 10, 18, 10));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ===== BODY =====
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(new Color(201, 207, 231));
        bodyPanel.setLayout(null);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        lblQuestionNumber = new JLabel("Question " + questionCount);
        lblQuestionNumber.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblQuestionNumber.setForeground(new Color(34, 66, 110));
        lblQuestionNumber.setBounds(80, 18, 160, 28);
        bodyPanel.add(lblQuestionNumber);

        // ===== QUESTION CARD =====
        JPanel questionCard = new JPanel();
        questionCard.setLayout(null);
        questionCard.setBackground(new Color(154, 160, 228));
        questionCard.setBounds(140, 50, 610, 250);
        bodyPanel.add(questionCard);

        txtQuestionContent = new JTextArea();
        txtQuestionContent.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtQuestionContent.setLineWrap(true);
        txtQuestionContent.setWrapStyleWord(true);
        txtQuestionContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane spQuestionContent = new JScrollPane(txtQuestionContent);
        spQuestionContent.setBounds(18, 14, 574, 66);
        questionCard.add(spQuestionContent);

        txtOptionA = new JTextField();
        txtOptionB = new JTextField();
        txtOptionC = new JTextField();
        txtOptionD = new JTextField();

        styleOptionField(txtOptionA);
        styleOptionField(txtOptionB);
        styleOptionField(txtOptionC);
        styleOptionField(txtOptionD);

        txtOptionA.setBounds(18, 92, 240, 28);
        txtOptionB.setBounds(312, 92, 240, 28);
        txtOptionC.setBounds(18, 132, 240, 28);
        txtOptionD.setBounds(312, 132, 240, 28);

        questionCard.add(txtOptionA);
        questionCard.add(txtOptionB);
        questionCard.add(txtOptionC);
        questionCard.add(txtOptionD);

        cbA = new JCheckBox();
        cbB = new JCheckBox();
        cbC = new JCheckBox();
        cbD = new JCheckBox();

        styleAnswerCheckBox(cbA);
        styleAnswerCheckBox(cbB);
        styleAnswerCheckBox(cbC);
        styleAnswerCheckBox(cbD);

        cbA.setBounds(264, 92, 24, 28);
        cbB.setBounds(558, 92, 24, 28);
        cbC.setBounds(264, 132, 24, 28);
        cbD.setBounds(558, 132, 24, 28);

        questionCard.add(cbA);
        questionCard.add(cbB);
        questionCard.add(cbC);
        questionCard.add(cbD);

        JLabel lblNote = new JLabel("Note: A check indicates the correct answer");
        lblNote.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblNote.setForeground(new Color(55, 55, 90));
        lblNote.setBounds(18, 170, 310, 20);
        questionCard.add(lblNote);

        txtExplanation = new JTextField();
        txtExplanation.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtExplanation.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        txtExplanation.setBounds(18, 200, 574, 34);
        questionCard.add(txtExplanation);

        // ===== BUTTONS =====
        btnAddQuestion = new JButton("Add new question");
        styleButton(btnAddQuestion, new Color(37, 19, 201), 18);
        btnAddQuestion.setBounds(285, 325, 300, 36);
        bodyPanel.add(btnAddQuestion);

        btnDeleteQuestion = new JButton("Delete Question");
        styleButton(btnDeleteQuestion, new Color(171, 85, 47), 14);
        btnDeleteQuestion.setBounds(110, 430, 140, 36);
        bodyPanel.add(btnDeleteQuestion);

        btnBack = new JButton("Back");
        styleButton(btnBack, new Color(120, 120, 120), 14);
        btnBack.setBounds(670, 430, 120, 36);
        bodyPanel.add(btnBack);

        registerEvents();
    }

    private void styleOptionField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
    }

    private void styleAnswerCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(new Color(154, 160, 228));
        checkBox.setFocusPainted(false);
        checkBox.setBorderPainted(true);
        checkBox.setText("");
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void styleButton(JButton button, Color bgColor, int fontSize) {
        button.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    private void registerEvents() {
        cbA.addActionListener(e -> keepOnlyOneCorrect(cbA));
        cbB.addActionListener(e -> keepOnlyOneCorrect(cbB));
        cbC.addActionListener(e -> keepOnlyOneCorrect(cbC));
        cbD.addActionListener(e -> keepOnlyOneCorrect(cbD));

        btnAddQuestion.addActionListener(e -> addQuestion());
        btnDeleteQuestion.addActionListener(e -> deleteLastQuestion());

        btnBack.addActionListener(e -> {
            new CreateNewQuiz(currentTeacher);
            dispose();
        });
    }

    private void keepOnlyOneCorrect(JCheckBox selected) {
        JCheckBox[] boxes = {cbA, cbB, cbC, cbD};
        for (JCheckBox box : boxes) {
            if (box != selected) {
                box.setSelected(false);
            }
        }
    }

    private void addQuestion() {
        String content = txtQuestionContent.getText().trim();
        String optionA = txtOptionA.getText().trim();
        String optionB = txtOptionB.getText().trim();
        String optionC = txtOptionC.getText().trim();
        String optionD = txtOptionD.getText().trim();
        String explanation = txtExplanation.getText().trim();

        if (content.isEmpty() || optionA.isEmpty() || optionB.isEmpty()
                || optionC.isEmpty() || optionD.isEmpty() || explanation.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all question fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String correctAnswer = getCorrectAnswer();
        if (correctAnswer == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select exactly one correct answer.",
                    "Correct Answer Required",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        addedQuestions.add("Question " + questionCount + " - Correct Answer: " + correctAnswer);

        JOptionPane.showMessageDialog(
                this,
                "Question added successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

        questionCount++;
        lblQuestionNumber.setText("Question " + questionCount);
        clearQuestionForm();
    }

    private String getCorrectAnswer() {
        int selectedCount = 0;
        String answer = null;

        if (cbA.isSelected()) {
            selectedCount++;
            answer = "A";
        }
        if (cbB.isSelected()) {
            selectedCount++;
            answer = "B";
        }
        if (cbC.isSelected()) {
            selectedCount++;
            answer = "C";
        }
        if (cbD.isSelected()) {
            selectedCount++;
            answer = "D";
        }

        if (selectedCount != 1) {
            return null;
        }

        return answer;
    }

    private void clearQuestionForm() {
        txtQuestionContent.setText("");
        txtOptionA.setText("");
        txtOptionB.setText("");
        txtOptionC.setText("");
        txtOptionD.setText("");
        txtExplanation.setText("");

        cbA.setSelected(false);
        cbB.setSelected(false);
        cbC.setSelected(false);
        cbD.setSelected(false);
    }

    private void deleteLastQuestion() {
        if (addedQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "There is no added question to delete.",
                    "No Question",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        addedQuestions.remove(addedQuestions.size() - 1);

        if (questionCount > 1) {
            questionCount--;
        }

        lblQuestionNumber.setText("Question " + questionCount);

        JOptionPane.showMessageDialog(
                this,
                "Last added question deleted.",
                "Deleted",
                JOptionPane.INFORMATION_MESSAGE
            );
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            Teacher demoTeacher = new Teacher("T001", "Nguyen Minh Anh", "minhanh.teacher@gmail.com");
//            new AddQuiz(
//                    demoTeacher,
//                    "Java Basics Quiz",
//                    "Java, OOP",
//                    "Easy",
//                    "2026-04-10",
//                    "2026-04-20",
//                    "30",
//                    "CS101 - Introduction to Programming"
//            );
//        });
//    }
}