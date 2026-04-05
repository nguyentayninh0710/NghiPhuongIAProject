package QuizApplication.ui;

import QuizApplication.model.Teacher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TeacherDashboard extends JFrame {

    private final Teacher currentTeacher;

    private DefaultListModel<String> classListModel;
    private DefaultListModel<String> quizListModel;

    private JList<String> classList;
    private JList<String> quizList;

    private JButton btnCreateNewQuiz;
    private JButton btnResultsDashboard;

    private JLabel lblWelcome;
    private JLabel lblTeacherIdValue;
    private JLabel lblTeacherNameValue;
    private JLabel lblTeacherEmailValue;

    public TeacherDashboard(Teacher teacher) {
        this.currentTeacher = teacher;

        initComponents();
        loadTeacherInfo();
        loadSampleDashboardData();
    }

    private void initComponents() {
        setTitle("Teacher Dashboard");
        setSize(780, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(232, 233, 237));
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(780, 95));
        headerPanel.setBackground(new Color(171, 85, 47));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Teacher’s Dashboard", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        titleLabel.setBorder(new EmptyBorder(20, 10, 15, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ===== BODY =====
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(new Color(232, 233, 237));
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBorder(new EmptyBorder(18, 60, 25, 60));

        lblWelcome = new JLabel("Welcome, Teacher");
        lblWelcome.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblWelcome.setForeground(new Color(55, 55, 55));
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblWelcome.setBorder(new EmptyBorder(0, 0, 14, 0));
        bodyPanel.add(lblWelcome);

        // ===== TEACHER INFO PANEL =====
        JPanel infoPanel = buildTeacherInfoPanel();
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(infoPanel);

        bodyPanel.add(Box.createVerticalStrut(20));

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 28, 0));
        buttonPanel.setBackground(new Color(232, 233, 237));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnCreateNewQuiz = new JButton("Create New Quiz");
        styleButton(btnCreateNewQuiz, new Color(43, 34, 122));

        btnResultsDashboard = new JButton("Results Dashboard");
        styleButton(btnResultsDashboard, new Color(122, 17, 0));

        buttonPanel.add(btnCreateNewQuiz);
        buttonPanel.add(btnResultsDashboard);

        bodyPanel.add(buttonPanel);
        bodyPanel.add(Box.createVerticalStrut(22));

        // ===== CURRENT CLASSES =====
        JLabel classTitle = new JLabel("Current Classes");
        classTitle.setFont(new Font("SansSerif", Font.PLAIN, 20));
        classTitle.setForeground(new Color(44, 74, 112));
        classTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(classTitle);
        bodyPanel.add(Box.createVerticalStrut(10));

        classListModel = new DefaultListModel<>();
        classList = new JList<>(classListModel);
        classList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        classList.setFixedCellHeight(34);
        classList.setBackground(new Color(194, 213, 233));
        classList.setForeground(new Color(45, 45, 45));
        classList.setSelectionBackground(new Color(160, 190, 220));
        classList.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JScrollPane classScrollPane = new JScrollPane(classList);
        classScrollPane.setPreferredSize(new Dimension(560, 110));
        classScrollPane.setMaximumSize(new Dimension(560, 110));
        classScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        classScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bodyPanel.add(classScrollPane);

        bodyPanel.add(Box.createVerticalStrut(24));

        // ===== CREATED QUIZZES =====
        JLabel quizTitle = new JLabel("Created Quizzes");
        quizTitle.setFont(new Font("SansSerif", Font.PLAIN, 20));
        quizTitle.setForeground(new Color(44, 74, 112));
        quizTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(quizTitle);
        bodyPanel.add(Box.createVerticalStrut(10));

        quizListModel = new DefaultListModel<>();
        quizList = new JList<>(quizListModel);
        quizList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        quizList.setFixedCellHeight(34);
        quizList.setBackground(new Color(207, 199, 112));
        quizList.setForeground(new Color(45, 45, 45));
        quizList.setSelectionBackground(new Color(186, 178, 90));
        quizList.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JScrollPane quizScrollPane = new JScrollPane(quizList);
        quizScrollPane.setPreferredSize(new Dimension(560, 130));
        quizScrollPane.setMaximumSize(new Dimension(560, 130));
        quizScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        quizScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bodyPanel.add(quizScrollPane);

        bodyPanel.add(Box.createVerticalGlue());

        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        registerEvents();
    }

    private JPanel buildTeacherInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 12, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setMaximumSize(new Dimension(560, 110));
        infoPanel.setPreferredSize(new Dimension(560, 110));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                new EmptyBorder(12, 16, 12, 16)
        ));

        JLabel lblTeacherId = new JLabel("Teacher ID:");
        JLabel lblTeacherName = new JLabel("Teacher Name:");
        JLabel lblTeacherEmail = new JLabel("Teacher Email:");

        styleInfoLabel(lblTeacherId);
        styleInfoLabel(lblTeacherName);
        styleInfoLabel(lblTeacherEmail);

        lblTeacherIdValue = new JLabel("-");
        lblTeacherNameValue = new JLabel("-");
        lblTeacherEmailValue = new JLabel("-");

        styleInfoValue(lblTeacherIdValue);
        styleInfoValue(lblTeacherNameValue);
        styleInfoValue(lblTeacherEmailValue);

        infoPanel.add(lblTeacherId);
        infoPanel.add(lblTeacherIdValue);
        infoPanel.add(lblTeacherName);
        infoPanel.add(lblTeacherNameValue);
        infoPanel.add(lblTeacherEmail);
        infoPanel.add(lblTeacherEmailValue);

        return infoPanel;
    }

    private void styleInfoLabel(JLabel label) {
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        label.setForeground(new Color(70, 70, 70));
    }

    private void styleInfoValue(JLabel label) {
        label.setFont(new Font("SansSerif", Font.PLAIN, 15));
        label.setForeground(new Color(30, 30, 30));
    }

    private void loadTeacherInfo() {
        String teacherId = "N/A";
        String teacherName = "Teacher";
        String teacherEmail = "N/A";

        if (currentTeacher != null) {
            if (currentTeacher.getTeacherId() != null && !currentTeacher.getTeacherId().trim().isEmpty()) {
                teacherId = currentTeacher.getTeacherId();
            }
            if (currentTeacher.getTeacherName() != null && !currentTeacher.getTeacherName().trim().isEmpty()) {
                teacherName = currentTeacher.getTeacherName();
            }
            if (currentTeacher.getTeacherEmail() != null && !currentTeacher.getTeacherEmail().trim().isEmpty()) {
                teacherEmail = currentTeacher.getTeacherEmail();
            }
        }

        lblWelcome.setText("Welcome, " + teacherName);
        lblTeacherIdValue.setText(teacherId);
        lblTeacherNameValue.setText(teacherName);
        lblTeacherEmailValue.setText(teacherEmail);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setPreferredSize(new Dimension(190, 42));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void registerEvents() {
        btnCreateNewQuiz.addActionListener(e -> openCreateQuizPage());
        btnResultsDashboard.addActionListener(e -> openResultsDashboard());

        classList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && classList.getSelectedValue() != null) {
                System.out.println("Selected class: " + classList.getSelectedValue());
            }
        });

        quizList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && quizList.getSelectedValue() != null) {
                System.out.println("Selected quiz: " + quizList.getSelectedValue());
            }
        });
    }

    private void loadSampleDashboardData() {
        classListModel.clear();
        quizListModel.clear();

        classListModel.addElement("SE06201 - Software Engineering");
        classListModel.addElement("CS101 - Introduction to Programming");
        classListModel.addElement("DB202 - Database Systems");

        quizListModel.addElement("Q001 - Java Basics Quiz");
        quizListModel.addElement("Q002 - OOP Concepts Quiz");
        quizListModel.addElement("Q003 - SQL Fundamentals Quiz");
    }

    private void openCreateQuizPage() {
        JOptionPane.showMessageDialog(
                this,
                "Create New Quiz button clicked.",
                "Create New Quiz",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void openResultsDashboard() {
        JOptionPane.showMessageDialog(
                this,
                "Results Dashboard button clicked.",
                "Results Dashboard",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Teacher demoTeacher = new Teacher("T001", "Nguyen Minh Anh", "minhanh.teacher@gmail.com");
            new TeacherDashboard(demoTeacher).setVisible(true);
        });
    }
}