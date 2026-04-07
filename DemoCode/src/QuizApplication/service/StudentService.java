package QuizApplication.service;

import QuizApplication.dao.StudentDAO;
import QuizApplication.model.ClassRoom;
import QuizApplication.model.Student;
import QuizApplication.model.StudentLoginResult;
import QuizApplication.model.StudentQuizDashboardItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public StudentLoginResult loginStudent(String email, String password) {
        String normalizedEmail = email == null ? "" : email.trim();
        String normalizedPassword = password == null ? "" : password.trim();

        if (normalizedEmail.isEmpty()) {
            return new StudentLoginResult(false, "Email not empty", null);
        }

        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", normalizedEmail)) {
            return new StudentLoginResult(false, "Email invalid", null);
        }

        if (normalizedPassword.isEmpty()) {
            return new StudentLoginResult(false, "Password not empty", null);
        }

        Student student = studentDAO.findByEmailAndPassword(normalizedEmail, normalizedPassword);

        if (student == null) {
            return new StudentLoginResult(false, "Incorrect email or password!", null);
        }

        return new StudentLoginResult(true, "Login successfully", student);
    }

    public List<ClassRoom> getStudentClasses(Student student) {
        if (student == null || student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            return new ArrayList<>();
        }
        return studentDAO.getClassesByStudent(student.getStudentId().trim());
    }

    public List<StudentQuizDashboardItem> getStudentAssignedQuizzes(
            Student student, String classId, String keyword) {
        if (student == null || student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            return new ArrayList<>();
        }
        if (classId == null || classId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return studentDAO.getAssignedQuizzesByStudentAndClass(
                student.getStudentId().trim(),
                classId.trim(),
                keyword == null ? "" : keyword.trim()
        );
    }
}