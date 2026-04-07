package QuizApplication.ui;
import javax.swing.plaf.basic.BasicButtonUI;
import QuizApplication.model.Teacher;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ResultsDashboard extends JFrame {

    private final Teacher currentTeacher;

    private JComboBox<String> cbClassFilter;
    private JComboBox<String> cbScoreFilter;
    private DatePicker dpStartDate;
    private DatePicker dpEndDate;
    private JTextField txtSearch;

    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JButton btnSearch;
    private JButton btnReset;
    private JButton btnPrevPage;
    private JButton btnNextPage;
    private JButton btnBack;

    private JLabel lblPageInfo;

    private final List<Object[]> allRows = new ArrayList<>();
    private List<Object[]> filteredRows = new ArrayList<>();

    private int currentPage = 1;
    private final int rowsPerPage = 6;

    public ResultsDashboard(Teacher teacher) {
        this.currentTeacher = teacher;
        initComponents();
        loadSampleData();
        applyFilters();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Results Dashboard");
        setSize(980, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(233, 235, 241));
        setContentPane(mainPanel);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(980, 110));
        headerPanel.setBackground(new Color(122, 17, 0));

        JLabel lblTitle = new JLabel("Results Dashboard", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblTitle.setBorder(new EmptyBorder(20, 10, 20, 10));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ===== BODY =====
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(new Color(233, 235, 241));
        bodyPanel.setLayout(null);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        // ===== FILTER BAR =====
        cbClassFilter = new JComboBox<>();
        cbClassFilter.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbClassFilter.setBounds(110, 35, 130, 32);
        bodyPanel.add(cbClassFilter);

        cbScoreFilter = new JComboBox<>();
        cbScoreFilter.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbScoreFilter.setBounds(255, 35, 130, 32);
        bodyPanel.add(cbScoreFilter);

        DatePickerSettings startSettings = new DatePickerSettings();
        startSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        startSettings.setAllowKeyboardEditing(true);
        startSettings.setSizeTextFieldMinimumWidth(120);

        dpStartDate = new DatePicker(startSettings);
        dpStartDate.setBounds(400, 35, 150, 32);
        dpStartDate.getComponentToggleCalendarButton().setText("📅");
        bodyPanel.add(dpStartDate);

        DatePickerSettings endSettings = new DatePickerSettings();
        endSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        endSettings.setAllowKeyboardEditing(true);
        endSettings.setSizeTextFieldMinimumWidth(120);

        dpEndDate = new DatePicker(endSettings);
        dpEndDate.setBounds(565, 35, 150, 32);
        dpEndDate.getComponentToggleCalendarButton().setText("📅");
        bodyPanel.add(dpEndDate);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtSearch.setBounds(110, 85, 380, 34);
        bodyPanel.add(txtSearch);

        btnSearch = new JButton("Search");
        styleButton(btnSearch, new Color(122, 17, 0), 14);
        btnSearch.setBounds(510, 85, 90, 34);
        bodyPanel.add(btnSearch);

        btnReset = new JButton("Reset");
        styleButton(btnReset, new Color(120, 120, 120), 14);
        btnReset.setBounds(615, 85, 90, 34);
        bodyPanel.add(btnReset);

        // ===== TABLE =====
        String[] columnNames = {"Student", "Quiz", "Score", "Time", "Status"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultsTable.setRowHeight(32);
        resultsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        resultsTable.getTableHeader().setBackground(new Color(185, 207, 231));
        resultsTable.setSelectionBackground(new Color(210, 225, 240));
        resultsTable.setGridColor(new Color(220, 220, 220));
        resultsTable.setFillsViewportHeight(true);

        sorter = new TableRowSorter<>(tableModel);
        resultsTable.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(110, 150, 760, 300);
        bodyPanel.add(scrollPane);

        // ===== PAGINATION =====
        btnPrevPage = new JButton("←");
        styleButton(btnPrevPage, new Color(214, 135, 92), 18);
        btnPrevPage.setBounds(390, 490, 45, 36);
        bodyPanel.add(btnPrevPage);

        lblPageInfo = new JLabel("1 / 1", SwingConstants.CENTER);
        lblPageInfo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPageInfo.setForeground(new Color(40, 65, 100));
        lblPageInfo.setBounds(445, 490, 90, 36);
        bodyPanel.add(lblPageInfo);

        btnNextPage = new JButton("→");
        styleButton(btnNextPage, new Color(214, 135, 92), 18);
        btnNextPage.setBounds(545, 490, 45, 36);
        bodyPanel.add(btnNextPage);

        // ===== BACK =====
        btnBack = new JButton("Back");
        styleButton(btnBack, new Color(120, 120, 120), 14);
        btnBack.setBounds(760, 490, 110, 36);
        bodyPanel.add(btnBack);

        registerEvents();
        initFilterData();
    }

    private void styleButton(JButton button, Color bgColor, int fontSize) {
        button.setUI(new BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
    }

    private void registerEvents() {
        btnSearch.addActionListener(e -> {
            currentPage = 1;
            applyFilters();
        });

        btnReset.addActionListener(e -> resetFilters());

        btnPrevPage.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                refreshTablePage();
            }
        });

        btnNextPage.addActionListener(e -> {
            int totalPages = getTotalPages();
            if (currentPage < totalPages) {
                currentPage++;
                refreshTablePage();
            }
        });

        btnBack.addActionListener(e -> {
            new TeacherDashboard(currentTeacher);
            dispose();
        });
    }

    private void initFilterData() {
        cbClassFilter.addItem("All Classes");
        cbClassFilter.addItem("SE06201");
        cbClassFilter.addItem("CS101");
        cbClassFilter.addItem("DB202");

        cbScoreFilter.addItem("All Scores");
        cbScoreFilter.addItem(">= 90");
        cbScoreFilter.addItem(">= 80");
        cbScoreFilter.addItem(">= 70");
        cbScoreFilter.addItem("< 70");
    }

    private void loadSampleData() {
        allRows.clear();

        allRows.add(new Object[]{"An Nguyen", "Java Basics Quiz", 95, "25 min", "Completed", "SE06201", LocalDate.of(2026, 4, 2)});
        allRows.add(new Object[]{"Binh Tran", "OOP Quiz", 82, "31 min", "Completed", "SE06201", LocalDate.of(2026, 4, 3)});
        allRows.add(new Object[]{"Chi Le", "SQL Fundamentals", 76, "29 min", "Completed", "DB202", LocalDate.of(2026, 4, 4)});
        allRows.add(new Object[]{"Dung Pham", "Java Basics Quiz", 65, "35 min", "Late", "CS101", LocalDate.of(2026, 4, 5)});
        allRows.add(new Object[]{"Em Huynh", "Collections Quiz", 91, "22 min", "Completed", "SE06201", LocalDate.of(2026, 4, 6)});
        allRows.add(new Object[]{"Giang Vo", "Array Quiz", 88, "27 min", "Completed", "CS101", LocalDate.of(2026, 4, 7)});
        allRows.add(new Object[]{"Huy Do", "Loop Quiz", 73, "30 min", "Completed", "CS101", LocalDate.of(2026, 4, 8)});
        allRows.add(new Object[]{"Khanh Bui", "SQL Fundamentals", 69, "33 min", "Late", "DB202", LocalDate.of(2026, 4, 9)});
        allRows.add(new Object[]{"Linh Ngo", "OOP Quiz", 97, "21 min", "Completed", "SE06201", LocalDate.of(2026, 4, 10)});
        allRows.add(new Object[]{"Minh Truong", "Exception Quiz", 84, "26 min", "Completed", "CS101", LocalDate.of(2026, 4, 11)});
        allRows.add(new Object[]{"Nam Phan", "Java Basics Quiz", 78, "28 min", "Completed", "SE06201", LocalDate.of(2026, 4, 12)});
        allRows.add(new Object[]{"Phuc Ly", "Database Quiz", 92, "24 min", "Completed", "DB202", LocalDate.of(2026, 4, 13)});
    }

    private void applyFilters() {
        filteredRows = new ArrayList<>();

        String selectedClass = (String) cbClassFilter.getSelectedItem();
        String selectedScore = (String) cbScoreFilter.getSelectedItem();
        String keyword = txtSearch.getText().trim().toLowerCase();

        LocalDate startDate = dpStartDate.getDate();
        LocalDate endDate = dpEndDate.getDate();

        for (Object[] row : allRows) {
            String student = row[0].toString();
            String quiz = row[1].toString();
            int score = (int) row[2];
            String classCode = row[5].toString();
            LocalDate quizDate = (LocalDate) row[6];

            boolean match = true;

            // Filter class
            if (!"All Classes".equals(selectedClass) && !classCode.equals(selectedClass)) {
                match = false;
            }

            // Filter score
            if (">= 90".equals(selectedScore) && score < 90) {
                match = false;
            } else if (">= 80".equals(selectedScore) && score < 80) {
                match = false;
            } else if (">= 70".equals(selectedScore) && score < 70) {
                match = false;
            } else if ("< 70".equals(selectedScore) && score >= 70) {
                match = false;
            }

            // Filter start date
            if (startDate != null && quizDate.isBefore(startDate)) {
                match = false;
            }

            // Filter end date
            if (endDate != null && quizDate.isAfter(endDate)) {
                match = false;
            }

            // Search specific
            if (!keyword.isEmpty()) {
                boolean searchMatch = student.toLowerCase().contains(keyword)
                        || quiz.toLowerCase().contains(keyword)
                        || row[4].toString().toLowerCase().contains(keyword);
                if (!searchMatch) {
                    match = false;
                }
            }

            if (match) {
                filteredRows.add(row);
            }
        }

        currentPage = 1;
        refreshTablePage();
    }

    private void refreshTablePage() {
        tableModel.setRowCount(0);

        int totalPages = getTotalPages();
        if (totalPages == 0) {
            totalPages = 1;
        }

        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, filteredRows.size());

        for (int i = startIndex; i < endIndex; i++) {
            Object[] row = filteredRows.get(i);

            Vector<Object> rowData = new Vector<>();
            rowData.add(row[0]); // Student
            rowData.add(row[1]); // Quiz
            rowData.add(row[2]); // Score
            rowData.add(row[3]); // Time
            rowData.add(row[4]); // Status

            tableModel.addRow(rowData);
        }

        lblPageInfo.setText(currentPage + " / " + totalPages);

        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < totalPages);
    }

    private int getTotalPages() {
        if (filteredRows.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) filteredRows.size() / rowsPerPage);
    }

    private void resetFilters() {
        cbClassFilter.setSelectedIndex(0);
        cbScoreFilter.setSelectedIndex(0);
        dpStartDate.clear();
        dpEndDate.clear();
        txtSearch.setText("");
        currentPage = 1;
        applyFilters();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Teacher demoTeacher = new Teacher("T001", "Nguyen Minh Anh", "minhanh.teacher@gmail.com");
            new ResultsDashboard(demoTeacher);
        });
    }
}