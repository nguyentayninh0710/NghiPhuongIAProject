package QuizApplication.ui;

import QuizApplication.model.ClassRoom;
import QuizApplication.model.QuizTag;
import QuizApplication.model.Teacher;
import QuizApplication.service.QuizService;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreateNewQuiz extends JFrame {

    private final Teacher currentTeacher;
    private final QuizService quizService;

    private JTextField txtQuizName;
    private JTextField txtDuration;

    private JComboBox<String> cbQuizTags;
    private JComboBox<String> cbAssignedClasses;

    private final Map<String, Integer> quizTagMap = new LinkedHashMap<>();
    private final Map<String, String> classMap = new LinkedHashMap<>();

    private JRadioButton rbHard;
    private JRadioButton rbMedium;
    private JRadioButton rbEasy;

    private DatePicker dpStartDate;
    private DatePicker dpEndDate;

    private JButton btnCreate;
    private JButton btnBack;

    public CreateNewQuiz(Teacher teacher) {
        this.currentTeacher = teacher;
        this.quizService = new QuizService();

        initComponents();
        loadDropdownData();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Create New Quiz");
        setSize(860, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(220, 223, 237));
        setContentPane(mainPanel);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(860, 110));
        headerPanel.setBackground(new Color(43, 31, 115));

        JLabel lblTitle = new JLabel("Create New Quiz", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblTitle.setBorder(new EmptyBorder(20, 10, 20, 10));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(new Color(201, 207, 231));
        bodyPanel.setLayout(null);

        JLabel lblQuizName = new JLabel("Quiz Name");
        lblQuizName.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblQuizName.setForeground(new Color(44, 74, 112));
        lblQuizName.setBounds(70, 30, 150, 30);
        bodyPanel.add(lblQuizName);

        txtQuizName = new JTextField();
        txtQuizName.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtQuizName.setBounds(70, 60, 420, 32);
        bodyPanel.add(txtQuizName);

        JLabel lblLevel = new JLabel("Level");
        lblLevel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblLevel.setForeground(new Color(44, 74, 112));
        lblLevel.setBounds(530, 30, 100, 30);
        bodyPanel.add(lblLevel);

        rbHard = new JRadioButton("Hard");
        rbMedium = new JRadioButton("Medium");
        rbEasy = new JRadioButton("Easy");

        styleLevelRadio(rbHard);
        styleLevelRadio(rbMedium);
        styleLevelRadio(rbEasy);

        rbHard.setBounds(530, 60, 70, 30);
        rbMedium.setBounds(610, 60, 90, 30);
        rbEasy.setBounds(710, 60, 70, 30);

        ButtonGroup levelGroup = new ButtonGroup();
        levelGroup.add(rbHard);
        levelGroup.add(rbMedium);
        levelGroup.add(rbEasy);
        rbEasy.setSelected(true);

        bodyPanel.add(rbHard);
        bodyPanel.add(rbMedium);
        bodyPanel.add(rbEasy);

        JLabel lblTags = new JLabel("Quiz Tag");
        lblTags.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblTags.setForeground(new Color(44, 74, 112));
        lblTags.setBounds(70, 110, 150, 30);
        bodyPanel.add(lblTags);

        cbQuizTags = new JComboBox<>();
        cbQuizTags.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cbQuizTags.setBounds(70, 140, 420, 32);
        bodyPanel.add(cbQuizTags);

        JLabel lblStartDate = new JLabel("Start Date");
        lblStartDate.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblStartDate.setForeground(new Color(44, 74, 112));
        lblStartDate.setBounds(70, 190, 150, 30);
        bodyPanel.add(lblStartDate);

        DatePickerSettings startSettings = new DatePickerSettings();
        startSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        startSettings.setAllowKeyboardEditing(true);
        startSettings.setSizeTextFieldMinimumWidth(140);

        dpStartDate = new DatePicker(startSettings);
        dpStartDate.setBounds(70, 220, 210, 32);
        dpStartDate.setDateToToday();
        dpStartDate.getComponentDateTextField().setToolTipText("Format: yyyy-MM-dd");
        dpStartDate.getComponentToggleCalendarButton().setText("📅");
        dpStartDate.getComponentToggleCalendarButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        bodyPanel.add(dpStartDate);

        JLabel lblEndDate = new JLabel("End Date");
        lblEndDate.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblEndDate.setForeground(new Color(44, 74, 112));
        lblEndDate.setBounds(330, 190, 150, 30);
        bodyPanel.add(lblEndDate);

        DatePickerSettings endSettings = new DatePickerSettings();
        endSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        endSettings.setAllowKeyboardEditing(true);
        endSettings.setSizeTextFieldMinimumWidth(140);

        dpEndDate = new DatePicker(endSettings);
        dpEndDate.setBounds(330, 220, 210, 32);
        dpEndDate.setDate(LocalDate.now().plusDays(7));
        dpEndDate.getComponentDateTextField().setToolTipText("Format: yyyy-MM-dd");
        dpEndDate.getComponentToggleCalendarButton().setText("📅");
        dpEndDate.getComponentToggleCalendarButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        bodyPanel.add(dpEndDate);

        JLabel lblDuration = new JLabel("Duration (minutes)");
        lblDuration.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblDuration.setForeground(new Color(44, 74, 112));
        lblDuration.setBounds(70, 275, 180, 30);
        bodyPanel.add(lblDuration);

        txtDuration = new JTextField();
        txtDuration.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtDuration.setBounds(70, 305, 170, 32);
        bodyPanel.add(txtDuration);

        JLabel lblAssignedClasses = new JLabel("Assigned Class");
        lblAssignedClasses.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblAssignedClasses.setForeground(new Color(44, 74, 112));
        lblAssignedClasses.setBounds(70, 390, 180, 30);
        bodyPanel.add(lblAssignedClasses);

        cbAssignedClasses = new JComboBox<>();
        cbAssignedClasses.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cbAssignedClasses.setBounds(70, 420, 420, 34);
        bodyPanel.add(cbAssignedClasses);

        btnBack = new JButton("Back");
        styleActionButton(btnBack, new Color(120, 120, 120));
        btnBack.setBounds(510, 470, 110, 46);
        bodyPanel.add(btnBack);

        btnCreate = new JButton("Create");
        styleActionButton(btnCreate, new Color(43, 31, 115));
        btnCreate.setBounds(640, 470, 130, 46);
        bodyPanel.add(btnCreate);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        registerEvents();
    }

    private void loadDropdownData() {
        loadQuizTags();
        loadAssignedClasses();
    }

    private void loadQuizTags() {
        cbQuizTags.removeAllItems();
        quizTagMap.clear();

        List<QuizTag> quizTags = quizService.getAllQuizTags();

        for (QuizTag quizTag : quizTags) {
            String display = quizTag.getQuizTagDescription();
            cbQuizTags.addItem(display);
            quizTagMap.put(display, quizTag.getQuizTagId());
        }

        if (cbQuizTags.getItemCount() == 0) {
            cbQuizTags.addItem("No quiz tag available");
        }
    }

    private void loadAssignedClasses() {
        cbAssignedClasses.removeAllItems();
        classMap.clear();

        if (currentTeacher == null || currentTeacher.getTeacherId() == null) {
            cbAssignedClasses.addItem("No class available");
            return;
        }

        List<ClassRoom> classes = quizService.getClassesByTeacher(currentTeacher.getTeacherId());

        for (ClassRoom classRoom : classes) {
            String display = classRoom.getClassId() + " - " + classRoom.getClassName();
            cbAssignedClasses.addItem(display);
            classMap.put(display, classRoom.getClassId());
        }

        if (cbAssignedClasses.getItemCount() == 0) {
            cbAssignedClasses.addItem("No class available");
        }
    }

    private void styleLevelRadio(JRadioButton radioButton) {
        radioButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        radioButton.setBackground(new Color(201, 207, 231));
        radioButton.setFocusPainted(false);
    }

    private void styleActionButton(JButton button, Color bgColor) {
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void registerEvents() {
        btnBack.addActionListener(e -> {
            new TeacherDashboard(currentTeacher);
            dispose();
        });

        btnCreate.addActionListener(e -> createQuizAndOpenAddQuiz());
    }

    private void createQuizAndOpenAddQuiz() {
        String quizName = txtQuizName.getText().trim();
        String duration = txtDuration.getText().trim();

        String selectedQuizTagText = (String) cbQuizTags.getSelectedItem();
        String selectedClassText = (String) cbAssignedClasses.getSelectedItem();

        Integer selectedQuizTagId = quizTagMap.get(selectedQuizTagText);
        String selectedClassId = classMap.get(selectedClassText);

        if (quizName.isEmpty() || duration.isEmpty() || selectedQuizTagId == null || selectedClassId == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all quiz information before continuing.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (dpStartDate.getDate() == null || dpEndDate.getDate() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select both start date and end date.",
                    "Missing Date",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (dpEndDate.getDate().isBefore(dpStartDate.getDate())) {
            JOptionPane.showMessageDialog(
                    this,
                    "End date must be after or equal to start date.",
                    "Invalid Date Range",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!duration.matches("\\d+")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Duration must be a valid number of minutes.",
                    "Invalid Duration",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int levelValue;
        String levelText;

        if (rbHard.isSelected()) {
            levelValue = 3;
            levelText = "Hard";
        } else if (rbMedium.isSelected()) {
            levelValue = 2;
            levelText = "Medium";
        } else {
            levelValue = 1;
            levelText = "Easy";
        }

        String startDate = dpStartDate.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = dpEndDate.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            String createdQuizId = quizService.createQuizAndAssignToClass(
                    currentTeacher,
                    quizName,
                    levelValue,
                    selectedQuizTagId,
                    selectedClassId
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Quiz created successfully with ID: " + createdQuizId,
                    "Create Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new AddQuiz(
                    currentTeacher,
                    createdQuizId,
                    quizName,
                    selectedQuizTagText,
                    levelText,
                    startDate,
                    endDate,
                    duration,
                    selectedClassText
            );

            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Teacher demoTeacher = new Teacher("T001", "Nguyen Minh Anh", "minhanh.teacher@gmail.com");
            new CreateNewQuiz(demoTeacher);
        });
    }
}