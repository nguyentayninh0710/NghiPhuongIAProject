package QuizApplication.service;

import QuizApplication.dao.QuizDAO;
import QuizApplication.model.Assignment;
import QuizApplication.model.ClassRoom;
import QuizApplication.model.Quiz;
import QuizApplication.model.QuizTag;
import QuizApplication.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class QuizService {

    private final QuizDAO quizDAO;

    public QuizService() {
        this.quizDAO = new QuizDAO();
    }

    public List<QuizTag> getAllQuizTags() {
        return quizDAO.getAllQuizTags();
    }

    public List<ClassRoom> getClassesByTeacher(String teacherId) {
        if (teacherId == null || teacherId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return quizDAO.getClassesByTeacher(teacherId.trim());
    }

    public String createQuizAndAssignToClass(Teacher teacher,
                                             String quizTitle,
                                             int level,
                                             int quizTagId,
                                             String classId) {

        if (teacher == null) {
            throw new IllegalArgumentException("Teacher session is missing.");
        }

        String teacherId = safeTrim(teacher.getTeacherId());
        String normalizedTitle = safeTrim(quizTitle);
        String normalizedClassId = safeTrim(classId);

        if (teacherId.isEmpty()) {
            throw new IllegalArgumentException("Teacher ID is missing.");
        }

        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException("Quiz title must not be empty.");
        }

        if (normalizedClassId.isEmpty()) {
            throw new IllegalArgumentException("Class must be selected.");
        }

        if (quizTagId <= 0) {
            throw new IllegalArgumentException("Quiz tag is invalid.");
        }

        if (level < 1 || level > 3) {
            throw new IllegalArgumentException("Quiz level is invalid.");
        }

        if (quizDAO.existsQuizTitleForTeacher(normalizedTitle, teacherId)) {
            throw new IllegalArgumentException("This quiz title already exists for the current teacher.");
        }

        Quiz quiz = new Quiz();
        quiz.setQuizTitle(normalizedTitle);
        quiz.setLevel(level);
        quiz.setQuizTagId(quizTagId);
        quiz.setTeacherId(teacherId);

        Assignment assignment = new Assignment();
        assignment.setClassId(normalizedClassId);

        String createdQuizId = quizDAO.createQuizAndAssignToClass(quiz, assignment);

        if (createdQuizId == null || createdQuizId.trim().isEmpty()) {
            throw new IllegalStateException("Failed to create quiz in database.");
        }

        return createdQuizId;
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }
}