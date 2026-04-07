package QuizApplication.ui;

import QuizApplication.auth.SessionManager;
import QuizApplication.dao.StudentQuizDAO;
import QuizApplication.model.ClassRoom;
import QuizApplication.model.Student;
import QuizApplication.model.StudentQuizDashboardItem;
import QuizApplication.model.StudentQuizResultSummary;
import QuizApplication.service.StudentQuizService;
import QuizApplication.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends JFrame {

    private static final int PAGE_SIZE = 3;

    private final Student currentStudent;
    private final StudentService studentService;
    private final StudentQuizService studentQuizService;

    private JTextField txtSearch;
    private JButton btnSearch;
    private JComboBox<ClassComboItem> cboClass;

    private JPanel quizListPanel;
    private JLabel lblPageInfo;
    private JButton btnPrevPage;
    private JButton btnNextPage;

    private JButton btnLogout;
    private JButton btnBackHome;

    private JLabel lblStudentInfo;

    private List<ClassRoom> studentClasses;
    private List<StudentQuizDashboardItem> allQuizItems;

    private int currentPage = 1;

    public StudentDashboard(Student student) {
        this.currentStudent = student;
        this.studentService = new StudentService();
        this.studentQuizService = new StudentQuizService();
        this.studentClasses = new ArrayList<>();
        this.allQuizItems = new ArrayList<>();

        initComponents();
        loadInitialData();
    }

    private void initComponents() {
        setTitle("Student Dashboard");
        setSize(980, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 247));
        setContentPane(mainPanel);

        mainPanel.add(buildHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(buildBodyPanel(), BorderLayout.CENTER);
    }

    private JPanel buildHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(980, 90));
        headerPanel.setBackground(new Color(67, 129, 190));

        JLabel titleLabel = new JLabel("Student’s Dashboard", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        titleLabel.setBorder(new EmptyBorder(15, 10, 15, 10));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel buildBodyPanel() {
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(new Color(240, 242, 247));
        bodyPanel.setBorder(new EmptyBorder(18, 24, 18, 24));

        JPanel topArea = new JPanel();
        topArea.setLayout(new BoxLayout(topArea, BoxLayout.Y_AXIS));
        topArea.setBackground(new Color(240, 242, 247));

        lblStudentInfo = new JLabel("Student: -");
        lblStudentInfo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblStudentInfo.setForeground(new Color(60, 60, 60));
        lblStudentInfo.setBorder(new EmptyBorder(0, 2, 10, 2));
        lblStudentInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filterPanel = buildFilterPanel();
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topArea.add(lblStudentInfo);
        topArea.add(filterPanel);

        bodyPanel.add(topArea, BorderLayout.NORTH);

        quizListPanel = new JPanel();
        quizListPanel.setLayout(new BoxLayout(quizListPanel, BoxLayout.Y_AXIS));
        quizListPanel.setBackground(new Color(240, 242, 247));

        JScrollPane scrollPane = new JScrollPane(quizListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(new Color(240, 242, 247));
        scrollPane.getViewport().setBackground(new Color(240, 242, 247));

        bodyPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = buildBottomPanel();
        bodyPanel.add(bottomPanel, BorderLayout.SOUTH);

        return bodyPanel;
    }

    private JPanel buildFilterPanel() {
        JPanel filterPanel = new JPanel(new BorderLayout(12, 0));
        filterPanel.setBackground(new Color(240, 242, 247));
        filterPanel.setBorder(new EmptyBorder(0, 0, 12, 0));
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JPanel leftPanel = new JPanel(new BorderLayout(8, 0));
        leftPanel.setBackground(new Color(240, 242, 247));

        JLabel iconSearch = new JLabel("🔎");
        iconSearch.setFont(new Font("SansSerif", Font.PLAIN, 18));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtSearch.setPreferredSize(new Dimension(380, 36));

        btnSearch = new JButton("Search");
        styleSmallButton(btnSearch, new Color(103, 158, 93), Color.WHITE);

        leftPanel.add(iconSearch, BorderLayout.WEST);
        leftPanel.add(txtSearch, BorderLayout.CENTER);
        leftPanel.add(btnSearch, BorderLayout.EAST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightPanel.setBackground(new Color(240, 242, 247));

        JLabel lblClass = new JLabel("Class");
        lblClass.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblClass.setForeground(new Color(80, 80, 80));

        cboClass = new JComboBox<>();
        cboClass.setPreferredSize(new Dimension(220, 34));
        cboClass.setFont(new Font("SansSerif", Font.PLAIN, 14));

        rightPanel.add(lblClass);
        rightPanel.add(cboClass);

        filterPanel.add(leftPanel, BorderLayout.CENTER);
        filterPanel.add(rightPanel, BorderLayout.EAST);

        btnSearch.addActionListener(e -> searchAndReload());
        txtSearch.addActionListener(e -> searchAndReload());

        cboClass.addActionListener(e -> {
            if (cboClass.getSelectedItem() != null) {
                currentPage = 1;
                reloadQuizData();
            }
        });

        return filterPanel;
    }

    private JPanel buildBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 242, 247));
        bottomPanel.setBorder(new EmptyBorder(12, 0, 0, 0));

        JPanel leftActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftActionPanel.setBackground(new Color(240, 242, 247));

        btnBackHome = new JButton("Back Home");
        styleSmallButton(btnBackHome, new Color(130, 160, 196), Color.WHITE);

        btnLogout = new JButton("Log Out");
        styleSmallButton(btnLogout, new Color(40, 104, 176), Color.WHITE);

        leftActionPanel.add(btnBackHome);
        leftActionPanel.add(btnLogout);

        JPanel centerPagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        centerPagePanel.setBackground(new Color(240, 242, 247));

        btnPrevPage = new JButton("←");
        btnNextPage = new JButton("→");
        lblPageInfo = new JLabel("1 / 1");

        stylePageButton(btnPrevPage);
        stylePageButton(btnNextPage);

        lblPageInfo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPageInfo.setForeground(new Color(60, 60, 60));

        centerPagePanel.add(btnPrevPage);
        centerPagePanel.add(lblPageInfo);
        centerPagePanel.add(btnNextPage);

        bottomPanel.add(leftActionPanel, BorderLayout.WEST);
        bottomPanel.add(centerPagePanel, BorderLayout.CENTER);

        btnPrevPage.addActionListener(e -> goPreviousPage());
        btnNextPage.addActionListener(e -> goNextPage());
        btnBackHome.addActionListener(e -> backHome());
        btnLogout.addActionListener(e -> logout());

        return bottomPanel;
    }

    private void loadInitialData() {
        loadStudentHeaderInfo();
        loadClassDropdown();
        reloadQuizData();
    }

    private void loadStudentHeaderInfo() {
        String studentId = currentStudent != null ? safe(currentStudent.getStudentId()) : "";
        String studentName = currentStudent != null ? safe(currentStudent.getStudentName()) : "";
        String studentEmail = currentStudent != null ? safe(currentStudent.getStudentEmail()) : "";

        lblStudentInfo.setText("Student: " + studentName + " | ID: " + studentId + " | Email: " + studentEmail);
    }

    private void loadClassDropdown() {
        cboClass.removeAllItems();
        studentClasses = studentService.getStudentClasses(currentStudent);

        if (studentClasses == null || studentClasses.isEmpty()) {
            cboClass.addItem(new ClassComboItem("", "No class"));
            cboClass.setEnabled(false);
            return;
        }

        cboClass.setEnabled(true);

        for (ClassRoom classRoom : studentClasses) {
            cboClass.addItem(new ClassComboItem(
                    safe(classRoom.getClassId()),
                    safe(classRoom.getClassName())
            ));
        }

        if (cboClass.getItemCount() > 0) {
            cboClass.setSelectedIndex(0);
        }
    }

    private void searchAndReload() {
        currentPage = 1;
        reloadQuizData();
    }

    private void reloadQuizData() {
        ClassComboItem selectedClass = (ClassComboItem) cboClass.getSelectedItem();

        if (selectedClass == null || selectedClass.getClassId().trim().isEmpty()) {
            allQuizItems = new ArrayList<>();
            refreshQuizListPanel();
            return;
        }

        String keyword = txtSearch.getText() == null ? "" : txtSearch.getText().trim();
        allQuizItems = studentService.getStudentAssignedQuizzes(currentStudent, selectedClass.getClassId(), keyword);

        if (currentPage > getTotalPages()) {
            currentPage = getTotalPages();
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        refreshQuizListPanel();
    }

    private void refreshQuizListPanel() {
        quizListPanel.removeAll();

        List<StudentQuizDashboardItem> currentItems = getCurrentPageItems();

        if (currentItems.isEmpty()) {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setBackground(Color.WHITE);
            emptyPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(205, 215, 225)),
                    new EmptyBorder(30, 20, 30, 20)
            ));
            emptyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            JLabel emptyLabel = new JLabel("No quizzes found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            emptyLabel.setForeground(new Color(110, 110, 110));

            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            quizListPanel.add(emptyPanel);
        } else {
            for (StudentQuizDashboardItem item : currentItems) {
                quizListPanel.add(buildQuizCard(item));
                quizListPanel.add(Box.createVerticalStrut(12));
            }
        }

        lblPageInfo.setText(currentPage + " / " + getTotalPages());
        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < getTotalPages());

        quizListPanel.revalidate();
        quizListPanel.repaint();
    }

    private JPanel buildQuizCard(StudentQuizDashboardItem item) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(191, 210, 231));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(175, 194, 214)),
                new EmptyBorder(12, 14, 12, 14)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(191, 210, 231));

        JLabel lblQuizTitle = new JLabel("[" + safe(item.getQuizTitle()) + "]");
        lblQuizTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblQuizTitle.setForeground(new Color(20, 20, 20));

        JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 4));
        metaPanel.setBackground(new Color(191, 210, 231));

        JLabel lblTopic = new JLabel("Topic: " + safe(item.getTopic()));
        lblTopic.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel statusDot = new JPanel();
        statusDot.setPreferredSize(new Dimension(28, 12));
        statusDot.setBackground(getDotColor(item.getStatus()));
        statusDot.setBorder(BorderFactory.createLineBorder(getDotColor(item.getStatus())));

        JLabel lblQuestionCount = new JLabel(item.getQuestionCount() + " questions");
        lblQuestionCount.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblDuration = new JLabel("Duration: " + item.getDurationMinutes() + " mins");
        lblDuration.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblDueDate = new JLabel("Due: " + safe(item.getDueDateText()));
        lblDueDate.setFont(new Font("SansSerif", Font.PLAIN, 14));

        metaPanel.add(lblTopic);
        metaPanel.add(statusDot);
        metaPanel.add(lblQuestionCount);
        metaPanel.add(lblDuration);
        metaPanel.add(lblDueDate);

        infoPanel.add(lblQuizTitle);
        infoPanel.add(metaPanel);

        JButton btnAction = createStatusButton(item);
        btnAction.setPreferredSize(new Dimension(150, 42));

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnAction, BorderLayout.EAST);

        return card;
    }

    private JButton createStatusButton(StudentQuizDashboardItem item) {
        String status = safe(item.getStatus()).toUpperCase();

        JButton button = new JButton(status);
        button.setFont(new Font("SansSerif", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);

        switch (status) {
            case "COMPLETED":
                button.setBackground(new Color(46, 36, 190));
                button.setEnabled(item.isCanReview());
                button.addActionListener(e -> viewQuizResult(item));
                break;

            case "OVERDUE":
                button.setBackground(new Color(150, 150, 150));
                button.setEnabled(item.isCanReview());
                if (item.isCanReview()) {
                    button.addActionListener(e -> viewQuizResult(item));
                }
                break;

            default:
                button.setText("START");
                button.setBackground(new Color(103, 158, 93));
                button.setEnabled(item.isCanStart());
                button.addActionListener(e -> startQuiz(item));
                break;
        }

        return button;
    }

    private Color getDotColor(String status) {
        String normalized = safe(status).toUpperCase();

        switch (normalized) {
            case "COMPLETED":
                return new Color(88, 190, 96);
            case "OVERDUE":
                return new Color(210, 44, 24);
            default:
                return new Color(236, 210, 68);
        }
    }

    private int getTotalPages() {
        if (allQuizItems == null || allQuizItems.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) allQuizItems.size() / PAGE_SIZE);
    }

    private List<StudentQuizDashboardItem> getCurrentPageItems() {
        if (allQuizItems == null || allQuizItems.isEmpty()) {
            return new ArrayList<>();
        }

        int fromIndex = (currentPage - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, allQuizItems.size());

        if (fromIndex >= allQuizItems.size() || fromIndex < 0) {
            return new ArrayList<>();
        }

        return allQuizItems.subList(fromIndex, toIndex);
    }

    private void goPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            refreshQuizListPanel();
        }
    }

    private void goNextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            refreshQuizListPanel();
        }
    }

    private void startQuiz(StudentQuizDashboardItem item) {
        try {
            SwingUtilities.invokeLater(() -> {
                StudentQuizPage quizPage = new StudentQuizPage(currentStudent, item);
                quizPage.setVisible(true);
            });
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Start Quiz Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void viewQuizResult(StudentQuizDashboardItem item) {
        try {
            StudentQuizDAO quizDAO = new StudentQuizDAO();

            Integer attemptId = quizDAO.getLatestCompletedAttemptId(
                    currentStudent.getStudentId(),
                    item.getQuizId(),
                    item.getClassId()
            );

            if (attemptId == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No completed attempt found for this quiz.",
                        "View Result",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            StudentQuizResultSummary summary = studentQuizService.getQuizSummary(attemptId);

            SwingUtilities.invokeLater(() -> {
                StudentQuizResultPage resultPage =
                        new StudentQuizResultPage(currentStudent, item, summary);
                resultPage.setVisible(true);
            });
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "View Result Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void logout() {
        SessionManager.clearSession();
        SwingUtilities.invokeLater(() -> new HomePage().setVisible(true));
        dispose();
    }

    private void backHome() {
        SwingUtilities.invokeLater(() -> new HomePage().setVisible(true));
        dispose();
    }

    private void styleSmallButton(JButton button, Color bgColor, Color fgColor) {
        button.setPreferredSize(new Dimension(110, 34));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
    }

    private void stylePageButton(JButton button) {
        button.setPreferredSize(new Dimension(52, 34));
        button.setFocusPainted(false);
        button.setBackground(new Color(108, 169, 224));
        button.setForeground(new Color(35, 35, 35));
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private static class ClassComboItem {
        private final String classId;
        private final String className;

        public ClassComboItem(String classId, String className) {
            this.classId = classId;
            this.className = className;
        }

        public String getClassId() {
            return classId;
        }

        @Override
        public String toString() {
            if (classId == null || classId.trim().isEmpty()) {
                return className;
            }
            return className + " (" + classId + ")";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Student demoStudent = new Student(
                    "S001",
                    "Pham Gia Bao",
                    "giabao.student@gmail.com",
                    "123456"
            );
            new StudentDashboard(demoStudent).setVisible(true);
        });
    }
}