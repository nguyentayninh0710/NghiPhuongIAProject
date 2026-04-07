package QuizApplication.service;

import QuizApplication.dao.StudentQuizDAO;
import QuizApplication.model.Student;
import QuizApplication.model.StudentQuizDashboardItem;
import QuizApplication.model.StudentQuizQuestionItem;
import QuizApplication.model.StudentQuizResultSummary;
import QuizApplication.model.StudentQuizReviewItem;

import java.util.ArrayList;
import java.util.List;

public class StudentQuizService {

    private final StudentQuizDAO studentQuizDAO;

    public StudentQuizService() {
        this.studentQuizDAO = new StudentQuizDAO();
    }

    public int startQuiz(Student student, StudentQuizDashboardItem dashboardItem) {
        validateStartQuizInput(student, dashboardItem);

        String studentId = student.getStudentId().trim();
        String quizId = dashboardItem.getQuizId().trim();
        String classId = dashboardItem.getClassId().trim();

        if (studentQuizDAO.hasCompletedAttempt(studentId, quizId, classId)) {
            throw new IllegalStateException("This quiz has already been completed and cannot be retaken.");
        }

        Integer inProgressAttemptId = studentQuizDAO.getInProgressAttemptId(studentId, quizId, classId);
        if (inProgressAttemptId != null) {
            return inProgressAttemptId;
        }

        return studentQuizDAO.createAttempt(
                studentId,
                quizId,
                classId,
                dashboardItem.getDurationMinutes()
        );
    }

    public List<StudentQuizQuestionItem> loadQuizQuestions(int attemptId) {
        if (attemptId <= 0) {
            return new ArrayList<>();
        }
        return studentQuizDAO.getQuestionsForAttempt(attemptId);
    }

    public void saveAnswer(int attemptId, String questionId, String selectedOption) {
        if (attemptId <= 0) {
            throw new IllegalArgumentException("Invalid attempt id.");
        }
        if (questionId == null || questionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Question id is required.");
        }
        if (selectedOption == null || selectedOption.trim().isEmpty()) {
            throw new IllegalArgumentException("Selected option is required.");
        }

        String normalizedOption = selectedOption.trim().toUpperCase();
        if (!normalizedOption.matches("[ABCD]")) {
            throw new IllegalArgumentException("Selected option must be A, B, C, or D.");
        }

        studentQuizDAO.saveOrUpdateAnswer(attemptId, questionId.trim(), normalizedOption);
    }

    public boolean isAttemptStillInProgress(int attemptId) {
        return studentQuizDAO.isAttemptStillInProgress(attemptId);
    }

    public StudentQuizResultSummary submitQuiz(int attemptId) {
        if (attemptId <= 0) {
            throw new IllegalArgumentException("Invalid attempt id.");
        }
        return studentQuizDAO.submitAndGradeAttempt(attemptId, false);
    }

    public StudentQuizResultSummary markQuizAsOverdue(int attemptId) {
        if (attemptId <= 0) {
            throw new IllegalArgumentException("Invalid attempt id.");
        }
        return studentQuizDAO.submitAndGradeAttempt(attemptId, true);
    }

    public StudentQuizResultSummary getQuizSummary(int attemptId) {
        if (attemptId <= 0) {
            throw new IllegalArgumentException("Invalid attempt id.");
        }
        return studentQuizDAO.getAttemptSummary(attemptId);
    }

    public List<StudentQuizReviewItem> getQuizReviewItems(int attemptId) {
        if (attemptId <= 0) {
            return new ArrayList<>();
        }
        return studentQuizDAO.getAttemptReviewItems(attemptId);
    }

    public int getAttemptDurationMinutes(int attemptId) {
        if (attemptId <= 0) {
            return 0;
        }
        return studentQuizDAO.getAttemptDurationMinutes(attemptId);
    }

    private void validateStartQuizInput(Student student, StudentQuizDashboardItem dashboardItem) {
        if (student == null || student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("Student session is invalid.");
        }

        if (dashboardItem == null) {
            throw new IllegalArgumentException("Quiz item is required.");
        }

        if (dashboardItem.getQuizId() == null || dashboardItem.getQuizId().trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz id is required.");
        }

        if (dashboardItem.getClassId() == null || dashboardItem.getClassId().trim().isEmpty()) {
            throw new IllegalArgumentException("Class id is required.");
        }

        if (!dashboardItem.isCanStart()) {
            throw new IllegalStateException("This quiz cannot be started.");
        }
    }
}